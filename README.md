# **Smart Train Ticket Verification System**

This project is a Java-based application designed to generate and verify railway tickets using QR codes. It provides a simple ticket management workflow where new tickets are created with a unique PNR, stored in a MySQL database, and later verified through camera-based QR scanning. The goal is to offer a digital, efficient system that supports quick ticket validation for railway staff.

### **Features**

* Generates tickets with PNR, train details, passenger information, seat and coach numbers, class, and quota.

* Creates QR codes containing ticket data.

* Saves ticket records in a MySQL database.

* Uses the system camera to scan QR codes for verification.

* Validates tickets by matching QR information with stored records.

### **Technologies Used**

* Java (JDK 17+)

* MySQL

* Maven

* ZXing (QR code generation and decoding)

* Webcam Capture API

* IntelliJ IDEA

### **Project Structure**

<img width="164" height="138" alt="image" src="https://github.com/user-attachments/assets/4e5887de-3a5f-4fec-b1cf-9fa5f4969982" />


### **How It Works**

####  **Ticket Generation**
The user enters train number, passenger name, and seat number.
The system generates a PNR, creates a QR code, produces a digital ticket image, and stores the ticket in the MySQL database.

####  **Ticket Verification**
The verification mode activates the system camera.
When a QR code is shown, the application decodes it and verifies the ticket against database records.

### **Future Enhancements**

* Exporting tickets as PDF

* Updating ticket status after verification

* Integration with actual railway data sources

* Android or web-based interface

#### Author

Tejas D
B.Tech Student – Java Developer


## **Snapshots**

### **IntellJ IDE**
<img width="959" height="599" alt="image" src="https://github.com/user-attachments/assets/e860b7e2-2784-464b-b63b-1b4c58e5d8b5" />

### **When you run the Main.java File**

<img width="530" height="182" alt="image" src="https://github.com/user-attachments/assets/356f3919-7a25-4f69-8929-f5c1140c0c1c" />


## **Suppose if you choose choice 2 - Generate newTicket & QR code**
It will asks some following basic credentials to generate the ticket
* Enter Train Number: 
* Enter Train Name: 
* Enter Passenger Name: 
* Enter Seat Number: 
* Enter Coach Number: 
* Enter Source Station: 
* Enter Destination Station: 
* Enter Journey Date (YYYY-MM-DD):

If you fill all the information currently like this,it will generates the new ticket along with QR in it

<img width="660" height="404" alt="image" src="https://github.com/user-attachments/assets/e8515851-826e-4ac4-9e13-355908aac942" />




### **Changes in the tickets file location and the Database**

### **Before Genarating**

#### **QR Ticket File Location-No ticktes are there**

<img width="959" height="221" alt="image" src="https://github.com/user-attachments/assets/6ff875be-e878-4922-8f1e-9526c2d57bfc" />



#### **DataBase-Tickets table is empty**

<img width="329" height="181" alt="image" src="https://github.com/user-attachments/assets/c0b3efbc-8963-47d6-b179-5393e5be0872" />



### **After Ticket generating**

#### **Ticket File Location - New Ticket added automatically**

<img width="959" height="221" alt="image" src="https://github.com/user-attachments/assets/60f8a7c3-1448-4584-a6f5-ce16ee9e04cb" />

#### **To the Database - Automatically tuple added**

<img width="959" height="110" alt="image" src="https://github.com/user-attachments/assets/5b33c5df-864a-4913-a91f-b2a90a4e1c2a" />

### The Generated Ticket is 

<img width="850" height="500" alt="PNR202657WHV_ticket" src="https://github.com/user-attachments/assets/4746fbdd-ae7d-40c7-835b-e6e0061b012a" />


##  **Suppose if you choose choice 1 -Verify ticket using Camera**

#### The camera will be opened like this

<img width="959" height="599" alt="image" src="https://github.com/user-attachments/assets/f5398a43-06c1-41ec-88f6-176e6c6b498d" />



#### If you show the valid generated ticket properly - it will give message like this

<img width="884" height="451" alt="image" src="https://github.com/user-attachments/assets/cfbfd7a5-4ae1-427a-adbd-d62e7c83174e" />



#### If you show any invalid or expired ticket - it will give message like this

<img width="808" height="423" alt="image" src="https://github.com/user-attachments/assets/e30a868b-e4ba-44b8-82df-8d8bd2da5344" />















