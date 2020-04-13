
package statistics.matcher;

import statistics.Player;

public class Not implements Matcher {
    Matcher matcher;

    public Not(Matcher m) {
        this.matcher = m;
    }
    
    
    
    @Override
    public boolean matches(Player p) {
        return !matcher.matches(p);
    }
    
}
