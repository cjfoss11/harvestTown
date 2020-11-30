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

import static org.testfx.matcher.control.TextMatchers.hasText;

public class AppTestsM2 extends ApplicationTest {
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

    //Charline Tests
    @Test
    public void verifyStartButton() {
        FxAssert.verifyThat("#startButton",
                LabeledMatchers.hasText("Let's start harvesting!"));
    }

    @Test
    public void verifyNullPlayerName() {
        clickOn("#startButton");
        clickOn("#nextButton");
        FxAssert.verifyThat("#invalidName", hasText("Must set a name to proceed"));
    }

    @Test
    public void testNextButton() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
    }

    @Test
    public void verifyName() {
        clickOn("#startButton");
        FxAssert.verifyThat("#name", LabeledMatchers.hasText("Name:"));
    }

    // Tests by Jennifer Bai.
    @Test
    public void verifyStartingSeed() {
        clickOn("#startButton");
        FxAssert.verifyThat("#startingSeed", LabeledMatchers.hasText("Starting Seed:"));
    }

    @Test
    public void verifyGameDifficulty() {
        clickOn("#startButton");
        FxAssert.verifyThat("#gameDifficulty", LabeledMatchers.hasText("Game Difficulty:"));
    }

    //amy unit test 1
    @Test
    public void verifyNextButton() {
        clickOn("#startButton");
        FxAssert.verifyThat("#nextButton", LabeledMatchers.hasText("Create your farm!"));
    }

    //amy unit test 2
    @Test
    public void verifyStartingSeason() {
        clickOn("#startButton");
        FxAssert.verifyThat("#startingSeason", LabeledMatchers.hasText("Starting Season:"));
    }

    @Test
    public void startingEasyMoney() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        FxAssert.verifyThat("#money", LabeledMatchers.hasText("Money: 10000.0 coins"));
    }

    @Test
    public void startingNormalMoney() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        FxAssert.verifyThat("#money", LabeledMatchers.hasText("Money: 5000.0 coins"));
    }

    @Test
    public void startingHardMoney() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(
                KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        FxAssert.verifyThat("#money", LabeledMatchers.hasText("Money: 3000.0 coins"));
    }

    @Test
    public void verifyDay() {
        clickOn("#startButton");
        clickOn("#nameText");
        type(KeyCode.T, KeyCode.O, KeyCode.M);
        clickOn("#gameDiffBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seedType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#seasonOptions").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#nextButton");
        FxAssert.verifyThat("#day", LabeledMatchers.hasText("\tDay: 1"));
    }
}