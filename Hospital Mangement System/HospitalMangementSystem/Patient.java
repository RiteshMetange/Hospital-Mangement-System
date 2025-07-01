package HospitalMangementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    Connection connection;
    Scanner sc;

    public Patient(Connection connection, Scanner sc) {
        this.connection = connection;
        this.sc = sc;

    }

    public void addPatinet() {
        System.out.print("Enter Patient Name :");
        String name = sc.next();
        System.out.print("Enter Age          :");
        int age = sc.nextInt();
        System.out.print("Enter Gender       :");
        String gender = sc.next();

        try {
            String insertquery = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(insertquery);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int rowsinserted = ps.executeUpdate();
            if (rowsinserted > 0) {
                System.out.println("\n ...Patient Added Successfully");
            } else {
                System.out.println("\n ...Failed to Add Patient");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void viewPatient() {

        try {
            String show = "SELECT * from patients";
            PreparedStatement ps = connection.prepareStatement(show);
            ResultSet resultSet = ps.executeQuery();
            System.out.println(" Patients Data:");
            System.out.println("+------------+-------------------+-------------+----------------+");
            System.out.println("| Patient ID | Patient Name      | Patient Age | Patient Gender |");
            System.out.println("+------------+-------------------+-------------+----------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                System.out.printf("| %-11s| %-18s| %-12s| %-15s|\n", id, name, age, gender);
                System.out.println("+------------+-------------------+-------------+----------------+");

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public boolean getpatient(int id) {
        try {
            String showbyid = "SELECT id FROM patients where id=?";
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

    public boolean deletepatient() {
        try {
            System.out.print("Enter Patient ID: ");
            int pid = sc.nextInt();

            System.out.print("Enter Appointment ID: ");
            int aid = sc.nextInt();

            // 1. Delete appointment first
            String delAppointment = "DELETE FROM appointments WHERE id = ?";
            PreparedStatement ps1 = connection.prepareStatement(delAppointment);
            ps1.setInt(1, aid);
            int rowsDeleted1 = ps1.executeUpdate();

            // 2. Then delete patient
            String delPatient = "DELETE FROM patients WHERE id = ?";
            PreparedStatement ps2 = connection.prepareStatement(delPatient);
            ps2.setInt(1, pid);
            int rowsDeleted2 = ps2.executeUpdate();

            if (rowsDeleted1 > 0 && rowsDeleted2 > 0) {
                System.out.println("The patient and their appointment record were deleted successfully.");
                return true;
            } else {
                System.out.println("Failed to delete one or both records.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showappointments() {
        try {
            String show = "Select * from appointments";
            PreparedStatement ps = connection.prepareStatement(show);
            ResultSet resultSet = ps.executeQuery();
            System.out.println("Appointments :");
            System.out.println("+----+------------+-----------+------------------+");
            System.out.println("| ID | patient ID | doctor ID | appointment Date |");
            System.out.println("+----+------------+-----------+------------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int pid = resultSet.getInt("patient_id");
                int did = resultSet.getInt("doctor_id");
                String adate = resultSet.getString("appointment_date");
                System.out.printf("| %-2s | %-10s | %-9s | %-16s |", id, pid, did, adate);
                System.out.println("\n+----+------------+-----------+------------------+");

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
}
