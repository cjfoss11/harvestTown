import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverScreen {
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 800;
    private static final BorderPane ROOT = new BorderPane();
    private static final Scene SCENE = new Scene(ROOT, SCREEN_WIDTH, SCREEN_HEIGHT);
    private static boolean restartGame = false;

    public static Scene getScene() { return SCENE; }
    public static boolean getRestartGame() {
        return restartGame;
    }
    public static void start(Stage stage) {
        VBox welcomeVBox = new VBox();
        welcomeVBox.setPadding(new Insets(10, 50, 50, 50));
        welcomeVBox.setAlignment(Pos.BASELINE_CENTER);
        Button newGame = new Button("Play Again");
        welcomeVBox.getChildren().add(newGame);
        Text welcomeText = new Text("Game Over!");
        welcomeText.setFont(Font.font("Helvetica", 40));

        //Config Welcome screen
        ROOT.setBottom(welcomeVBox);
        ROOT.setCenter(welcomeText);
        stage.setScene(SCENE);
        newGame.setOnAction(e -> {
            restart();
            restartGame = true;
            stage.setScene(WelcomeScreen.getScene());
            stage.show();
        });
    }

    public static void restart() {
        Inventory.getProducts().clear();
        Inventory.getCrops().clear();
        Inventory.getSeeds().clear();
        Plot.getPlots().clear();
        Inventory.setDay(1);
    }

}
