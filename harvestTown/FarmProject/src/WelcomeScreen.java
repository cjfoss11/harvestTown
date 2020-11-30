import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeScreen {
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 800;
    private static final BorderPane ROOT = new BorderPane();
    private static final Scene SCENE = new Scene(ROOT, SCREEN_WIDTH, SCREEN_HEIGHT);

    public static Scene getScene() { return SCENE; }

    public static void start(Stage stage) {
        //Adding the start button to Welcome screen
        VBox welcomeVBox = new VBox();
        welcomeVBox.setPadding(new Insets(10, 50, 50, 50));
        welcomeVBox.setAlignment(Pos.BASELINE_CENTER);
        Button startButton = new Button("Let's start harvesting!");
        startButton.setTextFill(Color.web("9EB25D"));

        startButton.setBackground(new Background(new BackgroundFill(Color.web("F1DB4B"), CornerRadii.EMPTY, Insets.EMPTY)));
        startButton.setMaxSize(300, 600);
        startButton.setId("startButton");
        welcomeVBox.getChildren().add(startButton);

        //Adding welcome text
        Text welcomeText = new Text("Welcome to Harvest Town!");
        welcomeText.setFont(Font.font("Georgia",60));
        welcomeText.setFill(Color.web("9EB25D"));

        //Config Welcome screen
        ROOT.setBottom(welcomeVBox);
        ROOT.setCenter(welcomeText);
        ROOT.setBackground(new Background(new BackgroundFill(Color.rgb(238, 252,206), CornerRadii.EMPTY, Insets.EMPTY)));

        stage.setScene(SCENE);

        //Setting action to go to setup screen
        startButton.setOnAction(e -> {
            stage.setScene(SetupScreen.getScene());
            SetupScreen.start(stage);
        });

        //Show Welcome screen
        stage.setTitle("Harvest Town");
        stage.show();
    }
}