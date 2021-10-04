package testCases;

import com.thoughtworks.gauge.Step;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class T_Avansas_Gauge {
    WebDriver driver;
    long count;

    @Step("Kullanıcı avansas sitesine girer")
    public void implementation1() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.avansas.com");
        WebDriverWait wait = new WebDriverWait(driver, 7);
        driver.manage().window().maximize();

    }

    @Step("Avansas.com da arama alanına “kalem”yazılarak ara butonu tıklanır.")
    public void implementation2() {
        WebElement searchTextInput = driver.findElement(By.cssSelector("input[name='q']"));
        searchTextInput.sendKeys("kalem");
        WebElement araBtn = driver.findElement(By.cssSelector("form#multiple-datasets > button"));
        araBtn.click();
    }

    @Step("Kategori listesinden kurşun kalemler kategorisi tıklanır.")
    public void implementation3() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // WebElement kategoriKursunKalemler = driver.findElement(By.partialLinkText("Kurşun Kalemler"));
        //WebElement kategoriKursunKalemler = driver.findElement(By.xpath("//h3[contains(text(), 'Kurşun Kalemler')]"));
        WebElement kategoriKursunKalemler = driver.findElement(By.cssSelector("img[title ^= 'Kurşun Kalemler']"));


        js.executeScript("arguments[0].scrollIntoView();", kategoriKursunKalemler);
        /*
        while(!(kategoriKursunKalemler.isDisplayed())) {
    	js.executeScript("scrollBy(0,100);");
        }
         */
        js.executeScript("scrollBy(0,-100);");
        // WebElement img = driver.findElement(By.cssSelector("img[title*= 'Kurşun Kalemler']"));

//            wait.until(ExpectedConditions.elementToBeClickable(kategoriKursunKalemler)).
        kategoriKursunKalemler.click();
    }

    @Step("Sıralama fonksiyonundan ada göre sırala seçilir.")
    public void implementation4() {
        //            Select dropDown = new Select(driver.findElement(By.id("sort")));
//            dropDown.selectByValue("isim-a-z");

        WebElement dropDown = driver.findElement(By.cssSelector("span[class *= 'select2 select2-container']"));
        dropDown.click();

        WebElement select = driver.findElement(By.cssSelector("li[id $= 'isim-a-z']"));
        select.click();
    }

    @Step("Gelen ürün listesinde adında “Bic Evolution” içeren kaç ürün olduğu tespit edilir.")
    public void implementation5() {
        List<WebElement> productList = driver.findElements(By.cssSelector("div[class *= 'list-container'] div[class='product-list']"));

        count = productList.stream().map(a -> a.getAttribute("data-product-name")).filter(a -> a.indexOf("Bic Evolution") != -1).count();
    }

    @Step("Ürün adeti tespit edilir ve 0 dan fazla ise test başarılıdır, 0 ise başarısızdır.")
    public void implementation6() {
        Assert.assertTrue(count > 0);
    }

    @Step("Tarayıcı kapatılır.")
    public void implementation7() {
        driver.quit();
    }
}

