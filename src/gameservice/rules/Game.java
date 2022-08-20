package gameservice.rules;

import gameservice.MatchService;
import gameservice.models.Player;

import java.util.concurrent.TimeUnit;

public class Game extends Set {
    private static int theCurrentGameWinner = 0;
    private static int playerServe = MatchService.getFirstServePlayer();

    public Game() {
        super();
    }

    public static int getPlayerServe() {
        return playerServe;
    }

    public static void setPlayerServe(int playerServe) {
        Game.playerServe = playerServe;
    }

    public static String getFirstServe(Player player1, Player player2) {
        if (MatchService.getFirstServePlayer() == 1) {
            setPlayerServe(1);
            return player1.getName();
        } else {
            setPlayerServe(2);
            return player2.getName();
        }
    }

    public static void changeServe() {
        if (getPlayerServe() == 1) {
            setPlayerServe(2);
        } else {
            setPlayerServe(1);
        }
    }

    public static int getTheCurrentGameWinner() {
        return theCurrentGameWinner;
    }

    public static void setTheCurrentGameWinner(int theCurrentGameWinner) {
        Game.theCurrentGameWinner = theCurrentGameWinner;
    }

    public static Integer[] returnPoints() {
        Integer[] points = new Integer[4];
        points[0] = 15;
        points[1] = 30;
        points[2] = 40;
        points[3] = 1;
        return points;
    }


    /**
     * This method takes two players as parameters and runs the game point by point. It invokes the playPoint method and,
     * if necessary, the playDeuce method.
     * @param player1
     * @param player2
     * @return Returns the playDeuce method or the printGameWinner method, both of them return an int: 1 if player1 won the game
     * 2 if player2 won the game.
     * @throws InterruptedException
     */
    public static Integer playGame(Player player1, Player player2) throws InterruptedException {
        int pointsPlayer1 = 0;
        int pointsPlayer2 = 0;
        int count1 = 0;
        int count2 = 0;
        System.out.println();
        if (getPlayerServe() == 1){
            System.out.println("Saca " + player1.getName());
        } else {
            System.out.println("Saca " + player2.getName());
        }

        do{
            if (playPoint(player1, player2) == 1) {
                pointsPlayer1 = returnPoints()[count1];
                System.out.println(pointsPlayer1 + "-" + pointsPlayer2);
                count1++;
            } else {
                pointsPlayer2 = returnPoints()[count2];
                System.out.println(pointsPlayer2 + "-" + pointsPlayer1);
                count2++;
            }
            if (pointsPlayer1 == 40 && pointsPlayer2 == 40) {
                changeServe();
                return playDeuce(player1, player2);
            }
        } while ((pointsPlayer1 != 1 || pointsPlayer2 != 1) && (count1 != 4 && count2 != 4));

        changeServe();

        return printGameWinner(player1, player2, count1, count2);
    }


    /**
     * Prints the winner of the current game.
     * @param player1
     * @param player2
     * @param count1
     * @param count2
     * @return Returns an int for counting the games won for each player
     */
    private static int printGameWinner(Player player1, Player player2, int count1, int count2) {
        if (count1 > count2) {
            System.out.println("\n" + player1.getName() + " ha ganado el game.");
            setTheCurrentGameWinner(1);
            return 1;
        } else {
            System.out.println("\n" + player2.getName() + " ha ganado el game.");
            setTheCurrentGameWinner(2);
            return 2;
        }
    }


    /**
     * This method takes two players as parameters and plays the point by generating a random number between 1 or 100.
     * The probability of each player is used as a range. It assigns the winner point to player2 as default. But if the
     * random number is within the range of the player1 probability, it assigns the winner point to the player1.
     * @param player1
     * @param player2
     * @return Returns an int wich is used for counting the points won by each player.
     * @throws InterruptedException
     */
    public static int playPoint(Player player1, Player player2) throws InterruptedException {
        int pointResult = (int) (Math.random() * 100);
        TimeUnit.MILLISECONDS.sleep(500);
        String name = player2.getName();
        int winnerPlayer = 2;
        if (pointResult <= player1.getProbability()) {
            name = player1.getName();
            winnerPlayer = 1;
        }
        System.out.println("Gana el punto " + name);
        return winnerPlayer;
    }


    /**
     *This method is only invoked if the game is 40-40. In order to return a winner, it invokes the playPoint method
     * controlling the result. If a player takes 2 points of advantage over the other, it notices it by using the control variable
     * and sets that player as the winner.
     * @param player1
     * @param player2
     * @return Returns an int used for counting the amount of games won for each player.
     * @throws InterruptedException
     */
    public static int playDeuce(Player player1, Player player2) throws InterruptedException {
        Integer pointPlayer1 = 0;
        Integer pointPlayer2 = 0;
        int control;
        do {
            if (playPoint(player1, player2) == 1) {
                pointPlayer1++;
            } else {
                pointPlayer2++;
            }
            control = pointPlayer1 - pointPlayer2;
            switch (control) {
                case 1 -> System.out.println("Ventaja " + player1.getName());
                case -1 -> System.out.println("Ventaja " + player2.getName());
                case 0 -> System.out.println("Iguales");
            }
        } while (Math.abs(control) != 2);
        if (pointPlayer1 > pointPlayer2) {
            System.out.println(player1.getName() + " ha ganado el game.");
            setTheCurrentGameWinner(1);
            return 1;
        } else {
            System.out.println(player2.getName() + " ha ganado el game.");
            setTheCurrentGameWinner(2);
            return 2;
        }
    }


    /**
     * This method exists to solve the problem of 6-6 sets count. It is only invoked in case the count of sets won for each player
     * is 6-6. It plays a special game invoking the playPoint method and counting the results from 0 to 7.
     * The playDeuce method is invoked in case the count is 6-6.
     * @param player1
     * @param player2
     * @return Returns an int which is used for counting the amount of sets won by each player.
     * @throws InterruptedException
     */
    public static int playTieBreak(Player player1, Player player2) throws InterruptedException {
        int pointPlayerOne = 0;
        int pointPlayerTwo = 0;
        System.out.println("\nSe jugara tiebreak para definir el set.\n");
        do {
            if (playPoint(player1, player2) == 1) {
                pointPlayerOne++;
                System.out.println(pointPlayerOne + "-" + pointPlayerTwo);
            } else {
                pointPlayerTwo++;
                System.out.println(pointPlayerTwo + "-" + pointPlayerOne);
            }
            if (pointPlayerOne == 6 && pointPlayerTwo == 6) {
                return playDeuce(player1, player2);
            }
        } while (pointPlayerOne < 7 && pointPlayerTwo < 7);
        changeServe();
        if (pointPlayerOne > pointPlayerTwo) {
           System.out.println("\n" + player1.getName() + " ha ganado el set.");
            setTheCurrentGameWinner(1);
            return 1;
        } else {
           System.out.println("\n" + player2.getName() + " ha ganado el set.");
            setTheCurrentGameWinner(2);
            return 2;
        }
    }
}

