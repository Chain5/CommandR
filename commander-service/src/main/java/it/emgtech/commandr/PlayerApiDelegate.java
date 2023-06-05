package it.emgtech.commandr;

import it.emgtech.commandr.player.model.GetPlayerResponse;
import it.emgtech.commandr.player.model.GetPlayerRequest;
import it.emgtech.commandr.player.model.ModifyPlayerRequest;
import it.emgtech.commandr.player.model.ModifyPlayerResponse;
import it.emgtech.commandr.player.model.SavePlayerRequest;
import it.emgtech.commandr.player.model.SavePlayerResponse;
import it.emgtech.commandr.player.service.IPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SavePlayerResponse> saveNewPlayer( @RequestBody SavePlayerRequest player ) {
        return ResponseEntity.ok( service.save( player ) );
    }

    @PostMapping ( value = "/get" )
    public ResponseEntity<GetPlayerResponse> getPlayer( @RequestBody GetPlayerRequest request ) {
        return ResponseEntity.ok( service.getPlayerById( request ) );
    }

    @PostMapping( value = "/modify" )
    public ResponseEntity<ModifyPlayerResponse> modifyPlayer ( @RequestBody ModifyPlayerRequest request ) {
        return ResponseEntity.ok( service.modifyPlayer( request ) );
    }

    //TODO: delete player
}
