package it.emgtech.commandr.match.repository;

import it.emgtech.commandr.match.model.entity.GameTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IGameTableRepository extends JpaRepository<GameTable, Long> {
    List<GameTable> getGameTablesByTournamentId(Long tournamentId);
    void deleteByTournamentIdIn(Long tournament);
}
