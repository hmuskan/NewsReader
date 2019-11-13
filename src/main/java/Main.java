
import javax.swing.*;
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
                    "root", "yourpasswordhere");
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
        prefs.setBounds(50, 150, 100, 50);
        frame.add(prefs);
        next = new JButton("Next");
        next.setBounds(200, 150, 100, 50);
        title = new JTextArea("Welcome to NewsReader App, Please select your news category.");
        title.setBounds(10, 10, 300, 100);
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        frame.add(title);
        frame.add(next);
        frame.setSize(500, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
