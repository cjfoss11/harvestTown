import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import java.util.concurrent.TimeoutException;

public class AppTestsM3 extends ApplicationTest {
    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();
    }

    @After
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void verifyMarketButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#marketButton");
    }

    @Test
    public void verifyBackToFarmButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#marketButton");
        clickOn("#backToFarm");
    }

    @Test
    public void harvestButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#harvestButton");
        String inventoryString = Inventory.getInventoryString();
        FxAssert.verifyThat("#inventoryLabel", LabeledMatchers.hasText(inventoryString));
    }

    @Test
    public void verifyInventory() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        FxAssert.verifyThat("#inventoryLabel",
                LabeledMatchers.hasText(Inventory.getInventoryString()));
    }

    @Test
    public void verifyBuyButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#marketButton");
        clickOn("#seedMarketList");
        type(KeyCode.ENTER);
        clickOn("#buyButton");
    }
}