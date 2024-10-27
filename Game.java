import java.util.Random;
import java.util.Scanner;

public class Game {
    private int size;
    private int[][] board;
    private int score;
    private boolean lose;
    private boolean win;
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);
    private MessageManager messageManager = new MessageManager();

    public Game(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.score = 0;
        this.lose = false;
        addRandomTile();
        addRandomTile();
    }

    private void addRandomTile() {
        int value = random.nextInt(10) < 9 ? 2 : 4;
        int x, y;
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (board[x][y] != 0);

        board[x][y] = value;
    }

    private boolean compact(int[] line) {
        boolean moved = false;
        int target = 0;
        for (int i = 0; i < line.length; i++) {
            if (line[i] != 0) {
                if (i != target) {
                    line[target] = line[i];
                    line[i] = 0;
                    moved = true;
                }
                target++;
            }
        }
        return moved;
    }

    private boolean merge(int[] line) {
        boolean merged = false;
        for (int i = 0; i < line.length - 1; i++) {
            if (line[i] != 0 && line[i] == line[i + 1]) {
                line[i] *= 2;
                score += line[i];
                line[i + 1] = 0;
                merged = true;
            }
        }
        return merged;
    }

    private boolean slideAndMerge(int[] line) {
        boolean moved = compact(line);
        boolean merged = merge(line);
        moved = compact(line) || moved || merged;
        return moved;
    }

    private boolean slideVertical(boolean upwards) {
        boolean moved = false;
        for (int col = 0; col < size; col++) {
            int[] line = new int[size];
            for (int row = 0; row < size; row++) {
                line[row] = upwards ? board[row][col] : board[size - row - 1][col];
            }
            moved |= slideAndMerge(line);
            for (int row = 0; row < size; row++) {
                if (upwards) {
                    board[row][col] = line[row];
                } else {
                    board[size - row - 1][col] = line[row];
                }
            }
        }
        return moved;
    }

    private void moveUp() {
        boolean moved = false;

        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (board[i][j] != 0) {
                    int k = i;
                    while (k > 0 && board[k - 1][j] == 0) {
                        board[k - 1][j] = board[k][j];
                        board[k][j] = 0;
                        k--;
                        moved = true;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0 && i > 0 && board[i - 1][j] == board[i][j]) {
                    board[i - 1][j] *= 2;
                    score += board[i - 1][j];
                    board[i][j] = 0;
                    moved = true;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (board[i][j] != 0) {
                    int k = i;
                    while (k > 0 && board[k - 1][j] == 0) {
                        board[k - 1][j] = board[k][j];
                        board[k][j] = 0;
                        k--;
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            addRandomTile();
        }
    }

    private void moveDown() {
        boolean moved = false;

        for (int i = 0; i < size; i++) {
            for (int j = size - 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int k = i;
                    while (k < size - 1 && board[k + 1][j] == 0) {
                        board[k + 1][j] = board[k][j];
                        board[k][j] = 0;
                        k++;
                        moved = true;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= 0; j--) {
                if (board[i][j] != 0 && j < size - 1 && board[i][j + 1] == board[i][j]) {
                    board[i][j + 1] *= 2;
                    score += board[i][j + 1];
                    board[i][j] = 0;
                    moved = true;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = size - 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int k = i;
                    while (k < size - 1 && board[k + 1][j] == 0) {
                        board[k + 1][j] = board[k][j];
                        board[k][j] = 0;
                        k++;
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            addRandomTile();
        }
    }

    private void moveLeft() {
        boolean moved = false;

        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (board[i][j] != 0) {
                    int k = j;
                    while (k > 0 && board[i][ k - 1] == 0) {
                        board[i][k - 1] = board[i][k];
                        board[i][k] = 0;
                        k--;
                        moved = true;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0 && j > 0 && board[i][j - 1] == board[i][j]) {
                    board[i][j - 1] *= 2;
                    score += board[i][j - 1];
                    board[i][j] = 0;
                    moved = true;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (board[i][j] != 0) {
                    int k = j;
                    while (k > 0 && board[i][ k - 1] == 0) {
                        board[i][k - 1] = board[i][k];
                        board[i][k] = 0;
                        k--;
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            addRandomTile();
        }
    }

    private void moveRight() {
        boolean moved = false;

        for (int i = 0; i < size; i++) {
            for (int j = size - 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int k = j;
                    while (k < size - 1 && board[i][k + 1] == 0) {
                        board[i][k + 1] = board[i][k];
                        board[i][k] = 0;
                        k++;
                        moved = true;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= 0; j--) {
                if (board[i][j] != 0 && j < size - 1 && board[i][j + 1] == board[i][j]) {
                    board[i][j + 1] *= 2;
                    score += board[i][j + 1];
                    board[i][j] = 0;
                    moved = true;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = size - 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int k = j;
                    while (k < size - 1 && board[i][k + 1] == 0) {
                        board[i][k + 1] = board[i][k];
                        board[i][k] = 0;
                        k++;
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            addRandomTile();
        }
    }

    private boolean isLose() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
                if (i > 0 && board[i - 1][j] == board[i][j]) {
                    return false;
                }
                if (i < size - 1 && board[i + 1][j] == board[i][j]) {
                    return false;
                }
                if (j > 0 && board[i][j - 1] == board[i][j]) {
                    return false;
                }
                if (j < size - 1 && board[i][j + 1] == board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWin() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public void move(String direction) {
        switch (direction) {
            case "z":
                moveUp();
                break;
            case "s":
                moveDown();
                break;
            case "q":
                moveLeft();
                break;
            case "d":
                moveRight();
                break;
            default:
                messageManager.printInvalidDirection();
        }

        lose = isLose();
        win = isWin();
    }

    public void printBoard() {
        messageManager.printBoard(board);
    }

    public void printScore() {
        messageManager.printScore(score);
    }

    public void printLose() {
        if (lose) {
            messageManager.printLose();
        }
    }

    public void printWin() {
        if (isWin()) {
            messageManager.printWin();
        }
    }

    public void play() {
        addRandomTile();
        addRandomTile();
        printBoard();
        printScore();

        while (!lose) {
            String direction = scanner.next();
            move(direction);
            printBoard();
            printScore();
            printLose();
            printWin();
        }
    }

    public static void main(String[] args) {
        Game game = new Game(4);
        game.play();
    }
}
