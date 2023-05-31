package it.emgtech.commandr.player.model.entity;

import it.emgtech.commandr.match.model.entity.GameTable;
import it.emgtech.commandr.match.model.entity.PlayerMatch;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Player {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;
    private String nickname;
    private String firstName;
    private String lastName;
    private String password;

    @OneToMany ( mappedBy = "player" )
    private List<TournamentScoreBoard> tournamentScoreBoards;

    @OneToMany ( mappedBy = "player" )
    private List<PlayerMatch> playerMatches;
}
