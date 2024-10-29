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
        return gameCopy.getScore() + evaluateGrid(gameCopy);
    }

    private int evaluateGrid(Game gameCopy) {
        int[][] board = gameCopy.getBoard();
        int score = 0;

        score += countEmptyCells(board) * 10;

        score += calculateGroupingScore(board);

        score += calculateCornerBonus(board);

        return score;
    }

    private int countEmptyCells(int[][] board) {
        int emptyCount = 0;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) emptyCount++;
            }
        }
        return emptyCount;
    }

    private int calculateGroupingScore(int[][] board) {
        int groupingScore = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != 0) {
                    if (row < board.length - 1 && board[row][col] == board[row + 1][col]) {
                        groupingScore += board[row][col];
                    }
                    if (col < board[row].length - 1 && board[row][col] == board[row][col + 1]) {
                        groupingScore += board[row][col];
                    }
                }
            }
        }
        return groupingScore;
    }

    private int calculateCornerBonus(int[][] board) {
        int cornerBonus = 0;

        int maxCell = getMaxCell(board);
        if (board[0][0] == maxCell || board[0][board.length - 1] == maxCell || board[board.length - 1][0] == maxCell || board[board.length - 1][board.length - 1] == maxCell) {
            cornerBonus += maxCell * 5;
        }
        return cornerBonus;
    }

    private int getMaxCell(int[][] board) {
        int maxCell = 0;
        for (int[] row : board) {
            for (int cell : row) {
                maxCell = Math.max(maxCell, cell);
            }
        }
        return maxCell;
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
