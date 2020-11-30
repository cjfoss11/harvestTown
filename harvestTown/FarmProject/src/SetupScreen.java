import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class SetupScreen {
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 800;
    private static final GridPane ROOT = new GridPane();
    private static final Scene SCENE = new Scene(ROOT, SCREEN_WIDTH, SCREEN_HEIGHT);

    private static double money;
    private static double gameDifficulty;
    private static Label moneyLabel;

    public static Scene getScene() {
        return SCENE;
    }

    public static Label getMoneyLabel() {
        return moneyLabel;
    }

    public static double getMoney() {
        return money;
    }

    public static double getGameDifficulty() {
        return gameDifficulty;
    }

    public static void start(Stage stage) {
        //Initialize root
        ROOT.setPadding(new Insets(25, 25, 25, 25));
        ROOT.setHgap(10);
        ROOT.setVgap(10);
        ROOT.setAlignment(Pos.CENTER);

        //Config Screen Setup
        Text configTitle = new Text("Let's get started!");
        configTitle.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        ROOT.add(configTitle, 0, 0, 2, 1);

        //Name Setup
        Label name = new Label("Name:");
        name.setId("name");
        ROOT.add(name, 0, 1);
        TextField nameText = new TextField();
        nameText.setId("nameText");
        ROOT.add(nameText, 1, 1);

        //Game Difficulty Setup
        Label gameDifficulty = new Label("Game Difficulty:");
        gameDifficulty.setId("gameDifficulty");
        ROOT.add(gameDifficulty, 0, 2);
        ObservableList<String> gameDifficultyOptions = FXCollections.observableArrayList(
                "Easy", "Normal", "Hard");
        ComboBox<ObservableList<String>> gameDiffBox = new ComboBox(gameDifficultyOptions);
        gameDiffBox.setId("gameDiffBox");
        ROOT.add(gameDiffBox, 1, 2);

        //Setting up changes to money for difficulty
        moneyLabel = new Label("Money: ");
        moneyLabel.setId("money");
        gameDiffBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue,
                                                                             newValue) -> {
            if (gameDifficultyOptions.get(newValue.intValue()).equals("Easy")) {
                money = 10000;
                moneyLabel.setText("Money: " + money + " coins");
                SetupScreen.gameDifficulty = 2.5;
            } else if (gameDifficultyOptions.get(newValue.intValue()).equals("Normal")) {
                money = 5000;
                SetupScreen.gameDifficulty = 1.0;
                moneyLabel.setText("Money: " + money + " coins");
            } else {
                money = 3000;
                SetupScreen.gameDifficulty = 0.5;
                moneyLabel.setText("Money: " + money + " coins");
            }
        });

        //Starting Seed Setup
        Label startingSeed = new Label("Starting Seed:");
        startingSeed.setId("startingSeed");
        ROOT.add(startingSeed, 0, 3);
        final ObservableList<String> startingSeedTypeOptions =
                FXCollections.observableArrayList("Wheat", "Corn", "Strawberry");
        ComboBox<ObservableList<String>> seedTypeBox = new ComboBox(startingSeedTypeOptions);
        seedTypeBox.setId("seedType");
        ROOT.add(seedTypeBox, 1, 3);

        HashMap<String, InventoryItem> seeds = Inventory.getSeeds();
        seedTypeBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (startingSeedTypeOptions.get(newValue.intValue()).equals("Wheat")) {
                        seeds.put("Wheat", new InventoryItem("Wheat", "seed", 16, 100));
                    } else if (startingSeedTypeOptions.get(newValue.intValue()).equals("Corn")) {
                        seeds.put("Corn", new InventoryItem("Corn", "seed", 16, 100));
                    } else {
                        seeds.put("Strawberry", new InventoryItem("Strawberry", "seed", 16, 100));
                    }
                });

        //Starting season Setup
        Label startingSeason = new Label("Starting Season:");
        startingSeason.setId("startingSeason");
        ROOT.add(startingSeason, 0, 4);
        ObservableList<String> startingSeasonOptions = FXCollections.observableArrayList(
                "Spring", "Summer", "Fall", "Winter");
        ComboBox<ObservableList<String>> seasonOptionBox = new ComboBox(startingSeasonOptions);
        seasonOptionBox.setId("seasonOptions");
        ROOT.add(seasonOptionBox, 1, 4);

        //Next Button Setup
        Button nextButton = new Button("Create your farm!");
        nextButton.setId("nextButton");
        nextButton.setMaxSize(400, 400);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(nextButton);
        ROOT.add(hbBtn, 1, 5);
        final Text invalidName = new Text();
        invalidName.setId("invalidName");
        ROOT.add(invalidName, 1, 6);

        //Setting the next button to go to the farm scene
        nextButton.setOnAction(e -> {
            if (nameText.getText().isEmpty()) {
                invalidName.setFill(Color.FIREBRICK);
                invalidName.setText("Must set a name to proceed");
            } else {
                stage.setScene(FarmScreen.getScene());
                FarmScreen.start(stage);
                stage.show();
            }
        });
    }
}