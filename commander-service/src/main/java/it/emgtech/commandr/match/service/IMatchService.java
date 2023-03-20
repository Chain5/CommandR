package it.emgtech.commandr.match.service;

import it.emgtech.commandr.match.model.MatchesResponse;

public interface IMatchService {
    MatchesResponse generateMatches( Long tournamentId );
}
