import java.util.Random;

public class Bot {
    private Game game;
    private GameApp gameApp;

    public Bot(Game game, GameApp gameApp) {
        this.game = game;
        this.gameApp = gameApp;
    }

    public String getBestMove() {
        String[] directions = {"z", "s", "q", "d"};
        return directions[new Random().nextInt(directions.length)];
    }

    public void play() {
        while (!game.isLose() && !game.isWin()) {
            String direction = getBestMove();
            game.move(direction);
            gameApp.updateGrid();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
