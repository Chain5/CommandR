package it.emgtech.commandr.tournament.service.impl;

import it.emgtech.commandr.exception.ApiRequestException;
import it.emgtech.commandr.match.model.PlayerMatchDto;
import it.emgtech.commandr.match.model.entity.PlayerMatch;
import it.emgtech.commandr.match.service.IGameTableService;
import it.emgtech.commandr.player.model.PlayerResponse;
import it.emgtech.commandr.player.model.entity.Player;
import it.emgtech.commandr.player.service.IPlayerService;
import it.emgtech.commandr.tournament.model.ScoreBoardRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentResponse;
import it.emgtech.commandr.tournament.model.TournamentScoreBoardResponse;
import it.emgtech.commandr.tournament.model.UpdateScoreBoardRequest;
import it.emgtech.commandr.tournament.model.UpdateScoreBoardResponse;
import it.emgtech.commandr.tournament.model.entity.Tournament;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import it.emgtech.commandr.tournament.repository.ITournamentScoreBoardRepository;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.tournament.service.ITournamentService;
import it.emgtech.commandr.util.Mapper;
import it.emgtech.commandr.util.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TournamentScoreBoardServiceImpl implements ITournamentScoreBoardService {

    private final Mapper mapper;
    private final ITournamentScoreBoardRepository repository;
    private final IGameTableService gameTableService;
    private final ITournamentService tournamentService;

    @Override
    public SubscribeToTournamentResponse subscribe( SubscribeToTournamentRequest request ) {
        Optional<Tournament> tournamentOpt = tournamentService.findTournamentById( request.getTournamentId() );

        if ( tournamentOpt.isEmpty() ) {
            throw new ApiRequestException( MessageResponse.NO_TOURNAMENT_FOUND );
        }

        if ( tournamentOpt.get().isStarted() ) {
            throw new ApiRequestException( MessageResponse.TOURNAMENT_ALREADY_STARTED );
        }

        final TournamentScoreBoard tournamentScoreBoard = new TournamentScoreBoard(
                null,
                request.getTournamentId(),
                request.getPlayerId(),
                0
        );

        return mapper.map( repository.save( tournamentScoreBoard ), SubscribeToTournamentResponse.class );
    }

    @Override
    public int unsubscribePlayer( Long tournamentId, Long playerId ) {
        Optional<Tournament> tournamentOpt = tournamentService.findTournamentById( tournamentId );

        if ( tournamentOpt.isEmpty() ) {
            throw new ApiRequestException( MessageResponse.NO_TOURNAMENT_FOUND );
        }

        return repository.deleteTournamentScoreBoardByTournamentIdAndPlayerId( tournamentId, playerId );
    }

    @Override
    public List<TournamentScoreBoardResponse> getScoreBoard( ScoreBoardRequest request ) {
        List<TournamentScoreBoardResponse> scoreBoardResponses = new ArrayList<>();
        List<TournamentScoreBoard> scoreBoards = repository.getTournamentScoreBoardsByTournamentIdOrderByPlayerTotalScoreDesc( request.getTournamentId() );

        int i = 0;
        for ( TournamentScoreBoard scoreBoard : scoreBoards ) {
            Player player = scoreBoard.getPlayer();
            scoreBoardResponses.add( new TournamentScoreBoardResponse(
                    i + 1,
                    new PlayerResponse(
                            player.getId(),
                            player.getNickname(),
                            player.getFirstName(),
                            player.getLastName() ),
                    scoreBoard.getPlayerTotalScore()
            ) );
            i++;
        }

        return scoreBoardResponses;
    }

    @Override
    public List<Player> getPlayersByTournamentId( Long tournamentId ) {
        List<TournamentScoreBoard> scoreBoards = repository.getTournamentScoreBoardsByTournamentIdOrderByIdAsc( tournamentId );
        return scoreBoards.stream().map( TournamentScoreBoard::getPlayer ).collect( Collectors.toList() );
    }


    @Override
    public UpdateScoreBoardResponse updateScoreBoard( UpdateScoreBoardRequest request ) {
        List<PlayerMatchDto> playerMatchList = new ArrayList<>();
        List<TournamentScoreBoard> scoreBoardList = repository.getTournamentScoreBoardsByTournamentIdOrderByPlayerTotalScoreDesc( request.getTournamentId() );

        for ( TournamentScoreBoard tournamentScoreBoard : scoreBoardList ) {
            Player player = tournamentScoreBoard.getPlayer();
            Optional<PlayerMatch> playerMatch = player.getPlayerMatches().stream().filter( el -> el.getGameTable().getTournamentId().equals( request.getTournamentId() ) ).findFirst();
            playerMatch.ifPresent( match -> playerMatchList.add( new PlayerMatchDto( match ) ) );
        }

        if ( playerMatchList.isEmpty() ) {
            throw new ApiRequestException( MessageResponse.NO_SCORE_BOARD_TO_UPDATE );
        }

        internalUpdateScoreBoard( request.getTournamentId(), playerMatchList );
        return new UpdateScoreBoardResponse();
    }

    private int internalUpdateScoreBoard( Long tournamentId, List<PlayerMatchDto> playerMatchDto ) {
        int updatedRecordCounter = 0;
        for ( PlayerMatchDto player : playerMatchDto ) {
            TournamentScoreBoard tournamentScoreBoard = repository.findByTournamentIdAndPlayerId( tournamentId, player.getPlayerId() );
            tournamentScoreBoard.setPlayerTotalScore( tournamentScoreBoard.getPlayerTotalScore() + player.getScore() );
            repository.save( tournamentScoreBoard );
            updatedRecordCounter++;
        }
        gameTableService.setUpdatedFlag( tournamentId, true );
        return updatedRecordCounter;
    }

}
