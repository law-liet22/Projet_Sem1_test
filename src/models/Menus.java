package models;
import java.util.HashMap;

/**
 * Classe gérant l'affichage des menus et leurs valeurs associées
 */
public class Menus {
    // Dictionnaire associant les noms des options du menu principal à leurs valeurs
    static HashMap<String, Integer> valeursMenuP = new HashMap<>();

    // === Configuration du Menu principal ===
    static int valSortie = 0; // Valeur pour quitter
    static String afficher = "Afficher l'inventaire"; // Libellé de l'option
    static int valAfficher = 1; // Valeur pour afficher
    static String modifier = "Modifier l'inventaire (ajouter, modifier, supprimer)";
    static int valModifier = 2; // Valeur pour modifier
    static String trier = "Trier l'inventaire";
    static int valTrier = 3; // Valeur pour trier

    // === Configuration du Menu modifications ===
    // Dictionnaire associant les noms des options du menu modification à leurs valeurs
    static HashMap<String, Integer> valeursMenuM = new HashMap<>();
    static int valModifAjouter = 1; // Valeur pour ajouter
    static String modifAjouter = "Ajouter un objet à l'inventaire";
    static int valModifModifier = 2; // Valeur pour modifier
    static String modifModifier = "Modifier un objet présent dans l'inventaire";
    static int valModifSupprimer = 3; // Valeur pour supprimer
    static String modifSupprimer = "Supprimer un objet de l'inventaire";


    /**
     * Affiche le menu principal du programme
     * Efface d'abord le terminal puis affiche les options disponibles
     */
    public static void afficherMenuPrincipal()
    {
        // Efface le terminal pour un affichage propre
        Terminal.effacerTerminal();
        
        // Affiche le menu principal avec toutes les options
        Affichage.afficherLn("""
                ------------------------------
                        Menu principal""" +                                 
                "\n" + valAfficher+ ". " + afficher + 
                "\n" + valModifier + ". " + modifier + 
                "\n" + valTrier + ". " + trier +
                "\n" + valSortie + ". Arrêt du programme\n------------------------------");
    }

    /**
     * Retourne le dictionnaire des valeurs du menu principal
     * Associe chaque nom d'action à sa valeur numérique
     * @return HashMap contenant les paires nom-valeur du menu principal
     */
    public static HashMap<String, Integer> getValeursMenuP()
    {
        valeursMenuP.put("Sortie", valSortie);
        valeursMenuP.put("Afficher", valAfficher);
        valeursMenuP.put("Modifier", valModifier);
        valeursMenuP.put("Trier", valTrier);

        return valeursMenuP;
    }
    
    /**
     * Retourne le dictionnaire des valeurs du menu modification
     * Associe chaque nom d'action à sa valeur numérique
     * @return HashMap contenant les paires nom-valeur du menu modification
     */
    public static HashMap<String, Integer> getValeursMenuM()
    {
        valeursMenuM.put("Ajouter", valModifAjouter);
        valeursMenuM.put("Modifier", valModifModifier);
        valeursMenuM.put("Supprimer", valModifSupprimer);
        valeursMenuM.put("Retour", valSortie);

        return valeursMenuM;
    }

    /**
     * Affiche le menu des modifications d'inventaire
     * Efface d'abord le terminal puis affiche les options de modification
     */
    public static void afficherMenuModification()
    {
        // Efface le terminal pour un affichage propre
        Terminal.effacerTerminal();

        // Affiche le menu de modification avec toutes les options
        Affichage.afficherLn("""
                ------------------------------
                       Menu modifications""" +                                 
                "\n" + valModifAjouter+ ". " + modifAjouter + 
                "\n" + valModifModifier + ". " + modifModifier + 
                "\n" + valModifSupprimer + ". " + modifSupprimer +
                "\n" + valSortie + ". Retour au menu principal\n------------------------------");
    }
}
