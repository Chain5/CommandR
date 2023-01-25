package it.emgtech.commandr.player.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerResponse {
    private Long id;
    private String nickname;
    private String firstName;
    private String lastName;
}
