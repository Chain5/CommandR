package it.emgtech.commandr.match.model.response;

import lombok.Data;

import java.util.List;

@Data
public class MatchesResponse {

    private Long tournamentId;
    private List<MatchResponse> matches;

}
