package it.emgtech.commandr.match.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class GameTable {
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long id;
    private Long tournamentId;
    private Integer tableNumber;
    private boolean isFinished;
}
