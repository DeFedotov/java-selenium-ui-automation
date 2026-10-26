package patterns;

import configs.TestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static constants.Browsers.*;

public class WebDriverFactory {
    static TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    public static WebDriver createWebDriver(String browser) {
        WebDriver driver;

        // 1. Проверяем удаленный URL из всех возможных источников (переменная окружения, свойство Gradle, конфиг Owner)
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (remoteUrl == null || remoteUrl.isEmpty()) {
            remoteUrl = System.getProperty("SELENIUM_REMOTE_URL");
        }
        if (remoteUrl == null || remoteUrl.isEmpty()) {
            remoteUrl = configProperties.remoteUrl();
        }

        // 2. Если адрес найден — запускаем удаленный драйвер (для CI / Docker / Selenium Grid)
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL for remote URL: " + remoteUrl, e);
            }
        } else {
            // 3. Иначе — локальный запуск (если запуск идет на вашей машине без Docker)
            driver = switch (browser.toLowerCase()) {
                case CHROME -> new ChromeDriver();
                case FIREFOX -> new FirefoxDriver();
                case SAFARI -> new SafariDriver();
                default -> throw new IllegalArgumentException("Browser " + browser + " is not supported");
            };
            driver.manage().window().maximize();
        }

        return driver;
    }
}