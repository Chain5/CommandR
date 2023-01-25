package it.emgtech.commandr.match.service;

import it.emgtech.commandr.match.model.response.MatchesResponse;
import java.util.List;

public interface IMatchService {
    MatchesResponse generateMatches(Long tournamentId);
}
