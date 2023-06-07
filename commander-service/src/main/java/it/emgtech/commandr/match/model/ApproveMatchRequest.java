package it.emgtech.commandr.match.model;

import lombok.Data;

@Data
public class ApproveMatchRequest {
    private Long tournamentId;
    private Long playerId;
    private boolean approve;
}
