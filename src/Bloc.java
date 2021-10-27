public class Bloc {
    private Environnement environnement;
    private String nom;
    private int priorite;

    public Bloc(String nom) {
        this.nom = nom;
        this.priorite = 0;
    }

    public void seDeplacer(int x) {
        int index = 0;
        for (int i = 0; i < environnement.getHashMap().size(); i++) {
            if (environnement.getHashMap().get(i).contains(this)) {
                index = environnement.getHashMap().get(i).indexOf(this);
                //environnement.getHashMap().get(i).set(index, null);
                environnement.getHashMap().get(i).remove(index);
                environnement.getHashMap().get(x).add(this);
                //System.out.println(this);
            }
        }
        /*index = environnement.getHashMap().get(x).indexOf(this.getNom());
        if (index != 0) {
            Bloc auDessous = environnement.findBlocByName(environnement.getHashMap().get(x).get(index - 1));
            auDessous.setBlocDessus(this);
            this.setBlocDessous(auDessous);
        }*/

    }

    public void seDeplacer2 (int x) {

    }

    public void pousser() {

    }

    public String getNom() {
        return nom;
    }

    public Environnement getEnvironnement() {
        return environnement;
    }

    public void setEnvironnement(Environnement environnement) {
        this.environnement = environnement;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    @Override
    public String toString() {
        return "Bloc{" +
                ", nom='" + nom + '\'' +
                ", Priorite='" + priorite + '\'' +
                '}';
    }
}
