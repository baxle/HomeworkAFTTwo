import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParametrizedTest {
    private static WebDriver chromeDriver;
    private static String baseUrl = "https://www.rgs.ru/";
    private static RgsRequest rgsRequest;
    private static RgsStart rgsStart;
    private static String driverName;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Путин", "Владимир", "Владимирович"},
                {"Медведев", "Дмитрий", "Анатольевич"},
                {"Трамп", "Дональд", "Джон"}
        });
    }

    @Parameterized.Parameter
    public String lastName;

    @Parameterized.Parameter(1)
    public String firstName;

    @Parameterized.Parameter(2)
    public String middleName;

    @BeforeClass
    public static void setUp() throws Exception {
        driverName = System.getProperty("driver").toLowerCase();
        System.setProperty("webdriver.gecko.driver", "drv/newgeckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");

        if (driverName.equals("chrome")) {
            chromeDriver = new ChromeDriver();
        }
        else if (driverName.equals("firefox")) {
            chromeDriver = new FirefoxDriver();
        }
        else {
            System.err.println("Введите другой браузер");
            chromeDriver.quit();
        }



        chromeDriver.manage().window().maximize();

        rgsRequest = PageFactory.initElements(chromeDriver, RgsRequest.class);
        rgsStart = PageFactory.initElements(chromeDriver, RgsStart.class);

        rgsRequest.open(baseUrl);
        rgsStart.getDmsPage();
        rgsStart.checkLinkDms();

        rgsRequest.verificationLinkDms();
        rgsRequest.openSendRequestForm();
        rgsRequest.verificationTitle();


    }


    @Test
    public void chromeTesting() throws Exception {

        rgsRequest.fillLastName(lastName);
        rgsRequest.fillFirstName(firstName);
        rgsRequest.fillMiddleName(middleName);


        rgsRequest.fillRegion("Москва");
        rgsRequest.fillEmail("qwertyqwerty");
        rgsRequest.fillPhone("8005553535");
        rgsRequest.fillComment("Без комментариев.");
        rgsRequest.fillDate("12.12.2019");
        rgsRequest.checkBoxClicker();

        rgsRequest.buttonM_Clicker();

        rgsRequest.checkLastName(lastName);
        rgsRequest.checkFirstName(firstName);
        rgsRequest.checkMiddleName(middleName);
        rgsRequest.checkRegion("Москва");
        rgsRequest.checkEmail("qwertyqwerty");
        rgsRequest.checkErrorEmail("Введите адрес электронной почты");
        rgsRequest.checkPhone("+7 (800) 555-35-35");
        rgsRequest.checkComment("Без комментариев.");
        rgsRequest.checkDate("12.12.2019");
        rgsRequest.checkCheckBox();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        chromeDriver.quit();
    }
}