package acces_donnees;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fichier {
    
    private File _fichier;
    
    public Fichier(String nomFichier) {
        _fichier = new File(nomFichier);
    }
    
    public void ecrire(String s, boolean append) {
        try {
            FileWriter fw = new FileWriter(_fichier, append);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(s);
            
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Fichier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String lire() {
        try {
            StringBuilder sb = new StringBuilder();
            try (FileReader fr = new FileReader(_fichier); BufferedReader br = new BufferedReader(fr)) {
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
