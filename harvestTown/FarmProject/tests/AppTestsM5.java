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
import org.testfx.matcher.control.TextMatchers;
import java.util.concurrent.TimeoutException;

public class AppTestsM5 extends ApplicationTest {
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
    public void verifyNoPesticideButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#plantButton");
        type(KeyCode.ENTER);
        clickOn("#pesticideButton");
        type(KeyCode.ENTER);
    }

    @Test
    public void verifyNoFertilizerButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#plantButton");
        type(KeyCode.ENTER);
        clickOn("#fertilizerButton");
        type(KeyCode.ENTER);
    }

    @Test
    public void verifyPesticide() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#plantButton");
        type(KeyCode.ENTER);
        clickOn("#marketButton");
        clickOn("#objectMarketList");
        type(KeyCode.ENTER);
        clickOn("#buyButton");
        clickOn("#backToFarm");
        clickOn("#pesticideButton");
    }

    @Test
    public void verifyFertilizer() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#plantButton");
        type(KeyCode.ENTER);
        clickOn("#marketButton");
        moveTo("#objectMarketList").moveBy(0, -20).clickOn();
        type(KeyCode.ENTER);
        clickOn("#buyButton");
        clickOn("#backToFarm");
        clickOn("#fertilizerButton");
        FxAssert.verifyThat("#fertilizerText", TextMatchers.hasText("Fertilizer Level: 1"));
    }

    @Test
    public void verifyFertilizerInventory() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#plantButton");
        type(KeyCode.ENTER);
        clickOn("#marketButton");
        moveTo("#objectMarketList").moveBy(0, -20).clickOn();
        type(KeyCode.DIGIT2);
        type(KeyCode.ENTER);
        clickOn("#buyButton");
        clickOn("#backToFarm");
        clickOn("#fertilizerButton");
        FxAssert.verifyThat("#inventoryLabel",
                LabeledMatchers.hasText(Inventory.getInventoryString()));
    }
}