package com.train;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;
import java.sql.*;

public class verifyTicket {

    public static void verifyTicket() {
        try {
            Webcam webcam = Webcam.getDefault();
            webcam.open();
            System.out.println("🎥 Camera opened! Show your QR code...");

            while (true) {
                BufferedImage image = webcam.getImage();
                if (image == null) continue;

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);

                if (result != null) {
                    String qrCode = result.getText();
                    System.out.println("🔍 QR Detected: " + qrCode);
                    checkDatabase(qrCode);
                    break;
                }
            }
            webcam.close();
        } catch (Exception e) {
            System.out.println("⚠️ QR not detected. Try again...");
        }
    }

    private static void checkDatabase(String qrCode) {
        String url = "jdbc:mysql://localhost:3306/train_tickets"; // change DB name
        String user = "root"; // your username
        String password = "root"; // your password

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM tickets WHERE qr_code = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, qrCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ VALID TICKET FOUND!");
                System.out.println("Name: " + rs.getString("passenger_name"));
                System.out.println("Train: " + rs.getString("train_number"));
                System.out.println("Seat: " + rs.getString("seat_number"));
                System.out.println("Status: " + rs.getString("status"));
            } else {
                System.out.println("❌ INVALID TICKET!");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

