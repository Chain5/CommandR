package it.emgtech.commandr.match.model;

import lombok.Data;

@Data
public class UpdateScoreRequest {
    private Long tournamentId;
    private Long playerId;
    private Integer newScore;
}
