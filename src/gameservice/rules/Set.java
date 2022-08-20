package gameservice.rules;

import gameservice.MatchService;
import gameservice.models.Player;


import java.util.LinkedList;


public class Set extends Match {

    private static LinkedList<Integer> resultOfTheSet = new LinkedList<>();

    public Set() {
        super();
    }

    public static LinkedList<Integer> getResultOfTheSet() {
        return resultOfTheSet;
    }

    public static void setResultOfTheSet(LinkedList<Integer> resultOfTheSet) {
        Set.resultOfTheSet = resultOfTheSet;
    }

    /**
     * This method takes two players as parameters and plays the set. It invokes the playGame method or the
     * playTieBreak method depending on the case.
     * @param player1
     * @param player2
     * @return Returns an int which is used for counting the won sets of each player.
     * @throws InterruptedException
     */
    public static Integer playSet(Player player1, Player player2) throws InterruptedException {

        int gamesWon1=0;
        int gamesWon2=0;
        int theSetWinner;

        while (gamesWon1 < 6 && gamesWon2 < 6) {
            int theGameWinner = Game.playGame(player1, player2);
            if (theGameWinner == 1) {
                gamesWon1++;
                System.out.println(gamesWon1 + "-" + gamesWon2);
            } else {
                gamesWon2++;
                System.out.println(gamesWon2 + "-" + gamesWon1);
            }
            if (gamesWon1 == 5 && gamesWon2 == 5) {
                int almostLastGame = Game.playGame(player1, player2);
                if (almostLastGame == 1) {
                    gamesWon1++;
                    System.out.println(gamesWon1 + "-" + gamesWon2);
                } else {
                    gamesWon2++;
                    System.out.println(gamesWon2 + "-" + gamesWon1);
                }
                break;
            }
        }
                if (gamesWon1 == 6 && gamesWon2 == 5) {
                    if (Game.playGame(player1, player2) == 2) {
                        gamesWon2++;
                        System.out.println(gamesWon2 + "-" + gamesWon1);
                        return increaseGameCountPlayTiebreak(player1, player2, gamesWon1, gamesWon2);
                    } else {
                        gamesWon1++;
                    }
                } else if (gamesWon2 == 6 && gamesWon1 == 5){
                    if (Game.playGame(player1, player2) == 1){
                        gamesWon1++;
                        System.out.println(gamesWon1 + "-" + gamesWon2);
                        return increaseGameCountPlayTiebreak(player1, player2, gamesWon1, gamesWon2);
                    } else {
                        gamesWon2++;
                    }
                }
        if (gamesWon1 >= 6) {
            System.out.println("\n" + gamesWon1 + "-" + gamesWon2);
            System.out.println("\n\n" + player1.getName() + " ha ganado el set.");
            theSetWinner = 1;
        } else {
            System.out.println("\n" + gamesWon2 + "-" + gamesWon1);
            System.out.println("\n\n" + player2.getName() + " ha ganado el set.");
            theSetWinner = 2;
        }
        changeFirstServeOfTheSet();

        resultOfTheSet.add(gamesWon1);
        resultOfTheSet.add(gamesWon2);
        return theSetWinner;
    }

    private static void changeFirstServeOfTheSet() {
        if (MatchService.getFirstServePlayer() == 1){
            MatchService.setFirstServePlayer(2);
        } else{
            MatchService.setFirstServePlayer(1);
        }
    }

    private static int increaseGameCountPlayTiebreak(Player player1, Player player2, int gamesWon1, int gamesWon2) throws InterruptedException {
        if ((Game.playTieBreak(player1, player2)) == 1){
            gamesWon1++;
            System.out.println(gamesWon1 + "-" + gamesWon2);
            resultOfTheSet.add(gamesWon1);
            resultOfTheSet.add(gamesWon2);
            changeFirstServeOfTheSet();
            return 1;
        } else {

            gamesWon2++;
            System.out.println(gamesWon1 + "-" + gamesWon2);
            resultOfTheSet.add(gamesWon1);
            resultOfTheSet.add(gamesWon2);
            changeFirstServeOfTheSet();
            return 2;
        }
    }
}
