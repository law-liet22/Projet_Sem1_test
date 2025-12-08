package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe permettant d'interagir avec un programme externe (en C)
 * pour effectuer le tri du fichier CSV
 */
public class InterractionCSV {
    // Chemin vers l'exécutable C qui effectue le tri
    private String cheminExecutable;

    /**
     * Constructeur de la classe
     * @param cheminExecutable Le chemin vers le programme C compilé
     */
    public InterractionCSV(String cheminExecutable)
    {
        this.cheminExecutable = cheminExecutable;
    }

    /**
     * Exécute le programme C de tri avec les paramètres fournis
     * @param cheminCSV Le chemin vers le fichier CSV à trier
     * @param critere Le critère de tri (1-7)
     * @return 1 si succès, code d'erreur négatif sinon
     */
    public int executerTri(String cheminCSV, int critere)
    {
        try
        {
            // Prépare la commande avec les arguments (exécutable, fichier CSV, critère)
            ProcessBuilder pb = new ProcessBuilder(cheminExecutable, cheminCSV, String.valueOf(critere));

            // Redirige les erreurs vers le flux de sortie standard
            pb.redirectErrorStream(true);

            // Lance le programme C
            Process process = pb.start();

            // Crée un lecteur pour récupérer la sortie du programme
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // StringBuilder pour stocker toute la sortie
            StringBuilder output = new StringBuilder();
            String ligne;

            // Lit toutes les lignes de sortie du programme
            while ((ligne = reader.readLine()) != null)
            {
                output.append(ligne).append("\n");
            }

            // Attend la fin du processus et récupère le code de sortie
            int exitCode = process.waitFor();

            // Vérifie si l'exécution s'est terminée avec une erreur
            if (exitCode != 0)
            {
                return exitCode; // Retourne le code d'erreur
            }

            return 1; // Succès
        }
        catch (IOException | InterruptedException e)
        {
            // En cas d'exception, retourne un code d'erreur
            return -1;
        }
    }
}
