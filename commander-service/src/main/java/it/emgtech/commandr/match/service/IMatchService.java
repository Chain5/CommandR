package it.emgtech.commandr.match.service;

import it.emgtech.commandr.match.model.MatchesResponse;
import it.emgtech.commandr.match.model.entity.PlayerMatch;

public interface IMatchService {
    MatchesResponse generateMatches( Long tournamentId );

    PlayerMatch updateOrInsertPlayerMatch( PlayerMatch playerMatch );
}
