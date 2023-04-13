package it.emgtech.commandr.player.model;

import lombok.Data;

@Data
public class SavePlayerRequest {
    private String nickname;
    private String firstName;
    private String lastName;
    private String password;
}
