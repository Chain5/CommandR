package it.emgtech.commandr.match.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.match.model.GenerateMatchRequest;
import it.emgtech.commandr.match.model.GetMatchRequest;
import it.emgtech.commandr.match.model.MatchTransformer;
import it.emgtech.commandr.match.model.MatchesResponse;
import it.emgtech.commandr.match.model.PlayerMatchDto;
import it.emgtech.commandr.match.model.entity.GameTable;
import it.emgtech.commandr.match.model.entity.PlayerMatch;
import it.emgtech.commandr.match.repository.IMatchRepository;
import it.emgtech.commandr.match.service.IGameTableService;
import it.emgtech.commandr.match.service.IMatchService;
import it.emgtech.commandr.player.model.entity.Player;
import it.emgtech.commandr.player.service.IPlayerService;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.tournament.service.ITournamentService;
import it.emgtech.commandr.util.JsonConverter;
import it.emgtech.commandr.util.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements IMatchService {

    // TODO: update -> number of players to start a tournament has to be multiple of four.

    private static final Integer MIN_PLAYER_FOR_TOURNAMENT = 4;
    private static final Integer MAX_MATCH_GENERATION = 4;

    private final IMatchRepository repository;
    private final IGameTableService gameTableService;
    private final ITournamentScoreBoardService tournamentScoreBoardService;
    private final IPlayerService playerService;
    private final ITournamentService tournamentService;
    private final MatchTransformer transformer;

    @Override
    public MatchesResponse generateMatches( GenerateMatchRequest request ) {
        final Long tournamentId = request.getTournamentId();
        final List<Long> tournamentPlayerIds = tournamentScoreBoardService.getPlayersByTournamentId( tournamentId );
        final List<GameTable> tournamentMatches = gameTableService.getGameTablesByTournamentId( tournamentId );

        // Common checks before generating new matches
        commonChecks( tournamentPlayerIds, tournamentMatches );

        // create and save new matches
        return generateMatches( tournamentId, tournamentPlayerIds, tournamentMatches );
    }

    @Override
    public PlayerMatch updateOrInsertPlayerMatch( PlayerMatch playerMatch, Long tournamentId ) {
        List<PlayerMatch> playerMatchList = repository.findByPlayerId( playerMatch.getPlayerId() );
        PlayerMatch playerMatchFound = playerMatchList.stream().filter( el -> el.getGameTable().getTournamentId().equals( tournamentId ) ).findFirst().orElse( null );

        if ( playerMatchFound != null ) {
            mergeMetPlayers( playerMatchFound, playerMatch.getMetPlayers() );
        } else {
            playerMatchFound = playerMatch;
            playerMatchFound.setId( null );
        }
        playerMatchFound.setScore( 0 );
        playerMatchFound.setApprovedScore( false );
        return repository.save( playerMatchFound );
    }

    @Override
    public MatchesResponse getMatches( GetMatchRequest request ) {
        return null; //TODO: implement
    }

    private void commonChecks( List<Long> playerList, List<GameTable> remainingMatches ) {

        if ( playerList.isEmpty() ) {
            throw new ApiRequestException( MessageResponse.NO_PLAYER_IN_TOURNAMENT );
        }

        if ( playerList.size() < MIN_PLAYER_FOR_TOURNAMENT ) {
            throw new ApiRequestException( MessageResponse.NOT_ENOUGH_PLAYERS );
        }

        // All match should be concluded
        if ( remainingMatches.stream().anyMatch( match -> !match.isFinished() ) ) {
            throw new ApiRequestException( MessageResponse.ALL_MATCHES_NOT_CONCLUDED );
        }

        // Check if exceeding max match generation (should be == tableNumber and 4 in case tableNumber > 4)
        int numberOfTable = remainingMatches.size();
        if ( numberOfTable > 0 ) {
            int generatedMatchCounter = remainingMatches.get( 0 ).getTournament().getGeneratedMatchCounter();
            if ( generatedMatchCounter >= MAX_MATCH_GENERATION || generatedMatchCounter >= remainingMatches.size() ) {
                throw new ApiRequestException( MessageResponse.MAX_MATCH_GENERATION_REACHED );
            }
        }
    }

    private MatchesResponse generateMatches( Long tournamentId, List<Long> tournamentPlayers, List<GameTable> tournamentMatches ) {
        if ( tournamentMatches.isEmpty() ) {
            // first of all, create new game tables
            List<GameTable> createdGames = createNewGameTables( tournamentId, tournamentPlayers.size() );
            return createNewMatches( createdGames, tournamentPlayers, true );
        } else {
            return createNewMatches( tournamentMatches, tournamentPlayers, false );
        }
    }

    private List<GameTable> createNewGameTables( Long tournamentId, int numberOfPlayers ) {
        //TODO: controlla il numero dei giocatori (devi escludere eventuali avanzi)
        int numberOfGameTable = numberOfPlayers / MIN_PLAYER_FOR_TOURNAMENT;
        if ( numberOfPlayers % MIN_PLAYER_FOR_TOURNAMENT == 3 ) {
            numberOfGameTable++;
        }
        return gameTableService.saveGameTables( tournamentId, numberOfGameTable );
    }

    private MatchesResponse createNewMatches( List<GameTable> createdGames, List<Long> tournamentPlayers, boolean isTournamentJustStarted ) {
        List<PlayerMatch> matchesToInsert = new ArrayList<>();

        // Generate a bi-dimensional array of matches
        final Long[][] autogeneratedMatches = isTournamentJustStarted ?
                generateMatchesFromPlayerList( tournamentPlayers ) :
                generateMatchesFromPreviousMatches( createdGames );

        // Creating a list of player match to insert
        for ( int gameIndex = 0; gameIndex < createdGames.size(); gameIndex++ ) {
            Long[] currentMatchPlayers = autogeneratedMatches[gameIndex];
            for ( Long currentMatchPlayer : currentMatchPlayers ) {
                if ( currentMatchPlayer == null ) continue;
                PlayerMatch playerMatch = new PlayerMatch();
                playerMatch.setId( null );
                playerMatch.setScore( 0 );
                playerMatch.setApprovedScore( false );
                playerMatch.setPlayerId( currentMatchPlayer );
                playerMatch.setGameTableId( createdGames.get( gameIndex ).getId() );
                playerMatch.setMetPlayers(
                        convertToString( Arrays.stream( currentMatchPlayers ).filter( Objects::nonNull ).filter( p -> !p.equals( currentMatchPlayer ) ).collect( Collectors.toList() ) ) );
                matchesToInsert.add( playerMatch );
            }
        }

        Long tournamentId = createdGames.get( 0 ).getTournamentId();
        // save new player matches
        savePlayerMatches( matchesToInsert, tournamentId );
        // increase generated_match_counter field by 1
        tournamentService.increaseGeneratedMatchCounter( tournamentId );

        // return the response
        List<Player> playerList = playerService.getPlayersByIds( tournamentPlayers );
        return transformer.transform( matchesToInsert, createdGames, playerList );
    }

    private void savePlayerMatches( List<PlayerMatch> matchesToInsert, Long tournamentId ) {
        for ( PlayerMatch player : matchesToInsert ) {
            updateOrInsertPlayerMatch( player, tournamentId );
        }
    }

    private Long[][] generateMatchesFromPlayerList( List<Long> playerIds ) {
        Collections.shuffle( playerIds );
        final int playerNumber = playerIds.size();
        int numberOfTables = playerNumber / MIN_PLAYER_FOR_TOURNAMENT; //TODO: better verify the number of tables
        Long[][] tables = new Long[numberOfTables][MIN_PLAYER_FOR_TOURNAMENT];

        int k = 0;
        for ( int i = 0; i < numberOfTables; i++ ) {
            for ( int j = 0; j < MIN_PLAYER_FOR_TOURNAMENT; j++ ) {
                if ( k < playerNumber ) {
                    tables[i][j] = playerIds.get( k );
                    k++;
                    continue;
                }
                break;
            }
        }
        return tables;
    }

    private Long[][] generateMatchesFromPreviousMatches( List<GameTable> gameTables ) {

        final List<PlayerMatchDto> playerMatch = retrievePlayerFromGameTable( gameTables );
        updateTournamentScoreBoard( gameTables.get( 0 ).getTournamentId(), playerMatch );

        final int numberOfTables = gameTables.size();
        Long[][] table = new Long[numberOfTables][MIN_PLAYER_FOR_TOURNAMENT];
        Collections.shuffle( playerMatch );

        HashMap<Integer, List<PlayerMatchDto>> newMatches = new HashMap<>();
        List<PlayerMatchDto> playerToRemove = new ArrayList<>();

        for ( int tableNumber = 0; tableNumber < numberOfTables; tableNumber++ ) {
            newMatches.put( tableNumber, new ArrayList<>() );
            playerMatch.removeAll( playerToRemove );
            for ( PlayerMatchDto player : playerMatch ) {
                List<PlayerMatchDto> playerIntoMatch = newMatches.get( tableNumber );
                if ( playerIntoMatch.size() == MIN_PLAYER_FOR_TOURNAMENT ) {
                    break;
                }
                if ( playerIntoMatch.stream().noneMatch( p -> p.hasMet( player.getPlayerId() ) ) ) {
                    newMatches.get( tableNumber ).add( player );
                    playerToRemove.add( player );
                }
            }
        }

        for ( Map.Entry<Integer, List<PlayerMatchDto>> entry : newMatches.entrySet() ) {
            for ( int i = 0; i < entry.getValue().size(); i++ ) {
                table[entry.getKey()][i] = entry.getValue().get( i ).getPlayerId();
            }
        }

        gameTableService.resetGameTableForNewMatches( gameTables.stream().map( GameTable::getId ).collect( Collectors.toList() ) );

        return table;
    }

    private void updateTournamentScoreBoard( Long tournamentId, List<PlayerMatchDto> player ) {
        tournamentScoreBoardService.updateScoreBoard( tournamentId, player );
    }

    private List<PlayerMatchDto> retrievePlayerFromGameTable( List<GameTable> gameTables ) {
        List<PlayerMatchDto> playerMatchDtos = new ArrayList<>();

        for ( GameTable gameTable : gameTables ) {
            List<PlayerMatch> playerMatches = gameTable.getPlayerMatches();
            for ( PlayerMatch playerMatch : playerMatches ) {
                playerMatchDtos.add( new PlayerMatchDto( playerMatch ) );
            }
        }

        return playerMatchDtos;
    }

    private void mergeMetPlayers( PlayerMatch playerToUpdate, String newMetPlayerList ) {
        String mergedMetPlayers = JsonConverter.mergeStringMetPlayer( playerToUpdate.getMetPlayers(), newMetPlayerList );
        playerToUpdate.setMetPlayers( mergedMetPlayers );
    }

    private String convertToString( List<Long> metPlayers ) {
        return JsonConverter.convertListLongIntoJsonString( metPlayers );
    }

}
