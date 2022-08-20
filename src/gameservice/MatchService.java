package gameservice;

import gameservice.models.Player;
import gameservice.rules.Match;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class MatchService {
    public static int firstServePlayer;
    public static String serveMessage = "Tiene el saque ";
    static Scanner sc = new Scanner(System.in);
    static Scanner scanner = new Scanner(System.in);

    public static int getFirstServePlayer() {
        return firstServePlayer;
    }

    public static void setFirstServePlayer(int firstServePlayer) {
        MatchService.firstServePlayer = firstServePlayer;
    }



    /**
     *This method prints a welcome message before the match starts.
     */
    public static void welcomeMessage() {
        System.out.println("Bienvenido al simulador virtual de tenis.");
        for (int i = 0; i < 20; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
                System.out.print("==");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\n");
    }



    /**
     * This method collects and validates the tournament name.
     */
    public static void collectTournamentName() {
        System.out.print("Ingrese el nombre del torneo: ");
        Match.setTournamentName(sc.nextLine().trim().toUpperCase());
        if (Match.getTournamentName().isEmpty()) {
            System.out.println("Atencion! Debe ingresar un nombre.");
            collectTournamentName();
        }
    }



    /**
     * This method requests the user to enter Player's name and invokes the validateProbability method in order to
     * validate the probability registered by the user.
     * @return Returns a new player using the collected data.
     */
    public static Player collectPlayerData()  {

        System.out.println("A continuacion, ingrese el nombre y apellido del jugador");
        String name = sc.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("El nombre es requerido.");
            collectPlayerData();
        }
        int probabilities= validateProbability(scanner);
        return new Player(name.toUpperCase(), probabilities);
    }


    /**
     * This method request the user to enter the Player's probability of winning the game and validates it.
     * @param scanner
     * @return Returns an int which is used in probabilityRectification method.
     */
    public static int validateProbability(Scanner scanner) {
        int probabilityValidated;
        try {
            System.out.println("Ingrese en una escala del 1 al 100, las probabilidades de que gane el jugador");
            probabilityValidated = Integer.parseInt(scanner.next());
            if (probabilityValidated <= 0 || probabilityValidated > 100) {
                probabilityValidated = validateProbability(scanner);
            }
        } catch (NumberFormatException e) {
            probabilityValidated = validateProbability(scanner);
        }
        return probabilityValidated;
    }


    
    
    /**
     * This method takes two players as parameters. It extracts their probabilities values and sums it. It uses that result as
     * the new 100% of probabilities and then calculates each probability by cross-multiplying. Finally sets the probability
     * attribute with the result.
     * @param player1
     * @param player2
     */
    public static void probabilityRectification(Player player1, Player player2){

        int sum = player1.getProbability()+ player2.getProbability();
        int probability1 = 100 * player1.getProbability() / sum;
        int probability2 = 100 - probability1;
        System.out.println(player1.getName() + " tiene " + probability1 + "% de chances de ganar el partido");
        System.out.println(player2.getName() + " tiene " + probability2 + "% de chances de ganar el partido");
        player1.setProbability(probability1);
        player2.setProbability(probability2);
    }



    /**
     * This method takes two players as parameters, generates a random number between one and two and assignes it
     * to the variable firstServePlayer.
     * @param player1
     * @param player2
     * @throws InterruptedException
     */
    public static void drawFirstServe(Player player1, Player player2) throws InterruptedException {
        System.out.println("Se lanza la moneda para asignar quien tiene el primer saque");
        for (int i = 0; i < 30; i++) {
            System.out.print(".");
            TimeUnit.MILLISECONDS.sleep(150);
        }
        System.out.println();
        int drawWinnerNumber = (int) Math.ceil(Math.random() * 2);
        setFirstServePlayer(drawWinnerNumber);
        if (drawWinnerNumber == 1){
            System.out.println(serveMessage + player1.getName() + "\n");
        } else {
            System.out.println(serveMessage + player2.getName() + "\n");
        }
    }



    /**
     * This method prints a string showing the tournament resume: tournament name, player1 and player2.
     * @param player1
     * @param player2
     */
    public static void mensajeComienzoTorneo(Player player1, Player player2){
        StringBuilder sb = new StringBuilder("El torneo ")
                .append(Match.getTournamentName()).append(" en el que se disputaran ").append(player1.getName())
                .append(" y ").append(player2.getName()).append(" esta por comenzar.");
        System.out.println(sb);
        System.out.println();
    }
}









