package it.emgtech.commandr.tournament.model.entity;

import it.emgtech.commandr.player.model.entity.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@Entity
public class TournamentScoreBoard {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;
    private Long tournamentId;
    private Long playerId;
    private Integer playerTotalScore;

    public TournamentScoreBoard( Long id, Long tournamentId, Long playerId, Integer playerTotalScore ) {
        this.id = id;
        this.tournamentId = tournamentId;
        this.playerId = playerId;
        this.playerTotalScore = playerTotalScore;
    }

    @ManyToOne ( targetEntity = Tournament.class, fetch = FetchType.EAGER )
    @JoinColumn ( name = "tournamentId", insertable = false, updatable = false )
    private Tournament tournament;

    @ManyToOne ( targetEntity = Player.class, fetch = FetchType.EAGER )
    @JoinColumn ( name = "playerId", insertable = false, updatable = false )
    private Player player;
}
