package com.train;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);
             Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                System.out.println("❌ Database connection failed!");
                return;
            }

            System.out.println("Select an option:");
            System.out.println("1. Verify Ticket using Camera");
            System.out.println("2. Generate New Ticket & QR Code");
            System.out.print("Enter choice (1/2): ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    QRCodeVerifier.verifyTicketWithCamera();
                    break;

                case "2":
                    generateTicketFlow(sc, conn);
                    break;

                default:
                    System.out.println("❌ Invalid choice! Exiting...");
                    break;
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void generateTicketFlow(Scanner sc, Connection conn) {
        try {
            // --- User Inputs ---
            System.out.print("Enter Train Number: ");
            String trainNo = sc.nextLine().trim();

            System.out.print("Enter Train Name: ");
            String trainName = sc.nextLine().trim();

            System.out.print("Enter Passenger Name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter Seat Number: ");
            String seat = sc.nextLine().trim();

            System.out.print("Enter Coach Number: ");
            String coach = sc.nextLine().trim();

            System.out.print("Enter Source Station: ");
            String source = sc.nextLine().trim();

            System.out.print("Enter Destination Station: ");
            String destination = sc.nextLine().trim();

            System.out.print("Enter Journey Date (YYYY-MM-DD): ");
            String journeyDateStr = sc.nextLine().trim();
            LocalDate journeyDate;
            try {
                journeyDate = LocalDate.parse(journeyDateStr);
            } catch (DateTimeParseException dtpe) {
                System.out.println("❌ Invalid date format. Use YYYY-MM-DD.");
                return;
            }

            // --- Generate PNR ---
            String pnr = generatePnr();

            // --- QR Code Data ---
            String qrData = "PNR: " + pnr +
                    "\nTrain: " + trainNo + " - " + trainName +
                    "\nName: " + name +
                    "\nSeat: " + seat +
                    "\nCoach: " + coach +
                    "\nClass: Sleeper" +
                    "\nJourney Date: " + journeyDate +
                    "\nFrom: " + source +
                    "\nTo: " + destination;

            // --- Generate QR Code Image ---
            String fileName = pnr + ".png";
            try {
                QRCodeGenerator.generateQRCode(qrData, fileName);
                System.out.println("✅ QR Code generated successfully: " + fileName);
            } catch (WriterException | IOException e) {
                System.out.println("❌ QR generation failed: " + e.getMessage());
                return;
            }

            // --- Save to Database ---
            String insertSQL = "INSERT INTO tickets " +
                    "(pnr, train_number, train_name, passenger_name, seat_number, coach_number, " +
                    "journey_date, qr_payload, qr_file, status, booking_datetime, class, quota) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
                ps.setString(1, pnr);
                ps.setString(2, trainNo);
                ps.setString(3, trainName);
                ps.setString(4, name);
                ps.setString(5, seat);
                ps.setString(6, coach);
                ps.setDate(7, Date.valueOf(journeyDate));
                ps.setString(8, qrData);
                ps.setString(9, fileName);
                ps.setString(10, "valid");
                ps.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(12, "Sleeper");
                ps.setString(13, "General");
                ps.executeUpdate();
            }

            System.out.println("✅ Ticket saved to database successfully!");

            // --- Generate Ticket Image ---
            TicketGenerator.generateTicket(
                    pnr,
                    trainNo,
                    trainName,
                    name,
                    LocalDateTime.now().toString(),
                    journeyDate.toString(),
                    source,
                    destination,
                    coach,
                    seat,
                    "Sleeper",
                    "General"
            );

            System.out.println("✅ Ticket generated successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error while generating ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Helper Functions ---
    private static String generatePnr() {
        long ts = System.currentTimeMillis() % 1000000;
        return "PNR" + ts + randomLetters(3);
    }

    private static String randomLetters(int n) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append((char) ('A' + r.nextInt(26)));
        return sb.toString();
    }
}
