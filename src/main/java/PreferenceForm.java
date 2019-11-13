import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreferenceForm {
    JFrame frame;
    JComboBox prefs;
    public String getCategory() {
        List<String> categories = new ArrayList();
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
        return null;
    }

    private void initialiseGraphics(List<String> categories) {

    }

}
