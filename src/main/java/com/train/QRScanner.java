package com.train;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class QRScanner {
    public static String readQRCode(String filePath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (IOException | NotFoundException e) {
            System.out.println(" Error reading QR Code: " + e.getMessage());
            return null;
        }
    }
}
