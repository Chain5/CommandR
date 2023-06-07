package it.emgtech.commandr.tournament.service;

import it.emgtech.commandr.match.model.PlayerMatchDto;
import it.emgtech.commandr.player.model.entity.Player;
import it.emgtech.commandr.tournament.model.ScoreBoardRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentResponse;
import it.emgtech.commandr.tournament.model.TournamentScoreBoardResponse;
import it.emgtech.commandr.tournament.model.UpdateScoreBoardRequest;
import it.emgtech.commandr.tournament.model.UpdateScoreBoardResponse;

import java.util.List;

public interface ITournamentScoreBoardService {
    SubscribeToTournamentResponse subscribe( SubscribeToTournamentRequest request );

    int unsubscribePlayer( Long tournamentId, Long playerId );

    List<TournamentScoreBoardResponse> getScoreBoard( ScoreBoardRequest request );

    List<Player> getPlayersByTournamentId( Long tournamentId );

    UpdateScoreBoardResponse updateScoreBoard( UpdateScoreBoardRequest tournamentId );

    int updateScoreBoard( Long tournamentId, List<PlayerMatchDto> playerMatchDto );

}
