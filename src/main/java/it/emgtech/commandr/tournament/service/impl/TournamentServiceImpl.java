package it.emgtech.commandr.tournament.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.tournament.model.entity.Tournament;
import it.emgtech.commandr.tournament.repository.ITournamentRepository;
import it.emgtech.commandr.tournament.service.ITournamentService;
import it.emgtech.commandr.util.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements ITournamentService {

    ITournamentRepository repository;

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
