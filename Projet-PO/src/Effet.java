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
    @Type(value = AppliqueDegatParBlocage.class, name = "appliqueDegat"),
    @Type(value = Blocage.class, name = "blocage"),
    @Type(value = Energie.class, name = "energie"),
    @Type(value = Faiblesse.class, name = "faiblesse"),
    @Type(value = Force.class, name = "force"),
    @Type(value = Fragile.class, name = "fragile"),
    @Type(value = PerdrePV.class, name = "perdrePV"),
    @Type(value = Vulnerable.class, name = "vulnerable"),
})
public abstract class Effet implements java.io.Serializable {
    protected int pointEffet;

    public Effet() {
        this.pointEffet = 0;
    }

    public Effet(int pointEffet) {
        this.pointEffet = pointEffet;
    }

    protected abstract void appliquerEffet(Entite lanceur, ArrayList<Entite> listeCible);

    public abstract String toString();

    public int getPointEffet() {
        return pointEffet;
    }

    public void setPointEffet(int pointEffet) {
        this.pointEffet = pointEffet;
    }
}
