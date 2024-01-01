/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package batailleNavale;


import static batailleNavale.BatailleNavale.audioInput;
import static batailleNavale.BatailleNavale.clip;
import static batailleNavale.BatailleNavale.musicPath;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author User
 */
public class BatailleNavale1 extends javax.swing.JFrame {

    //private ServerBTN server;
    static String jeSuisPret = "false";
    static String j2EstPret = "false";

    static String chrono = " ";

    static String nom;
    static String nom2;

    Socket socket = new Socket("localhost", 8080);
    PrintWriter outToGive = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader inToGive = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    private int tailleGrille;
    private final int tailleGrilleMulti = 10;
    private Joueur joueur1, joueur2;

    //Multi
    private JoueurMulti j1, j2;
    static boolean bothPret;
    static String start;
    //private boolean partieDemarree = false;
    FenetreJoueurMulti fj1;

 static File path;

    String filepath = "bo1.wav";
    static AudioInputStream audioInput;
    static InputStream musicPath;
    static Clip clip;

    protected static final Font textFont = new Font("Fira Sans", 0, 12);
    protected static final Font pannelFont = new Font("Fira Sans", 1, 12);
    protected static final Font buttonFont = new Font("Fira Sans", 1, 13);

    protected static final Color bgColor = new Color(255, 255, 255);
    protected static final Color gridColor = new Color(211, 217, 234);
    protected static final Color FontColor = new Color(41, 79, 107);
    protected static final Color caseColor = new Color(245, 246, 251);

    protected static final Color aLeau = new Color(42, 58, 144);
    protected static final Color touche = new Color(234, 40, 57);
    protected static final Color navire = new Color(0, 165, 81);

    /**
     * Creates new form BatailleNavale1
     */
    public BatailleNavale1() throws Exception {
        initComponents();
        
        

        //Multijoueur
        boutonRejoindre.setVisible(false);
        boutonPret.setVisible(false);
        nomJ2.setVisible(false);
        panelCo.setVisible(true);
        panelChat.setVisible(false);
        isReady.setVisible(false);
        isReady2.setVisible(false);
        moiPret.setVisible(false);
        çaPret.setVisible(false);

        chat.setEditable(false);

        Statistiques stat = new Statistiques();

        partieJ.setText(String.valueOf(stat.getPartiesJouees()));
        partieP.setText(String.valueOf(stat.getPartiesPerdues()));
        partieG.setText(String.valueOf(stat.getPartiesGagnees()));
        tirRecu.setText(String.valueOf(stat.getTirsReçus()));
        tirsRates.setText(String.valueOf(stat.getTirsRates()));
        tirsT.setText(String.valueOf(stat.getTirsReussis()));
        winrate.setText(String.valueOf((100 * (double) (stat.getPartiesGagnees() / (double) stat.getPartiesJouees()))) + " %");
        precision.setText(String.valueOf((100 * (double) (stat.getTirsReussis() / ((double) (stat.getTirsReussis() + stat.getTirsRates()))))) + " %");

        if (stat.getPartiesJouees() > 10 && stat.getPartiesJouees() <= 20) {
            artMenu.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank2_150.jpg"));
            artMulti.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank2_150.jpg"));
            artReplays.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank2_150.jpg"));
            artStat.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank2_150.jpg"));
        }

        if (stat.getPartiesJouees() > 20) {

            artMenu.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank3_151.jpg"));
            artMulti.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank3_151.jpg"));
            artStat.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank3_151.jpg"));
            artReplays.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank3_151.jpg"));

        }

        nomP1.setText("Morty");
        nomP2.setText("Rick");

        choix1.add("Facile");
        choix1.add("Moyen");
        choix1.add("Chasseur ULtime");
        choix1.add("Euhhhhhh?");

        choix2.add("Facile");
        choix2.add("Moyen");
        choix2.add("Chasseur ULtime");
        choix2.add("Euhhhhhh?");

        new Thread(() -> {
            while (true) {
                try {
                    if (bothPret) {
                        panelChat.setVisible(true);
                        int[] tableauNaviresAuto = genererTableauNavires();
                        fj1 = new FenetreJoueurMulti(thisNom.getText(), 6, tableauNaviresAuto);
                        fj1.setBounds(880, 200, 850, 501);
                        j1 = new JoueurGraphiqueMulti(fj1.getGrilleDefense(), fj1.getGrilleTirs(), thisNom.getText(), nomJ2.getText());
                        System.out.println("instancié ligne 128");
                        demarrerPartieMulti();

                        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

                        bothPret = false;  // Remettez bothPret à false après avoir démarré la partie

                    }

                    String message = inToGive.readLine();
                    if (message != null) {

                        
                        System.out.println(message);
                        if(message.startsWith("Vous")){
                                chat.setText(chat.getText()+ "\n" + nomJ2.getText()+" : " + message.substring(7) );
                        }
                        if (message.startsWith("NOMJOUEUR:")) {
                            nom2 = message.substring("NOMJOUEUR:".length());
                            nomJ2.setText(nom2);
                            nomJ2.setVisible(true);

                            isReady2.setText(nomJ2.getText() + " est en attente");
                            isReady2.setVisible(true);
                        }

                        if (message.equals("START")) { // DANS LE CAS Où UN JOUEUR RECOIT EN PREMEIR l'APPEL DU PRET, CELUI ENVOIR START POUR INITIER LA PARTIE
                            bothPret = false;

                            panelChat.setVisible(true);

                            int[] tableauNaviresAuto = genererTableauNavires();
                            fj1 = new FenetreJoueurMulti(thisNom.getText(), 6, tableauNaviresAuto);
                            fj1.setBounds(880, 200, 850, 501);
                            System.out.println("instancié ligne 156");
                            j1 = new JoueurGraphiqueMulti(fj1.getGrilleDefense(), fj1.getGrilleTirs(), thisNom.getText(), nomJ2.getText());

                            demarrerPartieMulti();

                        }

                        if (message.equals("true")) {
                            j2EstPret = "true";
                            if (jeSuisPret.equals("true") && j2EstPret.equals("true")) {
                                bothPret = true;
                                panelChat.setVisible(true);

                            }
                            çaPret.setVisible(true);
                            if (bothPret) {
                                int[] tableauNaviresAuto = genererTableauNavires();
                                fj1 = new FenetreJoueurMulti(thisNom.getText(), 6, tableauNaviresAuto);
                                fj1.setBounds(880, 200, 850, 501);
                                j1 = new JoueurGraphiqueMulti(fj1.getGrilleDefense(), fj1.getGrilleTirs(), thisNom.getText(), nomJ2.getText());
                                panelChat.setVisible(true);
                                System.out.println("instancié ligne 176");
                                demarrerPartieMulti();

                                bothPret = false;
                                if (outToGive != null) {
                                    outToGive.println("START");
                                }
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        buttonGroup10 = new javax.swing.ButtonGroup();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jEditorPane5 = new javax.swing.JEditorPane();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jTabbedPane_onglets = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textFieldTailleGrille = new javax.swing.JEditorPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        nomP2 = new javax.swing.JEditorPane();
        rdbtnJ2Graphique = new javax.swing.JRadioButton();
        rdbtnJ2Texte = new javax.swing.JRadioButton();
        rdbtnJ2Auto = new javax.swing.JRadioButton();
        btnDemarrerPartie = new javax.swing.JButton();
        rdbtnJ1Graphique = new javax.swing.JRadioButton();
        rdbtnJ1Texte = new javax.swing.JRadioButton();
        rdbtnJ1Auto = new javax.swing.JRadioButton();
        choix2 = new java.awt.Choice();
        choix1 = new java.awt.Choice();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        nomP1 = new javax.swing.JEditorPane();
        histoError = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        logErr = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        artMenu = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        panelCo = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        nomJ2 = new javax.swing.JLabel();
        boutonRejoindre = new javax.swing.JButton();
        boutonPret = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        thisNom = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        isReady = new javax.swing.JLabel();
        isReady2 = new javax.swing.JLabel();
        moiPret = new javax.swing.JLabel();
        çaPret = new javax.swing.JLabel();
        artMulti = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panelChat = new javax.swing.JPanel();
        barreChat = new javax.swing.JTextField();
        boutonEnvoyer = new javax.swing.JButton();
        chatScroll = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        launch = new javax.swing.JButton();
        artReplays = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        choisir = new javax.swing.JButton();
        jLabel3_choix = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        tirRecu = new javax.swing.JLabel();
        partieJ = new javax.swing.JLabel();
        partieG = new javax.swing.JLabel();
        partieP = new javax.swing.JLabel();
        tirsRates = new javax.swing.JLabel();
        tirsT = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        winrate = new javax.swing.JLabel();
        precision = new javax.swing.JLabel();
        artStat = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jEditorPane1);

        jMenuItem1.setText("jMenuItem1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jScrollPane5.setViewportView(jEditorPane5);

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setResizable(false);

        jTabbedPane_onglets.setToolTipText("Menu");
        jTabbedPane_onglets.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTabbedPane_onglets.setName("Bataille Navale"); // NOI18N
        jTabbedPane_onglets.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jTabbedPane_ongletsComponentHidden(evt);
            }
        });

        jPanel4.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jPanel4ComponentRemoved(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Joueur 1 :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Taille de la grille  :");

        jScrollPane3.setViewportView(textFieldTailleGrille);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nom :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Joueur 2 : ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Nom :");

        jScrollPane4.setViewportView(nomP2);

        buttonGroup2.add(rdbtnJ2Graphique);
        rdbtnJ2Graphique.setText("Joueur Graphique");
        rdbtnJ2Graphique.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnJ2GraphiqueActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdbtnJ2Texte);
        rdbtnJ2Texte.setText("Joueur Texte");

        buttonGroup2.add(rdbtnJ2Auto);
        rdbtnJ2Auto.setText("Joueur Auto ");
        rdbtnJ2Auto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnJ2AutoActionPerformed(evt);
            }
        });

        btnDemarrerPartie.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDemarrerPartie.setText("Lancer la partie");
        btnDemarrerPartie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDemarrerPartieActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbtnJ1Graphique);
        rdbtnJ1Graphique.setText("Joueur Graphique");
        rdbtnJ1Graphique.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnJ1GraphiqueActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbtnJ1Texte);
        rdbtnJ1Texte.setText("Joueur Texte");

        buttonGroup1.add(rdbtnJ1Auto);
        rdbtnJ1Auto.setText("Joueur Auto ");
        rdbtnJ1Auto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnJ1AutoActionPerformed(evt);
            }
        });

        choix2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        choix2.setFocusable(false);
        choix2.setName("niveau"); // NOI18N

        choix1.setName("niveau"); // NOI18N

        jLabel6.setText("Niveau Joueur Auto :");

        jLabel7.setText("Niveau Joueur Auto :");

        jScrollPane8.setViewportView(nomP1);

        histoError.setForeground(new java.awt.Color(255, 0, 0));
        histoError.setText("Historique d'erreurs");

        logErr.setForeground(new java.awt.Color(255, 0, 51));
        jScrollPane6.setViewportView(logErr);

        jButton1.setText("Effacer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        artMenu.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank1_160.jpg")); // NOI18N
        artMenu.setText("artPix");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(rdbtnJ1Auto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)))
                        .addGap(19, 19, 19))
                    .addComponent(rdbtnJ1Texte)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbtnJ1Graphique, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbtnJ2Texte)
                            .addComponent(rdbtnJ2Graphique, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(rdbtnJ2Auto))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(choix1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(choix2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(artMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(350, 350, 350))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(btnDemarrerPartie))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addComponent(histoError, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(rdbtnJ1Graphique))
                    .addComponent(artMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(rdbtnJ1Texte, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdbtnJ1Auto)
                        .addComponent(jLabel6))
                    .addComponent(choix1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(rdbtnJ2Graphique)
                .addGap(27, 27, 27)
                .addComponent(rdbtnJ2Texte, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdbtnJ2Auto)
                            .addComponent(jLabel7))
                        .addGap(48, 48, 48)
                        .addComponent(btnDemarrerPartie)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(histoError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1)))
                    .addComponent(choix2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        jTabbedPane_onglets.addTab("Menu", jPanel4);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 204, 51));
        jLabel17.setText("Port : 8080");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 204, 51));
        jLabel19.setText("IP : 192.168.1.90");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 204, 51));
        jLabel18.setText("Vous êtes connecté au serveur   ");

        javax.swing.GroupLayout panelCoLayout = new javax.swing.GroupLayout(panelCo);
        panelCo.setLayout(panelCoLayout);
        panelCoLayout.setHorizontalGroup(
            panelCoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCoLayout.createSequentialGroup()
                        .addGroup(panelCoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCoLayout.setVerticalGroup(
            panelCoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 255, 204));
        jLabel11.setText("Taille de la grille  :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Vous :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Entrez votre Nom :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Joueur 2 : ");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Nom :");

        nomJ2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        nomJ2.setForeground(new java.awt.Color(0, 204, 204));
        nomJ2.setText("En attente d'un joueur ");

        boutonRejoindre.setText("Rejoindre");
        boutonRejoindre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonRejoindreActionPerformed(evt);
            }
        });

        boutonPret.setText("Prêt");
        boutonPret.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        boutonPret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonPretActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("6*6");

        thisNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thisNomActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 204));
        jLabel13.setText("info: entrez votre nom pour pouvoir rejoindre le serveur");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addComponent(jLabel11))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(thisNom, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(nomJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boutonPret, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonRejoindre, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(151, 151, 151))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(thisNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jLabel13)
                .addGap(1, 1, 1)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(nomJ2))
                .addGap(18, 18, 18)
                .addComponent(boutonRejoindre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(boutonPret)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        isReady.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        isReady.setForeground(new java.awt.Color(51, 153, 0));
        isReady.setText("Joueur 1 : ");

        isReady2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        isReady2.setForeground(new java.awt.Color(51, 153, 0));
        isReady2.setText("Joueur 2 : ");

        moiPret.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        moiPret.setText("Prêt");

        çaPret.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        çaPret.setText("Prêt");

        artMulti.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank1_160.jpg")); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Salon de partie");

        barreChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barreChatActionPerformed(evt);
            }
        });

        boutonEnvoyer.setText("Envoyer");
        boutonEnvoyer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boutonEnvoyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonEnvoyerActionPerformed(evt);
            }
        });

        chat.setColumns(20);
        chat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chat.setRows(5);
        chatScroll.setViewportView(chat);

        javax.swing.GroupLayout panelChatLayout = new javax.swing.GroupLayout(panelChat);
        panelChat.setLayout(panelChatLayout);
        panelChatLayout.setHorizontalGroup(
            panelChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barreChat)
                    .addGroup(panelChatLayout.createSequentialGroup()
                        .addComponent(chatScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonEnvoyer, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelChatLayout.setVerticalGroup(
            panelChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boutonEnvoyer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelChatLayout.createSequentialGroup()
                        .addComponent(chatScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(barreChat, javax.swing.GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(panelCo, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(artMulti, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(isReady)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(isReady2)
                .addGap(82, 82, 82))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(moiPret)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(çaPret)
                .addGap(104, 104, 104))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isReady)
                    .addComponent(isReady2))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(çaPret))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(moiPret)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(panelChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(artMulti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane_onglets.addTab("Multijoueurs", jPanel5);

        jLabel15.setText("Sélectionner un fichier pour démarrer un replay de partie :");

        jButton4.setText("Fermer");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        launch.setText("Lancer le replay");
        launch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        launch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchActionPerformed(evt);
            }
        });

        artReplays.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank1_160.jpg")); // NOI18N

        choisir.setForeground(new java.awt.Color(0, 102, 102));
        choisir.setText("Choisir :");
        choisir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choisirActionPerformed(evt);
            }
        });

        jLabel3_choix.setText(" ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(artReplays, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(choisir, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3_choix, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(launch, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(choisir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3_choix))
                .addGap(18, 18, 18)
                .addComponent(launch, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(artReplays, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jTabbedPane_onglets.addTab("Replays", jPanel6);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText("· Parties jouées : ");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setText("· Parties perdues :");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("· Parties gagnées :");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setText("· Nombre de tirs reçues :");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setText("· Nombre de tirs ratés :");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setText("· Nombre de tirs touchés :");

        tirRecu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tirRecu.setForeground(new java.awt.Color(255, 0, 51));
        tirRecu.setText("0.0");

        partieJ.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        partieJ.setForeground(new java.awt.Color(51, 51, 255));
        partieJ.setText("0.0");

        partieG.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        partieG.setForeground(new java.awt.Color(51, 51, 255));
        partieG.setText("0.0");

        partieP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        partieP.setForeground(new java.awt.Color(51, 51, 255));
        partieP.setText("0.0");

        tirsRates.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tirsRates.setForeground(new java.awt.Color(255, 51, 51));
        tirsRates.setText("0.0");

        tirsT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tirsT.setForeground(new java.awt.Color(255, 51, 51));
        tirsT.setText("0.0");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setText("Précision :");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setText("WinRate Victoire/Défaite:");

        winrate.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        winrate.setForeground(new java.awt.Color(51, 51, 255));
        winrate.setText("0.0");

        precision.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        precision.setForeground(new java.awt.Color(255, 0, 0));
        precision.setText("0.0");

        artStat.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\BatailleNavale\\src\\main\\java\\batailleNavale\\rank1_160.jpg")); // NOI18N

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setText("Statistiques Globaux Multijoueurs");

        jLabel33.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(204, 204, 0));
        jLabel33.setText("Jouez, montez en rang et déverouillez du contenus exclusifs ! ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(artStat, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(171, 171, 171))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(115, 115, 115))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(partieJ)
                                .addGap(54, 54, 54))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(partieP)
                                    .addComponent(partieG)
                                    .addComponent(tirRecu)
                                    .addComponent(tirsRates)
                                    .addComponent(tirsT)
                                    .addComponent(winrate)
                                    .addComponent(precision))
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(partieJ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(partieP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(partieG))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(tirRecu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(tirsRates))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(tirsT))
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(winrate))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(precision))
                .addGap(24, 24, 24)
                .addComponent(artStat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addGap(17, 17, 17))
        );

        jTabbedPane_onglets.addTab("Statistiques", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_onglets, javax.swing.GroupLayout.PREFERRED_SIZE, 495, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_onglets, javax.swing.GroupLayout.PREFERRED_SIZE, 702, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void rdbtnJ2GraphiqueActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void btnDemarrerPartieActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        demarrerPartie();
    }                                                 

    private void rdbtnJ2AutoActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jPanel4ComponentRemoved(java.awt.event.ContainerEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void rdbtnJ1GraphiqueActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void rdbtnJ1AutoActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTabbedPane_ongletsComponentHidden(java.awt.event.ComponentEvent evt) {                                                    
        // TODO add your handling code here:
    }                                                   

    private void boutonRejoindreActionPerformed(java.awt.event.ActionEvent evt) {                                                

        boutonPret.setVisible(true);
        panelCo.setVisible(true);
        //jeSuisPret = "true";

        isReady.setText(nom + " est en attente");
        isReady.setVisible(true);

        if (outToGive != null) {
            outToGive.println("NOMJOUEUR:" + nom); // Envoyer le nom du joueur
            // Envoyer l'état de prêt
        }


    }                                               

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void launchActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
        demarrerPartieMultiReplay();
    }                                      

    private void boutonPretActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        boutonPret.setVisible(false);
        //  isReady.setText(nom + " est prêt");
        // isReady.setVisible(true);

        // jeSuisPret="true";
        moiPret.setVisible(true);
        jeSuisPret = "true";
        //System.out.println(jeSuisPret);

        if (outToGive != null) {
            //     out.println("Je suis prêt");
            outToGive.println("true"); // true pour indiquer que vous êtes prêt

            if (jeSuisPret.equals("true") && j2EstPret.equals("true")) {
                panelChat.setVisible(true);

                boutonPret.setVisible(false);

            }
        }
    }                                          

    private void thisNomActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        nom = thisNom.getText();

        if (!(nom.equals(" "))) {
            boutonRejoindre.setVisible(true);

        }
    }                                       

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        logErr.setText(" ");
    }                                        

    private void boutonEnvoyerActionPerformed(java.awt.event.ActionEvent evt) {                                              

        String message = barreChat.getText();
        if (!message.isEmpty()) {
            // Ajout du message dans le champ de chat
            chat.setText(chat.getText() + "\nVous : " + message);
            barreChat.setText(""); // Effacer la barre de chat après l'envoi

            try {
                // Tentative d'envoi du message
                outToGive.println("Vous : " + message);
            } catch (Exception e) {
                // Traitement de l'exception, par exemple afficher un message d'erreur
                logErr.setText("Message null");
                System.err.println("Erreur lors de l'envoi du message: " + e.getMessage());
                // Vous pouvez également choisir d'afficher un message d'erreur dans l'interface utilisateur
            }

            // TODO add your handling code here:
        }

    }                                             

    private void barreChatActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void choisirActionPerformed(java.awt.event.ActionEvent evt) {                                        

        JFileChooser fileChooser = new JFileChooser();

        // Définir le répertoire initial du JFileChooser sur le bureau dans le dossier "Replays"
        File desktop = new File(System.getProperty("user.home") + File.separator + "Desktop");
        File replaysFolder = new File(desktop, "ReplaysBTN");
        fileChooser.setCurrentDirectory(replaysFolder);

        // Afficher la boîte de dialogue pour sélectionner un fichier
        int result = fileChooser.showOpenDialog(this);

        // Vérifier si l'utilisateur a sélectionné un fichier
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtenez le fichier sélectionné
            File selectedFile = fileChooser.getSelectedFile();
            path = selectedFile;

            // Afficher le chemin du fichier sélectionné dans jLabel3_choix
            jLabel3_choix.setText(selectedFile.getAbsolutePath());
        }
        if (path != null) {
            launch.setVisible(true);
        }
        // TODO add your handling code here:
    }                                       

    private int[] genererTableauNavires() {
        int[] tableauNaviresAuto = new int[15];
        if (tailleGrilleMulti <= 3) {
            tableauNaviresAuto[0] = 2;
            return tableauNaviresAuto;
        }

        if (tailleGrilleMulti <= 5) {
            tableauNaviresAuto[0] = 2;
            tableauNaviresAuto[1] = 2;
            return tableauNaviresAuto;
        }

        for (int i = 1; i < tailleGrilleMulti / 2; i++) {
            if (i <= 5) {
                tableauNaviresAuto[i - 1] = i;
            } else {
                tableauNaviresAuto[i - 1] = i - 5;
            }
        }
        return tableauNaviresAuto;

    }

    private void demarrerPartie() {
        boolean ready = true;

        try {
            tailleGrille = Integer.parseInt(textFieldTailleGrille.getText());

            if (tailleGrille <= 2) {
                logErr.setText("La taille de la grille doit être minimum 3 😡😡😡😡😡😡 ");
                textFieldTailleGrille.setText("6");
                ready = false;
            }

        } catch (NumberFormatException e) {
            logErr.setText("La valeur fournie dans tailleGrille n'est pas un nombre ou null 😡😡😡😡😡😡");
            textFieldTailleGrille.setText("6");
            ready = false;
        }

        String nomJ1 = nomP1.getText();
        String nomJ2 = nomP2.getText();

        if (ready == true) {
            int[] tableauNaviresAuto = genererTableauNavires();

            // Essayer de créer les joueurs
            try {
                if (rdbtnJ1Graphique.isSelected()) {

                    FenetreJoueur fj1 = new FenetreJoueur(nomJ1, tailleGrille, tableauNaviresAuto);
                    joueur1 = new JoueurGraphique(fj1.getGrilleDefense(), fj1.getGrilleTirs(), nomJ1);
                    fj1.setVisible(true);

                } else if (rdbtnJ1Texte.isSelected()) {

                    joueur1 = new JoueurTexte(new GrilleNavale(tailleGrille, tableauNaviresAuto), nomJ1);
                } else if (rdbtnJ1Auto.isSelected()) {

                    joueur1 = new JoueurAutoStrategieLvl1(new GrilleNavale(tailleGrille, tableauNaviresAuto), nomJ1);
                }

                if (rdbtnJ2Graphique.isSelected()) {

                    FenetreJoueur fj2 = new FenetreJoueur(nomJ2, tailleGrille, tableauNaviresAuto);
                    fj2.setBounds(880, 200, 850, 501);
                    joueur2 = new JoueurGraphique(fj2.getGrilleDefense(), fj2.getGrilleTirs(), nomJ2);
                    fj2.setVisible(true);

                } else if (rdbtnJ2Texte.isSelected()) {

                    joueur2 = new JoueurTexte(new GrilleNavale(tailleGrille, tableauNaviresAuto), nomJ2);

                } else if (rdbtnJ2Auto.isSelected()) {

                    joueur2 = new JoueurAutoStrategieLvl4(new GrilleNavale(tailleGrille, tableauNaviresAuto), nomJ2);
                }
            } catch (IllegalArgumentException e) {
                logErr.setText("La taille de la grille doit être entre 2 et 26");
                textFieldTailleGrille.setText("6");

                ready = false;
                System.out.println("problem ligne 1327");
            }

            if (ready == true) {
                //	pauseMusic();
                new Thread() {
                    public void run() {

                        try {
                            if (nomP2.getText().equals("") || (nomP1.getText().equals(""))) {
                                logErr.setText("Vous devez entrer un nom pour jouer");
                                nomP1.setText("ODIN");
                                nomP2.setText("BAHAMUT");
                            }
                            if (((rdbtnJ1Texte.isSelected()) || (rdbtnJ1Auto.isSelected()) || (rdbtnJ1Graphique.isSelected()))
                                    && (((!rdbtnJ2Texte.isSelected()) && (!rdbtnJ2Auto.isSelected()) && (!rdbtnJ2Graphique.isSelected())))) {
                                logErr.setText("Vous devez sélectionner un type de partie");
                                rdbtnJ2Graphique.setSelected(true);
                                rdbtnJ1Graphique.setSelected(true);
                            } else if (((rdbtnJ2Texte.isSelected()) || (rdbtnJ2Auto.isSelected()) || (rdbtnJ2Graphique.isSelected()))
                                    && (((!rdbtnJ1Texte.isSelected()) && (!rdbtnJ1Auto.isSelected()) && (!rdbtnJ1Graphique.isSelected())))) {
                                logErr.setText("Vous devez sélectionner un type de partie");
                                rdbtnJ1Graphique.setSelected(true);
                                rdbtnJ2Graphique.setSelected(true);

                            } else if (((!rdbtnJ2Texte.isSelected()) && (!rdbtnJ2Auto.isSelected()) && (!rdbtnJ2Graphique.isSelected()))
                                    && (((!rdbtnJ1Texte.isSelected()) && (!rdbtnJ1Auto.isSelected()) && (!rdbtnJ1Graphique.isSelected())))) {
                                logErr.setText("Vous devez sélectionner un type de partie");
                                rdbtnJ1Graphique.setSelected(true);
                                rdbtnJ2Graphique.setSelected(true);
                            }

                        } catch (IllegalArgumentException e) {
                            System.err.println("Une erreur s'est produite : " + e.getMessage());
                        }
                        joueur1.jouerAvec(joueur2);
                    }
                }.start();
            }
        }
    }

    private synchronized void demarrerPartieMulti() {

        boolean ready = true;

        tailleGrille = 10;

        String nomJ1 = thisNom.getText();
        //	String nomP2 = nomJ2.getText();

        if (ready == true) {
            // Essayer de créer les joueurs
            try {
                fj1.setVisible(true);

            } catch (IllegalArgumentException e) {

                ready = false;
            }

            if (ready == true) {
                //	pauseMusic();
                new Thread() {
                    public void run() {
                        System.out.println("Socket dans BTN1" + socket);
                        try {
                            System.out.println("Dans BT1 : flux entrant :" + inToGive + "flux sortant : " + outToGive);

                            j1.jouerAvecMulti(nomJ2.getText());
                        } catch (Exception ex) {
                            Logger.getLogger(BatailleNavale1.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.start();
            }
        }
    }
    
    
    public synchronized void demarrerPartieMultiReplay() {

        boolean ready = true;

        tailleGrille = 10;

        int[] tableauNaviresAuto = genererTableauNavires();
        
                String nomJ1 = nomP1.getText();
        String nomJ2 = nomP2.getText();

        if (ready == true) {
            // Essayer de créer les joueurs
            //	try {

       FenetreJoueur fj2 = new FenetreJoueur(nomJ2, 6, tableauNaviresAuto);
                    fj2.setBounds(50, 200, 850, 501);
                    joueur2 = new JoueurGraphique(fj2.getGrilleDefense(), fj2.getGrilleTirs(), nomJ1);
                  
                    
          

           FenetreJoueur fj1 = new FenetreJoueur(nomJ1, 6, tableauNaviresAuto);
                    fj1.setBounds(950, 200, 850, 501);
                    joueur1 = new JoueurGraphique(fj2.getGrilleDefense(), fj2.getGrilleTirs(), nomJ2);
                   

            fj1.setVisible(true);
            fj2.setVisible(true);

//			}  catch (IllegalArgumentException e) {
//				
//				ready = false;
//			}
            if (ready == true) {
                //	pauseMusic();
                new Thread() {
                    public void run() {

                        try {

                            joueur1.jouerAvecMultiReplay(joueur2, path);
                        } catch (Exception ex) {
                            Logger.getLogger(BatailleNavale1.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.start();
            }
        }

    }
    
 
   

    public static void playMusic() {
        try {
            if (musicPath.available() > 0) {
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void pauseMusic() {
        clip.stop();
        clip.close();
    }

    public static void initMusic(String location) {
        try {
            musicPath = BatailleNavale.class.getResourceAsStream("/resources/" + location);
            audioInput = AudioSystem.getAudioInputStream(musicPath);

            // playMusic();
        } catch (Exception e) {
            System.err.println("can't find file");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BatailleNavale1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BatailleNavale1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BatailleNavale1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BatailleNavale1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new BatailleNavale1().setVisible(true);
                } catch (Exception e) {
                    System.out.println("probleme lors du démarrage");
                }

            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel artMenu;
    private javax.swing.JLabel artMulti;
    private javax.swing.JLabel artReplays;
    private javax.swing.JLabel artStat;
    private javax.swing.JTextField barreChat;
    private javax.swing.JButton boutonEnvoyer;
    private javax.swing.JButton boutonPret;
    private javax.swing.JButton boutonRejoindre;
    private javax.swing.JButton btnDemarrerPartie;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup10;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private javax.swing.JTextArea chat;
    private javax.swing.JScrollPane chatScroll;
    private javax.swing.JButton choisir;
    private java.awt.Choice choix1;
    private java.awt.Choice choix2;
    private javax.swing.JLabel histoError;
    private javax.swing.JLabel isReady;
    private javax.swing.JLabel isReady2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JEditorPane jEditorPane5;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel3_choix;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane_onglets;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JButton launch;
    private javax.swing.JTextPane logErr;
    private javax.swing.JLabel moiPret;
    private javax.swing.JLabel nomJ2;
    private javax.swing.JEditorPane nomP1;
    private javax.swing.JEditorPane nomP2;
    private javax.swing.JPanel panelChat;
    private javax.swing.JPanel panelCo;
    private javax.swing.JLabel partieG;
    private javax.swing.JLabel partieJ;
    private javax.swing.JLabel partieP;
    private javax.swing.JLabel precision;
    private javax.swing.JRadioButton rdbtnJ1Auto;
    private javax.swing.JRadioButton rdbtnJ1Graphique;
    private javax.swing.JRadioButton rdbtnJ1Texte;
    private javax.swing.JRadioButton rdbtnJ2Auto;
    private javax.swing.JRadioButton rdbtnJ2Graphique;
    private javax.swing.JRadioButton rdbtnJ2Texte;
    private javax.swing.JEditorPane textFieldTailleGrille;
    private javax.swing.JTextField thisNom;
    private javax.swing.JLabel tirRecu;
    private javax.swing.JLabel tirsRates;
    private javax.swing.JLabel tirsT;
    private javax.swing.JLabel winrate;
    private javax.swing.JLabel çaPret;
    // End of variables declaration                   
}
