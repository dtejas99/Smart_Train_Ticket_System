**Smart Train Ticket Verification System**

This project is a Java-based application designed to generate and verify railway tickets using QR codes. It provides a simple ticket management workflow where new tickets are created with a unique PNR, stored in a MySQL database, and later verified through camera-based QR scanning. The goal is to offer a digital, efficient system that supports quick ticket validation for railway staff.

**Features**

* Generates tickets with PNR, train details, passenger information, seat and coach numbers, class, and quota.

* Creates QR codes containing ticket data.

* Saves ticket records in a MySQL database.

* Uses the system camera to scan QR codes for verification.

* Validates tickets by matching QR information with stored records.

**Technologies Used**

* Java (JDK 17+)

* MySQL

* Maven

* ZXing (QR code generation and decoding)

* Webcam Capture API

* IntelliJ IDEA

**Project Structure**

<img width="164" height="138" alt="image" src="https://github.com/user-attachments/assets/4e5887de-3a5f-4fec-b1cf-9fa5f4969982" />


**How It Works**

* **Ticket Generation**
The user enters train number, passenger name, and seat number.
The system generates a PNR, creates a QR code, produces a digital ticket image, and stores the ticket in the MySQL database.

* **Ticket Verification**
The verification mode activates the system camera.
When a QR code is shown, the application decodes it and verifies the ticket against database records.

**Future Enhancements**

* Exporting tickets as PDF

* Updating ticket status after verification

* Integration with actual railway data sources

* Android or web-based interface

Author

Tejas D
B.Tech Student – Java Developer
