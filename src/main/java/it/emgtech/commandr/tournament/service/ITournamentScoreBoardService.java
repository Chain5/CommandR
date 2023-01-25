package it.emgtech.commandr.tournament.service;

import it.emgtech.commandr.player.model.Player;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import it.emgtech.commandr.tournament.model.request.SubscribeToTournamentRequest;
import it.emgtech.commandr.tournament.model.response.TournamentScoreBoardResponse;

import java.util.List;

public interface ITournamentScoreBoardService {

    TournamentScoreBoard subscribe(SubscribeToTournamentRequest request);

    List<TournamentScoreBoardResponse> getScoreBoard(Long tournamentId);

    List<Long> getPlayersByTournamentId(Long tournamentId);
}
