package it.emgtech.commandr.match.service;

import it.emgtech.commandr.match.model.GenerateMatchRequest;
import it.emgtech.commandr.match.model.GetMatchRequest;
import it.emgtech.commandr.match.model.MatchesResponse;
import it.emgtech.commandr.match.model.entity.PlayerMatch;

public interface IMatchService {
    MatchesResponse generateMatches( GenerateMatchRequest request );

    PlayerMatch updateOrInsertPlayerMatch( PlayerMatch playerMatch, Long tournamentId );

    MatchesResponse getMatches( GetMatchRequest request );
}
