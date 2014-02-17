/*
 Cette class fait le choix du pilote de connection avec la base Mysql
 et etablie la connextion avec la base 
 ensuite insert et recupÃ©r des donneÃ©s de la base 
 et exploite les donnÃ©es recuperÃ©s pour les afficher
 */

import java.sql.*; // importer toutes les class de JDBC

public class ConnexionMySQL {

    public ConnexionMySQL() {
        System.out.println("Instentiation de La Class JDBC");

    }
	  //---------------------- Fonction de Connection avec la base de donées

    public void connect(String Nom_de_la_Base) throws ClassNotFoundException, SQLException {
        // chargement du pilot
        Class.forName("com.mysql.jdbc.Driver");
        // Etablissement de la connection
        String DBurl = "jdbc:mysql://localhost/" + Nom_de_la_Base;
        Connection con = DriverManager.getConnection(DBurl, "root", "");
        if (con != null) {
            System.out.println("Connection est bien etablie Ã  la base : >> " + Nom_de_la_Base);
        }

        // CrÃ©ation des rÃ©quete 
        Statement smt = con.createStatement();

   //Selection
    }
	 //__________________________

    //Fonction de l'insertion de la base de données
    public void insert_Scores(String Nom_de_la_Base, String Nom_Table, int diffeculté, String Durée, int id, int idUtilisateur, int Score) throws ClassNotFoundException, SQLException {
        // chargement du pilot
        Class.forName("com.mysql.jdbc.Driver");
        // Etablissement de la connection
        String DBurl = "jdbc:mysql://localhost/" + Nom_de_la_Base;
        Connection con = DriverManager.getConnection(DBurl, "root", "");
        if (con != null) {
            System.out.println("Connection est bien etablie Ã  la base : >> " + Nom_de_la_Base);
        }

        // CrÃ©ation des rÃ©quete 
        Statement smt = con.createStatement();

        //insertion
// int i = smt.executeUpdate("INSERT INTO `partie`.`partie-joueur` VALUES ("+a+", "+b+", "+c+", "+nom_du_joueur+", "+Date+");") ;
        int i = smt.executeUpdate("INSERT INTO `partie`.`" + Nom_Table + "` VALUES (" + diffeculté + ", " + id + "," + Durée + ", " + idUtilisateur + ", " + Score + ");");
   // int int int String String 

   // int int int date int.
        if (i != 0) {
            System.out.println("Insertion faite dans la tables Scores");
        }
        System.out.println("____________________________");
        if (i == 0) {
            System.out.println("Insertion n'est pas faite");
        }
        System.out.println("____________________________");
    }
//_______________________________

    public void insert_Utilisateurs(String Nom_de_la_Base, String Nom_Table, String Mail, int b, String MOtdPass, String Pseudo) throws ClassNotFoundException, SQLException {
        // chargement du pilot
        Class.forName("com.mysql.jdbc.Driver");
        // Etablissement de la connection
        String DBurl = "jdbc:mysql://localhost/" + Nom_de_la_Base;
        Connection con = DriverManager.getConnection(DBurl, "root", "");
        if (con != null) {
            System.out.println("Connection est bien etablie Ã  la base : >> " + Nom_de_la_Base);
        }

// CrÃ©ation des rÃ©quete 
        Statement smt = con.createStatement();

        //insertion
//int i = smt.executeUpdate("INSERT INTO `partie`.`partie-joueur` VALUES ("+a+", "+b+", "+c+", "+nom_du_joueur+", "+Date+");") ;
        int i = smt.executeUpdate("INSERT INTO `partie`.`" + Nom_Table + "` VALUES (" + Mail + ", " + b + "," + MOtdPass + ", " + Pseudo + ");");
  // int int int String String 

  // int int int date int.
        if (i != 0) {
            System.out.println("Insertion faite dans la table Utilisateurs");
        }
        System.out.println("____________________________");
        if (i == 0) {
            System.out.println("Insertion n'est pas faite dans la table Utilisateurs");
        }
        System.out.println("____________________________");
    }
//_______________________________

   // Fonction d'affichage du contenue de la base de donées
    public void Afficher_Score(String Nom_de_la_Base) throws ClassNotFoundException, SQLException {
        // chargement du pilot
        Class.forName("com.mysql.jdbc.Driver");
        // Etablissement de la connection
        String DBurl = "jdbc:mysql://localhost/" + Nom_de_la_Base;
        Connection con = DriverManager.getConnection(DBurl, "root", "");
        if (con != null) {
            System.out.println("Connection est bien etablie Ã  la base : >> " + Nom_de_la_Base);
        }

        // CrÃ©ation des rÃ©quete 
        Statement smt = con.createStatement();

        //Selection
        ResultSet rs = smt.executeQuery("SELECT *  FROM  `partie-joueur` LIMIT 0 , 30");
        //affichage de selection
        while (rs.next()) {

            String score = rs.getString("Score");
            String Diffic = rs.getString("Difficulté");
            String Durée = rs.getString("Durée");
            String Joueur = rs.getString("Joueur");
            String Date = rs.getString("Date");

            System.out.println("Score :" + score);
            System.out.println("Difficulté" + Diffic);
            System.out.println("Durée" + Durée);
            System.out.println("Nom_Joueur" + Joueur);
            System.out.println("Date" + Date);
        }
    }
//_______________________________

}
