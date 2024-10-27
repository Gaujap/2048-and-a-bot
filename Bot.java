import javafx.application.Platform;

import java.util.Random;

public class Bot {
    private Game game;
    private GameApp gameApp;

    public Bot(Game game, GameApp gameApp) {
        this.game = game;
        this.gameApp = gameApp;
    }

    public String getBestMove() {
        int bestScore = -1;
        String bestDirection = "";

        String[] directions = {"z", "s", "q", "d"};
        for (String direction : directions) {
            Game gameCopy = new Game(game.setSize());
            gameCopy.setBoard(copyBoard(game.getBoard()));
            gameCopy.setScore(game.getScore());

            gameCopy.move(direction);

            if (gameCopy.getScore() > bestScore) {
                bestScore = gameCopy.getScore();
                bestDirection = direction;
            }
        }
        return bestDirection;
    }

    private int[][] copyBoard(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }


    public void play() {
        while (!game.isLose() && !game.isWin()) {
            String direction = getBestMove();
            game.move(direction);

            Platform.runLater(() -> {
                gameApp.updateGrid();
                gameApp.updateScoreAndStatus();
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
