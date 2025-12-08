package utils;

/**
 * Classe utilitaire contenant le chemin vers le fichier de données
 * Centralise la configuration du chemin du fichier CSV
 */
public class DataPath {
    // Chemin relatif vers le fichier CSV de l'inventaire
    private static String dataPath = "out/data/inventaire.csv";
    
    /**
     * Retourne le chemin vers le fichier de données de l'inventaire
     * @return Le chemin relatif du fichier CSV
     */
    public static String getDataPath()
    {
        return dataPath;
    }
}
