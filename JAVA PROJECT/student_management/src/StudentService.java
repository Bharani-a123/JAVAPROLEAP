import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentService {

    public StudentService() {
    }

    // Add new student
    public void addStudent(String name, String department, double grade) {
        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed");
                return;
            }

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO students (name, department, grade) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, grade);
            ps.executeUpdate();
            System.out.println("Student added successfully");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // List all students
    public void listStudents() {
        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed");
                return;
            }

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");
            System.out.println("=== Student List ===");

            while (rs.next()) {
                PrintStream out = System.out;
                int id = rs.getInt("id");
                out.println(id + ". " + rs.getString("name")
                        + " | Department: " + rs.getString("department")
                        + " | Grade: " + rs.getDouble("grade"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
