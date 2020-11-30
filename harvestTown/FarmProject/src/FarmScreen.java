import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.*;

public class FarmScreen {
    private static Stage stages;
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 800;
    private static final BorderPane ROOT = new BorderPane();
    private static final Scene SCENE = new Scene(ROOT, SCREEN_WIDTH, SCREEN_HEIGHT);
    private static final GridPane GRID = new GridPane();
    private static double money = SetupScreen.getMoney();
    private static GridPane[] grids = new GridPane[16];
    private static HashMap<Integer, Plot> plots = Plot.getPlots();
    private static Label dayLabel;
    private static Label inventoryLabel;
    private static VBox vbox;
    private static int count = 0;
    private static boolean clicked = false;
    private static int[] waterLevels = new int[16];
    private static Text[] waterTexts = new Text[16];
    private static int[] maturityLevels = new int[16];
    private static Text[] maturityTexts = new Text[16];
    private static int[] fertilizerLevels = new int[16];
    private static Text[] fertilizerTexts = new Text[16];
    private static Text[][] cropTexts = new Text[4][4];
    private static Button[] buttons = new Button[16];
    private static String[][] seed = new String[4][4];
    private static int numHarvests = 0;
    private static int numWaters = 0;
    private static int countNumDead = 0;
    private static int lastRow = 2;
    private static int lastCol = 0;
    private static int price = 500;
    private static int numPlots = 8;

    public static Scene getScene() {
        return SCENE;
    }

    public static Label getInventoryLabel() {
        return inventoryLabel;
    }

    public static String getCropText(int col, int row) {
        return cropTexts[col][row].getText();
    }

    public static String getWaterText(int index) {
        return waterTexts[index].getText();
    }

    public static String getSeed(int col, int row) {
        if (seed[col][row] == null) {
            return " ";
        }
        return seed[col][row];
    }

    public static void start(Stage stage) {
        stages = stage;
        //inventory panel
        vbox = new VBox();
        vbox.setPadding(new Insets(10, 15, 10, 15));
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN,
                CornerRadii.EMPTY, Insets.EMPTY)));
        Label inventoryName = new Label("Inventory: \n");
        inventoryLabel = new Label(Inventory.getInventoryString());
        inventoryLabel.setId("inventoryLabel");
        vbox.getChildren().addAll(inventoryName, inventoryLabel);

        //makes the header box
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 12, 10, 12));
        int day = Inventory.getDay();
        dayLabel = new Label("\tDay: " + day);
        dayLabel.setId("dayLabel");
        Pane spacer = new Pane();
        spacer.setMinSize(550, 1);
        Label moneyLabel = SetupScreen.getMoneyLabel();
        Button nextDayButton = new Button("Next Day");
        nextDayButton.setId("nextDayButton");
        Pane buttonSpacer = new Pane();
        buttonSpacer.setMinSize(20, 1);
        Button expandPlotButton = new Button("Expand");
        expandPlotButton.setId("expandPlotButton");
        Pane buttonSpacers = new Pane();
        buttonSpacers.setMinSize(20, 1);
        Button marketButton = new Button("Market");
        marketButton.setId("marketButton");
        hbox.getChildren().addAll(moneyLabel, dayLabel, spacer, nextDayButton,
                buttonSpacer, marketButton, buttonSpacers, expandPlotButton);
        hbox.setBackground(new Background(new BackgroundFill(Color.CORAL,
                CornerRadii.EMPTY, Insets.EMPTY)));

        stage.setScene(FarmScreen.getScene());
        stage.show();
        expandPlotButton.setOnAction(PLOT_HANDLER);
        nextDayButton.setOnAction(NEXT_DAY_HANDLER);
        marketButton.setOnAction(e -> {
            stage.setScene(MarketScreen.getScene());
            if (!clicked) {
                MarketScreen.start(stage);
                clicked = true;
            }
            stage.show();
        });
        //makes the plots of farmland
        if (!GameOverScreen.getRestartGame()) {
            createFarm();
        } else {
            GRID.setGridLinesVisible(false);
            GRID.getColumnConstraints().clear();
            GRID.getRowConstraints().clear();
            GRID.getChildren().clear();
            GRID.setGridLinesVisible(true);
            createFarm();
        }

        GRID.setStyle("-fx-grid-lines-visible: true");
        ROOT.setTop(hbox);
        ROOT.setCenter(GRID);
        ROOT.setLeft(vbox);

    }

    private static void createFarm() {
        createFarmPlots();

        for (int i = 0; i < 4; i++) {
            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPercentWidth(25);
            GRID.getColumnConstraints().add(column1);
            RowConstraints row1 = new RowConstraints();
            row1.setPercentHeight(25);
            GRID.getRowConstraints().add(row1);
        }
    }

    private static void createFarmPlots() {
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 4; c++) {
                GridPane g = new GridPane();
                g.setPadding(new Insets(5, 5, 5, 5));
                g.setVgap(20);
                g.setAlignment(Pos.CENTER);
                cropTexts[c][r] = new Text(" ");
                cropTexts[c][r].setId("cropText");
                maturityTexts[4 * r + c] = new Text(" ");
                g.add(cropTexts[c][r], 0, 0);

                waterLevels[4 * r + c] = 3;
                waterTexts[4 * r + c] = new Text("Water Level: Fair");
                waterTexts[4 * r + c].setId("waterText");
                g.add(waterTexts[4 * r + c], 0, 1);
                fertilizerLevels[4 * r + c] = 0;
                fertilizerTexts[4 * r + c] = new Text("Fertilizer Level: 0");
                fertilizerTexts[0].setId("fertilizerText");
                g.add(fertilizerTexts[4 * r + c], 0, 2);
                g.add(new Text(" "), 0, 3);

                buttons[4 * r + c] = new Button(" Plant ");
                buttons[0].setId("plantButton");
                buttons[4 * r + c].setOnAction(PLANT_HANDLER);
                g.add(buttons[4 * r + c], 0, 4);
                grids[4 * r + c] = g;
                GRID.add(g, c, r);
                Plot p = new Plot(r, c, " ", 0, 3);
            }
        }
    }

    private static String selectSeed() {
        List<String> choices = new ArrayList<>();
        HashMap<String, InventoryItem> seeds = Inventory.getSeeds();
        seeds.forEach((k, v) -> {
            if (v.getQuantity() > 0) {
                choices.add(k);
            }
        });

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("SEED SELECTOR");
        dialog.setContentText("Choose your seed:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return " ";
    }

    public static void changePlotWaterLevel(int plot, int w) {
        waterLevels[plot] += w;
        plots.get(plot).setWater(waterLevels[plot]);
        if (!(waterTexts[plot].getText().equals("OVERWATERED")
                || (waterTexts[plot].getText().equals("DRIED UP")))) {
            if (waterLevels[plot] > 5) {
                waterTexts[plot].setText("OVERWATERED");
                if (!Plot.getPlots().get(plot).getCrop().equals(" ")) {
                    maturityTexts[plot].setText("\nDEAD");
                }
            } else if (waterLevels[plot] == 4) {
                waterTexts[plot].setText("Water Level: Excellent");
            } else if (waterLevels[plot] == 5) {
                waterTexts[plot].setText("Water Level: High");
            } else if (waterLevels[plot] == 2) {
                waterTexts[plot].setText("Water Level: Fair");
            } else if (waterLevels[plot] == 1) {
                waterTexts[plot].setText("Water Level: Low");
            } else if (waterLevels[plot] < 1) {
                waterTexts[plot].setText("DRIED UP");
                if (!Plot.getPlots().get(plot).getCrop().equals(" ")) {
                    maturityTexts[plot].setText("\nDEAD");
                }
                buttons[plot].setText("Water");
                buttons[plot].setOnAction(WATER_HANDLER);
            } else {
                waterTexts[plot].setText("Water Level: Good");
            }
        }
    }

    public static void changeAllWaterLevels(int i) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (plots.get(4 * r + c) != null) {
                    waterLevels[4 * r + c] += i;
                    plots.get(4 * r + c).setWater(waterLevels[4 * r + c]);
                    if (!(waterTexts[4 * r + c].getText().equals("OVERWATERED")
                            || (waterTexts[4 * r + c].getText().equals("DRIED UP")))) {
                        if (waterLevels[4 * r + c] > 5) {
                            waterTexts[4 * r + c].setText("OVERWATERED");
                            if (!Plot.getPlots().get(4 * r + c).getCrop().equals(" ")) {
                                maturityTexts[4 * r + c].setText("\nDEAD");
                            }
                        } else if (waterLevels[4 * r + c] == 4) {
                            waterTexts[4 * r + c].setText("Water Level: Excellent");
                        } else if (waterLevels[4 * r + c] == 5) {
                            waterTexts[4 * r + c].setText("Water Level: High");
                        } else if (waterLevels[4 * r + c] == 2) {
                            waterTexts[4 * r + c].setText("Water Level: Fair");
                        } else if (waterLevels[4 * r + c] == 1) {
                            waterTexts[4 * r + c].setText("Water Level: Low");
                        } else if (waterLevels[4 * r + c] < 1) {
                            waterTexts[4 * r + c].setText("DRIED UP");
                            if (!Plot.getPlots().get(4 * r + c).getCrop().equals(" ")) {
                                maturityTexts[4 * r + c].setText("\nDEAD");
                            }
                            buttons[4 * r + c].setText("Water");
                            buttons[4 * r + c].setOnAction(WATER_HANDLER);
                        } else {
                            waterTexts[4 * r + c].setText("Water Level: Good");
                        }
                    }
                }
            }
        }
    }

    public static void changeAllFertilizerLevels(int i) {
        for (int j = 0; j < 16; j++) {
            if (Plot.getPlots().get(j) != null) {
                int f = Plot.getPlots().get(j).getFertilizer() + i;
                if (f >= 0) {
                    Plot.getPlots().get(j).setFertilizer(f);
                    fertilizerLevels[j] += i;
                    fertilizerTexts[j].setText("Fertilizer Level: " + fertilizerLevels[j]);
                }
            }
        }
    }

    private static final EventHandler<ActionEvent> PESTICIDE_HANDLER = event -> {
        Button clicked = (Button) event.getSource();
        int c = GridPane.getColumnIndex(clicked.getParent().getParent());
        int r = GridPane.getRowIndex(clicked.getParent().getParent());
        Plot p = Plot.getPlots().get(4 * r + c);
        if (Inventory.getProducts().containsKey("Pesticide")
                && Inventory.getProducts().get("Pesticide").getQuantity() >= 1) {
            int q = Inventory.getProducts().get("Pesticide").getQuantity();
            p.setPesticide(true);
            Inventory.getProducts().get("Pesticide").setQuantity(q - 1);
            HBox fpHBox = (HBox) clicked.getParent();
            fpHBox.getChildren().remove(clicked);
            vbox.getChildren().clear();
            Label inventoryName = new Label("Inventory: \n");
            inventoryLabel = new Label(Inventory.getInventoryString());
            inventoryLabel.setId("inventoryLabel");
            vbox.getChildren().addAll(inventoryName, inventoryLabel);
        } else {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("ALERT");
            dialog.setContentText("You do not have enough pesticide in your inventory.");
            Window window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
            dialog.show();
        }
    };

    private static final EventHandler<ActionEvent> FERTILIZER_HANDLER = event -> {
        Button clicked = (Button) event.getSource();
        int c = GridPane.getColumnIndex(clicked.getParent().getParent());
        int r = GridPane.getRowIndex(clicked.getParent().getParent());
        Plot p = Plot.getPlots().get(4 * r + c);
        int curr = p.getFertilizer();
        if (Inventory.getProducts().containsKey("Fertilizer")
                && Inventory.getProducts().get("Fertilizer").getQuantity() >= 1) {
            int q = Inventory.getProducts().get("Fertilizer").getQuantity();
            if (curr < 10) {
                p.setFertilizer(curr + 1);
                Inventory.getProducts().get("Fertilizer").setQuantity(q - 1);
                fertilizerLevels[4 * r + c] += 1;
                fertilizerTexts[4 * r + c].setText("Fertilizer Level: "
                        + fertilizerLevels[4 * r + c]);
                if (fertilizerLevels[4 * r + c] == 10) {
                    fertilizerTexts[4 * r + c].setText("Fertilizer Level: "
                            + fertilizerLevels[4 * r + c] + " (max)");
                }
                vbox.getChildren().clear();
                Label inventoryName = new Label("Inventory: \n");
                inventoryLabel = new Label(Inventory.getInventoryString());
                inventoryLabel.setId("inventoryLabel");
                vbox.getChildren().addAll(inventoryName, inventoryLabel);
            }
        } else {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("ALERT");
            dialog.setContentText("You do not have enough fertilizer in your inventory.");
            Window window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
            dialog.show();
        }
    };

    private static final EventHandler<ActionEvent> PLANT_HANDLER = event -> {
        Button clicked = (Button) event.getSource();
        int c = GridPane.getColumnIndex(clicked.getParent());
        int r = GridPane.getRowIndex(clicked.getParent());
        String crop = selectSeed();
        if (!crop.equals(" ")) {
            Plot p = Plot.getPlots().get(4 * r + c);
            p.setCrop(crop);
            p.setMaturity(1);
            p.setPesticide(false);
            p.setFertilizer(0);
            GridPane g = grids[4 * r + c];
            g.getChildren().remove(clicked);
            seed[c][r] = crop;
            maturityTexts[4 * r + c].setText(" Seed");
            maturityLevels[4 * r + c] = 1;
            g.getChildren().remove(cropTexts[c][r]);
            cropTexts[c][r].setText(seed[c][r] + maturityTexts[4 * r + c].getText());
            g.add(cropTexts[c][r], 0, 0);

            g.getChildren().remove(waterTexts[4 * r + c]);
            waterTexts[4 * r + c].setText("Water Level: Fair");
            waterLevels[4 * r + c] = p.getWater();
            g.add(waterTexts[4 * r + c], 0, 1);

            g.getChildren().remove(fertilizerTexts[4 * r + c]);
            fertilizerLevels[4 * r + c] = 0;
            fertilizerTexts[4 * r + c].setText("Fertilizer Level: 0");
            g.add(fertilizerTexts[4 * r + c], 0, 2);

            InventoryItem item = Inventory.getSeeds().get(seed[c][r]);
            item.setQuantity(item.getQuantity() - 1);
            vbox.getChildren().clear();
            Label inventoryName = new Label("Inventory: \n");
            inventoryLabel = new Label(Inventory.getInventoryString());
            inventoryLabel.setId("inventoryLabel");
            vbox.getChildren().addAll(inventoryName, inventoryLabel);

            HBox fpHBox = new HBox();
            if (!Plot.getPlots().get(4 * r + c).getPesticide()) {
                buttons[4 * r + c] = new Button("Spray Pesticide");
                buttons[4 * r + c].setId("pesticideButton");
                buttons[4 * r + c].setOnAction(PESTICIDE_HANDLER);
                fpHBox.getChildren().add(buttons[4 * r + c]);
            }
            if (Plot.getPlots().get(4 * r + c).getFertilizer() < 10) {
                buttons[4 * r + c] = new Button("Fertilize");
                buttons[0].setId("fertilizerButton");
                buttons[4 * r + c].setOnAction(FERTILIZER_HANDLER);
                fpHBox.getChildren().add(buttons[4 * r + c]);
            }
            if (!grids[4 * r + c].getChildren().contains(fpHBox)) {
                grids[4 * r + c].add(fpHBox, 0, 3);
            }
        }
    };

    private static final EventHandler<ActionEvent> PLOT_HANDLER = event -> {
        Button clicked = (Button) event.getSource();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Buying Plots?");
        alert.setContentText("Price: " + price + "\nWould you like to buy?");

        Optional<ButtonType> result = alert.showAndWait();
        int c = lastCol;
        int r = lastRow;
        if (result.get() == ButtonType.OK) {
            if (money < price) {
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("Information Dialog");
                alerte.setHeaderText(null);
                alerte.setContentText("Not enough money :(");
                alerte.showAndWait();
                return;
            }
            if (lastCol == 3 && lastRow == 3) {
                Alert done = new Alert(Alert.AlertType.INFORMATION);
                done.setTitle("Information Dialog");
                done.setHeaderText(null);
                done.setContentText("No more plots left to buy.");
                done.showAndWait();
                return;
            }
            GridPane g = new GridPane();
            g.setPadding(new Insets(5, 5, 5, 5));
            g.setVgap(20);
            g.setAlignment(Pos.CENTER);
            cropTexts[c][r] = new Text(" ");
            cropTexts[c][r].setId("cropText");
            maturityTexts[4 * r + c] = new Text(" ");
            g.add(cropTexts[c][r], 0, 0);

            waterLevels[4 * r + c] = 3;
            waterTexts[4 * r + c] = new Text("Water Level: Fair");
            waterTexts[4 * r + c].setId("waterText");
            g.add(waterTexts[4 * r + c], 0, 1);
            fertilizerLevels[4 * r + c] = 0;
            fertilizerTexts[4 * r + c] = new Text("Fertilizer Level: 0");
            fertilizerTexts[0].setId("fertilizerText");
            g.add(fertilizerTexts[4 * r + c], 0, 2);
            g.add(new Text(" "), 0, 3);

            buttons[4 * r + c] = new Button(" Plant ");
            buttons[0].setId("plantButton");
            buttons[4 * r + c].setOnAction(PLANT_HANDLER);
            g.add(buttons[4 * r + c], 0, 4);
            grids[4 * r + c] = g;
            GRID.add(g, lastCol, lastRow);
            Plot p = new Plot(r, c, " ", 0, 3);
            count++;
            if (lastCol == 3) {
                lastCol = 0;
                lastRow++;
            } else {
                lastCol++;
            }
            if (count > 0) {
                numPlots++;
                money -= price;
                price += 500;
            }
            SetupScreen.getMoneyLabel().setText("Money: " + money + " coins");
        }
    };

    private static final EventHandler<ActionEvent> WATER_HANDLER = event -> {
        if ((!Inventory.getProducts().containsKey("Irrigation") && numWaters < 10)
                || (Inventory.getProducts().containsKey("Irrigation") && numWaters < 20)) {
            Button clicked = (Button) event.getSource();
            int c = GridPane.getColumnIndex(clicked.getParent());
            int r = GridPane.getRowIndex(clicked.getParent());
            waterLevels[4 * r + c]++;
            plots.get(4 * r + c).setWater(waterLevels[4 * r + c]);
            if (!(waterTexts[4 * r + c].getText().equals("OVERWATERED"))
                    || (waterTexts[4 * r + c].getText().equals("DRIED UP"))) {
                if (waterLevels[4 * r + c] > 5) {
                    waterTexts[4 * r + c].setText("OVERWATERED");
                    if (!Plot.getPlots().get(4 * r + c).getCrop().equals(" ")) {
                        maturityTexts[4 * r + c].setText("\nDEAD");
                    }
                } else if (waterLevels[4 * r + c] == 4) {
                    waterTexts[4 * r + c].setText("Water Level: Excellent");
                } else if (waterLevels[4 * r + c] == 5) {
                    waterTexts[4 * r + c].setText("Water Level: High");
                } else if (waterLevels[4 * r + c] == 2) {
                    waterTexts[4 * r + c].setText("Water Level: Fair");
                } else if (waterLevels[4 * r + c] == 1) {
                    waterTexts[4 * r + c].setText("Water Level: Low");
                } else if (waterLevels[4 * r + c] < 1) {
                    waterTexts[4 * r + c].setText("DRIED UP");
                    if (!Plot.getPlots().get(4 * r + c).getCrop().equals(" ")) {
                        maturityTexts[4 * r + c].setText("\nDEAD");
                    }
                } else {
                    waterTexts[4 * r + c].setText("Water Level: Good");
                }
            }
            numWaters++;
        } else {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("ALERT");
            if (!Inventory.getProducts().containsKey("Irrigation")) {
                dialog.setContentText("You have reached the watering maximum for the day."
                        + " Please purchase Irrigation from the market to water more plots.");
            } else {
                dialog.setContentText("You have reached the watering maximum for the day.");
            }
            Window window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
            dialog.show();
        }
    };

    private static final EventHandler<ActionEvent> HARVEST_HANDLER = event -> {
        if ((!Inventory.getProducts().containsKey("Tractor") && numHarvests < 4)
                || (Inventory.getProducts().containsKey("Tractor") && numHarvests < 8)) {
            Button clicked = (Button) event.getSource();
            int c = GridPane.getColumnIndex(clicked.getParent());
            int r = GridPane.getRowIndex(clicked.getParent());
            plots = Plot.getPlots();
            String crop = plots.get(4 * r + c).getCrop();
            boolean pesticide = plots.get(4 * r + c).getPesticide();
            if (pesticide) {
                crop += " (p)";
            }
            plots.get(4 * r + c).setCrop(" ");
            plots.get(4 * r + c).setMaturity(0);
            int bonus = 1 + plots.get(4 * r + c).getFertilizer();
            HashMap<String, InventoryItem> crops = Inventory.getCrops();
            if (crops.containsKey(crop)) {
                InventoryItem cropInv = crops.get(crop);
                int numCrops = cropInv.getQuantity() + new Random().nextInt(5) + bonus;
                cropInv.setQuantity(numCrops);
                crops.replace(crop, cropInv);
            } else {
                crops.put(crop, new InventoryItem(crop, "crop",
                        new Random().nextInt(5) + bonus, 100, pesticide));
            }
            inventoryLabel.setText(Inventory.getInventoryString());
            vbox.getChildren().set(1, inventoryLabel);
            grids[4 * r + c].getChildren().clear();
            waterTexts[4 * r + c].setText(" ");
            maturityTexts[4 * r + c].setText(" ");
            buttons[4 * r + c].setText("Plant");
            buttons[4 * r + c].setOnAction(PLANT_HANDLER);
            grids[4 * r + c].getChildren().addAll(buttons[4 * r + c], waterTexts[4 * r + c]);
            numHarvests++;
        } else {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("ALERT");
            if (!Inventory.getProducts().containsKey("Tractor")) {
                dialog.setContentText("You have reached the harvesting maximum for the day."
                        + " Please purchase a Tractor from the market to harvest more crops.");
            } else {
                dialog.setContentText("You have reached the harvesting maximum for the day.");
            }
            Window window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
            dialog.show();
        }
    };

    private static final EventHandler<ActionEvent> NEXT_DAY_HANDLER = event -> {
        Inventory.incDay();
        int nextDay = Inventory.getDay();
        dayLabel.setText("\tDay: " + nextDay);
        changeAllWaterLevels(-1);
        changeAllFertilizerLevels(-1);
        numWaters = 0;
        numHarvests = 0;
        for (int i = 0; i < maturityTexts.length; i++) {
            if (maturityTexts[i] != null) {
                String maturity = maturityTexts[i].getText();
                if (!(waterLevels[i] > 5 || waterLevels[i] < 1)) {
                    if (maturity.contains("Seed") && (waterLevels[i] >= 3)) {
                        if (Plot.getPlots().get(i).getFertilizer() > 0) {
                            maturityTexts[i].setText(" Immature Plant");
                            maturityLevels[i] = 3;
                        } else {
                            maturityTexts[i].setText(" Sprout");
                            maturityLevels[i] = 2;
                        }
                    } else if (maturity.contains("Sprout") && (waterLevels[i] >= 3)) {
                        if (Plot.getPlots().get(i).getFertilizer() > 0) {
                            maturityTexts[i].setText(" Mature Plant");
                            maturityLevels[i] = 4;
                        } else {
                            maturityTexts[i].setText(" Immature Plant");
                            maturityLevels[i] = 3;
                        }
                    } else if (maturity.contains("Immature Plant") && (waterLevels[i] >= 3)) {
                        maturityTexts[i].setText(" Mature Plant");
                        maturityLevels[i] = 4;
                    }
                } else {
                    if (!Plot.getPlots().get(i).getCrop().equals(" ")) {
                        maturityTexts[i].setText("\nDEAD");
                        maturityLevels[i] = 5;
                    }
                }
                plots.get(i).setMaturity(maturityLevels[i]);
            }
        }
        RandomEvent randomEvent = new RandomEvent();
        double gameDiff = SetupScreen.getGameDifficulty();
        int[] arr;
        if (gameDiff == 2.5) {
            arr = randomEvent.randomEvent(9);
            //easy level chances of event: 1/3
        } else if (gameDiff == 1) {
            //increases chances that a random disaster event happens (3/7)
            arr = randomEvent.randomEvent(7);
        } else {
            //increases chances that a random disaster event happens(3/5)
            arr = randomEvent.randomEvent(5);
        }
        int i = 0;
        if (randomEvent.getRandomNum() == 0) {
            //arr means rain
            for (Plot plot : plots.values()) {
                int temp = waterLevels[i];
                plot.setWater(plot.getWater() + arr[i]);
                changePlotWaterLevel(plot.getPlotNum(), arr[i]);
                if (waterLevels[i] - temp != 0) {
                    waterTexts[i].setText(waterTexts[i].getText()
                            + "\nIncreased by: " + (waterLevels[i] - temp));
                }
                i++;
            }
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("ALERT");
            dialog.setContentText("Oh no! There was a RAINSTORM! Water levels may be affected.");
            dialog.show();
            Window window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
        } else if (randomEvent.getRandomNum() == 1) {
            //arr means drought
            for (Plot plot : plots.values()) {
                int temp = waterLevels[i];
                plot.setWater(plot.getWater() - arr[i]);
                changePlotWaterLevel(plot.getPlotNum(), -arr[i]);
                if (waterLevels[i] - temp != 0) {
                    waterTexts[i].setText(waterTexts[i].getText()
                            + "\nDecreased By: " + (-1 * (waterLevels[i] - temp)));
                }
                i++;
            }
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("ALERT");
            dialog.setContentText("Oh no! There was a DROUGHT! Water levels may be affected.");
            Window window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
            dialog.show();
        } else if (randomEvent.getRandomNum() == 2) {
            //arr means locust and dead plots
            for (Plot plot : plots.values()) {
                if (arr[i] == 0) {
                    if (plot.getPesticide()) {
                        maturityTexts[i].setText(maturityTexts[i].getText()
                                + "\nProtected by pesticide");
                    } else {
                        if (!Plot.getPlots().get(i).getCrop().equals(" ")) {
                            maturityLevels[i] = 5;
                            maturityTexts[i].setText("\nDEAD");
                            plot.setMaturity(5);
                        }
                    }
                }
                i++;
            }
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("ALERT");
            dialog.setContentText("Oh no! There are LOCUSTS! Some plots may now be dead.");
            Window window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
            dialog.show();
        }
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (maturityTexts[4 * r + c] != null) {
                    if ((maturityTexts[4 * r + c].getText().contains("Mature Plant"))
                            && (!maturityTexts[4 * r + c].getText().contains("DEAD"))) {
                        buttons[4 * r + c] = new Button("Harvest");
                        buttons[4 * r + c].setId("harvestButton");
                        buttons[4 * r + c].setOnAction(HARVEST_HANDLER);
                        grids[4 * r + c].add(buttons[4 * r + c], 0, 4);
                    } else if (maturityTexts[4 * r + c].getText().contains("Seed")
                            || maturityTexts[4 * r + c].getText().contains("Sprout")
                            || maturityTexts[4 * r + c].getText().contains("Immature Plant")) {
                        Button waterButton = new Button("Water");
                        buttons[4 * r + c] = waterButton;
                        buttons[0].setId("waterButton");
                        buttons[4 * r + c].setOnAction(WATER_HANDLER);
                        grids[4 * r + c].add(buttons[4 * r + c], 0, 4);

                        HBox fpHBox = new HBox();
                        if (!Plot.getPlots().get(4 * r + c).getPesticide()) {
                            buttons[4 * r + c] = new Button("Spray Pesticide");
                            buttons[0].setId("pesticideButton");
                            buttons[4 * r + c].setOnAction(PESTICIDE_HANDLER);
                            fpHBox.getChildren().add(buttons[4 * r + c]);
                        }
                        if (Plot.getPlots().get(4 * r + c).getFertilizer() < 10) {
                            buttons[4 * r + c] = new Button("Fertilize");
                            buttons[4 * r + c].setId("fertilizerButton");
                            buttons[4 * r + c].setOnAction(FERTILIZER_HANDLER);
                            fpHBox.getChildren().add(buttons[4 * r + c]);
                        }
                        if (!grids[4 * r + c].getChildren().contains(fpHBox)) {
                            grids[4 * r + c].add(fpHBox, 0, 3);
                        }
                    } else {
                        if ((Plot.getPlots().get(4 * r + c).getCrop().equals(" ")
                                || maturityLevels[4 * r + c] == 5)
                                && waterLevels[4 * r + c] >= 1) {
                            buttons[4 * r + c] = new Button(" Plant ");
                            buttons[4 * r + c].setOnAction(PLANT_HANDLER);
                            grids[4 * r + c].add(buttons[4 * r + c], 0, 4);
                        } else {
                            buttons[4 * r + c] = new Button("Water");
                            buttons[4 * r + c].setOnAction(WATER_HANDLER);
                            grids[4 * r + c].add(buttons[4 * r + c], 0, 4);
                        }
                    }
                }
            }
        }
        countNumDead = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if ((cropTexts[c][r] != null) && (maturityTexts[4 * r + c] != null)) {
                    cropTexts[c][r].setText(getSeed(c, r) + maturityTexts[4 * r + c].getText());
                    if (maturityTexts[4 * r + c].getText().contains("DEAD")
                            || waterTexts[4 * r + c].getText().contains("DRIED UP")
                            || waterTexts[4 * r + c].getText().contains("OVERWATERED")) {
                        countNumDead++;
                    }
                }
            }
        }
        if (countNumDead == 11 && money == 0.0) {
            stages.setScene(GameOverScreen.getScene());
            GameOverScreen.start(stages);
            stages.show();
        }
    };
}