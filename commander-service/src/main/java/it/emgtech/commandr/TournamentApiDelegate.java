package it.emgtech.commandr;

import it.emgtech.commandr.tournament.model.NewTournamentRequest;
import it.emgtech.commandr.tournament.model.NewTournamentResponse;
import it.emgtech.commandr.tournament.model.ScoreBoardRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentResponse;
import it.emgtech.commandr.tournament.model.TournamentScoreBoardResponse;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.tournament.service.ITournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<NewTournamentResponse> saveNewTournament( @RequestBody NewTournamentRequest request ) {
        return ResponseEntity.ok( service.save( request ) );
    }

    @PostMapping ( value = "/subscribe" )
    public ResponseEntity<SubscribeToTournamentResponse> subscribeToTournament( @RequestBody SubscribeToTournamentRequest request ) {
        return ResponseEntity.ok( scoreBoardService.subscribe( request ) );
    }

    @GetMapping ( value = "/getScoreBoard" )
    public ResponseEntity<List<TournamentScoreBoardResponse>> getScoreBoard( @RequestBody ScoreBoardRequest request ) {
        return ResponseEntity.ok( scoreBoardService.getScoreBoard( request ) );
    }
}
