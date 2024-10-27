import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GameApp extends Application {
    private Game game;
    private GridPane grid;
    private Label scoreLabel;
    private Text statusText;

    @Override
    public void start(Stage primaryStage) {
        game = new Game(4);
        grid = new GridPane();
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font(18));
        statusText = new Text();
        statusText.setFont(Font.font(18));

        BorderPane root = new BorderPane();
        VBox topContainer = new VBox(10);
        topContainer.getChildren().addAll(scoreLabel, statusText);

        root.setTop(topContainer);
        root.setCenter(grid);
        updateGrid();

        Scene scene = new Scene(root, 400, 450);
        primaryStage.setTitle("2048 Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case Z -> game.move("z");
            case S -> game.move("s");
            case Q -> game.move("q");
            case D -> game.move("d");
        }
        updateGrid();
        updateScoreAndStatus();
    }

    private void updateGrid() {
        grid.getChildren().clear();
        int[][] board = game.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Label cell = new Label(String.valueOf(board[row][col] == 0 ? "" : board[row][col]));
                cell.setPrefSize(100, 100);
                cell.setFont(Font.font(24));
                cell.setTextAlignment(TextAlignment.CENTER);
                cell.setStyle(getCellStyle(board[row][col]));
                grid.add(cell, col, row);
            }
        }
    }

    private void updateScoreAndStatus() {
        scoreLabel.setText("Score: " + game.getScore());

        if (game.isWin()) {
            statusText.setText("Congratulations! You've won!");
        } else if (game.isLose()) {
            statusText.setText("Game Over! You've lost.");
        } else {
            statusText.setText("");
        }
    }

    private String getCellStyle(int value) {
        String color;
        switch (value) {
            case 2 -> color = "#eee4da";
            case 4 -> color = "#ede0c8";
            case 8 -> color = "#f2b179";
            case 16 -> color = "#f59563";
            case 32 -> color = "#f67c5f";
            case 64 -> color = "#f65e3b";
            case 128 -> color = "#edcf72";
            case 256 -> color = "#edcc61";
            case 512 -> color = "#edc850";
            case 1024 -> color = "#edc53f";
            case 2048 -> color = "#edc22e";
            default -> color = "#cdc1b4";
        }
        return "-fx-background-color: " + color + "; -fx-alignment: center; -fx-border-color: black;";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
