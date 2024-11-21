package Controller;

import View.EntravesView;

import java.util.Scanner;

public class EntraveController {
    // Faire appel à afficherMenu de la vue Entrave afin d'afficher le menu des entraves
    // routières
    public static void afficherMenu(Scanner scanner){
        EntravesView.afficherMenu(scanner);
    }

}
