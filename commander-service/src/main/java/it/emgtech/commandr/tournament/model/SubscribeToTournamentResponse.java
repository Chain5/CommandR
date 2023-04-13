package it.emgtech.commandr.tournament.model;

import lombok.Data;

@Data
public class SubscribeToTournamentResponse {
    private Long id;
    private Long tournamentId;
    private Long playerId;
    private Integer playerTotalScore;
}
