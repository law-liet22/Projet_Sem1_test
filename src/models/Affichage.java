package models;
import Attendre;


public class Affichage {
    public static void afficherLn(String str)
    {
        System.out.println(str);
    }

    public static void afficherSansLn(String str)
    {
        System.out.print(str);
    }

    public static void afficherAvecPointsSecondes(String msg) {
    // Affiche le message d'erreur
    afficherSansLn(msg);

    // Affiche un point toutes les 0,5 secondes
    for (int i = 0; i < 3; i++) {
        afficherSansLn("."); // affichage du point
        Attendre.attendreDouble(1.5); // 0,5 seconde
        //System.out.flush();    // force l'affichage immédiat
    }

    afficherLn("\n");; // saut de ligne après les points
}
}
