package main;
import java.util.Arrays;

import patterns.Pattern;

import java.util.ArrayList;

enum TypeMontre {
    PetitSlimePiquant,
    PetitSlimeAcide,
    Machouilleur,
    Cultiste,
    SlimePiquant,
    SlimeAcide,
    Hexaghost,
}

public class Monstre extends Entite {
    private Pattern pattern;

    public Monstre() {
        super("", 0, 0);
        this.pattern = null;
    }

    public Monstre(String nom, int pvMax, Pattern pattern) {
        super(nom, pvMax, 0);
        this.pattern = pattern;
    }

    public void jouerAction() {
        this.pattern.jouerAction(this);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "Monstre [" + super.toString() + "]";
    }
}
