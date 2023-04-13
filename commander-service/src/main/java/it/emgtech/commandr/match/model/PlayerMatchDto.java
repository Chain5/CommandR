package it.emgtech.commandr.match.model;

import it.emgtech.commandr.match.model.entity.PlayerMatch;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Optional;

public class PlayerMatchDto extends PlayerMatch {
    private static final int MAX_ENCOUNTER = 2;

    JSONObject metPlayerJson;

    public PlayerMatchDto( PlayerMatch playerMatch ) {
        this.setId( playerMatch.getId() );
        this.setGameTableId( playerMatch.getGameTableId() );
        this.setPlayerId( playerMatch.getPlayerId() );
        this.setScore( playerMatch.getScore() );
        this.setApprovedScore( playerMatch.isApprovedScore() );
        JSONParser parser = new JSONParser();
        try {
            this.metPlayerJson = ( JSONObject ) parser.parse( playerMatch.getMetPlayers() );
        } catch ( ParseException e ) {
            System.err.println( "Error parsing metPlayerJson: " + e );
        }
    }

    public boolean hasMet( Long playerId ) {
        String jsonKey = playerId.toString();
        if ( metPlayerJson.get( jsonKey ) != null ) {
            Optional<Object> encounterOpt = Optional.ofNullable( metPlayerJson.get( jsonKey ) );
            return encounterOpt.filter( o -> Integer.parseInt( o.toString() ) >= MAX_ENCOUNTER ).isPresent();
        } else {
            return false;
        }
    }
}
