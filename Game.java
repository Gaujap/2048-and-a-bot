public class Game {
    private int size;
    private int[][] board;
    private int score;
    private boolean lose;

    public Game(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.score = 0;
        this.lose = false;
    }

    private void addRandomTile() {
        // Add a random tile to the board
    }

    private void moveUp() {
        // Move all tiles up
    }

    private void moveDown() {
        // Move all tiles down
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
        // Print the board
    }

    public void printScore() {
        // Print the score
    }

    public void printLose() {
        // Print if the game is lost
    }

    public void play() {
        // Play the game
    }

    public static void main(String[] args) {
        Game game = new Game(4);
        game.play();
    }
}
