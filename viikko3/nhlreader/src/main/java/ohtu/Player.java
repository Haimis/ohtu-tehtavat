
package ohtu;

public class Player implements Comparable<Player> {
    private String name;
    private int goals;
    private int assists;
    private int penalties;
    private String team;
    private String nationality;
    private int points;




    public void setName(String name, int goals, int assists, int penalties, String team, String nationality) {
        this.name = name;
        this.goals = goals;
        this.assists = assists;
        this.points = 112;
        this.penalties = penalties;
        this.team = team;
        this.nationality = nationality;
        
    }

    public String getName() {
        return name;
    }

        public String getNationality() {
        return nationality;
    }


    @Override
    public String toString() {
        return name + " team " + team + " " + goals + " + " + assists + " = " + (assists + goals);
    }

    @Override
    public int compareTo(Player a) {
        if ((this.goals + this.assists) == (a.goals+a.assists)) {
            return a.goals - this.goals;
        }
        return (a.goals+a.assists) - (this.goals + this.assists);
    }
      
}