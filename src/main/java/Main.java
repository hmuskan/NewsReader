
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    static JFrame frame;
    static JComboBox prefs;
    static JButton next;
    static String category;
    static JTextArea title;
    public static void main(String[] args) {
        final List<String> categories = new ArrayList();
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb",
                    "root", "muskan3799");
            ResultSet rs = mycon.createStatement().executeQuery("select * from preference;");
            while(rs.next())
                categories.add(rs.getString(1));
            mycon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialiseGraphics(categories);
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                category = (String) prefs.getSelectedItem();
                System.out.println(category);
                frame.setVisible(false);
                NewsManager.populateHeadlines(category);
            }
        });


    }

    private static void initialiseGraphics(List<String> categories) {
        frame = new JFrame("Welcome to NewsReader");
        prefs = new JComboBox(categories.toArray());
        prefs.setBounds(75, 300, 100, 50);
        frame.add(prefs);
        next = new JButton("Next");
        next.setBounds(200, 300, 100, 50);
        title = new JTextArea("\n\nWelcome to NewsReader App, Please select your news category.");
        title.setBounds(10, 10, 360, 250);
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        title.setBorder(BorderFactory.createLineBorder(Color.black));
        title.setBackground(Color.cyan);
        title.setForeground(Color.black);
        title.setFont(new Font("Imprint MT Shadow", Font.BOLD, 25));
        frame.add(title);
        frame.add(next);
        frame.setSize(400, 500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
