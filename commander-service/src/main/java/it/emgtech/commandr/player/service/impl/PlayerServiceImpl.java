package it.emgtech.commandr.player.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.player.model.entity.Player;
import it.emgtech.commandr.player.repository.IPlayerRepository;
import it.emgtech.commandr.player.service.IPlayerService;
import it.emgtech.commandr.util.MessageResponse;
import it.emgtech.commandr.util.PasswordUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements IPlayerService {

    private final IPlayerRepository repository;

    @Override
    public Player save( Player player ) {
        // setting id to null: will be set by JPA
        player.setId( null );

        // verifying password validity
        if ( !PasswordUtility.checkPasswordValidity( player.getPassword() ) )
            throw new ApiRequestException( MessageResponse.PASSWORD_NOT_VALID );

        player.setPassword( PasswordUtility.getSecurePassword( player.getPassword() ) );
        //TODO: continue with checks
        return repository.save( player );
    }

    @Override
    public List<Player> getPlayersByIds( List<Long> ids ) {
        return repository.findByIdIn( ids );
    }
}
