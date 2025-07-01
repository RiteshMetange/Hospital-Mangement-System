package HospitalMangementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {

    Connection connection;

    public Doctors(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors() {

        try {
            String show = "SELECT * from doctors";
            PreparedStatement ps = connection.prepareStatement(show);
            ResultSet resultSet = ps.executeQuery();
            System.out.println("Doctors Data:");
            System.out.println("Note : Only Admins Can Add Doctors");
            System.out.println("+------------+-------------------+----------------------+");
            System.out.println("| Doctors ID | Doctors Name      | Doctors Specilization|");
            System.out.println("+------------+-------------------+----------------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");

                System.out.printf("| %-11s| %-18s| %-20s |\n", id, name, department);
                System.out.println("+------------+-------------------+----------------------+");

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public boolean getDoctors(int id) {
        try {
            String showbyid = "SELECT id FROM doctors where id=?";
            PreparedStatement ps = connection.prepareStatement(showbyid);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;

    }

}
