package it.emgtech.commandr.player.repository;

import it.emgtech.commandr.player.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlayerRepository extends JpaRepository<Player, Long> {
    Player save( Player player );

    Optional<Player> findById( Long id );

    List<Player> findByIdIn( List<Long> ids );
}
