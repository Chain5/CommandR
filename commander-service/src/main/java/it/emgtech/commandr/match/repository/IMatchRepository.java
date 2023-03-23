package it.emgtech.commandr.match.repository;

import it.emgtech.commandr.match.model.entity.PlayerMatch;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMatchRepository extends JpaRepository<PlayerMatch, Long> {
    List<PlayerMatch> getMatchesByGameTableIdIn( List<Long> gameTableIds );

    PlayerMatch save( PlayerMatch tournamentScoreBoard );

    PlayerMatch findByGameTableIdAndPlayerId ( Long gameTableId, Long playerId );
}
