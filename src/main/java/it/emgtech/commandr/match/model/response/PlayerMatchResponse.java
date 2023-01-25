package it.emgtech.commandr.match.model.response;

import lombok.Data;

@Data
public class PlayerMatchResponse {
    private Long id;
    private String nickname;
    private String firstName;
    private String lastName;
    private Integer score;
    private boolean hasApprovedScore;
}
