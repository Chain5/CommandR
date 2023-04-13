package it.emgtech.commandr.tournament.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.tournament.model.NewTournamentRequest;
import it.emgtech.commandr.tournament.model.NewTournamentResponse;
import it.emgtech.commandr.tournament.model.entity.Tournament;
import it.emgtech.commandr.tournament.repository.ITournamentRepository;
import it.emgtech.commandr.tournament.service.ITournamentService;
import it.emgtech.commandr.util.Mapper;
import it.emgtech.commandr.util.MessageResponse;
import liquibase.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements ITournamentService {

    private final Mapper mapper;
    private final ITournamentRepository repository;

    @Override
    public NewTournamentResponse save( NewTournamentRequest tournamentRequest ) {
        if ( StringUtil.isEmpty( tournamentRequest.getTournamentName() )
                || tournamentRequest.getStartDate().isAfter( LocalDate.now() ) ) {
            throw new ApiRequestException( MessageResponse.TOURNAMENT_NOT_VALID );
        }

        Tournament tournament = mapper.map( tournamentRequest, Tournament.class );
        // setting id to null: will be set by JPA
        tournament.setId( null );
        tournament.setEndDate( null );
        return mapper.map( repository.save( tournament ), NewTournamentResponse.class );
    }
}
