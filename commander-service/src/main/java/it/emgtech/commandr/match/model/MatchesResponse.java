package it.emgtech.commandr.match.model;

import lombok.Data;

import java.util.List;

@Data
public class MatchesResponse {
    private Long tournamentId;
    private List<MatchResponse> matches;
}
