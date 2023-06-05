package it.emgtech.commandr.tournament.model.entity;

import it.emgtech.commandr.match.model.entity.GameTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Tournament {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;
    private String tournamentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer generatedMatchCounter;

    @OneToMany ( mappedBy = "tournament" )
    private List<TournamentScoreBoard> tournamentScoreBoards;

    @OneToMany ( mappedBy = "tournament" )
    private List<GameTable> gameTables;

}
