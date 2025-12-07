package utils;

import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    // Parse une ligne CSV correcte avec guillemets
    public static String[] parseCSVLine(String ligne) {
        List<String> champs = new ArrayList<>();
        StringBuilder courant = new StringBuilder();
        boolean dansGuillemets = false;

        for (int i = 0; i < ligne.length(); i++) {
            char c = ligne.charAt(i);

            if (c == '"') {
                if (dansGuillemets && i + 1 < ligne.length() && ligne.charAt(i + 1) == '"') {
                    courant.append('"');
                    i++;
                } else {
                    dansGuillemets = !dansGuillemets;
                }
            }
            else if (c == ',' && !dansGuillemets) {
                champs.add(courant.toString());
                courant.setLength(0);
            }
            else {
                courant.append(c);
            }
        }
        champs.add(courant.toString());

        return champs.toArray(new String[0]);
    }

    // Convertit un champ CSV en format sécurisé (guillemets si besoin)
    public static String escape(String champ) {
        if (champ.contains(",") || champ.contains("\"")) {
            champ = champ.replace("\"", "\"\"");
            return "\"" + champ + "\"";
        }
        return champ;
    }
}
