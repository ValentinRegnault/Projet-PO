package effets;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Classe abstraite représentant un effet. Pour la sérialisation des classes de type Effet, on
 * utilise un champs "type" avec le nom de la classe fille qui est sérialisée.
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({@Type(value = AppliqueDegat.class, name = "appliqueDegat"),
        @Type(value = AppliqueDegatParBlocage.class, name = "appliqueDegatParBlocage"),
        @Type(value = Blocage.class, name = "blocage"),
        @Type(value = Pioche.class, name = "pioche"),
        @Type(value = Energie.class, name = "energie"),
        @Type(value = Faiblesse.class, name = "faiblesse"),
        @Type(value = Force.class, name = "force"), @Type(value = Fragile.class, name = "fragile"),
        @Type(value = PerdrePV.class, name = "perdrePV"),
        @Type(value = Vulnerable.class, name = "vulnerable"),
        @Type(value = CarteSlimeDansDefausse.class, name = "carteSlimeDansDefausse"),
        @Type(value = Rituel.class, name = "rituel"), @Type(value = Divise.class, name = "divise"),
        @Type(value = CarteBrulureDansDefausse.class, name = "ajoutCarteBrulureDansDefausse"),
        @Type(value = CartePlaieDansDefausse.class, name = "cartePlaieDansDefausse")})
public abstract class Effet implements java.io.Serializable, Cloneable {
    protected int pointEffet;

    /**
     * Type de cible de l'effet. Lorsque l'on appel la méthode appliquerEffet, on lui passe en
     * paramètre une liste de cibles, qui doivent correspondrent au type de cible.
     */
    private TypeCible typeCible;

    protected Effet() {
        this.pointEffet = 0;
    }

    protected Effet(int pointEffet, TypeCible typeCible) {
        this.pointEffet = pointEffet;
        this.typeCible = typeCible;
    }

    /**
     * Applique l'effet sur les cibles.
     * 
     * @param lanceur Le lanceur de l'effet
     * @param cibles Les cibles de l'effet, qui doivent correspondre au type de cible
     *        {@link Effet#getTypeCible()} de l'effet.
     */
    public abstract void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef,
            Heros herosRef, List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif);

    public abstract String toString();

    public int getPointEffet() {
        return pointEffet;
    }

    public void setPointEffet(int pointEffet) {
        this.pointEffet = pointEffet;
    }

    public TypeCible getTypeCible() {
        return typeCible;
    }

    public void setTypeCible(TypeCible typeCible) {
        this.typeCible = typeCible;
    }
}
