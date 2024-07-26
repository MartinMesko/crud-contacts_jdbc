package mato;

import java.sql.*;
import java.util.stream.Stream;

public class Main
{
    public static void main( String[] args )
    {
        String select = "SELECT * FROM contact";
        String connection = "jdbc:mysql://localhost:3306/contacts";

        try {
            Connection connection1 = DriverManager.getConnection(connection, "root", "2030isNowLinda2015");
            PreparedStatement ps = connection1.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phone = rs.getInt("phone");

                System.out.println("id: " + id + " name: " + name + " email: " + email + " phone: " + phone);

            }
        } catch (SQLException e) {
            System.out.println("Error connecting");
        }
    }
}
