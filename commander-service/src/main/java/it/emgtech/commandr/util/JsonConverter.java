package it.emgtech.commandr.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConverter {

    public static String convertListLongIntoJsonString( List<Long> list ) {
        StringBuilder sb = new StringBuilder( "{" );
        String suffix = "";
        for ( Long id : list ) {
            sb.append( suffix );
            sb.append( '"' + id + '"' ).append( ": 1" );
            suffix = ",";
        }
        sb.append( "}" );
        return sb.toString();
    }

    public static String convertMapToJsonString( Map<String, Integer> map ) {
        JSONObject obj = new JSONObject();
        obj.putAll( map );
        return obj.toJSONString();
    }

    public static String mergeStringMetPlayer( String metPlayers, String newMetPlayerList ) {
        try {
            Map<String, Integer> metPlayerMap = new ObjectMapper().readValue( metPlayers, HashMap.class );
            Map<String, Integer> newMetPlayerMap = new ObjectMapper().readValue( newMetPlayerList, HashMap.class );
            for ( Map.Entry<String, Integer> newPlayer : newMetPlayerMap.entrySet() ) {
                metPlayerMap.merge( newPlayer.getKey(), newPlayer.getValue(), JsonConverter::calculate );
            }
            return convertMapToJsonString( metPlayerMap );
        } catch ( JsonProcessingException e ) {
            System.err.println( "Error converting json object to hashmap" );
            throw new RuntimeException( e );
        }
    }

    private static Integer calculate( Integer n1, Integer n2 ) {
        return n1 + n2;
    }
}
