package models;

/**
 * Classe utilitaire pour gérer l'affichage dans le terminal
 */
public class Affichage {
    /**
     * Affiche une chaîne de caractères avec un saut de ligne à la fin
     * @param str La chaîne à afficher
     */
    public static void afficherLn(String str)
    {
        System.out.println(str);
    }

    /**
     * Affiche une chaîne de caractères sans saut de ligne à la fin
     * @param str La chaîne à afficher
     */
    public static void afficherSansLn(String str)
    {
        System.out.print(str);
    }

    /**
     * Affiche un message suivi de points d'attente animés
     * Utilisé pour donner un feedback visuel à l'utilisateur
     * @param msg Le message à afficher avant l'animation
     */
    public static void afficherAvecPointsSecondes(String msg) {
        // Affiche le message principal
        afficherSansLn(msg);

        // Boucle pour afficher 3 points avec une pause entre chaque
        for (int i = 0; i < 3; i++) {
            afficherSansLn("."); // Affichage du point
            Attendre.attendreDouble(0.7); // Pause d'1 seconde
        }

        // Saut de ligne après les points
        afficherLn("\n");
    }
}
