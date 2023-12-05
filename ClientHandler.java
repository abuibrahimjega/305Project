
package javaapplication1;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ClientHandler implements Runnable {

     final private Socket clientSocket;
    final private BufferedReader reader;
    final private BufferedWriter writer;
    final private int clientId;

    public ClientHandler(Socket clientSocket, int clientId) throws IOException {
       this.clientSocket = clientSocket;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.clientId = clientId;
    }

    @Override
     public void run() {
        try {
            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("Client " + clientId + ": " + clientMessage);
                processClientMessage(clientMessage);
            }
        } catch (IOException e) {
            System.err.println("Error handling client #" + clientId + ": " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void processClientMessage(String message) throws IOException {
        if (message.equalsIgnoreCase("exit")) {
            // Handle exit command
            closeConnection();
        } else if (message.equalsIgnoreCase("bye")) {
            // Handle bye command
            writer.write("** YOU ARE OUT OF SERVICE **");
            writer.newLine();
            writer.flush();
            System.out.println("** Client " + clientId + " has logged out. **");
        } else if (message.equalsIgnoreCase("name")) {
            // Handle name command
            writer.write("YOU ARE CLIENT " + clientId);
            writer.newLine();
            writer.flush();
        } else if (message.equalsIgnoreCase("hi") || message.equalsIgnoreCase("hello") || 
                   message.equalsIgnoreCase("hai") || message.equalsIgnoreCase("hey") || 
                   message.equalsIgnoreCase("hay")) {
            // Handle greetings
            writer.write("-- WELCOME TO THE SERVER, LEGEND --");
            writer.newLine();
            writer.flush();
        } else if (message.equalsIgnoreCase("date")) {
            // Handle date command
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("\t\t hh:mm a -------\ndd/MM/yyyy\n");
            writer.write(sdf.format(currentDate));
            writer.newLine();
            writer.flush();
        } else if (message.equalsIgnoreCase("?")) {
            // Handle help command
            writer.write("Available commands: exit, bye, name, hi, date, ?");
            writer.newLine();
            writer.flush();
        } else {
            // Handle unknown command
            writer.write("Message Received.");
            writer.newLine();
            writer.flush();
        }
    }

    private void closeConnection() {
        try {
            clientSocket.close();
            System.out.println("Connection with Client " + clientId + " closed.");
        } catch (IOException e) {
            System.err.println("Error closing connection for client #" + clientId + ": " + e.getMessage());
        }
    }
}