package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire pour parser et échapper les données CSV
 * Gère correctement les guillemets et les virgules dans les champs
 */
public class CSVUtils {

    /**
     * Parse une ligne CSV en tenant compte des guillemets
     * Gère les cas où des virgules ou guillemets sont dans les données
     * @param ligne La ligne CSV à parser
     * @return Un tableau de chaînes contenant les champs extraits
     */
    public static String[] parseCSVLine(String ligne) {
        // Liste pour stocker les champs extraits
        List<String> champs = new ArrayList<>();
        // Buffer pour construire le champ courant
        StringBuilder courant = new StringBuilder();
        // Indique si on est à l'intérieur de guillemets
        boolean dansGuillemets = false;

        // Parcourt chaque caractère de la ligne
        for (int i = 0; i < ligne.length(); i++) {
            char c = ligne.charAt(i);

            // Gestion des guillemets
            if (c == '"') {
                // Vérifie si c'est un guillemet échappé ("") ou un délimiteur
                if (dansGuillemets && i + 1 < ligne.length() && ligne.charAt(i + 1) == '"') {
                    // Guillemet échappé : ajoute un seul guillemet au champ
                    courant.append('"');
                    i++; // Saute le deuxième guillemet
                } else {
                    // Bascule l'état (entrée ou sortie des guillemets)
                    dansGuillemets = !dansGuillemets;
                }
            }
            // Gestion des virgules (séparateurs de champs)
            else if (c == ',' && !dansGuillemets) {
                // Virgule hors guillemets : fin du champ courant
                champs.add(courant.toString());
                courant.setLength(0); // Réinitialise le buffer
            }
            // Caractère normal : ajoute au champ courant
            else {
                courant.append(c);
            }
        }
        // Ajoute le dernier champ
        champs.add(courant.toString());

        // Convertit la liste en tableau
        return champs.toArray(new String[0]);
    }

    /**
     * Échappe un champ CSV en ajoutant des guillemets si nécessaire
     * Protège les virgules et guillemets dans les données
     * @param champ Le champ à échapper
     * @return Le champ échappé avec guillemets si nécessaire
     */
    public static String escape(String champ) {
        // Vérifie si le champ contient des caractères spéciaux
        if (champ.contains(",") || champ.contains("\"")) {
            // Échappe les guillemets existants en les doublant
            champ = champ.replace("\"", "\"\"");
            // Entoure le champ de guillemets
            return "\"" + champ + "\"";
        }
        // Pas besoin d'échappement
        return champ;
    }
}
