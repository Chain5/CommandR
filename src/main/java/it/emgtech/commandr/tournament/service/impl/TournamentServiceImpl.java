package it.emgtech.commandr.tournament.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.tournament.model.entity.Tournament;
import it.emgtech.commandr.tournament.repository.ITournamentRepository;
import it.emgtech.commandr.tournament.service.ITournamentService;
import it.emgtech.commandr.util.MessageResponse;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;

@Service
public class TournamentServiceImpl implements ITournamentService {

    ITournamentRepository repository;

    @Inject
    public TournamentServiceImpl(ITournamentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tournament save(Tournament tournament) {
        // setting id to null: will be set by JPA
        tournament.setId(null);

        if(tournament.getTournamentName() == null ||
            tournament.getTournamentName().isEmpty() ||
            tournament.getStartDate().isAfter(LocalDate.now()) ||
            tournament.getEndDate() != null) {
            throw new ApiRequestException(MessageResponse.TOURNAMENT_NOT_VALID);
        }
        return repository.save(tournament);
    }
}
