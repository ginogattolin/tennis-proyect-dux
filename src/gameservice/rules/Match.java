package gameservice.rules;

import gameservice.MatchService;
import gameservice.models.Player;

import java.util.Scanner;

public class Match {

    private static int numberOfSets;
    public static String tournamentName;


    public Match() {
    }

    public static String getTournamentName() {
        return tournamentName;
    }

    public static void setTournamentName(String tournamentName) {
        Match.tournamentName = tournamentName;
    }

    public static int getNumberOfSets() {
        return numberOfSets;
    }

    public void setNumberOfSets(int numberOfSets) {
        Match.numberOfSets = numberOfSets;
    }



    /**
     * This method requires the user to enter the amount of sets to play and assignes it to numberOfSets variable.
     *
     * @return Returns an int with the amount of sets.
     */
    public int setsDefinition() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese 3 para jugar al mejor de 3 sets.");
        System.out.println("Ingrese 5 para jugar al mejor de 5 sets.");
        int sets;
        try {
            sets = Integer.parseInt(scanner.nextLine());
            if (sets != 3 && sets != 5) {
                sets = setsDefinition();
            } else {
                setNumberOfSets(sets);
            }
        } catch (NumberFormatException e) {
            sets = setsDefinition();
        }
        return sets;
    }


    /**
     * This is the most important method in the app. It takes two players as parameters and runs the full match.
     * In order to do that, invokes the method playSet.
     *
     * @param player1
     * @param player2
     * @throws InterruptedException
     */
    public void startMatch(Player player1, Player player2) throws InterruptedException {

        int setsPlayer1 = 0;
        int setsPlayer2 = 0;
        String sb = "Resultado parcial del partido:\n";

        while ((setsPlayer1 + setsPlayer2) <= numberOfSets) {
            if (Set.playSet(player1, player2) == 1) {
                setsPlayer1++;
                System.out.println(sb + player1.getName() + " " + setsPlayer1 + "-" + setsPlayer2 + " " + player2.getName());
            } else {
                setsPlayer2++;
                System.out.println(sb + player1.getName() + " " + setsPlayer1 + "-" + setsPlayer2 + " " + player2.getName());
            }
            System.out.println("\n\n");
            if (numberOfSets == 3 && (setsPlayer1 == 2 || setsPlayer2 == 2)) {
                break;
            } else if (numberOfSets == 5 && (setsPlayer1 == 3 || setsPlayer2 == 3)) {
                break;
            }
        }
        showFinalResults(player1, player2);
        if (setsPlayer1 > setsPlayer2) {
            System.out.println("\n" + player1.getName().toUpperCase() + " ha ganado el torneo " + tournamentName.toUpperCase() + "!!!\n");
        } else {
            System.out.println("\n" + player2.getName().toUpperCase() + " ha ganado el torneo " + tournamentName.toUpperCase() + "!!!\n");
        }
    }

    public void showFinalResults(Player player1, Player player2) {

        int count = 0;
        int[] resultsPlayer1of3 = new int[3];
        int[] resultsPlayer1of5 = new int[5];
        int[] resultsPlayer2of3 = new int[3];
        int[] resultsPlayer2of5 = new int[5];

        while (!Set.getResultOfTheSet().isEmpty()) {
            if (getNumberOfSets() == 3) {
                resultsPlayer2of3[count] = Set.getResultOfTheSet().getLast();
                Set.getResultOfTheSet().removeLast();

                resultsPlayer1of3[count] = Set.getResultOfTheSet().getLast();
                Set.getResultOfTheSet().removeLast();
            } else {
                resultsPlayer2of5[count] = Set.getResultOfTheSet().getLast();
                Set.getResultOfTheSet().removeLast();

                resultsPlayer1of5[count] = Set.getResultOfTheSet().getLast();
                Set.getResultOfTheSet().removeLast();
            }
            count++;
        }
        printFinalResults(player1, player2,resultsPlayer1of3, resultsPlayer1of5, resultsPlayer2of3, resultsPlayer2of5);
    }

    private void printFinalResults(Player player1, Player player2, int[] resPl1of3, int[] resPl1of5, int[] resPl2of3, int[] resPl2of5) {
        String s = " ";
        System.out.println(getTournamentName());
        int longerName = Math.max(player1.getName().length(), player2.getName().length());
        int difference = longerName - Math.min(player1.getName().length(), player2.getName().length());
        String aux = "";
        for (int i = 0; i < difference; i++) {
            aux += " ";
        }
        String name1 = player1.getName();
        String name2 = player2.getName();
        if (player1.getName().length() > player2.getName().length()) {
            name2 += aux;
        } else {
            name1 += aux;
        }
        if (getNumberOfSets() == 3) {
            System.out.println(name1 + s + resPl1of3[2] + s + resPl1of3[1] + s + resPl1of3[0]);
            System.out.println(name2 + s + resPl2of3[2] + s + resPl2of3[1] + s + resPl2of3[0]);
        } else {
            System.out.println(name1 + s + resPl1of5[4] + s + resPl1of5[3] + s + resPl1of5[2] + s + resPl1of5[1] + s + resPl1of5[0]);
            System.out.println(name2 + s + resPl2of5[4] + s + resPl2of5[3] + s + resPl2of5[2] + s + resPl2of5[1] + s + resPl2of5[0]);
        }
    }

    public int askForRematch(Player player1, Player player2) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Jugar revancha? Presione 'y' para jugar, 'n' para cerrar el juego");
        String rematch = scanner.nextLine().trim().toUpperCase();

        switch (rematch) {
                case "Y" -> {
                    MatchService.drawFirstServe(player1, player2);
                    startMatch(player1, player2);
                    askForRematch(player1, player2);
                }
                case "N" -> {
                    return 0;
                }
                default -> {
                    System.out.println("Es necesaria una respuesta valida.");
                    askForRematch(player1, player2);
                }
            }
            return 0;
        }
    }
