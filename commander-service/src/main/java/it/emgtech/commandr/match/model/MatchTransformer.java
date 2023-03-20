package it.emgtech.commandr.match.model;

import it.emgtech.commandr.match.model.entity.PlayerMatch;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MatchTransformer {

    public MatchesResponse transform( List<PlayerMatch> matchesToInsert, Long tournamentId ) {

        MatchesResponse response = new MatchesResponse();
        response.setTournamentId( tournamentId );
        response.setMatches( buildMatchesFromPlayers( matchesToInsert ) );

        return response;
    }

    public List<PlayerMatchResponse> transform( List<PlayerMatch> playerMatches ) {
        List<PlayerMatchResponse> responses = new ArrayList<>();
        for ( PlayerMatch player : playerMatches ) {
            PlayerMatchResponse playerResponse = new PlayerMatchResponse();
            playerResponse.setId( player.getId() ); // TODO: retrieve correct data for player
            playerResponse.setScore( 0 );
            playerResponse.setHasApprovedScore( false );
            responses.add( playerResponse );
        }
        return responses;
    }

    private List<MatchResponse> buildMatchesFromPlayers( List<PlayerMatch> matchesToInsert ) {
        List<MatchResponse> response = new ArrayList<>();

        Map<Long, List<PlayerMatch>> groupedPlayer = matchesToInsert.stream().collect( Collectors.groupingBy( PlayerMatch::getGameTableId ) );

        for ( Map.Entry<Long, List<PlayerMatch>> table : groupedPlayer.entrySet() ) {
            MatchResponse match = new MatchResponse();
            match.setScoreApproved( false );
            match.setTableNumber( Math.toIntExact( table.getKey() ) ); // TODO: to retrieve correct table number
            match.setPlayer( transform( table.getValue() ) );
        }

        return response;
    }
}
