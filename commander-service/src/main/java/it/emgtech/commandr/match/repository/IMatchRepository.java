package it.emgtech.commandr.match.repository;

import it.emgtech.commandr.match.model.entity.PlayerMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMatchRepository extends JpaRepository<PlayerMatch, Long> {
    List<PlayerMatch> getMatchesByGameTableIdIn( List<Long> gameTableIds );

    Optional<PlayerMatch> getMatchesByGameTableIdInAndPlayerId( List<Long> gameTableIds, Long playerId );

    PlayerMatch save( PlayerMatch tournamentScoreBoard );

    List<PlayerMatch> findByPlayerId( Long playerId );
}
