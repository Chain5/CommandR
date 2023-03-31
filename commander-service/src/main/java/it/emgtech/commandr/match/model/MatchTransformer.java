package it.emgtech.commandr.match.model;

import it.emgtech.commandr.match.model.entity.GameTable;
import it.emgtech.commandr.match.model.entity.PlayerMatch;
import it.emgtech.commandr.player.model.entity.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MatchTransformer {

    public MatchesResponse transform( List<PlayerMatch> matchesToInsert, List<GameTable> createdGames, List<Player> playerList ) {
        MatchesResponse response = new MatchesResponse();
        response.setTournamentId( createdGames.get( 0 ).getTournamentId() );
        response.setMatches( buildMatchesFromPlayers( matchesToInsert, createdGames, playerList ) );
        return response;
    }

    private List<MatchResponse> buildMatchesFromPlayers( List<PlayerMatch> matchesToInsert, List<GameTable> createdGames, List<Player> playerList ) {
        List<MatchResponse> response = new ArrayList<>();
        Map<Long, List<PlayerMatch>> groupedPlayer = matchesToInsert.stream().collect( Collectors.groupingBy( PlayerMatch::getGameTableId ) );

        for ( Map.Entry<Long, List<PlayerMatch>> table : groupedPlayer.entrySet() ) {
            MatchResponse match = new MatchResponse();
            match.setScoreApproved( false );
            Optional<Integer> tableNumberOpt = createdGames.stream().filter( t -> t.getId().equals( table.getKey() ) ).map( GameTable::getTableNumber ).findFirst();
            match.setTableNumber( tableNumberOpt.orElse( -1 ) );
            match.setPlayer( transform( table.getValue(), playerList ) );
            response.add( match );
        }
        return response;
    }

    public List<PlayerMatchResponse> transform( List<PlayerMatch> playerMatches, List<Player> playerList ) {
        List<PlayerMatchResponse> responses = new ArrayList<>();
        for ( PlayerMatch player : playerMatches ) {
            PlayerMatchResponse playerResponse = new PlayerMatchResponse();
            Optional<Player> playerOpt = playerList.stream().filter( p -> p.getId().equals( player.getPlayerId() ) ).findFirst();
            if ( playerOpt.isPresent() ) {
                playerResponse.setNickname( playerOpt.get().getNickname() );
                playerResponse.setFirstName( playerOpt.get().getFirstName() );
                playerResponse.setLastName( playerOpt.get().getLastName() );
            } else {
                playerResponse.setNickname( "UNKNOWN" );
                playerResponse.setFirstName( "UNKNOWN" );
                playerResponse.setLastName( "UNKNOWN" );
            }
            playerResponse.setScore( 0 );
            playerResponse.setHasApprovedScore( false );
            responses.add( playerResponse );
        }
        return responses;
    }
}
