import java.util.Random;

public class Bot {
    private Game game;

    public Bot(Game game) {
        this.game = game;
    }

    public String getBestMove() {
        String[] directions = {"z", "s", "q", "d"};
        return directions[new Random().nextInt(directions.length)];
    }
}
