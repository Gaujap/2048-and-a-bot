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
            if (!isMoveValid(direction)) continue;

            Game gameCopy = new Game(game.getSize());
            gameCopy.setBoard(copyBoard(game.getBoard()));
            gameCopy.setScore(game.getScore());

            gameCopy.move(direction);

            int currentScore = gameCopy.getScore();
            currentScore += getEmptyTiles(gameCopy.getBoard());
            currentScore += getCornerTiles(gameCopy.getBoard());
            currentScore -= getTileSmoothnessPenality(gameCopy.getBoard());

            if (currentScore > bestScore) {
                bestScore = currentScore;
                bestDirection = direction;
            }
        }

        return bestDirection.isEmpty() ? null : bestDirection;
    }

    public String getBestMoveMonteCarlo(int simulations) {
        int bestScore = -1;
        String bestDirection = "";
        String[] directions = {"z", "s", "q", "d"};

        for (String direction : directions) {
            if (!isMoveValid(direction)) continue;
            int totalScore = 0;

            for (int i = 0; i < simulations; i++) {
                Game gameCopy = new Game(game.getSize());
                gameCopy.setBoard(copyBoard(game.getBoard()));
                gameCopy.setScore(game.getScore());

                gameCopy.move(direction);

                int currentScore = gameCopy.getScore();
                currentScore += getEmptyTiles(gameCopy.getBoard());
                currentScore += getCornerTiles(gameCopy.getBoard());
                currentScore -= getTileSmoothnessPenality(gameCopy.getBoard());

                currentScore += simulateRandomGame(gameCopy, 10);

                totalScore += currentScore;
            }

            int averageScore = totalScore / simulations;

            if (averageScore > bestScore) {
                bestScore = averageScore;
                bestDirection = direction;
            }
        }
        return bestDirection.isEmpty() ? null : bestDirection;
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

    public int getEmptyTiles(int[][] board) {
        int emptyTiles = 0;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    emptyTiles++;
                }
            }
        }
        return emptyTiles * 10;
    }

    private int getCornerTiles(int[][] board) {
        int maxTile = getMaxTile(board);
        int cornerTile = Math.max(Math.max(board[0][0], board[0][board.length - 1]),
                Math.max(board[board.length - 1][0], board[board.length - 1][board.length - 1]));
        return (cornerTile == maxTile) ? 100 : 0;
    }

    private int getTileSmoothnessPenality(int[][] board) {
        int penalty = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (i + 1 < board.length) {
                    penalty += Math.abs(board[i][j] - board[i + 1][j]);
                }
                if (j + 1 < board[0].length) {
                    penalty += Math.abs(board[i][j] - board[i][j + 1]);
                }
            }
        }
        return penalty;
    }

    private int getMaxTile(int[][] board) {
        int maxTile = 0;
        for (int[] row : board) {
            for (int cell : row) {
                maxTile = Math.max(maxTile, cell);
            }
        }
        return maxTile;
    }

    private boolean isMoveValid(String direction) {
        Game gameCopy = new Game(game.getSize());
        gameCopy.setBoard(copyBoard(game.getBoard()));
        gameCopy.setScore(game.getScore());

        gameCopy.move(direction);
        return !areBoardsEqual(game.getBoard(), gameCopy.getBoard());
    }

    private boolean areBoardsEqual(int[][] board1, int[][] board2) {
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[i].length; j++) {
                if (board1[i][j] != board2[i][j]) return false;
            }
        }
        return true;
    }

    public void play() {
        while (!game.isLose()) {
            String direction = getBestMoveMonteCarlo(100);
//            String direction = getBestMove();
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
