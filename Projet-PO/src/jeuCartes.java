import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class jeuCartes {
    
    static Carte frappe = new Carte("Frappe", Carte.Rarete.Commun, 1, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(6))), false,true, "Inflige 6 dégâts à un ennemi.");

    static Carte defense = new Carte("Défense", Carte.Rarete.Commun, 1, new ArrayList<Effet>(Arrays.asList(new Blocage(5))), new ArrayList<Effet>(), false, false, "Gagne 5 points de blocage.");

    static Carte heurt = new Carte("Heurt", Carte.Rarete.Commun, 2, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(8), new Vulnerable(2))), false,true,"Inflige 8 dégâts à un ennemi puis 2 de Vulnérable.");

    // TODO: Même pas mal

    static Carte vagueDeFer = new Carte("Vague de fer", Carte.Rarete.Commun, 1, new ArrayList<Effet>(Arrays.asList(new Blocage(5))), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(5))), false, true, "Gagne 5 points de blocage puis inflige 5 dégâts à un ennemi.");

    // TODO : Frappe du pommeau

    static Carte frappeDouble = new Carte("Frappe double", Carte.Rarete.Commun, 1, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(5), new AppliqueDegat(5))), false, true, "Inflige 5 dégâts 2 fois à un ennemi.");

    static Carte enchainement = new Carte("Enchaînement", Carte.Rarete.Commun, 1, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegatTous(8))), false, false, "Inflige 8 dégâts à tous les ennemis.");

    static Carte epeeBoomerang = new Carte("Epée boomerang", Carte.Rarete.Commun, 1, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegatRandom(3,3))), false, false, "Inflige 3 dégâts à un ennemi au hasard. Cet effet s’applique 3 fois à la suite.");
    
    static Carte manchette = new Carte("Manchette", Carte.Rarete.Commun, 2, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(12), new Faiblesse(2))), false, true,"Inflige 12 dégâts à un ennemi puis 2 de Faiblesse.");

    static Carte plaquage = new Carte("Plaquage", Carte.Rarete.Commun, 1, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegatParBlocage())), false,true,"Inflige x dégâts à un ennemi, avec x le nombre de points de blocage de l’entité qui utilise la carte."); 

    // Non-commun
    static Carte saignee = new Carte("Saignée", Carte.Rarete.NonCommun, 0, new ArrayList<Effet>(Arrays.asList(new PerdrePV(3), new Energie(2))), new ArrayList<Effet>(), false,false, "Perdez 3 points de vie puis gagnez 2 points d’énergie.");

    static Carte hemokinesie = new Carte("Hémokinésie", Carte.Rarete.NonCommun, 1, new ArrayList<Effet>(Arrays.asList(new PerdrePV(2))), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(15))), false,true,
"Perdez 2 points de vie, inflige 15 points de dégâts à un ennemi.");

    static Carte uppercut = new Carte("Uppercut", Carte.Rarete.NonCommun, 2, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(13), new Faiblesse(1), new Vulnerable(1))), false,true,"Inflige 13 points de dégâts à un ennemi, puis inflige 1 points de Faiblesse et Vulnérable.");

    static Carte voleeDeCoups = new Carte("Volée de coups", Carte.Rarete.NonCommun, 1, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(2), new AppliqueDegat(2),new AppliqueDegat(2),new AppliqueDegat(2))), true,true,"Inflige 2 dégâts 4 fois à un ennemi. Cette carte est mise en exil après avoir été jouée.");

    static Carte voirRouge = new Carte("Voir rouge", Carte.Rarete.NonCommun, 1, new ArrayList<Effet>(Arrays.asList(new Energie(2))), new ArrayList<Effet>(), true,false,"Gagne 2 points d’énergies. Cette carte est mise en exil après avoir été jouée."); 

    static Carte enflammer = new Carte("Enflammer", Carte.Rarete.NonCommun, 1, new ArrayList<Effet>(Arrays.asList(new Force(2))), new ArrayList<Effet>(), true,false,"Gagne 2 de Force. Cette carte est mise en exil après avoir été joué.");

    static Carte desarmement = new Carte("Désarmement", Carte.Rarete.NonCommun, 1, new ArrayList<Effet>(Arrays.asList(new Force(-2))), new ArrayList<Effet>(), true,true, "Fait perdre 2 de Force à un ennemi. Cette carte est mise en exil après avoir été jouée.");
    
    // TODO Tenacité
    /*
     *
     * Tenacité 1 Ajoute 2 cartes Plaie à sa
main, gagne 15 points de blo-
cage.
     */

     // Cartes Rares

    static Carte gourdin = new Carte("Gourdin", Carte.Rarete.Rare, 3, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(32))), false,true,"Inflige 32 points de dégâts à un ennemi.");

    static Carte invincible = new Carte("Invincible", Carte.Rarete.Rare, 2, new ArrayList<Effet>(Arrays.asList(new Blocage(30))), new ArrayList<Effet>(), false,true,"Inflige 20 points de dégâts à un ennemi puis 2 de Faiblesse.");

    // TODO : Offrande

    // TODO : Forme démoniaque
}
