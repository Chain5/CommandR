package it.emgtech.commandr.tournament.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewTournamentRequest {
    private String tournamentName;
    private LocalDate startDate;
}
