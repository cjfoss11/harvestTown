import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Optional;

public class MarketScreen {
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 800;
    private static final BorderPane ROOT = new BorderPane();
    private static final Scene SCENE = new Scene(ROOT, SCREEN_WIDTH, SCREEN_HEIGHT);
    private static final VBox RECEIPT_VBOX = new VBox();
    private static final VBox SELL_RECEIPT_VBOX = new VBox();
    private static final VBox HIRE_RECEIPT_VBOX = new VBox();
    private static final int INVENTORY_SPACE = 100;

    private static double money = SetupScreen.getMoney();
    private static double totalPrice = 0;
    private static Text totalMoneyText;
    private static Text totalSellMoneyText;
    private static Text totalPriceText = new Text();
    private static Text totalSellPriceText = new Text();
    private static ListView<String> receipt = new ListView<>();
    private static TableView seedTableView = new TableView<>();
    private static TableView prodTableView = new TableView<>();
    private static TableView cropTableView = new TableView<>();
    private static TableView workersTableView = new TableView<>();
    private static ObservableList<String> receiptList;
    private static ObservableList<String> sellReceiptList;
    private static ObservableList<String> hireReceiptList;
    private static ListView<String> sellReceipt;
    private static ListView<String> hireReceipt;
    private static ListView<MarketItem> seedMarketList;
    private static ListView<MarketItem> objectMarketList;
    private static ListView<SellItem> sellList;
    private static ListView<FarmWorker> workersList;
    private static VBox sellListings = new VBox();
    private static VBox workerListings = new VBox();

    public static Scene getScene() {
        return SCENE;
    }

    public static double getMoney() {return money;}

    public static void start(Stage stage) {
        //Top header
        HBox header = new HBox();
        header.setPadding(new Insets(10, 12, 10, 12));
        Label headerName = new Label("Welcome to the Marketplace!");
        headerName.setFont(new Font("Helvetica", 40));
        final Pane spacer = new Pane();
        spacer.setMinSize(350, 1);
        Button backToFarm = new Button("Back to Farm");
        backToFarm.setId("backToFarm");
        header.getChildren().addAll(headerName, spacer, backToFarm);

        //Back to Farm Button
        backToFarm.setOnAction(e -> {
            stage.setScene(FarmScreen.getScene());
            stage.show();
        });

        //Center SplitPane
        TabPane market = new TabPane();
        Tab buyTab = new Tab("Buy");
        Tab sellTab = new Tab("Sell");
        Tab farmWorkersTab = new Tab("Farm Workers");
        SplitPane buyMarketPlace = new SplitPane();
        AnchorPane leftBuyPane = new AnchorPane();
        AnchorPane rightBuyPane = new AnchorPane();
        createSplitPane(buyMarketPlace, leftBuyPane, rightBuyPane);
        SplitPane sellMarketPlace = new SplitPane();
        AnchorPane leftSellPane = new AnchorPane();
        AnchorPane rightSellPane = new AnchorPane();
        createSplitPane(sellMarketPlace, leftSellPane, rightSellPane);
        buyTab.setContent(buyMarketPlace);
        sellTab.setContent(sellMarketPlace);
        SplitPane workerMarketPlace = new SplitPane();
        AnchorPane leftWorkerPane = new AnchorPane();
        AnchorPane rightWorkerPane = new AnchorPane();
        createSplitPane(workerMarketPlace, leftWorkerPane, rightWorkerPane);
        farmWorkersTab.setContent(workerMarketPlace);
        market.getTabs().addAll(buyTab, sellTab, farmWorkersTab);

        //MarketListings
        VBox marketListings = new VBox();
        createMarketListingsVBox(marketListings, leftBuyPane);

        //Sell Listings
        sellListings.setPrefSize(600, 400);
        leftSellPane.getChildren().add(sellListings);
        sellList = createSellListingsVBox();
        sellList.setPrefSize(500, 450);
        sellListings.getChildren().add(sellList);

        //Worker Listings
        workerListings.setPrefSize(600, 400);
        leftWorkerPane.getChildren().add(workerListings);
        workersList = createWorkerListingsVBox();
        workersList.setPrefSize(500, 450);
        workerListings.getChildren().add(workersList);

        //Hire Workers
        HIRE_RECEIPT_VBOX.setPrefSize(500, 500);
        rightWorkerPane.getChildren().add(HIRE_RECEIPT_VBOX);
        hireReceiptList = FXCollections.observableArrayList();
        hireReceipt = new ListView<>(hireReceiptList);
        addToHireReceipt(workersList, hireReceiptList);
        Button hireButton = new Button("Hire");
        HIRE_RECEIPT_VBOX.getChildren().addAll(hireReceipt, hireButton);

        //Seed Quantity Dialog Box
        RECEIPT_VBOX.setPrefSize(500, 500);
        rightBuyPane.getChildren().add(RECEIPT_VBOX);
        receiptList = FXCollections.observableArrayList();
        ListView<String> receipt = new ListView<>(receiptList);
        addToReceipt(seedMarketList, receiptList);
        addToReceipt(objectMarketList, receiptList);

        totalPriceText.setText("Total: " + totalPrice);
        totalMoneyText = new Text(SetupScreen.getMoneyLabel().getText());
        totalPriceText.setTextAlignment(TextAlignment.RIGHT);
        totalMoneyText.setTextAlignment(TextAlignment.RIGHT);
        RECEIPT_VBOX.getChildren().addAll(receipt, totalPriceText, totalMoneyText);

        //Sell Quantity Dialog Box
        SELL_RECEIPT_VBOX.setPrefSize(500, 500);
        rightSellPane.getChildren().add(SELL_RECEIPT_VBOX);
        sellReceiptList = FXCollections.observableArrayList();
        sellReceipt = new ListView<>(sellReceiptList);
        addToSellReceipt(sellList, sellReceiptList);

        totalSellPriceText = new Text("Total: " + totalPrice);
        totalSellMoneyText = new Text(SetupScreen.getMoneyLabel().getText());
        totalSellPriceText.setTextAlignment(TextAlignment.RIGHT);
        totalSellMoneyText.setTextAlignment(TextAlignment.RIGHT);
        SELL_RECEIPT_VBOX.getChildren().addAll(sellReceipt, totalSellPriceText, totalSellMoneyText);

        //Bottom GridPane
        GridPane inventoryPane = new GridPane();
        inventoryPane.setPrefSize(560, 180);
        inventoryPane.setAlignment(Pos.TOP_CENTER);
        inventoryPane.setPadding(new Insets(0, 20, 0, 20));
        inventoryPane.setHgap(20);
        Label inventoryName = new Label("Inventory:");
        createTableView(seedTableView);
        seedTableView.setItems(FXCollections.observableArrayList(Inventory.getSeeds().values()));
        createTableView(cropTableView);
        cropTableView.setItems(FXCollections.observableArrayList(Inventory.getCrops().values()));
        createTableView(prodTableView);
        prodTableView.setItems(FXCollections.observableArrayList(Inventory.getProducts().values()));

        inventoryPane.add(inventoryName, 0, 0);
        inventoryPane.add(seedTableView, 0, 1);
        inventoryPane.add(cropTableView, 1, 1);
        inventoryPane.add(prodTableView, 2, 1);

        //Buy Button
        Button buyButton = new Button("Purchase");
        buyButton.setId("buyButton");
        buyButton.setOnAction(event -> handleBuy(event));
        RECEIPT_VBOX.getChildren().add(buyButton);

        //Sell Button
        Button sellButton = new Button("Sell");
        sellButton.setOnAction(event -> handleSell(event));
        SELL_RECEIPT_VBOX.getChildren().add(sellButton);

        market.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, tab, t1) -> {
                if (tab.getText().equals("Buy")) {
                    sellReceiptList.clear();
                    sellList = createSellListingsVBox();
                    sellListings.getChildren().set(0, sellList);
                    addToSellReceipt(sellList, sellReceiptList);
                } else {
                    receiptList.clear();
                }
            }
        );

        ROOT.setTop(header);
        ROOT.setCenter(market);
        ROOT.setBottom(inventoryPane);
    }

    private static void addToReceipt(ListView<MarketItem> objectMarketList,
                                     ObservableList<String> receiptList) {
        objectMarketList.setOnMouseClicked(event -> {
            TextInputDialog objectQuantity = new TextInputDialog("1");
            MarketItem selectedItem = objectMarketList.getSelectionModel().
                    getSelectedItem();
            String itemName = selectedItem.getItemName();
            objectQuantity.setHeaderText("How many " + itemName + " do you want?");
            objectQuantity.setContentText("Please enter the number of " + itemName + " you want: ");
            Optional<String> result = objectQuantity.showAndWait();
            if (result.isPresent()) {
                int numOfItems = Integer.parseInt(result.get());
                if (numOfItems + Inventory.getNumOfInventory() <= INVENTORY_SPACE) {
                    double itemPrice = selectedItem.getPrice();
                    if (selectedItem.getItemType().equals("Seed")) {
                        receiptList.add(itemName + " Seeds x" + numOfItems
                                + "...................." + (numOfItems * itemPrice));
                        HashMap<String, InventoryItem> seeds = Inventory.getSeeds();
                        if (seeds.containsKey(itemName)) {
                            seeds.put(itemName, new InventoryItem(itemName, "seed",
                                    numOfItems + seeds.get(itemName).getQuantity()));
                        } else {
                            seeds.put(itemName, new InventoryItem(itemName, "seed", numOfItems));
                        }
                    } else {
                        receiptList.add(itemName + " x" + numOfItems
                                + "...................." + (numOfItems * itemPrice));
                        HashMap<String, InventoryItem> prods = Inventory.getProducts();
                        if (prods.containsKey(itemName)) {
                            Inventory.getProducts().put(itemName, new InventoryItem(itemName,
                                    "product", numOfItems + prods.get(itemName).getQuantity()));
                        } else {
                            Inventory.getProducts().put(itemName, new InventoryItem(itemName,
                                    "product", numOfItems));
                        }
                    }
                    totalPrice += numOfItems * itemPrice;
                    RECEIPT_VBOX.getChildren().set(1, new Text("Total: " + totalPrice));
                } else {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setContentText("Cannot add more items into inventory.\n"
                            + "Current inventory space: " + Inventory.getNumOfInventory() + "/100");
                    warning.showAndWait();
                }
            }
        });
    }
    private static void addToHireReceipt(ListView<FarmWorker> farmWorkerList,
                                         ObservableList<String> receiptList) {
        farmWorkerList.setOnMouseClicked(event -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Hire Confirmation");
            confirmation.setHeaderText("Hiring Confirmation");
            confirmation.setContentText("Are you sure you want to hire this individual?");
            Optional<ButtonType> result = confirmation.showAndWait();
            FarmWorker selectedItem = farmWorkerList.getSelectionModel().
                    getSelectedItem();
            String workerName = selectedItem.getName();
            double workerWage = selectedItem.getWage();
            if (result.get() == ButtonType.OK) {
                receiptList.add(workerName + " Daily Wage: " + workerWage + " coins");
            }
        });
    }

    private static void addToSellReceipt(ListView<SellItem> objectMarketList,
                                         ObservableList<String> receiptList) {
        objectMarketList.setOnMouseClicked(event -> {
            TextInputDialog objectQuantity = new TextInputDialog("1");
            SellItem selectedItem = objectMarketList.getSelectionModel().
                    getSelectedItem();
            String itemName = selectedItem.getName();
            String itemType = selectedItem.getType();
            objectQuantity.setHeaderText("How many " + itemName + "s do you want to sell?");
            objectQuantity.setContentText("Please enter the number of " + itemName + " " + itemType
                    + "s you want to sell: ");
            Optional<String> result = objectQuantity.showAndWait();
            if (result.isPresent()) {
                int numOfItems = Integer.parseInt(result.get());
                int invQuantity = 0;
                if (itemType.equals("seed")) {
                    invQuantity = Inventory.getSeeds().get(itemName).getQuantity();
                } else {
                    invQuantity = Inventory.getCrops().get(itemName).getQuantity();
                }
                if (numOfItems <= invQuantity) {
                    double itemPrice = Inventory.getWholeInventory().get(itemName).getSellPrice();
                    if (itemType.equals("seed")) {
                        receiptList.add(itemName + " Seeds x" + numOfItems
                                + "...................." + (numOfItems * itemPrice));
                        HashMap<String, InventoryItem> seeds = Inventory.getSeeds();
                        seeds.put(itemName, new InventoryItem(itemName, "seed",
                                seeds.get(itemName).getQuantity() - numOfItems));
                    } else {
                        receiptList.add(itemName + " Crops x" + numOfItems
                                + "...................." + (numOfItems * itemPrice));
                        HashMap<String, InventoryItem> crops = Inventory.getCrops();
                        Inventory.getCrops().put(itemName, new InventoryItem(itemName, "crop",
                                crops.get(itemName).getQuantity() - numOfItems));
                    }
                    totalPrice += numOfItems * itemPrice;
                    SELL_RECEIPT_VBOX.getChildren().set(1, new Text("Total: " + totalPrice));
                } else {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setContentText("Not enough inventory to sell "
                            + numOfItems + " " + itemName + " " + itemType + "s");
                    warning.showAndWait();
                }
            }
        });
    }

    private static void createTableView(TableView tableView) {
        TableColumn<String, InventoryItem> objNameCol = new TableColumn<>("Name:");
        TableColumn<String, InventoryItem> objQuantityCol = new TableColumn<>("Number:");
        objNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        objQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableView.getColumns().addAll(objNameCol, objQuantityCol);
        tableView.setPlaceholder(new Text("Nothing in inventory"));
    }

    private static void createSplitPane(SplitPane splitPane, AnchorPane leftPane,
                                        AnchorPane rightPane) {
        splitPane.setPrefSize(400, 300);
        splitPane.setMaxSize(400, 300);
        splitPane.setOpaqueInsets(new Insets(50, 50, 50, 50));
        leftPane.setPrefSize(400, 300);
        rightPane.setPrefSize(200, 200);
        splitPane.getItems().addAll(leftPane, rightPane);
    }

    private static int getBasePrice(String name) {
        if (name.contains("Wheat") || name.contains("Strawberry") || name.contains("Corn")) {
            return 50;
        } else if (name.contains("Apple")) {
            return 100;
        } else if (name.contains("Pumpkin")) {
            return 300;
        } else {
            return 800;
        }
    }

    private static ListView<SellItem> createSellListingsVBox() {
        ObservableList<SellItem> sellInventoryList = FXCollections.observableArrayList();
        Inventory.getSeeds().forEach((k, v) -> {
            sellInventoryList.add(new SellItem(k, "seed", v.getQuantity(),
                    getBasePrice(v.getName()), false));
        });
        Inventory.getCrops().forEach((k, v) -> {
            sellInventoryList.add(new SellItem(k, "crop", v.getQuantity(),
                    2 * getBasePrice(v.getName()), v.getPesticide()));
        });
        return new ListView<>(sellInventoryList);
    }

    private static ListView<FarmWorker> createWorkerListingsVBox() {
        ObservableList<FarmWorker> workers = FXCollections.observableArrayList(
                new FarmWorker("Farmhand John", 100, "Poor"),
                new FarmWorker("Farmhand Joe", 300, "Skilled"),
                new FarmWorker("Farmhand Cherry", 350, "Skilled"),
                new FarmWorker("Farmhand Tim", 650, "Highly Skilled"));
        return new ListView<>(workers);
    }

    private static void createMarketListingsVBox(VBox vBox, AnchorPane anchorPane) {
        vBox.setPrefSize(600, 400);
        anchorPane.getChildren().add(vBox);
        ObservableList<MarketItem> seedList = FXCollections.observableArrayList(
                new MarketItem("Seed", "Wheat", 50, 1),
                new MarketItem("Seed", "Strawberry", 50, 1),
                new MarketItem("Seed", "Corn", 50, 1),
                new MarketItem("Seed", "Pumpkin", 300, 4),
                new MarketItem("Seed", "Apple", 100, 3),
                new MarketItem("Seed", "Truffle", 800, 10));
        seedMarketList = new ListView<>(seedList);
        seedMarketList.setPrefSize(300, 250);
        Separator marketSeparator = new Separator();
        marketSeparator.setOrientation(Orientation.HORIZONTAL);
        objectMarketList = new ListView<>();
        objectMarketList.setPrefSize(300, 250);
        objectMarketList.setId("objectMarketList");
        objectMarketList.getItems().addAll(new MarketItem("Object", "Shovel", 500, 2),
                new MarketItem("Object", "Plough", 600, 2),
                new MarketItem("Object", "Fertilizer", 100, 1),
                new MarketItem("Object", "Pesticide", 100, 1),
                new MarketItem("Object", "Hoe", 300, 2),
                new MarketItem("Object", "Tractor", 10000, 3),
                new MarketItem("Object", "Irrigation", 15000, 3));
        vBox.getChildren().addAll(seedMarketList, marketSeparator, objectMarketList);
    }

    private static void handleBuy(ActionEvent event) {
        if (totalPrice <= money) {
            money -= totalPrice;
            totalPrice = 0;
            totalPriceText.setText("Total: " + totalPrice);
            totalMoneyText.setText("Money: " + money + " coins");
            totalSellMoneyText.setText("Money: " + money + " coins");
            SetupScreen.getMoneyLabel().setText("Money: " + money + " coins");
            receiptList.clear();
            RECEIPT_VBOX.getChildren().set(0, receipt);
            RECEIPT_VBOX.getChildren().set(1, totalPriceText);
            ObservableList<InventoryItem> newSeedsInv =
                    FXCollections.observableArrayList(Inventory.getSeeds().values());
            ObservableList<InventoryItem> newProductsInv =
                    FXCollections.observableArrayList(Inventory.getProducts().values());
            seedTableView.setItems(newSeedsInv);
            prodTableView.setItems(newProductsInv);
            Label farmInventory = FarmScreen.getInventoryLabel();
            farmInventory.setText(Inventory.getInventoryString());
        } else {
            Alert buyWarning = new Alert(Alert.AlertType.WARNING);
            buyWarning.setContentText("You do not have enough money to make this purchase.");
            buyWarning.showAndWait();
        }
    }

    private static void handleSell(ActionEvent event) {
        money += totalPrice;
        totalPrice = 0;
        totalSellPriceText.setText("Total: " + totalPrice);
        totalSellMoneyText.setText("Money: " + money + " coins");
        totalMoneyText.setText("Money: " + money + " coins");
        SetupScreen.getMoneyLabel().setText("Money: " + money + " coins");
        sellReceiptList.clear();
        SELL_RECEIPT_VBOX.getChildren().set(0, sellReceipt);
        SELL_RECEIPT_VBOX.getChildren().set(1, totalSellPriceText);
        sellList = createSellListingsVBox();
        sellListings.getChildren().set(0, sellList);
        addToSellReceipt(sellList, sellReceiptList);
        ObservableList<InventoryItem> newSeedsInv =
                FXCollections.observableArrayList(Inventory.getSeeds().values());
        ObservableList<InventoryItem> newCropsInv =
                FXCollections.observableArrayList(Inventory.getCrops().values());
        seedTableView.setItems(newSeedsInv);
        cropTableView.setItems(newCropsInv);
        Label farmInventory = FarmScreen.getInventoryLabel();
        farmInventory.setText(Inventory.getInventoryString());
    }
}