package it.emgtech.commandr.tournament.repository;

import it.emgtech.commandr.tournament.model.entity.Tournament;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITournamentRepository extends JpaRepository<Tournament, Long> {

    List<Tournament> getTournamentsByStartedAndFinished( boolean started, boolean finished );
}
