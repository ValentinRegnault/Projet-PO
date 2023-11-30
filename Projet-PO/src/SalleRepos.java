public class SalleRepos extends Salle {
    
    public void soin(){
        hero.setPv((int)Math.round(hero.getPvMax()*0.3));
    }

    public boolean jouerSalle(){
        soin();
        return true;
    }
}
