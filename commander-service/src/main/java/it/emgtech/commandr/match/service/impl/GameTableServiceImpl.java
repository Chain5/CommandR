package it.emgtech.commandr.match.service.impl;

import it.emgtech.commandr.match.model.entity.GameTable;
import it.emgtech.commandr.match.repository.IGameTableRepository;
import it.emgtech.commandr.match.service.IGameTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameTableServiceImpl implements IGameTableService {

    private final IGameTableRepository repository;

    @Override
    public List<GameTable> saveGameTables( Long tournamentId, Integer numberOfTables ) {
        List<GameTable> savedGameTables = new ArrayList<>();

        for ( int i = 0; i < numberOfTables; i++ ) {
            GameTable newGameTable = new GameTable();
            newGameTable.setTournamentId( tournamentId );
            newGameTable.setFinished( false );
            newGameTable.setTableNumber( i + 1 );
            savedGameTables.add( repository.save( newGameTable ) );
        }

        return savedGameTables;
    }

    @Override
    public List<GameTable> getGameTablesByTournamentId( Long tournamentId ) {
        return repository.getGameTablesByTournamentId( tournamentId );
    }

    @Override
    public int resetGameTableForNewMatches( List<Long> gameTableIds ) {
        int updatedRecordCounter = 0;
        for ( Long gameTableId : gameTableIds ) {
            GameTable gameTable = repository.findGameTableById( gameTableId );
            gameTable.setFinished( false );
            repository.save( gameTable );
            updatedRecordCounter++;
        }
        return updatedRecordCounter;
    }
}
