package models;
import java.util.HashMap;

public class MenuPrincipal {
    static HashMap<String, Integer> valeursMenu = new HashMap<>();
    static int valSortie = 0;
    static String afficher = "Afficher l'inventaire";
    static int valAfficher = 1;
    static String modifier = "Modifier l'inventaire (ajouter, modifier, supprimer)";
    static int valModifier = 2;

    public static void effacerTerminal() {
    for (int i = 0; i < 50; i++) {
        System.out.println();
    }
}


    public static void afficherMenu()
    {

        effacerTerminal();
        
        System.out.println("""
                ------------------------------
                        Menu principal""" +                                 
                "\n" + valAfficher+ ". " + afficher + 
                "\n" + valModifier + ". " + modifier + 
                "\n" + valSortie + ". Arrêt du programme\n------------------------------");
    }

    public static HashMap<String, Integer> getValeursMenu()
    {
        valeursMenu.put("Sortie", valSortie);
        valeursMenu.put("Afficher", valAfficher);
        valeursMenu.put("Modifier", valModifier);

        return valeursMenu;
    }
    
}
