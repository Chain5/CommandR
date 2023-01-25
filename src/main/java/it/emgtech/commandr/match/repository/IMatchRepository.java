package it.emgtech.commandr.match.repository;

import it.emgtech.commandr.match.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMatchRepository extends JpaRepository<Match, Long> {
    List<Match> getMatchesByGameTableIdIn(List<Long> gameTableIds);
    void deleteMatchesByGameTableIdIn(List<Long> gameTableIdsids);
}
