package it.emgtech.commandr.match.service.impl;

import it.emgtech.commandr.match.repository.IGameTableRepository;
import it.emgtech.commandr.match.service.IGameTableService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class GameTableServiceImpl implements IGameTableService {

    private final IGameTableRepository repository;

    @Inject
    public GameTableServiceImpl(IGameTableRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteGabeTableByTournamentId(Long tournamentId) {
        repository.deleteByTournamentIdIn(tournamentId);
    }
}
