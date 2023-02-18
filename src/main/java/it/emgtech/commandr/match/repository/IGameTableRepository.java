package it.emgtech.commandr.match.repository;

import it.emgtech.commandr.match.model.entity.GameTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGameTableRepository extends JpaRepository<GameTable, Long> {
    List<GameTable> getGameTablesByTournamentId(Long tournamentId);
    GameTable save(GameTable gameTable);
}
