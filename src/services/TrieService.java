package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import models.Affichage;

public class TrieService {
    public static void afficherFichierTrie(String chemin)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(chemin)))
        {
            String ligne;
            while((ligne = br.readLine()) != null)
            {
                Affichage.afficherLn(ligne);
            }

            Affichage.afficherLn("\nVous pouvez retrouver le fihcier ici : /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/data/inventaire_trie.txt");
        }
        catch (IOException e)
        {
            Affichage.afficherAvecPointsSecondes("Erreur lors de la lecture du fihcier trié : " + e.getMessage());
        }
    }
}
