package it.emgtech.commandr.player.service;

import it.emgtech.commandr.player.model.entity.Player;

import java.util.List;

public interface IPlayerService {
    Player save(Player player);

    List<Player> getPlayersByIds(List<Long> ids);
}
