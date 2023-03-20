package it.emgtech.commandr.match.model;

import it.emgtech.commandr.match.model.entity.PlayerMatch;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PlayerMatchDto extends PlayerMatch {
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
        return metPlayerJson.get( playerId ) != null && ( Integer ) metPlayerJson.get( playerId ) > 2;
    }

    public void addPlayerToMetPlayerList( Long metPlayerId ) {
        Integer counter = 1;
        if ( this.metPlayerJson.get( metPlayerId ) != null ) {
            counter = ( Integer ) metPlayerJson.get( metPlayerId );
            counter++;
        }
        metPlayerJson.put( metPlayerId, counter );
    }

}
