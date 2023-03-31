package it.emgtech.commandr;

import it.emgtech.commandr.match.repository.IGameTableRepository;
import it.emgtech.commandr.match.repository.IMatchRepository;
import it.emgtech.commandr.player.repository.IPlayerRepository;
import it.emgtech.commandr.tournament.repository.ITournamentRepository;
import it.emgtech.commandr.tournament.repository.ITournamentScoreBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration (exclude={ DataSourceAutoConfiguration.class } )
class CommandrApplicationTests {

    @MockBean
    private IGameTableRepository gameTableRepository;
    @MockBean
    private IMatchRepository matchRepository;
    @MockBean
    private IPlayerRepository playerRepository;
    @MockBean
    private ITournamentScoreBoardRepository tournamentScoreBoardRepository;
    @MockBean
    private ITournamentRepository tournamentRepository;

    @Test
    void contextLoads() {
    }

}
