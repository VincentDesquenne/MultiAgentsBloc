import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.*;

public class Environnement {

    private ArrayList<Bloc> listBlocs = new ArrayList<>();
    private HashMap<Integer, ArrayList<String>> hashMap = new HashMap<>();
    private ArrayList<String> solutionFinale = new ArrayList<>();
    private int position;


    public Environnement(ArrayList<Bloc> mesBlocs) {
        ArrayList<String> blocsName = new ArrayList<>();
        listBlocs = (ArrayList<Bloc>) mesBlocs.clone();
        ArrayList<Integer> mesEntiers = new ArrayList<>();
        Collections.shuffle(mesBlocs);
        for (int i = 0; i< 4; i ++) {
            mesBlocs.get(i).setEnvironnement(this);
            if (i == 0) {
                mesBlocs.get(i).setBlocDessous(null);
                mesBlocs.get(i).setBlocDessus(mesBlocs.get(i+1));
            } else if (i == 3) {
                mesBlocs.get(i).setBlocDessous(mesBlocs.get(i-1));
                mesBlocs.get(i).setBlocDessus(null);
            }
            else {
                mesBlocs.get(i).setBlocDessous(mesBlocs.get(i-1));
                mesBlocs.get(i).setBlocDessus(mesBlocs.get(i+1));
            }
            blocsName.add(mesBlocs.get(i).getNom());
        }
        hashMap.put(0, blocsName);
        hashMap.put(1, new ArrayList<>());
        hashMap.put(2, new ArrayList<>());
        solutionFinale.add("A");
        solutionFinale.add("B");
        solutionFinale.add("C");
        solutionFinale.add("D");
    }

    public void perception(Bloc bloc) {
        int indexHashMap = 0;
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i).contains(bloc.getNom())) {
                indexHashMap = i;
                break;
            }
        }
        int indexBloc = hashMap.get(indexHashMap).indexOf(bloc.getNom());
        Random rand = new Random();
        int nbAleatoire = indexHashMap;
        if (indexBloc == hashMap.get(indexHashMap).size()-1) {
            while (nbAleatoire == indexHashMap) {
                nbAleatoire = rand.nextInt(3);
            }
            bloc.seDeplacer(nbAleatoire);
        } else if (hashMap.get(indexHashMap).get(indexBloc + 1) != null) {
            //this.perception(hashMap.get(indexHashMap).get(indexBloc + 1)); // correspond à notre pousser
            bloc.pousser();
        } else {
            while (nbAleatoire == indexHashMap) {
                nbAleatoire = rand.nextInt(3);
            }
            bloc.seDeplacer(nbAleatoire);
        }
    }

    public boolean estTermine() {
        for (Map.Entry<Integer, ArrayList<String>> mapentry : hashMap.entrySet()) {
            if (mapentry.getValue().size() == 4) {
                for (int i = 0; i < mapentry.getValue().size(); i++) {
                    if (mapentry.getValue().get(i) != solutionFinale.get(i)) return false;
                }
            } else return false;
        }
        return true;
    }

    public Bloc findBlocByName(String nom) {
        for(int i=0; i < listBlocs.size(); i++){
            if(listBlocs.get(i).getNom() == nom){
                return listBlocs.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        for (Map.Entry<Integer, ArrayList<String>> mapentry : hashMap.entrySet()) {
            System.out.println("POSITION : " + mapentry.getKey());
            for (int i = 0; i < mapentry.getValue().size(); i++) {
                System.out.println("|| " + mapentry.getValue().get(mapentry.getValue().size()-i-1) + " ||");
            }
        }

        return "END \n -------------------------------------------------------";
    }

    public HashMap<Integer, ArrayList<String>> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<Integer, ArrayList<String>> hashMap) {
        this.hashMap = hashMap;
    }

    public ArrayList<String> getSolutionFinale() {
        return solutionFinale;
    }

    public void setSolutionFinale(ArrayList<String> solutionFinale) {
        this.solutionFinale = solutionFinale;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
