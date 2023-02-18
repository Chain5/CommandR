package it.emgtech.commandr;

import it.emgtech.commandr.player.model.entity.Player;
import it.emgtech.commandr.player.service.IPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/player")
@RequiredArgsConstructor
public class PlayerApiDelegate {

    private final IPlayerService service;

    @PostMapping(value = "/new")
    public Player saveNewPlayer(@RequestBody Player player) {
        return service.save(player);
    }

    //TODO: delete player
}
