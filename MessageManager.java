public class MessageManager {

    public void printBoard(int[][] board){
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public void printWin(){
        System.out.println("You won!");
    }

    public void printLose(){
        System.out.println("You lost!");
    }

    public void printScore(int score){
        System.out.println("Score: " + score);
    }

    public void printInvalidDirection(){
        System.out.println("Invalid direction. Use 'z', 'q', 's', 'd'.");
    }
}
