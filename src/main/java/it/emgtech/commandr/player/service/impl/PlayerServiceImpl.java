package it.emgtech.commandr.player.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.player.model.Player;
import it.emgtech.commandr.player.repository.IPlayerRepository;
import it.emgtech.commandr.player.service.IPlayerService;
import it.emgtech.commandr.util.MessageResponse;
import it.emgtech.commandr.util.PasswordUtility;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class PlayerServiceImpl implements IPlayerService {

    private IPlayerRepository repository;

    @Inject
    public PlayerServiceImpl(IPlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Player save(Player player) {
        // setting id to null: will be set by JPA
        player.setId(null);

        // verifying password validity
        if(!PasswordUtility.checkPasswordValidity(player.getPassword()))
            throw new ApiRequestException(MessageResponse.PASSWORD_NOT_VALID);

        player.setPassword(PasswordUtility.getSecurePassword(player.getPassword()));
        //TODO: continue with checks
        return repository.save(player);
    }

    @Override
    public List<Player> getPlayersByIds(List<Long> ids) {
        return repository.findByIdIn(ids);
    }
}
