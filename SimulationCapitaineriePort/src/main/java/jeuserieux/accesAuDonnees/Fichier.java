package jeuserieux.accesAuDonnees;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe permet, à partir d'un nom de fichier, d'écrire ou de lire son contenu
 * @author gary et jérémy
 */
public class Fichier {
    
    private final File _nomFichier;
    
    public Fichier(String nomFichier) {
        _nomFichier = new File(nomFichier);
    }
    
    /**
     * Cette fonction permet d'écrire du contenu dans _nomFichier
     * @param s contenu à écrire dans le fichier
     * @param append détermine si le fichier doit être écrasé ou si l'écriture se fait à la suite de son contenu actuel
     */
    public void ecrire(String s, boolean append) {
        try {
            FileWriter fw = new FileWriter(_nomFichier, append);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(s);
            
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Fichier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cette fonction lit le fichier _nomFichier et renvoie son contenu
     * @return retourne le contenu du fichier
     */
    public String lire() {
        try {
            StringBuilder sb = new StringBuilder();
            try (FileReader fr = new FileReader(_nomFichier); BufferedReader br = new BufferedReader(fr)) {
                String ligne = br.readLine();
                while(ligne!=null) {
                    sb.append(ligne);
                    ligne = br.readLine();
                }
            }
            return sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
