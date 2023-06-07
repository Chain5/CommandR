package it.emgtech.commandr.match.model;

import lombok.Data;

import java.util.List;

@Data
public class MatchResponse {
    private Integer tableNumber;
    private List<PlayerMatchResponse> player;
    private boolean isFinished;
}
