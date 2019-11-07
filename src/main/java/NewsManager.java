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
    static int newsCount = -1;
    static JFrame frame;
    static JTextArea textDisplay;
    static JButton nextButton, prevButton; //TODO: Create prev button, add functionality of going back, update i variable continuously

    public static void main(String[] args) {
        initialiseGraphics();
        List<String> newsList = new ArrayList();
        try {
            newsList = sendRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        textDisplay.setText("News Loaded. Press Next to view.");
        final List<String> finalNewsList = newsList;
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(newsCount < finalNewsList.size()) {
                    ++newsCount;
                    if(newsCount != 0)
                    prevButton.setVisible(true);
                    textDisplay.setText(finalNewsList.get(newsCount));

                } else {
                    nextButton.setVisible(false);
                }

            }
        });
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(newsCount==0){
                    prevButton.setVisible(false);
                }
                else{
                    nextButton.setVisible(true);
                    --newsCount;
                    textDisplay.setText(finalNewsList.get(newsCount));
                }
            }
        });
    }

    private static void initialiseGraphics() {
        //TODO: Beautify graphics
        frame = new JFrame("NewsReader");
        textDisplay = new JTextArea("Please wait while the news is loading...");
        prevButton = new JButton("Prev");
        prevButton.setVisible(false);
        prevButton.setBounds(225, 400, 70, 40);
        frame.add(prevButton);
        textDisplay.setLineWrap(true);
        textDisplay.setWrapStyleWord(true);
        textDisplay.setBounds(10,10, 380,150);
        frame.add(textDisplay);
        nextButton = new JButton("Next");
        nextButton.setBounds(300,400,70, 40);
        frame.add(nextButton);
        frame.setSize(400,500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            String text = ob.get("title") + "\n\n" + ob.get("description");
            news.add(text);
        }
        return news;
    }
}
