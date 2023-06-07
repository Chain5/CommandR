package it.emgtech.commandr.player.service;

import it.emgtech.commandr.player.model.GetPlayerResponse;
import it.emgtech.commandr.player.model.GetPlayerRequest;
import it.emgtech.commandr.player.model.ModifyPlayerRequest;
import it.emgtech.commandr.player.model.ModifyPlayerResponse;
import it.emgtech.commandr.player.model.SavePlayerRequest;
import it.emgtech.commandr.player.model.SavePlayerResponse;
import it.emgtech.commandr.player.model.entity.Player;

import java.util.List;

public interface IPlayerService {
    SavePlayerResponse save( SavePlayerRequest player );

    GetPlayerResponse getPlayerById( GetPlayerRequest request );

    List<Player> getPlayersByIds( List<Long> ids );

    ModifyPlayerResponse modifyPlayer( ModifyPlayerRequest request );
}
