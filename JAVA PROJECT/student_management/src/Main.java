import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();
        StudentService studentService = new StudentService();

        // Initialize sample students if table is empty
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) AS count FROM students")) {

            if (rs.next() && rs.getInt("count") == 0) {
                studentService.addStudent("John Doe", "Computer Science", 8.5);
                studentService.addStudent("Alice Smith", "Electronics", 9.1);
                studentService.addStudent("Rahul Kumar", "Mechanical", 7.8);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=== Welcome to Student Management System ===");
        System.out.println("1. Register Student");
        System.out.println("2. Login Student");
        System.out.print("Choose option: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Email: ");
            String email = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();
            userService.registerStudent(name, email, password);
        }

        System.out.print("Enter Email: ");
        String loginEmail = sc.nextLine();
        System.out.print("Enter Password: ");
        String loginPassword = sc.nextLine();
        int studentId = userService.loginStudent(loginEmail, loginPassword);

        if (studentId != -1) {
            System.out.println("\n=== Logged in Successfully ===");

            boolean running = true;
            while (running) {
                System.out.println("\nOptions:");
                System.out.println("1. Add new student");
                System.out.println("2. View all students");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                int opt = sc.nextInt();
                sc.nextLine();

                switch (opt) {
                    case 1:
                        System.out.print("Enter Student Name: ");
                        String sName = sc.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Enter Grade: ");
                        double grade = sc.nextDouble();
                        sc.nextLine();
                        studentService.addStudent(sName, dept, grade);
                        break;

                    case 2:
                        studentService.listStudents();
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid Option");
                }
            }
        }

        sc.close();
        System.out.println("Thank you for using Student Management System!");
    }
}
