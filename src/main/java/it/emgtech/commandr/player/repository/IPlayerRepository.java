package it.emgtech.commandr.player.repository;

import it.emgtech.commandr.player.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPlayerRepository extends JpaRepository<Player, Long> {
    Player save(Player player);
    List<Player> findByIdIn(List<Long> ids);
}
