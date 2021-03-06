package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players";
        
        String bodyText = Request.Get(url).execute().returnContent().asString();
                
        //System.out.println("json-muotoinen data:");
        //System.out.println( bodyText );

        Gson mapper = new Gson();
        Player[] players = mapper.fromJson(bodyText, Player[].class);

        Date date = new Date();
        String country = "FIN";

        Arrays.sort(players);


        System.out.println("Players from " + country + " " + date + "\n");
        for (Player player : players) {
            if (player.getNationality().equals(country)) {
                System.out.println(player);
            }
        }
        
        

        

        
    }
  
}
