
package javaapplication1;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankServer {
    private static ArrayList<ClientHandler> clients;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);
    public static int clientCounter = 0;

    private static void log(String message) {
        // Consider adding logging functionality here
        System.out.println(message);
    }

    public static void main(String[] args) {
        clients = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(50999)) {
            log("Server started and listening on port 50999...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCounter++;

                log("Client #" + clientCounter + " connected.");

                // Create a new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientCounter);
                clients.add(clientHandler);
                pool.execute(clientHandler);
            }
        } catch (IOException e) {
            log("Error starting the server: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}
