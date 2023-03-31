package it.emgtech.commandr;

import it.emgtech.commandr.tournament.model.ScoreBoardRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentRequest;
import it.emgtech.commandr.tournament.model.TournamentScoreBoardResponse;
import it.emgtech.commandr.tournament.model.entity.Tournament;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.tournament.service.ITournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping ( value = "/tournament" )
@RequiredArgsConstructor
public class TournamentApiDelegate {

    private final ITournamentService service;
    private final ITournamentScoreBoardService scoreBoardService;

    @PostMapping ( value = "/new" )
    public Tournament saveNewTournament( @RequestBody Tournament request ) {
        return service.save( request );
    }

    @PostMapping ( value = "/subscribe" )
    public TournamentScoreBoard subscribeToTournament( @RequestBody SubscribeToTournamentRequest request ) {
        return scoreBoardService.subscribe( request );
    }

    @GetMapping ( value = "/getScoreBoard" )
    public List<TournamentScoreBoardResponse> getScoreBoard( @RequestBody ScoreBoardRequest request ) {
        return scoreBoardService.getScoreBoard( request );
    }
}
