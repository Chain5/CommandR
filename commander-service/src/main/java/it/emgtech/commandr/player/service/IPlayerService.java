package it.emgtech.commandr.player.service;

import it.emgtech.commandr.player.model.GetPlayerRequest;
import it.emgtech.commandr.player.model.entity.Player;

import java.util.List;

public interface IPlayerService {
    Player save( Player player );

    Player getPlayerById( GetPlayerRequest request );

    List<Player> getPlayersByIds( List<Long> ids );
}
