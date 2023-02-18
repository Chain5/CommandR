package it.emgtech.commandr.player.repository;

import it.emgtech.commandr.player.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlayerRepository extends JpaRepository<Player, Long> {
    Player save(Player player);

    List<Player> findByIdIn(List<Long> ids);
}
