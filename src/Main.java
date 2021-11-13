import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Entrer le numéro de la question que vous voulez (1 ou 2)");
        int n = reader.nextInt();
        Scanner reader2 = new Scanner(System.in);  // Reading from System.in
        System.out.println("Entrer le nombre d'essais que vous voulez faire (minimum 1)");
        int n2 = reader2.nextInt();
        if (n == 1 && n2 >= 1) question1(n2);
        else if (n == 2 && n2 >= 1) question2(n2);

    }

    static void question1(int n) {
        int total = 0;
        for (int i = 0; i < n; i++) {
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
                blocSelect.perception();
                compteur++;
                System.out.println(env);
                System.out.println("NB Iteration : " + compteur);
                end = env.estTermine();
            }
            total += compteur;
        }
        System.out.println("Sur " + n + " essais, la moyenne pour des agents qui ne communiquent pas entre eux est de : " + total / n);
    }

    static void question2(int n) {
        int total = 0;
        for (int i = 0; i < n; i++) {
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
            Bloc blocSelect;
            env.communicationAgent();
            while (!end) {
                blocSelect = mesBlocs.stream().max(Comparator.comparingInt(Bloc::getPriorite)).get();
                blocSelect.perception2();
                compteur++;
                System.out.println(env);
                System.out.println("NB Iteration : " + compteur);
                end = env.estTermine();
            }
            total += compteur;
        }
        System.out.println("Sur " + n + " essais, la moyenne pour des agents qui communiquent entre eux est de : " + total / n);
    }
}
