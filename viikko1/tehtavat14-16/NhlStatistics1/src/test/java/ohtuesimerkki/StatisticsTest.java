
package ohtuesimerkki;


import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class StatisticsTest {
    Reader readerStub = new Reader() {
 
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();
 
            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));
 
            return players;
        }
    };
    Statistics stats;

    @Before
    public void setUp(){
        stats = new Statistics(readerStub);
    }  

    @Test
    public void searchToimiiJosPelaajaLöytyy() {
        Player p = stats.search("Semenko");
        assertEquals("Semenko", p.getName());
    }
    
    @Test
    public void searchToimiiJosPelaajaaEiLöydy() {
        Player p = stats.search("Semenkova");
        assertEquals(null, p);
    }

    @Test
    public void listTeamToimii() {
        List<Player> l = stats.team("EDM");
        assertEquals(3, l.size());
    }
    
    @Test
    public void topScorerToimii() {
        List<Player> l = stats.topScorers(3);
        assertEquals("Gretzky", l.get(0).getName());
    }
    
}
