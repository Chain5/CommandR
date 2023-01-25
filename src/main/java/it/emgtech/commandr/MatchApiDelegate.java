package it.emgtech.commandr;

import it.emgtech.commandr.match.model.response.MatchesResponse;
import it.emgtech.commandr.match.service.IGameTableService;
import it.emgtech.commandr.match.service.IMatchService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/match")
public class MatchApiDelegate {

    private IMatchService matchService;
    private IGameTableService gameTableService;

    public MatchApiDelegate(IMatchService matchService, IGameTableService gameTableService) {
        this.matchService = matchService;
        this.gameTableService = gameTableService;
    }

    @PostMapping(value = "/generate")
    public MatchesResponse saveNewPlayer(@RequestBody Long tournamentId) {
        return matchService.generateMatches(tournamentId);
    }



}
