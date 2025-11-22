package com.train;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.*;

public class QRCodeGenerator {
    public static void generateQRCode(String data, String fileName) throws WriterException, IOException {
        int width = 300;
        int height = 300;
        String fileType = "png";

        // ✅ Use absolute path for your QR folder
        String qrFolder = "C:/Users/dteja/IdeaProjects/Train_Ticket_System/qrcodes/";
        Path path = Paths.get(qrFolder + fileName + ".png");

        // ✅ Ensure the directory exists
        Files.createDirectories(path.getParent());

        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToPath(matrix, fileType, path);

        System.out.println("✅ QR Code generated: " + path);
    }
}
