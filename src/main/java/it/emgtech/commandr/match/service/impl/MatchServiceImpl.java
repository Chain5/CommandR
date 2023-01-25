package it.emgtech.commandr.match.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.match.model.entity.GameTable;
import it.emgtech.commandr.match.model.entity.Match;
import it.emgtech.commandr.match.model.response.MatchesResponse;
import it.emgtech.commandr.match.repository.IMatchRepository;
import it.emgtech.commandr.match.repository.IGameTableRepository;
import it.emgtech.commandr.match.service.IGameTableService;
import it.emgtech.commandr.match.service.IMatchService;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.util.MessageResponse;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements IMatchService {

    private static final Integer MIN_PLAYER_FOR_TOURNAMENT = 4;

    private final IMatchRepository matchRepository;
    private final IGameTableRepository gameTableRepository;
    private final IGameTableService gameTableService;
    private final ITournamentScoreBoardService tournamentScoreBoardService;

    @Inject
    public MatchServiceImpl(IMatchRepository matchRepository,
                            IGameTableRepository gameTableRepository,
                            IGameTableService gameTableService,
                            ITournamentScoreBoardService tournamentScoreBoardService) {
        this.matchRepository = matchRepository;
        this.gameTableRepository = gameTableRepository;
        this.gameTableService = gameTableService;
        this.tournamentScoreBoardService = tournamentScoreBoardService;
    }

    @Override
    public MatchesResponse generateMatches(Long tournamentId) {

        final List<Long> tournamentPlayerIds = tournamentScoreBoardService.getPlayersByTournamentId(tournamentId);
        final List<GameTable> tournamentMatches = gameTableRepository.getGameTablesByTournamentId(tournamentId);

        // Common checks before generating new matches
        commonChecks(tournamentPlayerIds, tournamentMatches);

        generateMatches(tournamentPlayerIds, tournamentMatches);

        deleteOldGameTableAndMatches(tournamentId, tournamentMatches);

        // TODO: salvere i nuovi record

        return null;
    }

    public void deleteMatchByGameTableIds(List<Long> gameTableIdList) {
        matchRepository.deleteMatchesByGameTableIdIn(gameTableIdList);
    }

    private void commonChecks(List<Long> playerList, List<GameTable> remainingMatches) {

        if(playerList.isEmpty()) {
            throw new ApiRequestException(MessageResponse.NO_PLAYER_IN_TOURNAMENT);
        }

        if(playerList.size() < MIN_PLAYER_FOR_TOURNAMENT) {
            throw new ApiRequestException(MessageResponse.NOT_ENOUGH_PLAYERS);
        }

        if(remainingMatches.stream().anyMatch(match -> !match.isFinished())) {
            throw new ApiRequestException(MessageResponse.ALL_MATCHES_NOT_COCLUDED);
        }
    }

    private List<MatchesResponse> generateMatches(List<Long> tournamentPlayers, List<GameTable> tournamentMatches) {
        Long[][] table;

        // if there are no gateTable, that means that it's the first match generation
        if(tournamentMatches.isEmpty()) {
            table = generateMatchesFromPlayerList(tournamentPlayers);
        } else {
            table = generateMatchesFromPreviousMatches(tournamentMatches);
        }


        //TODO: map the table into matches response


        return null;

    }

    private Long[][] generateMatchesFromPlayerList(List<Long> tournamentPlayers) {
        final int playerNumber = tournamentPlayers.size();
        int numberOfTables = playerNumber % MIN_PLAYER_FOR_TOURNAMENT;
        Long[][] table = new Long[numberOfTables][MIN_PLAYER_FOR_TOURNAMENT];

        int k=0;
        for (int i=0; i < numberOfTables; i++) {
            for (int j = 0; j < MIN_PLAYER_FOR_TOURNAMENT; j++) {
                if(k < playerNumber) {
                    table[i][j] = tournamentPlayers.get(k);
                    k++;
                    continue;
                }
                break;
            }
        }
        return table;
    }

    private Long[][] generateMatchesFromPreviousMatches(List<GameTable> tournamentMatches) {
        final int tableNumber = tournamentMatches.size();
        Long[][] table = new Long[tableNumber][MIN_PLAYER_FOR_TOURNAMENT];

        List<Match> oldMatches = matchRepository.getMatchesByGameTableIdIn(tournamentMatches.stream().map(GameTable::getId).collect(Collectors.toList()));

        List<Match> matches;
        for (int i=0; i < tableNumber; i++) {
            int finalIndex = i;
            matches = oldMatches.stream().filter(m -> Objects.equals(m.getGameTableId(), tournamentMatches.get(finalIndex).getId())).collect(Collectors.toList());
            for (int j = 0; j < matches.size(); j++) {
                table[i][j] = matches.get(j).getPlayerId();
            }
        }

        return table;
    }

    private void deleteOldGameTableAndMatches(Long tournamentId, List<GameTable> tournamentMatches) {
        deleteMatchByGameTableIds(tournamentMatches.stream().map(GameTable::getId).collect(Collectors.toList()));
        gameTableService.deleteGabeTableByTournamentId(tournamentId);
    }


}
