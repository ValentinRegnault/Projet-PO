package main;

import java.awt.Color;
import java.util.ArrayList;
import effets.Effet;
import ressources.Affichage;
import ressources.Config;

/**
 * Représente une carte du jeu.
 */
public class Carte extends Affichable implements Cloneable {

    /**
     * Enumération des différentes raretés de carte possibles.
     */
    public enum RareteCarte {
        Commun, NonCommun, Rare
    }

    public enum EtatCarte {
        DANS_PIOCHE, DANS_MAIN, DEFAUSSEE, EXILEE
    }

    private String nom;
    private RareteCarte rarete;
    private int cout;
    private ArrayList<Effet> effets;
    private String description;
    private boolean aExiler;
    private EtatCarte etat;
    /**
     * Si true, l'effet est positif, sinon il est négatif. Cela permet de savoir si la carte peut
     * etre donnée en récompense.
     */
    private boolean positive;

    public static final double LARGEUR_CARTE = 130.0;
    public static final double HAUTEUR_CARTE = 200.0;

    public Carte() {
        super(0.0, 0.0);
        this.nom = "";
        this.rarete = RareteCarte.Commun;
        this.cout = 0;
        this.effets = new ArrayList<>();
        this.aExiler = false;
        this.description = "";
        this.etat = EtatCarte.DANS_PIOCHE;
    }

    public Carte(String nom, RareteCarte rarete, int cout, ArrayList<Effet> effets, boolean exile,
            String description, EtatCarte etat) {
        super(0.0, 0.0);
        this.nom = nom;
        this.rarete = rarete;
        this.cout = cout;
        this.effets = effets;
        this.aExiler = exile;
        this.description = description;
        this.etat = etat;
    }

    @Override
    public void afficher() {
        if (this.etat != EtatCarte.DANS_MAIN)
            return;

        Affichage.rectanglePlein(getX(), getX() + LARGEUR_CARTE, getY(), getY() + HAUTEUR_CARTE,
                new Color(255, 255, 255));
        Affichage.rectangleContour(getX(), getX() + LARGEUR_CARTE, getY(), getY() + HAUTEUR_CARTE,
                new Color(0, 0, 0));

        Affichage.texteCentre(getX() + LARGEUR_CARTE / 2, getY() + HAUTEUR_CARTE - 10, nom,
                new Color(0, 0, 0));

        String[] lines = new String[description.length() / 13 + 2];
        for (int i = 0; i < lines.length - 1; i++) {
            lines[i] = description.substring(i * 13, Math.min((i + 1) * 13, description.length()));
        }
        lines[lines.length - 1] = "Coût : " + cout;


        for (int i = 0; i < lines.length; i++) {
            Affichage.texteGauche(getX() + 5, getY() + HAUTEUR_CARTE - 30 - i * 20, lines[i],
                    Config.POLICE_PAR_DEFAUT, Color.BLACK);
        }

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Carte cloned = (Carte) super.clone();

        cloned.effets = new ArrayList<Effet>();
        for (Effet effet : this.effets) {
            cloned.effets.add((Effet) effet.clone());
        }

        return cloned;
    }

    public String getNom() {
        return nom;
    }

    public RareteCarte getRarete() {
        return rarete;
    }

    public int getCout() {
        return cout;
    }

    public ArrayList<Effet> getEffets() {
        return effets;
    }

    public String getDescription() {
        return description;
    }

    public boolean isaExiler() {
        return aExiler;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRarete(RareteCarte rarete) {
        this.rarete = rarete;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setaExiler(boolean exile) {
        this.aExiler = exile;
    }

    public void setEffets(ArrayList<Effet> effets) {
        this.effets = effets;
    }

    public EtatCarte getEtat() {
        return etat;
    }

    public void setEtat(EtatCarte etat) {
        this.etat = etat;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positif) {
        this.positive = positif;
    }

    @Override
    public String toString() {
        return nom + " (" + rarete + ") description : " + description + " coût : " + cout;
    }


}
