package it.emgtech.commandr.tournament.repository;

import it.emgtech.commandr.tournament.model.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITournamentRepository extends JpaRepository<Tournament, Long> {
}
