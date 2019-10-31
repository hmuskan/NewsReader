import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class NewsManager {
    public static void main(String[] args) {
        try {
            sendRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest() throws Exception{
        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=a60e67d510164aa7822fa646d8aaaca5&pageSize=5";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.toString());
        JSONArray articles = (JSONArray) json.get("articles");
        Iterator<JSONObject> itr = articles.iterator();
        while(itr.hasNext()) {
            JSONObject ob = itr.next();
            System.out.println(ob.get("title"));
            System.out.println(ob.get("description"));
        }
    }
}
