import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class WelcomeScreen {
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 800;
    private static final BorderPane ROOT = new BorderPane();
    private static final StackPane STACK = new StackPane();
    private static final Scene SCENE = new Scene(ROOT, SCREEN_WIDTH, SCREEN_HEIGHT);

    public static Scene getScene() { return SCENE; }

    public static void start(Stage stage) {
        //Adding the start button to Welcome screen
        VBox welcomeVBox = new VBox();
        welcomeVBox.setPadding(new Insets(10, 50, 50, 50));
        welcomeVBox.setAlignment(Pos.BASELINE_CENTER);
        Button startButton = new Button("Let's start harvesting!");
        startButton.setTextFill(Color.web("311E10"));

        startButton.setBackground(new Background(new BackgroundFill(Color.web("D9DD92"), CornerRadii.EMPTY, Insets.EMPTY)));
        startButton.setMaxSize(400, 600);
        startButton.setPadding((new Insets(30, 30, 30, 30)));
        startButton.setId("startButton");

        //Adding welcome text
        Text welcomeText = new Text("Welcome to Harvest Town!");
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setFont(Font.font("Cooper Black",60));
        welcomeText.setFill(Color.web("DD6031"));
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(30, 30, 30, 30));
        vBox.getChildren().add(welcomeText);
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(startButton);
        vBox.setAlignment(Pos.CENTER);
        STACK.setBackground(new Background(new BackgroundFill(Color.web("EABE7C"), CornerRadii.EMPTY, Insets.EMPTY)));
        STACK.getChildren().add(vBox);
        STACK.setMaxSize(1000, 500);

        //Config Welcome screen
        ROOT.setBottom(welcomeVBox);
        ROOT.setCenter(STACK);
        ROOT.setBackground(new Background(new BackgroundFill(Color.web("ECE4B7"), CornerRadii.EMPTY, Insets.EMPTY)));

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