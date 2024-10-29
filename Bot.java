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

    public String getBestMoveMonteCarlo(int simulations) {
        int bestScore = -1;
        String bestDirection = "";
        String[] directions = {"z", "s", "q", "d"};

        for (String direction : directions) {
            int totalScore = 0;

            for (int i = 0; i < simulations; i++) {
                Game gameCopy = new Game(game.setSize());
                gameCopy.setBoard(copyBoard(game.getBoard()));
                gameCopy.setScore(game.getScore());

                gameCopy.move(direction);

                totalScore += simulateRandomGame(gameCopy, 10);
            }

            int averageScore = totalScore / simulations;

            if (averageScore > bestScore) {
                bestScore = averageScore;
                bestDirection = direction;
            }
        }
        return bestDirection;
    }

    private int simulateRandomGame(Game gameCopy, int steps) {
        Random random = new Random();

        for (int i = 0; i < steps; i++) {
            String randomDirection = getRandomDirection(random);
            gameCopy.move(randomDirection);

            if (gameCopy.isLose()) break;
        }
        return gameCopy.getScore();
    }

    private String getRandomDirection(Random random) {
        String[] directions = {"z", "s", "q", "d"};
        return directions[random.nextInt(directions.length)];
    }

    private int[][] copyBoard(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }


    public void play() {
        while (!game.isLose()) {
            String direction = getBestMoveMonteCarlo(100);
            game.move(direction);

            Platform.runLater(() -> {
                gameApp.updateGrid();
                gameApp.updateScoreAndStatus();
            });

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
