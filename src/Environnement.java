import java.util.*;

public class Environnement {

    private final HashMap<Integer, Stack<Bloc>> hashMap = new HashMap<>();
    private final Stack<Bloc> solutionFinale = new Stack<>();
    private ArrayList<Bloc> mesBlocs = new ArrayList<>();


    public Environnement(ArrayList<Bloc> mesBlocs) {
        ArrayList<String> blocsName = new ArrayList<>();
        Stack<Bloc> pile = new Stack<>();
        solutionFinale.add(new Bloc("A", null));
        solutionFinale.add(new Bloc("B", null));
        solutionFinale.add(new Bloc("C", null));
        solutionFinale.add(new Bloc("D", null));
        do {
            Collections.shuffle(mesBlocs);
        } while (this.estTermine());
        for (int i = 0; i < 4; i++) {
            mesBlocs.get(i).setEnvironnement(this);
            blocsName.add(mesBlocs.get(i).getNom());
            pile.add(mesBlocs.get(i));
        }
        hashMap.put(0, pile);
        hashMap.put(1, new Stack<>());
        hashMap.put(2, new Stack<>());

        this.mesBlocs = mesBlocs;
    }

    public void communicationAgent() {
        Bloc blocAvant = null;
        for (int i = 0; i < 4; i++) {
            if (this.getHashMap().get(0).get(i).getBlocDessous() == null) {
                blocAvant = this.getHashMap().get(0).get(i);
                this.getHashMap().get(0).get(i).setPriorite(4);
            }
        }
        int compteur = 3;
        while (compteur > 0) {
            for (int i = 0; i < 4; i++) {
                if (this.getHashMap().get(0).get(i).getBlocDessous() == blocAvant) {
                    blocAvant = this.getHashMap().get(0).get(i);
                    this.getHashMap().get(0).get(i).setPriorite(compteur);
                    break;
                }
            }
            compteur = compteur - 1;
        }
    }

    public boolean canMove(Bloc bloc) {
        int indexHashMap = 0;
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i).contains(bloc)) {
                indexHashMap = i; // regarde sur quel emplacement est le bloc (1, 2 ou 3)
                break;
            }
        }
        int indexBloc = hashMap.get(indexHashMap).indexOf(bloc);
        return indexBloc + 1 == this.hashMap.get(indexHashMap).size();
    }

    public int getAleatoirePosition(Bloc bloc) {
        Random rand = new Random();
        int indexHashMap = 0;
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i).contains(bloc)) {
                indexHashMap = i; // regarde sur quel emplacement est le bloc (1, 2 ou 3)
                break;
            }
        }
        int nbAleatoire = indexHashMap;
        while (nbAleatoire == indexHashMap) {
            nbAleatoire = rand.nextInt(3); // tire un des deux autres emplacements possibles
        }
        return nbAleatoire;
    }

    public Bloc getBlocAuDessus(Bloc bloc) {
        int indexHashMap = 0;
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i).contains(bloc)) {
                indexHashMap = i; // regarde sur quel emplacement est le bloc (1, 2 ou 3)
                break;
            }
        }
        int indexBloc = hashMap.get(indexHashMap).indexOf(bloc);
        return this.hashMap.get(indexHashMap).get(indexBloc + 1);
    }

    public int calculerBestMove(Bloc bloc) {
        int indexHashMap = 0;
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i).contains(bloc)) {
                indexHashMap = i; // regarde sur quel emplacement est le bloc (1, 2 ou 3)
                break;
            }
        }
        Random rand = new Random();
        int bestPosition = indexHashMap;

        if (bloc.getBlocDessous() == null) {
            for (int i = 0; i < 3; i++) { // si doit être le premier bloc, va chercher une place seul d'abord
                if (i != indexHashMap) {
                    if (this.hashMap.get(i).size() == 0) return i;
                }
            }
        } else { // n'est pas le premier bloc
            for (int i = 0; i < 3; i++) { // va chercher à se mettre au dessus du bon bloc
                if (i != indexHashMap) {
                    if (this.hashMap.get(i).size() != 0 && this.hashMap.get(i).peek() == bloc.getBlocDessous())
                        return i;
                }
            }
        }

        if (mesBlocs.stream().max(Comparator.comparingInt(Bloc::getPriorite)).get().getPriorite() == 4) { // cad que le premier n'a pas été placé, il lui faut une place de libre, -> QUESTION 2
            for (int i = 0; i < 3; i++) { // si n'est pas arrivé, va chercher à se mettre sur un bloc pas seul pour éviter de gêner le premier
                if (i != indexHashMap) {
                    if (this.hashMap.get(i).size() != 0) return i;
                }
            }
        }

        for (int i = 0; i < 3; i++) { // si n'est pas arrivé, va chercher à se mettre sur un bloc pas seul pour éviter de gêner le premier
            if (i != indexHashMap) {
                if (this.hashMap.get(i).size() == 0) return i;
            }
        }

        for (int i = 0; i < 3; i++) { // aucun place qui correspond, aucune place de libre, va se mettre pour ne pas embêter premier bloc, -> QUESTION 2
            if (i != indexHashMap) {
                if (this.hashMap.get(i).size() != 0 && this.hashMap.get(i).get(0).getPriorite() != 0) return i;
            }
        }

        while (bestPosition == indexHashMap) { // sinon tire au hasard car les deux positions sont mauvaises, donc l'une ou l'autre fera l'affaire
            bestPosition = rand.nextInt(3); // tire un des deux autres emplacements possibles, car aucun n'est un move meilleur que l'autre
        }
        return bestPosition;
    }

    public boolean estTermine() {
        for (Map.Entry<Integer, Stack<Bloc>> mapentry : hashMap.entrySet()) {
            if (mapentry.getValue().size() == 4) {
                for (int i = 0; i < mapentry.getValue().size(); i++) {
                    if (mapentry.getValue().get(i).getNom() != solutionFinale.get(i).getNom()) return false;
                }
                return true;
            }
        }
        return false;
    }

    public void needToBeMove(Bloc bloc) { // met à jour si le bloc est bien placé ou non
        int indexHashMap = 0;
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i).contains(bloc)) {
                indexHashMap = i; // regarde sur quel emplacement est le bloc (1, 2 ou 3)
            }
        }
        int indexBloc = hashMap.get(indexHashMap).indexOf(bloc);
        if (indexBloc == 0) {
            bloc.setSatisfied(bloc.getBlocDessous() == null);
        } else bloc.setSatisfied(bloc.getBlocDessous() == this.hashMap.get(indexHashMap).get(indexBloc - 1));
    }

    public HashMap<Integer, Stack<Bloc>> getHashMap() {
        return hashMap;
    }

    @Override
    public String toString() {
        for (Map.Entry<Integer, Stack<Bloc>> mapentry : hashMap.entrySet()) {
            System.out.println("POSITION : " + mapentry.getKey());
            for (int i = 0; i < mapentry.getValue().size(); i++) {
                System.out.println("|| " + mapentry.getValue().get(mapentry.getValue().size() - i - 1) + " ||");
            }
        }

        return "END \n -------------------------------------------------------";
    }
}
