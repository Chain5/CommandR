package it.emgtech.commandr.tournament.repository;

import it.emgtech.commandr.tournament.model.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITournamentRepository extends JpaRepository<Tournament, Long> {
}
