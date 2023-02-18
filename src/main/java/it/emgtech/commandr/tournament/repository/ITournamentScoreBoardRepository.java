package it.emgtech.commandr.tournament.repository;

import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITournamentScoreBoardRepository extends JpaRepository<TournamentScoreBoard, Long> {

    TournamentScoreBoard save(TournamentScoreBoard tournamentScoreBoard);

    List<TournamentScoreBoard> getTournamentScoreBoardsByTournamentIdOrderByPlayerTotalScoreDesc(Long tournamentId);
}
