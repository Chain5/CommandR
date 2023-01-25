package it.emgtech.commandr;

import it.emgtech.commandr.player.model.Player;
import it.emgtech.commandr.player.service.IPlayerService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@CrossOrigin
@RequestMapping(value = "/player")
public class PlayerApiDelegate {

    private final IPlayerService service;

    @Inject
    public PlayerApiDelegate(IPlayerService service) {
        this.service = service;
    }

    @PostMapping(value = "/new")
    public Player saveNewPlayer(@RequestBody Player player) {
        return service.save(player);
    }
}
