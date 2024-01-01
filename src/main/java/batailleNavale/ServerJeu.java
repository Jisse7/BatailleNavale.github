
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
/**
 *
 * @author User
 */
public class ServerJeu {



    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(4040);
        System.out.println("LANCEMENT du serveur de jeu");

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

         LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = date.format(formatter);

        String desktopPath = System.getProperty("user.home") + "\\Desktop";

              // Crée le dossier "replayBTN" sur le bureau
        String replayFolderPath = desktopPath + "\\ReplaysBTN";
        Path replayFolder = Paths.get(replayFolderPath);
        Files.createDirectories(replayFolder);

        String filePathPlayer1 = replayFolderPath + "\\actions_joueurs_" + formattedDate + ".txt";

        // Crée un verrou pour synchroniser l'écriture dans le fichier
        Lock fileWriteLock = new ReentrantLock();

        Thread player1Thread = new Thread(() -> {
            try {

               while (true) {
                    String message = player1In.readLine();
                    if (message != null) {

                        System.out.println("Joueur 1 : " + message);


                        // Enregistre l'action du premier joueur dans un fichier dédié
                          writeToFile(fileWriteLock, filePathPlayer1, "1:" + message);

                        // Transmet le message au deuxième joueur
                         player2Out.flush();
                        player2Out.println(message);


                      //  player2Out.print(message);




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
                    // player1Out.println(String.valueOf(compteurGlobal));

                    if (message != null) {


                        System.out.println("Joueur 2 : " + message);

                        // Enregistre l'action du deuxième joueur dans un fichier dédié
                        writeToFile(fileWriteLock, filePathPlayer1, "2:" + message);
                        // Transmet le message au premier joueur
                       // player1Out.flush();
                       player1Out.println(message);


                     //   player1Out.print(message);

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


