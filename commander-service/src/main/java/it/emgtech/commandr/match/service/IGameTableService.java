package it.emgtech.commandr.match.service;

import it.emgtech.commandr.match.model.entity.GameTable;

import java.util.List;

public interface IGameTableService {

    List<GameTable> saveGameTables( Long tournamentId, Integer numberOfTables );

    List<GameTable> getGameTablesByTournamentId( Long tournamentId );

    int resetGameTableForNewMatches( List<Long> gameTables );

    int setGameTableAsFinished( Long id );
}
