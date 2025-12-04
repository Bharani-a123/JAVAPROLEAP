import java.sql.*;

public class MovieService {
    public void addMovie(String title,String genre,double price)
    {
          try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                System.out.println("Database connection failed");
                return;
            }
            PreparedStatement ps = con.prepareStatement("INSERT INTO movie (title, genre, price) VALUES (?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, genre);
            ps.setDouble(3, price);
            ps.executeUpdate();
            System.out.println("Movie added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listMovies(){
        try(Connection con=DBConnection.getConnection())
        {
            if(con==null)
            {
                System.out.println("Database connection failed!");
                return;
            }

            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery("SELECT * FROM movie");
            System.out.println("===Movie List ===");
            while(rs.next())
            {
                System.out.println(rs.getInt("id")+ ". "+rs.getString("title")+"| Genre "+rs.getString("genre")+" | Price: "+rs.getDouble("price"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
