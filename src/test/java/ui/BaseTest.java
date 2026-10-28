package ui;

import configs.TestPropertiesConfig;
import extensions.AllureExtension;
import io.qameta.allure.Feature;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

@Feature("Extensions")
@ExtendWith(AllureExtension.class)
public class BaseTest {
    static WebDriver driver;
    static TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    @BeforeEach
    public void setUp() {
        initDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void initDriver() {
        // 1. Сначала проверяем переменную окружения
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");

        // 2. Если пустая, проверяем системное свойство Java (-DSELENIUM_REMOTE_URL=...)
        if (remoteUrl == null || remoteUrl.isEmpty()) {
            remoteUrl = System.getProperty("SELENIUM_REMOTE_URL");
        }

        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            // Удаленный запуск (Jenkins + Selenium Grid)
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--use-fake-ui-for-media-stream");
            options.addArguments("--deny-permission-prompts=false");
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL for remote URL: " + remoteUrl, e);
            }
        } else {
            // Локальный запуск (на вашей машине)
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
    }
}