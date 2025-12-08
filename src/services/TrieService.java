package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import models.Affichage;

/**
 * Service gérant l'affichage du fichier trié
 */
public class TrieService {
    /**
     * Lit et affiche le contenu du fichier trié
     * @param chemin Le chemin vers le fichier texte contenant l'inventaire trié
     */
    public static void afficherFichierTrie(String chemin)
    {
        // Utilise un try-with-resources pour fermer automatiquement le fichier
        try (BufferedReader br = new BufferedReader(new FileReader(chemin)))
        {
            String ligne;
            // Lit et affiche chaque ligne du fichier
            while((ligne = br.readLine()) != null)
            {
                Affichage.afficherLn(ligne);
            }

            // Affiche l'emplacement du fichier trié
            Affichage.afficherLn("\nVous pouvez retrouver le fihcier ici : /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/data/inventaire_trie.txt");
        }
        catch (IOException e)
        {
            // Affiche un message d'erreur en cas de problème de lecture
            Affichage.afficherAvecPointsSecondes("Erreur lors de la lecture du fihcier trié : " + e.getMessage());
        }
    }
}
