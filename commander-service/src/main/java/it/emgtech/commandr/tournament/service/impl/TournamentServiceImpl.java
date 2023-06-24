package it.emgtech.commandr.tournament.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.tournament.model.GetTournamentsResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements ITournamentService {

    private final Mapper mapper;
    private final ITournamentRepository repository;



    @Override
    public List<GetTournamentsResponse> getTournaments( boolean getOnlyNewTournaments ) {
        List<Tournament> tournaments;

        if( getOnlyNewTournaments ) {
            tournaments = repository.getTournamentsByStartedAndFinished(false, false);
        } else {
            tournaments = repository.findAll();
        }

        return buildResponse(tournaments);
    }

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
        tournament.setGeneratedMatchCounter( 0 );
        return mapper.map( repository.save( tournament ), NewTournamentResponse.class );
    }

    @Override
    public int increaseGeneratedMatchCounter( Long tournamentId ) {
        Optional<Tournament> tournamentOpt = repository.findById( tournamentId );
        if ( tournamentOpt.isEmpty() ) {
            return -1;
        }
        Tournament tournament = tournamentOpt.get();
        tournament.setGeneratedMatchCounter( tournament.getGeneratedMatchCounter() + 1 );
        repository.save( tournament );
        return 1;
    }

    @Override
    public Optional<Tournament> findTournamentById( Long tournamentId ) {
        return repository.findById( tournamentId );
    }

    @Override
    public boolean startTournament( Long tournamentId ) {
        Optional<Tournament> tournamentOpt = repository.findById( tournamentId );
        if ( tournamentOpt.isEmpty() ) {
            return false;
        }
        Tournament tournament = tournamentOpt.get();
        tournament.setStarted( true );
        repository.save( tournament );
        return true;
    }

    private List<GetTournamentsResponse> buildResponse( List<Tournament> tournaments ) {
        List<GetTournamentsResponse> responses = new ArrayList<>();

        for ( Tournament tournament : tournaments ) {
            GetTournamentsResponse response = mapper.map( tournament, GetTournamentsResponse.class );

            if(tournament.getTournamentScoreBoards() != null && !tournament.getTournamentScoreBoards().isEmpty() ) {
                response.setSubscribedPlayerCounter( tournament.getTournamentScoreBoards().size() );
            }
            responses.add( response );
        }
        return responses;
    }
}
