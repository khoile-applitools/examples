package demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.environment.EnvironmentUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.SessionUrls;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.locators.OcrRegion;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.SeleniumCheckSettings;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.ChromeEmulationInfo;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.IosDeviceInfo;
import com.applitools.eyes.visualgrid.model.IosDeviceName;
import com.applitools.eyes.visualgrid.model.IosVersion;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.google.common.base.Stopwatch;

public class OCRTest {
    
    private static WebDriver driver;
    
	public static void main(String[] args) {
		try {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			
			
			options.addArguments("--test-type");
			//options.setExperimentalOption("excludeSwitches", Arrays.asList("test-type"));
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		
			
			options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
			options.addArguments("--headless","--disable-web-security");
			driver = new ChromeDriver(options);
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			initEyes();
			
			
			Stopwatch stopwatch = Stopwatch.createStarted();
		
	        eyes.open(driver,"OCR App","OCR Test");
	        
	    
	        
	        eyes.setLogHandler(new StdoutLogHandler(true));
	    
	        driver.get("http://the-internet.herokuapp.com/shifting_content/menu");
	        
	        Eyes.setViewportSize(driver, new RectangleSize(800,400));
	      
	        Thread.sleep(2000);
	     
	        eyes.check("ocr", Target.window().fully().waitBeforeCapture(2000).lazyLoad());
	        
	        //List<String> text = eyes.extractText(new OcrRegion(new By.ByCssSelector("#content")));
	        List<String> text = eyes.extractText(new OcrRegion(new By.ByCssSelector("body")));
	        
	        for (int i = 0; i < text.size(); i++) {
	            System.out.printf("%d) found '%s'\n", i, text.get(i));
	        }
	        
	       
	        eyes.closeAsync();
	        
	        stopwatch.stop(); // optional

			long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			System.out.println("time: " + millis);
	        
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
	        driver.quit();
	        
	        /*
	        //eyes.abortAsync();
	     	// Print results*/
	        /*
	        TestResultsSummary allTestResults = runner.getAllTestResults(false);
			System.out.println(allTestResults);
			System.out.println(allTestResults.getAllResults()[0].getTestResults().getStepsInfo()[0].getApiUrls().getDiffImage());
			System.out.println(allTestResults.getAllResults()[0].getTestResults().getStepsInfo()[0].getApiUrls().getBaselineImage());
			System.out.println(allTestResults.getAllResults()[0].getTestResults().getStepsInfo()[0].getApiUrls().getCheckpointImage());
			*/
		
		}
		
        
	}
	
	//private static EyesRunner runner = new ClassicRunner();
	private static EyesRunner runner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));
    private static Eyes eyes = new Eyes(runner);
	//private static Eyes eyes = new Eyes();
	
	
	
	private static Configuration config;
    private static BatchInfo batch;
	    
	private static void initEyes() {
		eyes.setApiKey("");
        
		
        config = eyes.getConfiguration();
        config.addBrowser(900, 600, BrowserType.CHROME);
        config.addBrowser(1200, 800, BrowserType.SAFARI);
        
        config.setEnablePatterns(true);
        config.setLayoutBreakpoints(true);
        
        //config.setDisableBrowserFetching(true);
        
        
        
        
        //config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_13_Pro_Max)); //latest version - currently 16
        //config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_13_Pro_Max, IosVersion.ONE_VERSION_BACK)); //15.3
        //config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_13_Pro));
        //config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_12_mini));
        //config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_12_mini, IosVersion.ONE_VERSION_BACK));//15.3
        //config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
        //config.addDeviceEmulation(DeviceName.Pixel_2_XL, ScreenOrientation.PORTRAIT);
        //config.addDeviceEmulation(DeviceName.iPad_Pro, ScreenOrientation.PORTRAIT);
        //config.addDeviceEmulation(DeviceName.Galaxy_S20, ScreenOrientation.PORTRAIT);
        //config.addDeviceEmulation(DeviceName.Pixel_5, ScreenOrientation.PORTRAIT);
        
        //config.setDisableBrowserFetching(false);
        //config.setViewportSize(new RectangleSize(600,600));
       // config.setWaitBeforeCapture(5000);
        
        
        //config.setDisableBrowserFetching(false);        
        eyes.setConfiguration(config);
        //eyes.setWaitBeforeScreenshots(200);
        
        eyes.setStitchMode(StitchMode.CSS);
        
        /*
        eyes.setConfiguration(config);
        String env = System.getenv("TEST_ENV");
        if(env == null) {
        	env = "local";
        }*/
        //batch = new BatchInfo(env);
        batch = new BatchInfo("Nightly");
        //batch.setId("nightly" + param);
        batch.setSequenceName("Nightly");
        batch.addProperty("appName", "Test");
        batch.addProperty("appName", "Test2");
        batch.setNotifyOnCompletion(true);
        
        eyes.setBatch(batch);
	}

}
