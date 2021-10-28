public class Bloc {
    private Environnement environnement;
    private String nom;
    private int priorite;
    private Bloc blocDessous;
    private boolean isSatisfied;
    private boolean isPushed;

    public Bloc(String nom, Bloc blocDessous) {
        this.nom = nom;
        this.priorite = 0;
        this.blocDessous = blocDessous;
        this.isSatisfied = false;
        this.isPushed = false;
    }

    public void seDeplacer(int x) {
        int index = 0;
        for (int i = 0; i < environnement.getHashMap().size(); i++) {
            if (environnement.getHashMap().get(i).contains(this)) {
                index = environnement.getHashMap().get(i).indexOf(this);
                environnement.getHashMap().get(i).remove(index);
                environnement.getHashMap().get(x).add(this);
            }
        }

    }

    public boolean isPushed() {
        return this.isPushed;
    }

    public void setPushed(boolean pushed) {
        this.isPushed = pushed;
    }

    public void seDeplacer2 (int x) {
        int index = 0;
        for (int i = 0; i < environnement.getHashMap().size(); i++) {
            if (environnement.getHashMap().get(i).contains(this)) {
                index = environnement.getHashMap().get(i).indexOf(this);
                environnement.getHashMap().get(i).remove(index);
                environnement.getHashMap().get(x).add(this);

                break;
//                environnement.setNewPriorite(this, x);// met a jour priorite du bloc qui vient d'être déplacer
////                System.out.println("NEW PRIO DU BLOC : " + this.getNom() + " EST DE : " + environnement.quelEstMaPriorite(this, x));
//                if (environnement.getHashMap().get(i).size() != 0) environnement.setNewPriorite(environnement.getHashMap().get(i).peek(), i); // met a jour le bloc du dessous de celui qui a été déplacer
            }
        }


    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    public void setSatisfied(boolean satisfied) {
        isSatisfied = satisfied;
    }

    public void pousser(Bloc bloc) {
        bloc.setPushed(true);
        this.environnement.perception2(bloc);
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

    public Bloc getBlocDessous() {
        return blocDessous;
    }

    public void setBlocDessous(Bloc blocDessous) {
        this.blocDessous = blocDessous;
    }

    @Override
    public String toString() {
        return "Bloc{" +
                ", nom='" + nom + '\'' +
                ", Priorite ='" + priorite + '\'' +
                '}';
    }
}
