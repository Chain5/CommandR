package it.emgtech.commandr;

import it.emgtech.commandr.match.model.GenerateMatchRequest;
import it.emgtech.commandr.match.model.MatchesResponse;
import it.emgtech.commandr.match.service.IGameTableService;
import it.emgtech.commandr.match.service.IMatchService;
import lombok.RequiredArgsConstructor;
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
    public MatchesResponse generateMatches( @RequestBody GenerateMatchRequest request ) {
        return matchService.generateMatches( request );
    }

    //TODO: implement getMatches & getMatchById
}
