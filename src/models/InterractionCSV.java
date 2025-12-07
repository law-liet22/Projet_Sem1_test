package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterractionCSV {
    private String cheminExecutable;

    public InterractionCSV(String cheminExecutable)
    {
        this.cheminExecutable = cheminExecutable;
    }

    public String executerTri(String cheminCSV, int critere)
    {
        try
        {
            // Prépare la commande
            ProcessBuilder pb = new ProcessBuilder(cheminExecutable, cheminCSV, String.valueOf(critere));

            pb.redirectErrorStream(true);

            // Lance le programme C
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String ligne;

            while ((ligne = reader.readLine()) != null)
            {
                output.append(ligne).append("\n");
            }

            int exitCode = process.waitFor();

            if (exitCode != 0)
            {
                return "Erreur lors du tri (code " + exitCode + ") : \n" + output;
            }

            return "Trie effectué avec succès.\n" + output;
        }
        catch (IOException | InterruptedException e)
        {
            return "Erreur : " + e.getMessage();
        }
    }
}
