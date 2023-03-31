package it.emgtech.commandr;

import it.emgtech.commandr.player.model.GetPlayerRequest;
import it.emgtech.commandr.player.model.entity.Player;
import it.emgtech.commandr.player.service.IPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping ( value = "/player" )
@RequiredArgsConstructor
public class PlayerApiDelegate {

    private final IPlayerService service;

    @PostMapping ( value = "/new" )
    public Player saveNewPlayer( @RequestBody Player player ) {
        return service.save( player );
    }

    @PostMapping ( value = "/get" )
    public Player getPlayer( @RequestBody GetPlayerRequest request ) {
        return service.getPlayerById( request );
    }

    //TODO: delete player
}
