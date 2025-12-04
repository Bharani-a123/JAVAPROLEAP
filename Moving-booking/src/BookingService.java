import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookingService {
    public void bookSeat(int userId, int movieId, String seatNo) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO bookings(user_id, movie_id, seat_no) VALUES (?, ?, ?)"
            );
            ps.setInt(1, userId);
            ps.setInt(2, movieId);
            ps.setString(3, seatNo);

            ps.executeUpdate(); 
            System.out.println("Seat booked successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
