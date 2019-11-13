import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class NewsManager {
    static int newsCount = -1;
    static JFrame frame;
    static JTextArea textDisplay;
    static JButton nextButton, prevButton;
    //TODO: Add different textArea for title, description and time and add image on top
    public static void populateHeadlines(String category) {
        initialiseGraphics();
        List<News> newsList = new ArrayList();
        try {
            newsList = sendRequest(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        textDisplay.setText("\n\n\n\n\n\n               News Loaded. Press Next to view.");
        final List<News> finalNewsList = newsList;
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(newsCount < finalNewsList.size()) {
                    ++newsCount;
                    if(newsCount != 0)
                        prevButton.setVisible(true);

                    textDisplay.setText("Title: " + finalNewsList.get(newsCount).getTitle() + "\n\n" +
                            "Published at: " + finalNewsList.get(newsCount).getPublishedAt() + "\n\n" +
                            finalNewsList.get(newsCount).getDescription());

                } else {
                    nextButton.setVisible(false);
                }

            }
        });
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(newsCount == 0){
                    prevButton.setVisible(false);
                }
                else{
                    nextButton.setVisible(true);
                    --newsCount;

                    textDisplay.setText("Title: " + finalNewsList.get(newsCount).getTitle() + "\n\n" +
                            "Published at: " + finalNewsList.get(newsCount).getPublishedAt() + "\n\n" +
                            finalNewsList.get(newsCount).getDescription());
                }
            }
        });
    }

    private static void initialiseGraphics() {
        //TODO: Beautify graphics
        frame = new JFrame("NewsReader");
        textDisplay = new JTextArea("\n\n\n\n\n\n           Please wait while the news is loading...");
        prevButton = new JButton("Prev");
        prevButton.setVisible(false);
        prevButton.setBounds(225, 400, 60, 40);
        frame.add(prevButton);
        textDisplay.setLineWrap(true);
        textDisplay.setWrapStyleWord(true);
        textDisplay.setBounds(10,10, 360,250);
        textDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
        textDisplay.setBackground(Color.cyan);
        textDisplay.setForeground(Color.black);
        textDisplay.setFont(new Font("Imprint MT Shadow", Font.ITALIC, 15));
        frame.add(textDisplay);
        nextButton = new JButton("Next");
        nextButton.setBounds(300,400,60, 40);
        frame.add(nextButton);
        frame.setSize(400,500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static List<News> sendRequest(String category) throws Exception{
        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=a60e67d510164aa7822fa646d8aaaca5&pageSize=5&category=" + category;
        URL obj = new URL(url);
        List<News> newsList = new ArrayList();
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
            News news = new News();
            JSONObject ob = itr.next();
            news.setTitle((String) ob.get("title"));
            news.setDescription((String) ob.get("description"));
            news.setImgURL((String) ob.get("urlToImage"));
            String date = (String) ob.get("publishedAt");
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            Date formattedDate = df1.parse(date);
            news.setPublishedAt(formattedDate.toString());
            newsList.add(news);
        }
        return newsList;
    }
}
