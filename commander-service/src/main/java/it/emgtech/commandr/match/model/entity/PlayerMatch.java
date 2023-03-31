package it.emgtech.commandr.match.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
