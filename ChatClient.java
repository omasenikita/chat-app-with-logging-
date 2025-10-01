/*
 * ChatClient.java
 * Author: Nikita
 * Description: Client-side application for peer-to-peer chat using Java Sockets.
 * Features: Real-time messaging, timestamped logging, cross-platform compatibility.
 */

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5100);
        System.out.println(" Connected to server at localhost:5100");

        PrintStream outStream = new PrintStream(socket.getOutputStream());
        BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

        FileWriter logWriter = new FileWriter("chat_log_client.txt", true);

        System.out.println(" Chat Messenger Ready");
        System.out.println("----------------------------------------------------");

        String clientMsg, serverMsg;
        while (true) {
            System.out.print("Enter message: ");
            clientMsg = consoleInput.readLine();
            outStream.println(clientMsg);

            serverMsg = inStream.readLine();
            if (serverMsg == null) break;

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.println("Server says: " + serverMsg);
            logWriter.write("[" + timestamp + "] Client: " + clientMsg + "\n");
            logWriter.write("[" + timestamp + "] Server: " + serverMsg + "\n");
        }

        logWriter.close();
        socket.close();
    }
}