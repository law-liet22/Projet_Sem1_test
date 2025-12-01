package models;

public class Terminal {
    public static void effacerTerminal() 
    {
        // for (int i = 0; i < 50; i++) 
        // {
        //     System.out.println();
        // }

        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("windows")) 
            {
                // Pour Windows : lancer la commande "cls" via cmd
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } 
            else 
            {
                // Pour Linux / macOS ou terminaux compatibles ANSI
                System.out.print("\033[H\033[2J");
                // System.out.flush();
            }
        } catch (Exception e) {
            // En cas d'erreur (commande non supportée…), on ignore
        }
    }
}
