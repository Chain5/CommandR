package it.emgtech.commandr.match.repository;

import it.emgtech.commandr.match.model.entity.GameTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGameTableRepository extends JpaRepository<GameTable, Long> {
    Optional<GameTable> findById( Long id );

    List<GameTable> getGameTablesByTournamentId( Long tournamentId );

    GameTable save( GameTable gameTable );

    GameTable findGameTableById( Long id );
}
