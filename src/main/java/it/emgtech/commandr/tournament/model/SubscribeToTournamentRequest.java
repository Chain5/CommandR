package it.emgtech.commandr.tournament.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscribeToTournamentRequest {
    private Long playerId;
    private Long tournamentId;
}
