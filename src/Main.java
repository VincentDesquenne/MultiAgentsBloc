import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        int total = 0;


        // QUESTION 1
        /*for (int i = 0; i < 400; i ++) {
            Bloc blocA = new Bloc("A", null);
            Bloc blocB = new Bloc("B", blocA);
            Bloc blocC = new Bloc("C", blocB);
            Bloc blocD = new Bloc("D", blocC);

            ArrayList<Bloc> mesBlocs = new ArrayList<>();
            mesBlocs.add(blocA);
            mesBlocs.add(blocB);
            mesBlocs.add(blocC);
            mesBlocs.add(blocD);
            Environnement env = new Environnement(mesBlocs);
            boolean end = false;
            System.out.println(env);

            int compteur = 0;
            Random rand = new Random();
            int nbAleatoire = 0;
            Bloc blocSelect;
            while (!end) {
                nbAleatoire = rand.nextInt(4);
                blocSelect = mesBlocs.get(nbAleatoire);
                env.perception(blocSelect);
                compteur++;
                System.out.println(env);
                System.out.println("NB Iteration : " + compteur);
                end = env.estTermine();
            }
            total += compteur;
        }
        System.out.println("Sur 400 essais, la moyenne pour des agents qui communiquent entre eux est de : " + total/400);*/

        // QUESTION 2
        for (int i = 0; i < 400; i ++) {
            Bloc blocA = new Bloc("A", null);
            Bloc blocB = new Bloc("B", blocA);
            Bloc blocC = new Bloc("C", blocB);
            Bloc blocD = new Bloc("D", blocC);

            ArrayList<Bloc> mesBlocs = new ArrayList<>();
            mesBlocs.add(blocA);
            mesBlocs.add(blocB);
            mesBlocs.add(blocC);
            mesBlocs.add(blocD);
            Environnement env = new Environnement(mesBlocs);
            boolean end = false;
            System.out.println(env);
            int compteur = 0;
            int nbAleatoire = 0;
            Random rand = new Random();
            Bloc blocSelect;
            env.communicationAgent();
            while (!end) {
                blocSelect = mesBlocs.stream().max(Comparator.comparingInt(Bloc::getPriorite)).get();
                env.perception2(blocSelect);
                compteur++;
                System.out.println(env);
                System.out.println("NB Iteration : " + compteur);
                end = env.estTermine();
            }
            total += compteur;
        }
        System.out.println("Sur 400 essais, la moyenne pour des agents qui communiquent entre eux est de : " + total/400);

    }
}
