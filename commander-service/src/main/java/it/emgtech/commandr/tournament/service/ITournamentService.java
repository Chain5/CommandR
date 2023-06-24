package it.emgtech.commandr.tournament.service;

import it.emgtech.commandr.tournament.model.GetTournamentsResponse;
import it.emgtech.commandr.tournament.model.NewTournamentRequest;
import it.emgtech.commandr.tournament.model.NewTournamentResponse;
import it.emgtech.commandr.tournament.model.entity.Tournament;

import java.util.List;
import java.util.Optional;

public interface ITournamentService {
    NewTournamentResponse save( NewTournamentRequest tournament );

    int increaseGeneratedMatchCounter( Long tournamentId );

    Optional<Tournament> findTournamentById( Long tournamentId );

    boolean startTournament( Long tournamentId );

    List<GetTournamentsResponse> getTournaments( boolean getOnlyNewTournaments );
}
