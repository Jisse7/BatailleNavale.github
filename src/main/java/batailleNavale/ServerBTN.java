
package batailleNavale;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
 *
 * @author User
 */
public class ServerBTN {
  
   
   
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("LANCEMENT du serveur");

        // Attend le premier client
        Socket player1Socket = serverSocket.accept();
        BufferedReader player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
        PrintWriter player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
     
        
        System.out.println("Premier joueur connecté ");

        // Attend le deuxième client
        Socket player2Socket = serverSocket.accept();
        BufferedReader player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
        PrintWriter player2Out = new PrintWriter(player2Socket.getOutputStream(), true);
     
        
        
        System.out.println("Deuxième joueur connecté ");
        
   
        Thread player1Thread = new Thread(() -> {
            try {

               while (true) {
                    String message = player1In.readLine();
                    if (message != null) {

                        System.out.println("Joueur 1 : " + message);


                        player2Out.println(message);

                  System.out.println("envoi vers j2 : " + message);
          
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        player1Thread.start();

        Thread player2Thread = new Thread(() -> {
            try {
                while (true) {
                   
                 
                    String message = player2In.readLine();


                    if (message != null) {


                        System.out.println("Joueur 2 : " + message);


                       player1Out.println(message);

                        
     System.out.println("envoi vers j1 : " + message);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        player2Thread.start();
    }


    private static void writeToFile(Lock lock, String filePath, String message) {
        lock.lock();
        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(filePath, true))) {
            fileWriter.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
    

