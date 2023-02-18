package it.emgtech.commandr;

import it.emgtech.commandr.match.model.MatchesResponse;
import it.emgtech.commandr.match.service.IGameTableService;
import it.emgtech.commandr.match.service.IMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/match")
@RequiredArgsConstructor
public class MatchApiDelegate {

    private final IMatchService matchService;
    private final IGameTableService gameTableService;

    @PostMapping(value = "/generate")
    public MatchesResponse generateMatches(@RequestBody Long tournamentId) {
        return matchService.generateMatches(tournamentId);
    }
}
