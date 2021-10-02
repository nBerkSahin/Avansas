package testCases;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC_Avansas {
    WebDriver driver;

    @Test
    public void testAvansas() throws InterruptedException {
	//Create a instance of ChromeOptions class
	ChromeOptions options = new ChromeOptions();

	//Add chrome switch to disable notification - "**--disable-notifications**"
	options.addArguments("--disable-notifications");
	WebDriverManager.chromedriver().setup(); 
	driver = new ChromeDriver(options);
	
	//Launch the application
	driver.get("https://www.avansas.com");
        WebDriverWait wait = new WebDriverWait(driver, 7);
        driver.manage().window().maximize();
 
        // Avansas.com da arama alanına “kalem”yazılarak ara butonu tıklanır.
        WebElement searchTextInput = driver.findElement(By.cssSelector("input[name='q']"));
        searchTextInput.sendKeys("kalem");
        WebElement araBtn = driver.findElement(By.cssSelector("form#multiple-datasets > button"));
        araBtn.click();
        
        // Kategori listesinden kurşun kalemler kategorisi tıklanır.
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
        
        // Sıralama fonksiyonundan ada göre sırala seçilir.
        
//            Select dropDown = new Select(driver.findElement(By.id("sort")));
//            dropDown.selectByValue("isim-a-z");
        
        WebElement dropDown = driver.findElement(By.cssSelector("span[class *= 'select2 select2-container']"));
        dropDown.click();
        
        WebElement select = driver.findElement(By.cssSelector("li[id $= 'isim-a-z']"));
        select.click();
        
        // Gelen ürün listesinde adında “Bic Evolution” içeren kaç ürün olduğu tespit edilir.
        
        List<WebElement> productList = driver.findElements(By.cssSelector("div[class *= 'list-container'] div[class='product-list']"));
        
        long count = productList.stream().map(a-> a.getAttribute("data-product-name")).filter(a -> a.indexOf("Bic Evolution") != -1).count();
        // Ürün adeti tespit edilir ve 0 dan fazla ise test başarılıdır.
        Assert.assertTrue(count > 0);

        System.out.println(count);
        // Ürün adeti 0 ise test başarısızdır

    }
}
