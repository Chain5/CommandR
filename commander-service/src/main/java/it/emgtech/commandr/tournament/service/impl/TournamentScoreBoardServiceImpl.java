package it.emgtech.commandr.tournament.service.impl;

import it.emgtech.commandr.player.model.PlayerResponse;
import it.emgtech.commandr.player.model.entity.Player;
import it.emgtech.commandr.player.service.IPlayerService;
import it.emgtech.commandr.tournament.model.SubscribeToTournamentRequest;
import it.emgtech.commandr.tournament.model.TournamentScoreBoardResponse;
import it.emgtech.commandr.tournament.model.entity.TournamentScoreBoard;
import it.emgtech.commandr.tournament.repository.ITournamentScoreBoardRepository;
import it.emgtech.commandr.tournament.service.ITournamentScoreBoardService;
import it.emgtech.commandr.tournament.service.ITournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentScoreBoardServiceImpl implements ITournamentScoreBoardService {

    private final ITournamentScoreBoardRepository repository;
    private final IPlayerService playerService;
    private final ITournamentService tournamentService;

    @Override
    public TournamentScoreBoard subscribe( SubscribeToTournamentRequest request ) {
        return repository.save( new TournamentScoreBoard(
                null,
                request.getTournamentId(),
                request.getPlayerId(),
                0 ) );
    }

    @Override
    public List<TournamentScoreBoardResponse> getScoreBoard( Long tournamentId ) {
        List<TournamentScoreBoardResponse> scoreBoardResponses = new ArrayList<>();

        List<TournamentScoreBoard> scoreBoard =
                repository.getTournamentScoreBoardsByTournamentIdOrderByPlayerTotalScoreDesc( tournamentId );

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
}
