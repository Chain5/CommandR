package it.emgtech.commandr.tournament.model;

import it.emgtech.commandr.player.model.PlayerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TournamentScoreBoardResponse {
    private Integer ranking;
    private PlayerResponse player;
    private Integer score;
}
