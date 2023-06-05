package it.emgtech.commandr.tournament.service;

import it.emgtech.commandr.tournament.model.NewTournamentRequest;
import it.emgtech.commandr.tournament.model.NewTournamentResponse;
import it.emgtech.commandr.tournament.model.entity.Tournament;

public interface ITournamentService {
    NewTournamentResponse save( NewTournamentRequest tournament );

    int increaseGeneratedMatchCounter( Long tournamentId );

    Tournament findTournamentById( Long tournamentId );
}
