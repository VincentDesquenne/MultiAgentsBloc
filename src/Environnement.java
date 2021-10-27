import java.util.*;

public class Environnement {

    private HashMap<Integer, Stack<Bloc>> hashMap = new HashMap<>();
    private Stack<Bloc> solutionFinale = new Stack<>();
    private int position;


    public Environnement(ArrayList<Bloc> mesBlocs) {
        ArrayList<String> blocsName = new ArrayList<>();
        Stack<Bloc> pile = new Stack<>();
        ArrayList<Integer> mesEntiers = new ArrayList<>();
        Collections.shuffle(mesBlocs);
        for (int i = 0; i < 4; i++) {
            mesBlocs.get(i).setEnvironnement(this);
            blocsName.add(mesBlocs.get(i).getNom());
            pile.add(mesBlocs.get(i));
        }
        hashMap.put(0, pile);
        hashMap.put(1, new Stack<>());
        hashMap.put(2, new Stack<>());
        hashMap.get(0).get(3).setPriorite(2);
        solutionFinale.add(new Bloc("A", null));
        solutionFinale.add(new Bloc("B", null));
        solutionFinale.add(new Bloc("C", null));
        solutionFinale.add(new Bloc("D", null));
    }

    public void perception(Bloc bloc) {
        int indexHashMap = 0;
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i).contains(bloc)) {
                indexHashMap = i; // regarde sur quel emplacement est le bloc (1, 2 ou 3)
                break;
            }
        }
        int indexBloc = hashMap.get(indexHashMap).indexOf(bloc); //regarde position du bloc (tout en haut ou tout en bas par exemple)
        Random rand = new Random();
        int nbAleatoire = indexHashMap;
        this.needToBeMove(bloc, indexHashMap, indexBloc);
        if (bloc.isPushed()) { // si le bloc est poussée
            if (indexBloc + 1 == this.hashMap.get(indexHashMap).size()) { // si il est tout en haut, il se déplace
                while (nbAleatoire == indexHashMap) {
                    nbAleatoire = rand.nextInt(3); // tire un des deux autres emplacements possibles
                }
                bloc.setPushed(false); // du coup il n'est plus poussé
                bloc.seDeplacer(nbAleatoire); // il se déplace aléatoirement
            } else {
                bloc.pousser(hashMap.get(indexHashMap).get(indexBloc + 1)); // si il est poussé, mais ne peut pas se déplacer, il pousse
            }
        } else if (!bloc.isSatisfied()) { // si le bloc n'est pas poussé, mais n'est pas satisfait
            if (indexBloc + 1 == this.hashMap.get(indexHashMap).size()) { // il est tout en haut, il peut bouger
                while (nbAleatoire == indexHashMap) {
                    nbAleatoire = rand.nextInt(3); // tire un des deux autres emplacements possibles
                }
                bloc.seDeplacer(nbAleatoire);
            } else { // il ne peut pas bouger, il pousse
                bloc.pousser(hashMap.get(indexHashMap).get(indexBloc + 1));
            }
        }
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

    public void needToBeMove(Bloc bloc, int indexHashMap, int indexStack) { // met à jour si le bloc est bien placé ou non
        if (indexStack == 0) {
            bloc.setSatisfied(bloc.getBlocDessous() == null);
        } else if (bloc.getBlocDessous() == this.hashMap.get(indexHashMap).get(indexStack - 1)) {
            bloc.setSatisfied(true);
        }
        bloc.setSatisfied(false);
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

    public HashMap<Integer, Stack<Bloc>> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<Integer, Stack<Bloc>> hashMap) {
        this.hashMap = hashMap;
    }

    public Stack<Bloc> getSolutionFinale() {
        return solutionFinale;
    }

    public void setSolutionFinale(Stack<Bloc> solutionFinale) {
        this.solutionFinale = solutionFinale;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
