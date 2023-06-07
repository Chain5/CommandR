package it.emgtech.commandr.player.model;

import lombok.Data;

@Data
public class GetPlayerResponse {
    private Long id;
    private String nickname;
    private String firstName;
    private String lastName;
}
