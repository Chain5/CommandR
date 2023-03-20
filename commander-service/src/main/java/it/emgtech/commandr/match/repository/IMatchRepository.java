package it.emgtech.commandr.match.repository;

import it.emgtech.commandr.match.model.entity.PlayerMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMatchRepository extends JpaRepository<PlayerMatch, Long> {
    List<PlayerMatch> getMatchesByGameTableIdIn( List<Long> gameTableIds );

    void deleteMatchesByGameTableIdIn( List<Long> gameTableIdsids );
}
