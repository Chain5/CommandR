package it.emgtech.commandr.match.model;

import lombok.Data;

@Data
public class PlayerMatchResponse {
    private String nickname;
    private String firstName;
    private String lastName;
    private Integer score;
    private boolean hasApprovedScore;
}
