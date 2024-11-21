package Controller;

import View.TravauxView;

import java.util.Scanner;

public class TravauxController {
    // Faire appel à afficherMenu de la vue Travaux pour afficher le menu des travaux
    // en cours ou à venir
    public static void afficherMenu(Scanner scanner){
        TravauxView.afficherMenu(scanner);
    }
}
