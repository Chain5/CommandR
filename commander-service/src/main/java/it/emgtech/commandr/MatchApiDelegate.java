package it.emgtech.commandr;

import it.emgtech.commandr.match.model.ApproveMatchRequest;
import it.emgtech.commandr.match.model.ApproveMatchResponse;
import it.emgtech.commandr.match.model.GenerateMatchRequest;
import it.emgtech.commandr.match.model.MatchesResponse;
import it.emgtech.commandr.match.model.UpdateScoreRequest;
import it.emgtech.commandr.match.model.UpdateScoreResponse;
import it.emgtech.commandr.match.service.IMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping ( value = "/match" )
@RequiredArgsConstructor
public class MatchApiDelegate {

    private final IMatchService matchService;

    @PostMapping ( value = "/generate" )
    public ResponseEntity<MatchesResponse> generateMatches( @RequestBody GenerateMatchRequest request ) {
        return ResponseEntity.ok( matchService.generateMatches( request ) );
    }

    @PostMapping ( value = "/updateScore" )
    public ResponseEntity<UpdateScoreResponse> updateScore( @RequestBody UpdateScoreRequest request ) {
        return ResponseEntity.ok( matchService.updateScore( request ) );
    }

    @PostMapping ( value = "/approveMatch" )
    public ResponseEntity<ApproveMatchResponse> approveMatch( @RequestBody ApproveMatchRequest request ) {
        return ResponseEntity.ok( matchService.approveMatch( request ) );
    }

    //TODO: implement getMatches & getMatchById
}
