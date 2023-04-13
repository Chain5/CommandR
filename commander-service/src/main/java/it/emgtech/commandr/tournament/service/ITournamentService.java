package it.emgtech.commandr.tournament.service;

import it.emgtech.commandr.tournament.model.NewTournamentRequest;
import it.emgtech.commandr.tournament.model.NewTournamentResponse;

public interface ITournamentService {
    NewTournamentResponse save( NewTournamentRequest tournament );
}
