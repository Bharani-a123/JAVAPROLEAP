import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {

    public UserService() {
    }

    // Register new student user
    public void registerStudent(String name, String email, String password) {
        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed");
                return;
            }

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO student_users(name, email, password) VALUES(?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            System.out.println("Student registered successfully");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Student login
    public int loginStudent(String email, String password) {
        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed");
                return -1;
            }

            PreparedStatement ps = con.prepareStatement(
                    "SELECT id FROM student_users WHERE email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful");
                int userId = rs.getInt("id");
                con.close();
                return userId;
            } else {
                System.out.println("Invalid email or password");
                con.close();
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
