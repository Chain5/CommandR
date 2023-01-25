package it.emgtech.commandr.tournament.model.response;

import it.emgtech.commandr.player.model.PlayerResponse;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class TournamentScoreBoardResponse {
    private Integer ranking;
    private PlayerResponse player;
    private Integer score;
}
