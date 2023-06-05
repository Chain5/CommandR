package it.emgtech.commandr.tournament.service.impl;

import it.emgtech.commandr.match.model.PlayerMatchDto;
import it.emgtech.commandr.player.model.PlayerResponse;
import it.emgtech.commandr.player.model.entity.Player;
import it.emgtech.commandr.player.service.IPlayerService;
import it.emgtech.commandr.tournament.model.ScoreBoardRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentRequest;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentResponse;
import it.emgtech.commandr.tournament.model.TournamentScoreBoardResponse;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import it.emgtech.commandr.tournament.repository.ITournamentScoreBoardRepository;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentScoreBoardServiceImpl implements ITournamentScoreBoardService {

    private final Mapper mapper;
    private final ITournamentScoreBoardRepository repository;
    private final IPlayerService playerService;

    @Override
    public SubscribeToTournamentResponse subscribe( SubscribeToTournamentRequest request ) {
        final TournamentScoreBoard tournamentScoreBoard = new TournamentScoreBoard(
                null,
                request.getTournamentId(),
                request.getPlayerId(),
                0
        );
        return mapper.map( repository.save( tournamentScoreBoard ), SubscribeToTournamentResponse.class );
    }

    @Override
    public List<TournamentScoreBoardResponse> getScoreBoard( ScoreBoardRequest request ) {
        List<TournamentScoreBoardResponse> scoreBoardResponses = new ArrayList<>();
        List<TournamentScoreBoard> scoreBoard = repository.getTournamentScoreBoardsByTournamentIdOrderByPlayerTotalScoreDesc( request.getTournamentId() );
        List<Player> playerList = playerService.getPlayersByIds(
                scoreBoard.stream().map( TournamentScoreBoard::getPlayerId ).collect( Collectors.toList() ) );

        int i = 0;
        for ( TournamentScoreBoard sB : scoreBoard ) {
            Player p = playerList.get( i );
            scoreBoardResponses.add( new TournamentScoreBoardResponse(
                    i + 1,
                    new PlayerResponse( p.getId(), p.getNickname(), p.getFirstName(), p.getLastName() ),
                    sB.getPlayerTotalScore()
            ) );
            i++;
        }

        return scoreBoardResponses;
    }

    @Override
    public List<Long> getPlayersByTournamentId( Long tournamentId ) {
        List<TournamentScoreBoard> scoreBoards = repository.getTournamentScoreBoardsByTournamentIdOrderByPlayerTotalScoreDesc( tournamentId );
        return scoreBoards.stream().map( TournamentScoreBoard::getPlayerId ).collect( Collectors.toList() );
    }

    @Override
    public int updateScoreBoard( Long tournamentId, List<PlayerMatchDto> playerMatchDto ) {
        int updatedRecordCounter = 0;
        for ( PlayerMatchDto player : playerMatchDto ) {
            TournamentScoreBoard tournamentScoreBoard = repository.findByTournamentIdAndPlayerId( tournamentId, player.getPlayerId() );
            tournamentScoreBoard.setPlayerTotalScore( tournamentScoreBoard.getPlayerTotalScore() + player.getScore() );
            repository.save( tournamentScoreBoard );
            updatedRecordCounter++;
        }
        return updatedRecordCounter;
    }
}
