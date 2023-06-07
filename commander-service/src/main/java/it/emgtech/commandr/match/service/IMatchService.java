package it.emgtech.commandr.match.service;

import it.emgtech.commandr.match.model.ApproveMatchRequest;
import it.emgtech.commandr.match.model.ApproveMatchResponse;
import it.emgtech.commandr.match.model.GenerateMatchRequest;
import it.emgtech.commandr.match.model.GetMatchesRequest;
import it.emgtech.commandr.match.model.MatchesResponse;
import it.emgtech.commandr.match.model.UpdateScoreRequest;
import it.emgtech.commandr.match.model.UpdateScoreResponse;
import it.emgtech.commandr.match.model.entity.PlayerMatch;

public interface IMatchService {
    MatchesResponse generateMatches( GenerateMatchRequest request );

    PlayerMatch updateOrInsertPlayerMatch( PlayerMatch playerMatch, Long tournamentId );

    MatchesResponse getMatches( GetMatchesRequest request );

    UpdateScoreResponse updateScore( UpdateScoreRequest request );

    ApproveMatchResponse approveMatch( ApproveMatchRequest request );
}
