package it.emgtech.commandr.match.repository;

import it.emgtech.commandr.match.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMatchRepository extends JpaRepository<Match, Long> {
    List<Match> getMatchesByGameTableIdIn(List<Long> gameTableIds);
    void deleteMatchesByGameTableIdIn(List<Long> gameTableIdsids);
}
