///////////////////////////////////////////////////////////////////////////////////////////////////
// File Name: ChatServer.java
// Author:    Nikita Omase
// Date :     01-06-2025
// Description: Server-side application for peer-to-peer chat using Java Sockets.
// Features: Real-time messaging, timestamped logging, cross-platform compatibility.
/////////////////////////////////////////////////////////////////////////////////////////////////////


import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5100);
        System.out.println("Server is running on port 5100...");

        Socket socket = serverSocket.accept();
        System.out.println(" Client connected successfully.");

        PrintStream outStream = new PrintStream(socket.getOutputStream());
        BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

        FileWriter logWriter = new FileWriter("chat_log_server.txt", true);

        System.out.println(" Chat Messenger Ready");
        System.out.println("----------------------------------------------------");

        String clientMsg, serverMsg;
        while ((clientMsg = inStream.readLine()) != null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.println("Client says: " + clientMsg);
            logWriter.write("[" + timestamp + "] Client: " + clientMsg + "\n");

            System.out.print("Enter reply: ");
            serverMsg = consoleInput.readLine();
            outStream.println(serverMsg);
            logWriter.write("[" + timestamp + "] Server: " + serverMsg + "\n");
        }

        logWriter.close();
        socket.close();
        serverSocket.close();
    }

}
