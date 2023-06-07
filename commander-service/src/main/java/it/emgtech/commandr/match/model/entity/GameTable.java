package it.emgtech.commandr.match.model.entity;

import it.emgtech.commandr.tournament.model.entity.Tournament;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class GameTable {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;
    private Long tournamentId;
    private Integer tableNumber;
    private boolean isFinished;
    private boolean updatedScore;

    @ManyToOne ( targetEntity = Tournament.class, fetch = FetchType.EAGER )
    @JoinColumn ( name = "tournamentId", insertable = false, updatable = false )
    private Tournament tournament;

    @OneToMany ( mappedBy = "gameTable" )
    private List<PlayerMatch> playerMatches;
}
