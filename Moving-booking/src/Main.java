
import java.sql.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args)
    {
        Scanner sc =new Scanner(System.in);
        UserService userService = new UserService();
        MovieService movieService = new MovieService();
        BookingService bookingService = new BookingService();
         
       try(Connection con = DBConnection.getConnection()){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS count FROM movie");
            if(rs.next() && rs.getInt("count")==0){
                movieService.addMovie("Avengers:Endgame", "Action",250.00);
                movieService.addMovie("Inception", "Sci-fi",200.00);
                movieService.addMovie("Titanic", "Romance",150.00);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("===Welcome to Movie Ticket Booking System===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("Choose option");
        int choice = sc.nextInt();
        sc.nextLine();
        int userId= -1;
        if (choice==1)
        {
            System.out.print("Enter Name");
            String name = sc.nextLine();
            System.out.print("Enter Mail");
            String email= sc.nextLine();
            System.out.print("Enter Password");
            String password= sc.nextLine();
            userService.registerUser(name,email,password);}
            System.out.println("Enter email:");
            String email=sc.nextLine();
            System.out.println("Enter password:");
            String password=sc.nextLine();
            userId=userService.login(email, password);
            if(userId != -1){
                movieService.listMovies();
                int movieId =1;

               while (true) { 
                  System.out.println("Enter movie id to book: ");
                  movieId=sc.nextInt();
                  sc.nextLine();
                  
                  //Validate Movie ID exists

                  try(Connection con=DBConnection.getConnection())
                  {
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT id FROM movie WHERE id = "+ movieId);
                    if(rs.next())
                    {
                        break;
                    }
                    else{
                        System.out.println("Invalid Movie ID please try Again");

                    }
                  } catch(Exception e){

                    e.printStackTrace();
                  }
               }

               System.out.print("Enter seat number :");
               String seatNo = sc.nextLine();
               bookingService.bookSeat(userId,movieId,seatNo);
                    
                }
                sc.close();
                System.out.println("Thank you for Using Movie Booking System!");
            }

        }

    

