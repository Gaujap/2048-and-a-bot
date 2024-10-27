import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

    public static void main(String[] args) {
        launch(args);
    }
}
