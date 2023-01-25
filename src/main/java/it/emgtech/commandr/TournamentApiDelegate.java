package it.emgtech.commandr;

import it.emgtech.commandr.tournament.model.entity.Tournament;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import it.emgtech.commandr.tournament.model.request.SubscribeToTournamentRequest;
import it.emgtech.commandr.tournament.model.response.TournamentScoreBoardResponse;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.tournament.service.ITournamentService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/tournament")
public class TournamentApiDelegate {

    private final ITournamentService service;
    private final ITournamentScoreBoardService scoreBoardService;

    @Inject
    public TournamentApiDelegate(ITournamentService service, ITournamentScoreBoardService scoreBoardService) {
        this.service = service;
        this.scoreBoardService = scoreBoardService;
    }

    @PostMapping(value = "/new")
    public Tournament saveNewTournament(@RequestBody Tournament tournament) {
        return service.save(tournament);
    }

    @PostMapping(value = "/subscribe")
    public TournamentScoreBoard subscribeToTournament(@RequestBody SubscribeToTournamentRequest request) {
        return scoreBoardService.subscribe(request);
    }

    @GetMapping(value = "/getScoreBoard")
    public List<TournamentScoreBoardResponse> getScoreBoard(@RequestBody Long tournamentId) {
        return scoreBoardService.getScoreBoard(tournamentId);
    }
}
