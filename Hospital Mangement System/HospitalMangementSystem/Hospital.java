package HospitalMangementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospital {
    private static final String url = "jdbc:mysql://localhost:3306/hospitalmanagementsystem";
    private static final String password = "Kingstaromega2611";
    private static final String user = "root";

    Connection connection;

    Hospital(Connection connection) {
        this.connection = connection;

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            Connection connection = DriverManager.getConnection(url, user, password);
            Patient p = new Patient(connection, sc);
            Doctors d = new Doctors(connection);

            do {
                System.out.println("+------------+-------------------+------------+");
                System.out.println("|           HOSPITAL MANAGEMENT SYSTEM        |");
                System.out.println("+------------+-------------------+------------+");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. Delete Patient and Appointment");
                System.out.println("4. Avaliable Doctor");
                System.out.println("5. Book Appointment");
                System.out.println("6. Show Appointment");
                System.out.println("7. Exit");
                System.out.print("\n\nEnter Opration :");
                int ch = sc.nextInt();

                switch (ch) {
                    case 1:
                        p.addPatinet();
                        System.out.println();
                        break;
                    case 2:
                        p.viewPatient();
                        System.out.println();

                        break;
                    case 3:
                        p.deletepatient();
                        System.out.println();

                        break;
                    case 4:
                        d.viewDoctors();
                        System.out.println();

                        break;
                    case 5:
                        bookappointment(p, d, connection, sc);
                        System.out.println();

                        break;
                    case 6:
                        p.showappointments();
                        System.out.println();

                        break;
                    case 7:
                        System.out.print("Exit");
                        System.out.println();

                        break;
                    default:
                        System.out.print("Invalid choice");

                }

            } while (true);

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public static void bookappointment(Patient p, Doctors d, Connection connection, Scanner sc) {

        System.out.print("Enter Patient id: ");
        int patient_id = sc.nextInt();

        System.out.print("Enter Doctor id: ");
        int doctor_id = sc.nextInt();
        System.out.print("enter Appointment date in [ YYYY-MM-DD ]: ");
        String appo_date = sc.next();

        if (p.getpatient(patient_id) && d.getDoctors(doctor_id)) {
            if (doctoravaliable(doctor_id, appo_date, connection)) {
                String addappointment = "INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES (?,?,?)";
                try {
                    PreparedStatement ps = connection.prepareStatement(addappointment);
                    ps.setInt(1, patient_id);
                    ps.setInt(2, doctor_id);
                    ps.setString(3, appo_date);
                    int rowsinserted = ps.executeUpdate();
                    if (rowsinserted > 0) {
                        System.out.println("Appointment Booked Succesfully !!");
                    } else {
                        System.out.println("Failed to Book an Appointment");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println(
                        "Doctor not avaliable on this date [ " + appo_date + " ]" + " Kindly refer another date ");
            }

        } else {
            System.out.println("Either Patient Or Dotcor does not exist !!");
        }

    }

    private static boolean doctoravaliable(int doctor_id, String appo_date, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctor_id);
            ps.setString(2, appo_date);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    return true;

                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

}
