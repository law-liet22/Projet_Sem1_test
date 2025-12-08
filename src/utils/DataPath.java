package utils;

/**
 * Classe utilitaire contenant le chemin vers le fichier de données
 * Centralise la configuration du chemin du fichier CSV
 */
public class DataPath {
    // Chemin absolu vers le fichier CSV de l'inventaire
    private static String dataPath = "/home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/data/inventaire.csv";
    
    /**
     * Retourne le chemin vers le fichier de données de l'inventaire
     * @return Le chemin absolu du fichier CSV
     */
    public static String getDataPath()
    {
        return dataPath;
    }
}
