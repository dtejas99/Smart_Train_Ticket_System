package com.train;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.time.LocalDate;

public class QRCodeVerifier {

    public static void verifyTicketWithCamera() {
        System.out.println("📸 Opening camera... Show your QR code to verify.");

        Webcam webcam = Webcam.getDefault();
        if (webcam == null) {
            System.out.println("❌ No webcam detected!");
            return;
        }

        // Set webcam resolution
        webcam.setViewSize(new java.awt.Dimension(640, 480));

        // Create and display webcam panel
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setMirrored(true);

        JFrame window = new JFrame("QR Code Scanner");
        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setAlwaysOnTop(true);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setAlwaysOnTop(false);

        try {
            boolean verified = false;

            while (!verified) {
                BufferedImage image = webcam.getImage();
                if (image == null) continue;

                try {
                    // Try to decode QR from camera feed
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);

                    String qrData = result.getText();
                    System.out.println("🔍 QR Detected: " + qrData);

                    // Connect to database
                    String url = "jdbc:mysql://localhost:3306/railway_system";
                    String user = "root";
                    String password = "root";
                    Connection conn = DriverManager.getConnection(url, user, password);

                    // ✅ Query by PNR instead of qr_code
                    String query = "SELECT * FROM tickets WHERE pnr = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);

                    // Extract PNR from the QR text
                    String scannedPnr = null;
                    if (qrData.startsWith("PNR:")) {
                        scannedPnr = qrData.split("\n")[0].replace("PNR:", "").trim();
                    }

                    System.out.println("DEBUG: Extracted PNR from QR = " + scannedPnr);
                    stmt.setString(1, scannedPnr);

                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        // Retrieve ticket info
                        String status = rs.getString("status");
                        String passenger = rs.getString("passenger_name");
                        String trainNo = rs.getString("train_number");
                        String seat = rs.getString("seat_number");
                        Date journeyDate = rs.getDate("journey_date");

                        System.out.println("🔎 DB match -> PNR: " + scannedPnr +
                                " | Train: " + trainNo +
                                " | Name: " + passenger +
                                " | Seat: " + seat +
                                " | Status: " + status +
                                " | Journey: " + journeyDate);

                        // ✅ Validation checks
                        boolean statusOk = "valid".equalsIgnoreCase(status);
                        boolean dateOk = !journeyDate.toLocalDate().isBefore(LocalDate.now());

                        System.out.println("DEBUG: statusOk=" + statusOk + ", dateOk=" + dateOk);

                        if (statusOk && dateOk) {
                            System.out.println("✅ VALID TICKET!");
                        } else {
                            System.out.println("❌ INVALID TICKET (Expired or Used)!");
                        }

                    } else {
                        System.out.println("❌ INVALID TICKET (Not Found)!");
                    }

                    verified = true;
                    conn.close();
                    break;

                } catch (NotFoundException e) {
                    // Continue scanning until a QR is detected
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webcam.close();
            window.dispose();
            System.out.println("📷 Camera closed successfully!");
        }
    }
}
