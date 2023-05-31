package it.emgtech.commandr.match.model.entity;

import it.emgtech.commandr.player.model.entity.Player;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class PlayerMatch {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;
    private Long gameTableId;
    private Long playerId;
    private Integer score;
    private boolean approvedScore;
    private String metPlayers;

    @ManyToOne ( targetEntity = Player.class, fetch = FetchType.EAGER )
    @JoinColumn ( name = "playerId", insertable = false, updatable = false )
    private Player player;

    @ManyToOne ( targetEntity = Player.class, fetch = FetchType.EAGER )
    @JoinColumn ( name = "gameTableId", insertable = false, updatable = false )
    private GameTable gameTable;
}
