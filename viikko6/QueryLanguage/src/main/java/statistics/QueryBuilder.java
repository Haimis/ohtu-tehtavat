
package statistics;

import java.util.ArrayList;
import java.util.List;
import statistics.matcher.*;

public class QueryBuilder {
    List<Matcher> matchers;
    boolean and;
    
    public QueryBuilder() {
        this.matchers = new ArrayList<>();
        this.and = true;
    }
    
    public QueryBuilder playsIn(String string) {
        matchers.add(new PlaysIn(string));
        return this;
    }
    
    public QueryBuilder hasAtLeast(int value, String category) {
        matchers.add(new HasAtLeast(value, category));
        return this;
    }
    
    public QueryBuilder hasFewerThan(int value, String category) {
        matchers.add(new HasFewerThan(value, category));
        return this;
    }
    
    public Matcher build() {
        if (matchers.isEmpty()) {
            return (new All());
        }
        Matcher[] mList = new Matcher[matchers.size()];
        for (int i = 0; i < matchers.size(); i++) {
            mList[i] = matchers.get(i);
        }
        matchers.clear();
        if (!and) {
            return new Or(mList);
        }
        return new And(mList);
        
    }
    
    public QueryBuilder oneOf(Matcher m1, Matcher m2) {
        QueryBuilder qb = new QueryBuilder();
        qb.matchers.add(m1);
        qb.matchers.add(m2);
        qb.and = false;
        return qb;
    }
}
