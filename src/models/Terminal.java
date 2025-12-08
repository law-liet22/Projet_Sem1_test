package models;

/**
 * Classe utilitaire pour gérer les opérations sur le terminal
 */
public class Terminal {
    /**
     * Efface le contenu du terminal de manière multi-plateforme
     * Fonctionne différemment selon le système d'exploitation
     */
    public static void effacerTerminal() 
    {
        try {
            // Détecte le système d'exploitation
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("windows")) 
            {
                // Pour Windows : exécute la commande "cls" via cmd
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } 
            else 
            {
                // Pour Linux / macOS : utilise les codes ANSI pour effacer l'écran
                // \033[H : positionne le curseur en haut à gauche
                // \033[2J : efface tout l'écran
                System.out.print("\033[H\033[2J");
            }
        } catch (Exception e) {
            // En cas d'erreur (commande non supportée), on ignore silencieusement
        }
    }
}
