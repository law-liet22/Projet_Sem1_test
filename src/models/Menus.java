package models;
import java.util.HashMap;

public class Menus {
    static HashMap<String, Integer> valeursMenuP = new HashMap<>();
    static int valSortie = 0;
    static String afficher = "Afficher l'inventaire";
    static int valAfficher = 1;
    static String modifier = "Modifier l'inventaire (ajouter, modifier, supprimer)";
    static int valModifier = 2;

    // Menu modifications
    static HashMap<String, Integer> valeursMenuM = new HashMap<>();
    static int valModifAjouter = 1;
    static String modifAjouter = "Ajouter un objet à l'inventaire";
    static int valModifModifier = 2;
    static String modifModifier = "Modifier un objet présent dans l'inventaire";
    static int valModifSupprimer = 3;
    static String modifSupprimer = "Supprimer un objet de l'inventaire";


    public static void afficherMenuPrincipal()
    {

        Terminal.effacerTerminal();
        
        Affichage.afficherLn("""
                ------------------------------
                        Menu principal""" +                                 
                "\n" + valAfficher+ ". " + afficher + 
                "\n" + valModifier + ". " + modifier + 
                "\n" + valSortie + ". Arrêt du programme\n------------------------------");
    }

    public static HashMap<String, Integer> getValeursMenuP()
    {
        valeursMenuP.put("Sortie", valSortie);
        valeursMenuP.put("Afficher", valAfficher);
        valeursMenuP.put("Modifier", valModifier);

        return valeursMenuP;
    }
    
    public static HashMap<String, Integer> getValeursMenuM()
    {
        valeursMenuM.put("Ajouter", valModifAjouter);
        valeursMenuM.put("Modifier", valModifModifier);
        valeursMenuM.put("Supprimer", valModifSupprimer);

        return valeursMenuM;
    }


    public static void afficherMenuModification()
    {
        Terminal.effacerTerminal();

        Affichage.afficherLn("""
                ------------------------------
                       Menu modifications""" +                                 
                "\n" + valModifAjouter+ ". " + modifAjouter + 
                "\n" + valModifModifier + ". " + modifModifier + 
                "\n" + valModifSupprimer + ". " + modifSupprimer +
                "\n" + valSortie + ". Arrêt du programme\n------------------------------");
    }
}
