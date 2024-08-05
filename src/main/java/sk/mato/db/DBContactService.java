package sk.mato.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBContactService {
    private static final String READ_ALL = "SELECT * FROM contact";

    private static final String CREATE = "INSERT INTO contact (name, email, phone) VALUES (?, ?, ?)";

    private static final String DELETE = "DELETE FROM contact WHERE id = ?";

    private static final String UPDATE = "UPDATE contact SET name = ?, email = ?, phone = ? WHERE id = ?";

    private static final Logger logger = LoggerFactory.getLogger(DBContactService.class);

    public List<Contact> readAll() {
        try (Connection connection1 = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection1.prepareStatement(READ_ALL);
             ResultSet resultSet = ps.executeQuery()) {
            List<Contact> contacts = new ArrayList<>();

            while (resultSet.next()) {
                contacts.add(new Contact(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                ));
            }
            return contacts;
        } catch (SQLException e) {
            System.out.println("Error connecting");

        }
        return null;
    }

    public int create(String name, String email, String phone) {
        try (
                Connection conn = HikariCPDataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(CREATE))
        {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
           return preparedStatement.executeUpdate();

        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("email or phone already exists");
            return 0;
        }

        catch (SQLException e) {
            logger.error("Error creating a new Contact");
            return 0;
        }

    }
    public int delete(int id) {
        try(Connection conn = HikariCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting a contact");
            return 0;
        }
    }

    public int edit (Contact contact) {
        try(Connection conn = HikariCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE)){
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getEmail());
            preparedStatement.setString(3, contact.getPhone());
            preparedStatement.setInt(4, contact.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("email or phone already exists");
            return 0;
        }
        catch (SQLException e) {
            logger.error("Error editing a new Contact");
            e.printStackTrace();
            return 0;
        }

    }

}
