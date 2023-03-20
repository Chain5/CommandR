package it.emgtech.commandr.match.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.match.model.MatchTransformer;
import it.emgtech.commandr.match.model.MatchesResponse;
import it.emgtech.commandr.match.model.PlayerMatchDto;
import it.emgtech.commandr.match.model.entity.GameTable;
import it.emgtech.commandr.match.model.entity.PlayerMatch;
import it.emgtech.commandr.match.repository.IMatchRepository;
import it.emgtech.commandr.match.service.IGameTableService;
import it.emgtech.commandr.match.service.IMatchService;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.util.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements IMatchService {

    private static final Integer MIN_PLAYER_FOR_TOURNAMENT = 4;

    private final IMatchRepository matchRepository;
    private final IGameTableService gameTableService;
    private final ITournamentScoreBoardService tournamentScoreBoardService;
    private final MatchTransformer transformer;

    @Override
    public MatchesResponse generateMatches( Long tournamentId ) {
        final List<Long> tournamentPlayerIds = tournamentScoreBoardService.getPlayersByTournamentId( tournamentId );
        final List<GameTable> tournamentMatches = gameTableService.getGameTablesByTournamentId( tournamentId );

        // Common checks before generating new matches
        commonChecks( tournamentPlayerIds, tournamentMatches );

        // create and save new matches
        return generateMatches( tournamentId, tournamentPlayerIds, tournamentMatches );
    }

    private void commonChecks( List<Long> playerList, List<GameTable> remainingMatches ) {

        if ( playerList.isEmpty() ) {
            throw new ApiRequestException( MessageResponse.NO_PLAYER_IN_TOURNAMENT );
        }

        if ( playerList.size() < MIN_PLAYER_FOR_TOURNAMENT ) {
            throw new ApiRequestException( MessageResponse.NOT_ENOUGH_PLAYERS );
        }

        if ( remainingMatches.stream().anyMatch( match -> !match.isFinished() ) ) {
            throw new ApiRequestException( MessageResponse.ALL_MATCHES_NOT_COCLUDED );
        }
    }

    private MatchesResponse generateMatches( Long tournamentId, List<Long> tournamentPlayers, List<GameTable> tournamentMatches ) {
        if ( tournamentMatches.isEmpty() ) {
            // first of all, create new game tables
            List<GameTable> createdGames = createNewGameTables( tournamentId, tournamentPlayers.size() );
            return createNewMatches( createdGames, tournamentId, true );
        } else {
            return createNewMatches( tournamentMatches, tournamentId, false );
        }
    }

    private List<GameTable> createNewGameTables( Long tournamentId, int numberOfPlayers ) {
        int numberOfGameTable = numberOfPlayers / MIN_PLAYER_FOR_TOURNAMENT;
        if ( numberOfPlayers % MIN_PLAYER_FOR_TOURNAMENT == 3 ) {
            numberOfGameTable++;
        }
        return gameTableService.saveGameTables( tournamentId, numberOfGameTable );
    }

    private MatchesResponse createNewMatches( List<GameTable> createdGames, Long tournamentId, boolean isTournamentJustStarted ) {
        List<PlayerMatch> matchesToInsert = new ArrayList<>();

        final Long[][] autogeneratedMatches = isTournamentJustStarted ?
                generateMatchesFromPlayerList( createdGames ) :
                generateMatchesFromPreviousMatches( createdGames );

        final List<Long> createdGamesIds = createdGames.stream().map( GameTable::getId ).collect( Collectors.toList() );

        for ( int gameIndex = 0; gameIndex < createdGamesIds.size(); gameIndex++ ) {
            Long[] currentMatchPlayers = autogeneratedMatches[gameIndex];
            for ( Long currentMatchPlayer : currentMatchPlayers ) {
                PlayerMatch playerMatch = new PlayerMatch();
                playerMatch.setId( null );
                playerMatch.setScore( 0 );
                playerMatch.setApprovedScore( false );
                playerMatch.setPlayerId( currentMatchPlayer );
                playerMatch.setGameTableId( createdGamesIds.get( gameIndex ) );

                matchesToInsert.add( playerMatch );
            }
        }

        MatchesResponse matchesResponse = transformer.transform( matchesToInsert, tournamentId );

        matchRepository.saveAll( matchesToInsert );

        return matchesResponse;
    }

    private Long[][] generateMatchesFromPlayerList( List<GameTable> gameTables ) {
        final List<PlayerMatchDto> playerMatch = retrievePlayerFromGameTable( gameTables );
        Collections.shuffle( playerMatch );
        final int playerNumber = playerMatch.size();
        int numberOfTables = playerNumber % MIN_PLAYER_FOR_TOURNAMENT;
        Long[][] tables = new Long[numberOfTables][MIN_PLAYER_FOR_TOURNAMENT];

        int k = 0;
        for ( int i = 0; i < numberOfTables; i++ ) {
            for ( int j = 0; j < MIN_PLAYER_FOR_TOURNAMENT; j++ ) {
                if ( k < playerNumber ) {
                    tables[i][j] = playerMatch.get( k ).getPlayerId();
                    k++;
                    continue;
                }
                break;
            }
        }

        //TODO: update playerMatch values

        return tables;
    }

    private Long[][] generateMatchesFromPreviousMatches( List<GameTable> gameTables ) {
        final List<PlayerMatchDto> playerMatch = retrievePlayerFromGameTable( gameTables );
        final int tableNumber = gameTables.size();
        Long[][] table = new Long[tableNumber][MIN_PLAYER_FOR_TOURNAMENT];
        Collections.shuffle( playerMatch );

        HashMap<Integer, List<PlayerMatchDto>> newMatches = new HashMap<>();

        for ( int tn = 0; tn < tableNumber; tn++ ) {
            newMatches.put( tn, new ArrayList<>() );
            for ( PlayerMatchDto player : playerMatch ) {
                List<PlayerMatchDto> playerIntoMatch = newMatches.get( tn );
                if ( playerIntoMatch.stream().noneMatch( p -> p.hasMet( player.getPlayerId() ) ) ) {
                    addPlayerAndUpdateMetList( newMatches.get( tn ), player );
                    playerMatch.remove( player );
                }
            }
        }

        for ( Map.Entry<Integer, List<PlayerMatchDto>> entry : newMatches.entrySet() ) {
            for ( int i = 0; i < entry.getValue().size(); i++ ) {
                table[entry.getKey()][i] = entry.getValue().get( i ).getPlayerId();
            }
        }

        // TODO: update GameTable isFinished field

        return table;
    }

    private List<PlayerMatchDto> retrievePlayerFromGameTable( List<GameTable> gameTables ) {
        final List<Long> gameTablesIds = gameTables.stream().map( GameTable::getId ).collect( Collectors.toList() );
        final List<PlayerMatch> tournamentPlayer = matchRepository.getMatchesByGameTableIdIn( gameTablesIds );
        return tournamentPlayer.stream().map( PlayerMatchDto::new ).collect( Collectors.toList() );
    }

    private void addPlayerAndUpdateMetList( List<PlayerMatchDto> list, PlayerMatchDto player ) {
        for ( PlayerMatchDto playerInList : list ) {
            playerInList.addPlayerToMetPlayerList( player.getPlayerId() );
        }
        //at the end, add the new player
        list.add( player );
    }

}
