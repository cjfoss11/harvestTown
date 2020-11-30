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

public class AppTestsM4 extends ApplicationTest {
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
    public void verifyNextDayButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#nextDayButton");
        FxAssert.verifyThat("#dayLabel",
                LabeledMatchers.hasText("\tDay: " + Inventory.getDay()));
    }

    @Test
    public void verifyPlantButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#plantButton");
    }

    @Test
    public void verifyWaterButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#plantButton");
        type(KeyCode.ENTER);
        clickOn("#nextDayButton");
        clickOn("#waterButton");
        FxAssert.verifyThat("#waterText",
                TextMatchers.hasText(FarmScreen.getWaterText(0)));
    }

    @Test
    public void verifyPlantMaturity() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#plantButton");
        type(KeyCode.ENTER);
        clickOn("#nextDayButton");
        clickOn("#waterButton");
        FxAssert.verifyThat("#cropText",
                TextMatchers.hasText(FarmScreen.getCropText(0, 0)));
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
}