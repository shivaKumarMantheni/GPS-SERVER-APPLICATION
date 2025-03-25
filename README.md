# GPS-SERVER-APPLICATION
Overview
PortListener is a Java-based server application designed to listen on a specified TCP port for incoming client connections. When a client sends data, the server reads the data, processes it, and stores relevant information into a MySQL database.

The server is specifically set up to handle real-time data sent by clients, such as vehicle data, and stores it in a structured format. This application is built using standard Java libraries, including java.net for networking, java.io for input/output operations, and java.sql for interacting with a MySQL database.

Features
Listening for Client Connections: The server listens for incoming connections on a specified TCP port.

Data Handling: The server reads incoming data from connected clients and processes the received data.

Database Integration: The data is stored in a MySQL database (serverport) for further analysis or record-keeping.

Logging: Server activity such as new client connections, received data, and database updates is logged to the console.

Modular Design: Easy to modify and extend for different data formats or additional processing.

Technologies Used
Java: Core language for creating the server.

MySQL: For storing processed data.

JDBC (Java Database Connectivity): For connecting and interacting with the MySQL database.

TCP/IP Sockets: For networking communication between the server and client.

BufferedReader and InputStreamReader: For reading data from the client.

Requirements
Before running the application, ensure that you have the following set up:

Java Development Kit (JDK): Ensure you have JDK 8 or higher installed on your machine. You can check your Java version by running the following command:

bash
Copy
java -version
MySQL Database: You need to have MySQL installed and running. Also, you should create the database (serverport) and the table (serverdata) for storing the data.

Example SQL to create the table:

sql
Copy
CREATE DATABASE serverport;
USE serverport;

CREATE TABLE serverdata (
    vehicleNum VARCHAR(255),
    date DATE,
    time TIME,
    longitude FLOAT
);
JDBC Driver: Ensure that the MySQL JDBC driver is available in your project's classpath. You can download it from the official MySQL website or use a dependency management tool like Maven to include it in your project.

Setup
1. Clone the Repository (If applicable)
If you are working with a Git repository, clone it to your local machine:

bash
Copy
git clone <repository-url>
cd PortListener
2. Configure Database Connection
Ensure the DB_URL, DB_USER, and DB_PASSWORD in the PortListener class are set to the correct values for your MySQL database:

java
Copy
private static final String DB_URL = "jdbc:mysql://localhost:3306/serverport"; // Database URL
private static final String DB_USER = "root"; // Database username
private static final String DB_PASSWORD = "your-password"; // Database password
3. Compile and Run
Compile the Java file:

Open a terminal or command prompt and navigate to the directory containing PortListener.java.

Run the following command to compile the program:

bash
Copy
javac PortListener.java
Run the Java program:

After compiling, run the program using the following command:

bash
Copy
java PortListener
The server will start listening on port 10211 by default, as specified in the main method. You can change the port number in the code if necessary.

How It Works
Server Starts: When the server starts, it listens for incoming client connections on the specified port (10211 by default). It uses a ServerSocket to wait for client connections.

Accepting Client Connections: Once a client connects, the server reads data from the client's input stream. The server expects the client to send data in a specific format, with fields separated by commas and records separated by a # symbol.

Processing Data: After receiving the data, the server splits the input into components (e.g., vehicle number, date, time, longitude) and prepares an SQL INSERT statement to store this data in the MySQL database.

Database Insertion: The server establishes a connection to the MySQL database and inserts the processed data into the serverdata table. The application uses JDBC to execute SQL queries.

Stopping the Server: The server will continue to listen for new connections until it is manually stopped, or an error occurs. You can stop the server by closing the program or adding additional logic to gracefully shut it down.

Sample Data Format
The server expects data from the client in the following format:

bash
Copy
vehicleNum,date,time,longitude#additional_data
For example:

bash
Copy
ABC123,2025-03-25,10:30:00,45.12345#some_other_data
The data will be parsed, and the relevant information will be stored in the serverdata table.

Error Handling
IOException: Handles issues with the socket and input/output operations.

SQLException: Handles issues related to the database operations.

ClassNotFoundException: Handles the error when the JDBC driver is not found.

The application logs errors to the console to assist with debugging.

Stopping the Server
To stop the server, you can either:

Interrupt the running process by pressing Ctrl + C in the terminal.

Modify the code to listen for a custom shutdown signal or handle graceful server termination.

Contribution
If you would like to contribute to the development of this project:

Fork the repository and clone it to your local machine.

Make your changes in a separate branch.

Test your changes thoroughly.

Submit a pull request with a detailed description of your changes.
