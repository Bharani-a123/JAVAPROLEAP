
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    public void registerUser(String name, String email, String password) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                System.out.println("Database connection failed");
                return;
            }
            PreparedStatement ps = con.prepareStatement("INSERT INTO users(name, email,password) VALUES(?, ?,?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            System.out.println("User registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int login(String email, String password) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                System.out.println("Database connection failed");
                return -1;
            }
            PreparedStatement ps = con.prepareStatement("SELECT id FROM users WHERE email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful");
                return rs.getInt("id");
            } else {
                System.out.println("Invalid email or password");
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}