import gameservice.models.Player;
import gameservice.rules.Match;

import static gameservice.MatchService.*;


public class GameApp {

    public static void main(String[] args) throws InterruptedException {


        welcomeMessage();
        collectTournamentName();

        Match firstMatch = new Match();

        firstMatch.setsDefinition();
        Player player1 = collectPlayerData();
        Player player2 = collectPlayerData();

        probabilityRectification(player1, player2);
        mensajeComienzoTorneo(player1,player2);
        drawFirstServe(player1, player2);

        firstMatch.startMatch(player1, player2);

        firstMatch.askForRematch(player1, player2);
    }
}
