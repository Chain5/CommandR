package it.emgtech.commandr.player.model;

import lombok.Data;

@Data
public class ModifyPlayerRequest {
    private Long playerId;
    private String nickname;
    private String firstName;
    private String lastName;
}
