package it.emgtech.commandr.match.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.match.model.entity.GameTable;
import it.emgtech.commandr.match.model.entity.Match;
import it.emgtech.commandr.match.model.MatchesResponse;
import it.emgtech.commandr.match.repository.IMatchRepository;
import it.emgtech.commandr.match.service.IGameTableService;
import it.emgtech.commandr.match.service.IMatchService;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.util.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements IMatchService {

    private static final Integer MIN_PLAYER_FOR_TOURNAMENT = 4;

    private final IMatchRepository matchRepository;
    private final IGameTableService gameTableService;
    private final ITournamentScoreBoardService tournamentScoreBoardService;

    @Override
    public MatchesResponse generateMatches(Long tournamentId) {
        final List<Long> tournamentPlayerIds = tournamentScoreBoardService.getPlayersByTournamentId(tournamentId);
        final List<GameTable> tournamentMatches = gameTableService.getGameTablesByTournamentId(tournamentId);

        // Common checks before generating new matches
        commonChecks(tournamentPlayerIds, tournamentMatches);

        // create and save new matches
        generateMatches(tournamentId, tournamentPlayerIds, tournamentMatches);

        // TODO: return generatedMatches response
        return null;
    }

    private void commonChecks(List<Long> playerList, List<GameTable> remainingMatches) {

        if (playerList.isEmpty()) {
            throw new ApiRequestException(MessageResponse.NO_PLAYER_IN_TOURNAMENT);
        }

        if (playerList.size() < MIN_PLAYER_FOR_TOURNAMENT) {
            throw new ApiRequestException(MessageResponse.NOT_ENOUGH_PLAYERS);
        }

        if (remainingMatches.stream().anyMatch(match -> !match.isFinished())) {
            throw new ApiRequestException(MessageResponse.ALL_MATCHES_NOT_COCLUDED);
        }
    }

    private void generateMatches(Long tournamentId, List<Long> tournamentPlayers, List<GameTable> tournamentMatches) {
        if (tournamentMatches.isEmpty()) {
            // first of all, create new game tables
            List<GameTable> createdGamesIds = createNewGameTables(tournamentId, tournamentPlayers.size());
            createNewMatches(createdGamesIds, tournamentPlayers, true);
        } else {
            createNewMatches(tournamentMatches, tournamentPlayers, false);
        }
    }

    private List<GameTable> createNewGameTables(Long tournamentId, int numberOfPlayers) {
        int numberOfGameTable = numberOfPlayers / MIN_PLAYER_FOR_TOURNAMENT;
        if (numberOfPlayers % MIN_PLAYER_FOR_TOURNAMENT == 3) {
            numberOfGameTable++;
        }
        return gameTableService.saveGameTables(tournamentId, numberOfGameTable);
    }

    private void createNewMatches(List<GameTable> createdGames, List<Long> tournamentPlayers, boolean isTournamentJustStarted) {
        List<Match> matchesToInsert = new ArrayList<>();

        final Long[][] autogeneratedMatches = isTournamentJustStarted ?
                generateMatchesFromPlayerList(tournamentPlayers) :
                generateMatchesFromPreviousMatches(createdGames);

        final List<Long> createdGamesIds = createdGames.stream().map(GameTable::getId).collect(Collectors.toList());

        for (int gameIndex = 0; gameIndex < createdGamesIds.size(); gameIndex++) {
            Long[] currentMatchPlayers = autogeneratedMatches[gameIndex];
            for (int player = 0; player < currentMatchPlayers.length; player++) {
                Match match = new Match();
                match.setId(null);
                match.setScore(0);
                match.setApprovedScore(false);
                match.setPlayerId(currentMatchPlayers[player]);
                match.setGameTableId(createdGamesIds.get(gameIndex));

                matchesToInsert.add(match);
            }
        }
        matchRepository.saveAll(matchesToInsert);
    }

    private Long[][] generateMatchesFromPlayerList(List<Long> tournamentPlayers) {
        final int playerNumber = tournamentPlayers.size();
        int numberOfTables = playerNumber % MIN_PLAYER_FOR_TOURNAMENT;
        Long[][] tables = new Long[numberOfTables][MIN_PLAYER_FOR_TOURNAMENT];

        int k = 0;
        for (int i = 0; i < numberOfTables; i++) {
            for (int j = 0; j < MIN_PLAYER_FOR_TOURNAMENT; j++) {
                if (k < playerNumber) {
                    tables[i][j] = tournamentPlayers.get(k);
                    k++;
                    continue;
                }
                break;
            }
        }
        return tables;
    }

    private Long[][] generateMatchesFromPreviousMatches(List<GameTable> gameTables) {
        final int tableNumber = gameTables.size();
        Long[][] table = new Long[tableNumber][MIN_PLAYER_FOR_TOURNAMENT];
        final List<Long> gameTablesIds = gameTables.stream().map(GameTable::getId).collect(Collectors.toList());
        final List<Match> oldMatches = matchRepository.getMatchesByGameTableIdIn(gameTablesIds);

        List<Match> matches;
        for (int i = 0; i < tableNumber; i++) {
            int finalIndex = i;
            matches = oldMatches.stream().filter(m -> Objects.equals(m.getGameTableId(), gameTables.get(finalIndex).getId())).collect(Collectors.toList());
            for (int j = 0; j < matches.size(); j++) {
                table[i][j] = matches.get(j).getPlayerId();
            }
        }
        // deleting old matches
        matchRepository.deleteMatchesByGameTableIdIn(gameTablesIds);

        return table;
    }

}
