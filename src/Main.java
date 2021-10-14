import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static int endIteration = 10000;

    public static void main(String[] args) {
        Bloc blocA = new Bloc("A");
        Bloc blocB = new Bloc("B");
        Bloc blocC = new Bloc("C");
        Bloc blocD = new Bloc("D");

        ArrayList<Bloc> mesBlocs = new ArrayList<>();
        mesBlocs.add(blocA);
        mesBlocs.add(blocB);
        mesBlocs.add(blocC);
        mesBlocs.add(blocD);
        Environnement env = new Environnement(mesBlocs);
        boolean end = false;

        int compteur = 0;
        Random rand = new Random();
        int nbAleatoire = 0;
        Bloc blocSelect;
        while(!end && endIteration != 0){
            nbAleatoire = rand.nextInt(4);
            blocSelect = mesBlocs.get(nbAleatoire);
            env.perception(blocSelect);
            compteur++;
            System.out.println(env);
            System.out.println("NB Iteration : " + compteur);
            end = env.estTermine();
            endIteration--;
        }

    }
}
