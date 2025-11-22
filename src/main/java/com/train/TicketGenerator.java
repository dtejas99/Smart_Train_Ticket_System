package com.train;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketGenerator {

    public static void generateTicket(
            String pnrNumber,
            String trainNumber,
            String trainName,
            String passengerName,
            String bookingDateTime,
            String journeyDate,
            String sourceStation,
            String destinationStation,
            String coachNumber,
            String seatNumber,
            String travelClass,
            String quota
    )

    {
        try {
            // --- QR Code generation ---
            String qrData = "PNR: " + pnrNumber + "\nTrain: " + trainNumber + " - " + trainName +
                    "\nName: " + passengerName + "\nSeat: " + seatNumber + "\nCoach: " + coachNumber +
                    "\nClass: " + travelClass + "\nJourney Date: " + journeyDate;
            BitMatrix matrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.QR_CODE, 180, 180);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);

            int width = 850;
            int height = 500;
            BufferedImage ticket = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = ticket.createGraphics();

            // Anti-alias for smoother text and shapes
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            // Border
            g.setColor(new Color(0, 51, 102));
            g.setStroke(new BasicStroke(3));
            g.drawRect(10, 10, width - 20, height - 20);

            // Header strip
            g.setColor(new Color(0, 80, 180));
            g.fillRect(10, 10, width - 20, 70);

            // --- Logo ---
            try {
                BufferedImage logo = ImageIO.read(new File("C:\\Users\\dteja\\IdeaProjects\\Train_Ticket_System\\tickets\\irctc-logo-png_seeklogo-184098.png"));


                g.drawImage(logo, 25, 15, 60, 60, null);
            } catch (Exception e) {
                System.out.println("⚠️ Logo not found, skipping logo rendering.");
            }

            // Header text
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
            g.drawString("INDIAN RAILWAYS", 120, 55);

            // --- Ticket info section ---
            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString("Train Information", 40, 120);
            g.setStroke(new BasicStroke(1.5f));
            g.drawLine(40, 125, 250, 125);

            g.setFont(new Font("SansSerif", Font.PLAIN, 16));
            int y = 150;
            g.drawString("Train No : " + trainNumber, 40, y);
            g.drawString("Train Name : " + trainName, 300, y);
            y += 25;
            g.drawString("Journey Date : " + journeyDate, 40, y);
            g.drawString("Source : " + sourceStation, 300, y);
            y += 25;
            g.drawString("Destination : " + destinationStation, 40, y);
            g.drawString("Class : " + travelClass, 300, y);
            y += 25;
            g.drawString("Quota : " + quota, 40, y);

            // Passenger section
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString("Passenger Details", 40, y + 40);
            g.drawLine(40, y + 45, 250, y + 45);

            g.setFont(new Font("SansSerif", Font.PLAIN, 16));
            int yp = y + 70;
            g.drawString("Name : " + passengerName, 40, yp);
            g.drawString("PNR : " + pnrNumber, 300, yp);
            yp += 25;
            g.drawString("Coach : " + coachNumber, 40, yp);
            g.drawString("Seat : " + seatNumber, 300, yp);

            // QR code on the right
            g.drawImage(qrImage, 620, 150, 200, 200, null);
            g.setColor(Color.GRAY);
            g.drawRect(620, 150, 200, 200);

            // Status banner
            g.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
            g.setColor(new Color(0, 150, 0));
            g.drawString("STATUS: CONFIRMED ", 40, 370);

            // Booking info footer
            g.setFont(new Font("SansSerif", Font.ITALIC, 13));
            g.setColor(Color.DARK_GRAY);
            g.drawString("Booking Time: " + bookingDateTime, 40, 400);
            g.drawString("Valid only with original ID proof.", 40, 420);

            g.setColor(Color.GRAY);
            g.drawString("Generated on: " +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                    40, 440);

            g.dispose();

            // Save ticket
            File dir = new File("tickets");
            if (!dir.exists()) dir.mkdirs();

            Path outputPath = Paths.get("tickets/" + pnrNumber + "_ticket.png");
            ImageIO.write(ticket, "png", outputPath.toFile());

            System.out.println("✅ Ticket generated successfully: " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
