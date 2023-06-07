package it.emgtech.commandr.player.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.player.model.GetPlayerRequest;
import it.emgtech.commandr.player.model.GetPlayerResponse;
import it.emgtech.commandr.player.model.ModifyPlayerRequest;
import it.emgtech.commandr.player.model.ModifyPlayerResponse;
import it.emgtech.commandr.player.model.SavePlayerRequest;
import it.emgtech.commandr.player.model.SavePlayerResponse;
import it.emgtech.commandr.player.model.entity.Player;
import it.emgtech.commandr.player.repository.IPlayerRepository;
import it.emgtech.commandr.player.service.IPlayerService;
import it.emgtech.commandr.util.Mapper;
import it.emgtech.commandr.util.MessageResponse;
import it.emgtech.commandr.util.PasswordUtility;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements IPlayerService {

    private final Mapper mapper;
    private final IPlayerRepository repository;

    @Override
    public SavePlayerResponse save( SavePlayerRequest savePlayerRequest ) {
        Player player = mapper.map( savePlayerRequest, Player.class );
        // setting id to null: will be set by JPA
        player.setId( null );

        // verifying password validity
        if ( !PasswordUtility.checkPasswordValidity( player.getPassword() ) )
            throw new ApiRequestException( MessageResponse.PASSWORD_NOT_VALID );

        player.setPassword( PasswordUtility.getSecurePassword( player.getPassword() ) );

        // verifying if nickname is used by another user already
        if ( repository.findByNickname( player.getNickname() ).isPresent() ) {
            throw new ApiRequestException( MessageResponse.NICKNAME_ALREADY_IN_USE );
        }

        return mapper.map( repository.save( player ), SavePlayerResponse.class );
    }

    @Override
    public GetPlayerResponse getPlayerById( GetPlayerRequest request ) {
        return mapper.map( repository.findById( request.getPlayerId() ).orElse( null ), GetPlayerResponse.class );
    }

    @Override
    public List<Player> getPlayersByIds( List<Long> ids ) {
        return repository.findByIdIn( ids );
    }

    @Override
    public ModifyPlayerResponse modifyPlayer( ModifyPlayerRequest request ) {
        Optional<Player> playerOpt = repository.findById( request.getPlayerId() );

        if ( playerOpt.isPresent() ) {
            Player player = playerOpt.get();
            if ( StringUtils.isNoneBlank( request.getNickname() ) ) {
                player.setNickname( request.getNickname() );
            }
            if ( StringUtils.isNoneBlank( request.getFirstName() ) ) {
                player.setFirstName( request.getFirstName() );
            }
            if ( StringUtils.isNoneBlank( request.getLastName() ) ) {
                player.setFirstName( request.getLastName() );
            }
            return mapper.map( repository.save( player ), ModifyPlayerResponse.class );
        }

        throw new ApiRequestException( MessageResponse.NO_PLAYER_FOUND );
    }
}
