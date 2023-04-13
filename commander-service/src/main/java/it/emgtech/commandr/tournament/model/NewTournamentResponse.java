package it.emgtech.commandr.tournament.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewTournamentResponse {
    private Long id;
    private String tournamentName;
    private LocalDate startDate;
    private LocalDate endDate;
}
