package it.emgtech.commandr.tournament.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscribeToTournamentRequest {
    private Long playerId;
    private Long tournamentId;
}
