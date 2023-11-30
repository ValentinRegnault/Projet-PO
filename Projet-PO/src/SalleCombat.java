import java.util.ArrayList;

public abstract class SalleCombat extends Salle {
        private ArrayList<Monstre> equipeMonstre;

        public SalleCombat(ArrayList<Monstre> equipeMonstre) {
                this.equipeMonstre = equipeMonstre;
        }

        public ArrayList<Monstre> getEquipeMonstre() {
                return equipeMonstre;
        }
}
