package it.emgtech.commandr.tournament.service;

import it.emgtech.commandr.match.model.PlayerMatchDto;
import it.emgtech.commandr.tournament.model.ScoreBoardRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentResponse;
import it.emgtech.commandr.tournament.model.TournamentScoreBoardResponse;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;

import java.util.List;

public interface ITournamentScoreBoardService {
    SubscribeToTournamentResponse subscribe( SubscribeToTournamentRequest request );

    List<TournamentScoreBoardResponse> getScoreBoard( ScoreBoardRequest request );

    List<Long> getPlayersByTournamentId( Long tournamentId );

    int updateScoreBoard ( Long tournamentId, List<PlayerMatchDto> playerMatchDto );
}
