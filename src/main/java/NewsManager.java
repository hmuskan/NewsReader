import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsManager {
    static int i = 0;
    static JFrame f;
    static JTextArea t;
    static JButton b; //TODO: Create prev button, add functionality of going back, update i variable continuously
    public static void main(String[] args) {
        initialiseGraphics();
        List<String> newsList = new ArrayList();
        try {
            newsList = sendRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        t.setText("News Loaded. Press Next to view.");
        final List<String> finalNewsList = newsList;
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(i < finalNewsList.size()) {
                    t.setText(finalNewsList.get(i));
                    i++;
                } else {
                    b.setVisible(false);
                }

            }
        });
    }

    private static void initialiseGraphics() {
        //TODO: Beautify graphics
        f = new JFrame("NewsReader");
        t = new JTextArea("Please wait while the news is loading...");
        t.setLineWrap(true);
        t.setWrapStyleWord(true);
        t.setBounds(10,10, 380,150);
        f.add(t);
        b = new JButton("Next");
        b.setBounds(300,400,70, 40);
        f.add(b);
        f.setSize(400,500);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static List<String> sendRequest() throws Exception{
        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=a60e67d510164aa7822fa646d8aaaca5&pageSize=5";
        URL obj = new URL(url);
        List<String> news = new ArrayList();
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
            String text = (String)ob.get("title") + "\n\n" + (String)ob.get("description");
            /*System.out.println(ob.get("title"));
            System.out.println(ob.get("description"));*/
            news.add(text);
        }
        return news;
    }
}
