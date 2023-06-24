package it.emgtech.commandr.tournament.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetTournamentsResponse {
    private Long id;
    private String tournamentName;
    private LocalDate startDate;
    private boolean started;
    private boolean finished;
    private int subscribedPlayerCounter = 0;
}
