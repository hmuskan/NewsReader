import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;


public class Main {
    public static void main(String[] args) {
        NewsManager.populateHeadlines();
        try {
            Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "muskan3799");
            Statement st = mycon.createStatement();
            ResultSet rs = st.executeQuery("select * from emp;");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            mycon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
