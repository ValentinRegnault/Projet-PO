import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = AppliqueDegat.class, name = "appliqueDegat"),
        @Type(value = AppliqueDegatParBlocage.class, name = "appliqueDegatParBlocage"),
        @Type(value = Blocage.class, name = "blocage"),
        @Type(value = Energie.class, name = "energie"),
        @Type(value = Faiblesse.class, name = "faiblesse"),
        @Type(value = Force.class, name = "force"),
        @Type(value = Fragile.class, name = "fragile"),
        @Type(value = PerdrePV.class, name = "perdrePV"),
        @Type(value = Vulnerable.class, name = "vulnerable"),
        @Type(value = CarteSlimeDansDefausse.class, name = "carteSlimeDansDefausse"),
        @Type(value = Rituel.class, name = "rituel")
})
public abstract class Effet implements java.io.Serializable, Cloneable {
    protected int pointEffet;
    private TypeCible typeCible;

    public Effet() {
        this.pointEffet = 0;
    }

    public Effet(int pointEffet, TypeCible typeCible) {
        this.pointEffet = pointEffet;
        this.typeCible = typeCible;
    }

    protected abstract void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles);

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
