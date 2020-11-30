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

public class AppTestsM6 extends ApplicationTest {
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
    public void verifyTractor() {
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
        moveTo("#objectMarketList").moveBy(0, -60).clickOn();
        type(KeyCode.ENTER);
        clickOn("#buyButton");
        clickOn("#backToFarm");
        FxAssert.verifyThat("#inventoryLabel",
                LabeledMatchers.hasText(Inventory.getInventoryString()));
    }

    @Test
    public void verifyIrrigation() {
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
        moveTo("#objectMarketList").moveBy(0, -80).clickOn();
        type(KeyCode.ENTER);
        clickOn("#buyButton");
        clickOn("#backToFarm");
        FxAssert.verifyThat("#inventoryLabel",
                LabeledMatchers.hasText(Inventory.getInventoryString()));
    }

    @Test
    public void verifyExpand() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#expandPlotButton");
        type(KeyCode.ENTER);
    }

    @Test
    public void verifyNoMoreExpansion() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        clickOn("#expandPlotButton");
        type(KeyCode.ENTER);
        clickOn("#expandPlotButton");
        type(KeyCode.ENTER);
        clickOn("#expandPlotButton");
        type(KeyCode.ENTER);
        clickOn("#expandPlotButton");
        type(KeyCode.ENTER);
    }

    @Test
    public void verifyIrrigationStop() {
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
        moveTo("#objectMarketList").moveBy(0, -80).clickOn();
        type(KeyCode.ENTER);
        clickOn("#buyButton");
        clickOn("#backToFarm");
        clickOn("#plant1Button").type(KeyCode.ENTER);
        clickOn("#plant2Button").type(KeyCode.ENTER);
        clickOn("#plant3Button").type(KeyCode.ENTER);
        clickOn("#nextDay");
        clickOn("#water1Button");
        clickOn("#water1Button");
        clickOn("#water1Button");
        clickOn("#water1Button");
        clickOn("#water2Button");
        clickOn("#water2Button");
        clickOn("#water2Button");
        clickOn("#water3Button");
        clickOn("#water3Button");
        clickOn("#water3Button");
    }

}