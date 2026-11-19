package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import static constants.Constants.BASE_URL;

public class HomePage extends BasePage{

    By chapterLocator = By.cssSelector("h5.card-title");
    By linkLocator = By.xpath("./../a");

    @FindBy(xpath = "//h1[@class='display-4']")
    private WebElement titleLocator;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Home Page")
    public void openHomePage() {
        driver.get(BASE_URL);
    }

    @Step("Get Home Page URL")
    public String getHomePageUrl() {
        return BASE_URL;
    }

    @Step("Get Home Page Title")
    public String getHomePageTitle() {
        return titleLocator.getText();
    }

    @Step("Count all chapters on Home Page")
    public int countChapters(){
        int qtyChapters = 0;
        List<WebElement> chapters = driver.findElements(chapterLocator);
        for (WebElement chapter : chapters) {
            qtyChapters += 1;
        }
        return qtyChapters;
    }

    @Step("Count and click all Home Page chapters links")
    public int countAndClickLinks() {
        int qtyLinks = 0;
        List<WebElement> chapters = driver.findElements((By) chapterLocator);
        for (WebElement chapter : chapters) {
            List<WebElement> links = chapter.findElements(linkLocator);
            qtyLinks += links.size();
            System.out.println(chapter.getText());
            for (WebElement link : links) {
                System.out.println(link.getText());
                link.click();
                driver.navigate().back();
            }
        }
        return qtyLinks;
    }

    public LoginPage openLoginFormPage() {
        driver.findElement(By.xpath("//a[@href='login-form.html']")).click();
        return new LoginPage(driver);
    }

    public DialogBoxesPage openDialogBoxesPage() {
        driver.findElement(By.xpath("//a[@href='dialog-boxes.html']")).click();
        return new DialogBoxesPage(driver);
    }

    public DragAndDropPage openDragAndDropPage() {
        driver.findElement(By.xpath("//a[@href='drag-and-drop.html']")).click();
        return new DragAndDropPage(driver);
    }

    public ShadowDomPage openShadowDomPage() {
        driver.findElement(By.xpath("//a[@href='shadow-dom.html']")).click();
        return new ShadowDomPage(driver);
    }

    public LoadingImagesPage openLoadingImagesPage() {
        driver.findElement(By.xpath("//a[@href='loading-images.html']")).click();
        return new LoadingImagesPage(driver);
    }

    public MouseOverPage openMouseOverPage() {
        driver.findElement(By.xpath("//a[@href='mouse-over.html']")).click();
        return new MouseOverPage(driver);
    }

    public SlowCalculatorPage openSlowCalculatorPage() {
        driver.findElement(By.xpath("//a[@href='slow-calculator.html']")).click();
        return new SlowCalculatorPage(driver);
    }

    public WebStoragePage openWebStoragePage() {
        driver.findElement(By.xpath("//a[@href='web-storage.html']")).click();
        return new WebStoragePage(driver);
    }

    public CookiesPage openCookiesPage() {
        driver.findElement(By.xpath("//a[@href='cookies.html']")).click();
        return new CookiesPage(driver);
    }

    public NavigationPage openNavigationPage() {
        driver.findElement(By.xpath("//a[@href='navigation1.html']")).click();
        return new NavigationPage(driver);
    }
}
