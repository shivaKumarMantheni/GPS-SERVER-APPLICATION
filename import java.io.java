import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.sql.*;


public class PortListener {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/serverport"; // Database URL
    private static final String DB_USER = "root"; // Database username
    private static final String DB_PASSWORD = "Nani@2114"; // Database password

    private ServerSocket serverSocket;

    public static void main(String[] args) {
    	PortListener manager = new PortListener();
        
        // Start the server on a specified port
        manager.startServer(10211);
        
        // Retrieve and print the port number
        int port = manager.getPort();
        System.out.println("Server is listening on port: " + port);
        
        // Simulate some operations or delays
        try {
            Thread.sleep(5000); // Wait for 5 seconds before stopping the server
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        // Stop the server and close the port
        manager.stopServer();
    }

    // Starts the server and listens on the specified port
    public void startServer(int port) {
        try {
        	
            serverSocket = new ServerSocket(port);
            System.out.println("Server started and listening on port " + port);

            // Accept incoming connections
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getRemoteSocketAddress());

            // Read data from the client
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("Received: " + line);          
                saveDataToDatabase(line);

        } 
        
    }
       
        catch (IOException | SQLException e) {
            System.err.println("Error starting server on port " + port + ": " + e.getMessage());
        }
    }
    
    
    private void saveDataToDatabase(String data) throws SQLException {
    	
    	Connection connection = null;
	     PreparedStatement preparedStatement = null;

	        try {
	            // Load the JDBC driver (for MySQL)
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            
	            // Establish a connection to the database
	            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

	            // SQL query to insert data
	            String sql = "INSERT INTO serverdata (vehileNum, DATE, TIME,longitude) VALUES (?, ?, ?,?)";

	            // Create a PreparedStatement
	            preparedStatement = connection.prepareStatement(sql);

	            // Split the data based on comma
	            String [] words = data.split("#");
	    		String [] parts = words[0].split(",");
	    		
//	            if (parts.length == 30) {
	                // Set parameters
	                preparedStatement.setString(1,(parts[0].trim()));
	                preparedStatement.setInt(2, Integer.parseInt(parts[2].trim()));
	                preparedStatement.setInt(3, Integer.parseInt(parts[3].trim()));
	                preparedStatement.setFloat(4, Float.parseFloat(parts[4].trim()));
	                

	                // Execute the INSERT statement
	                preparedStatement.executeUpdate();
//	                System.out.println("Data saved to database: " + data);
//	            } else {
//	                System.err.println("Invalid data format: " + data);
//	            }
	            // Execute the update
	            int rowsAffected = preparedStatement.executeUpdate();
	            System.out.println("Rows affected: " + rowsAffected);

	        } catch (ClassNotFoundException e) {
	            System.out.println("JDBC Driver not found.");
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.out.println("Database error.");
	            e.printStackTrace();
	        } finally {
	            // Close resources
	            try {
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    	
    // Retrieves the port number the server is listening on
    public int getPort() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            return serverSocket.getLocalPort();
        } else {
            throw new IllegalStateException("ServerSocket is not initialized or already closed.");
        }
    }

    // Stops the server and closes the port
    public void stopServer() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("Server stopped and port " + getPort() + " is now closed.");
            } catch (IOException e) {
                System.err.println("Error closing server on port " + getPort() + ": " + e.getMessage());
            }
        } else {
            System.out.println("Server is already stopped or was never started.");
        }
    }
}