import java.util.Random;

public class Game {
    private int size;
    private int[][] board;
    private int score;
    private boolean lose;
    private Random random = new Random();

    public Game(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.score = 0;
        this.lose = false;
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
        // Move all tiles left
    }

    private void moveRight() {
        // Move all tiles right
    }

    private boolean isLose() {
        // Check if the game is lost
        return false;
    }

    public void move(String direction) {
        // Move all tiles in the given direction
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printScore() {
        // Print the score
    }

    public void printLose() {
        // Print if the game is lost
    }

    public void play() {
        addRandomTile();
        addRandomTile();
        printBoard();
        printScore();
        printLose();
    }

    public static void main(String[] args) {
        Game game = new Game(4);
        game.play();
    }
}
