package br.com.dwarflink.test;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.lang3.RandomStringUtils;

public class RequireLink {
  private static final String APPLICATION_URL = "http://localhost:9000";

  //User infos
  private static final String USERNAME = "johnLocke";
  private static final String PSSW = "4515162342";
  private static final String INCORRECT_PSSW = "4515162342XX";

  //HTML elements id
  private static final String CONTROL_PANEL_ID = "btnControlPanel";
  private static final String BTN_START = "btnStart";
  private static final String BTN_SIGNUP = "btnSignup";
  private static final String BTN_SIGNIN = "btnSignin";
  private static final String BTN_LOGOUT = "btnLogout";
  private static final String W_USERNAME = "username";

  //Sign form
  private static final String SIGN_UNAME = "username";
  private static final String SIGN_PSSW = "password";
  private static final String BTN_SUBMIT = "submitLink";

  //Main Page
  private static final String SUB_TITLE = "Your personal, secure and simple shortener link";

  //Erros Msg
  private static final String SIGNUP_ERROR = "Sorry! This username is already in use";
  private static final String SIGNIN_ERROR = "Sorry! Your Username OR Password are incorrect!";

  //Control Panel Page
  private static final String CONTROL_PANEL_WELCOME_MSG = "This is your control panel. From here, you can create different types of links.";
  private static final String BTN_SHORT_LINK = "shorten";
  private static final String BTN_TEMPORARY_LINK = "temporary";
  private static final String BTN_CUSTOMIZE_LINK = "customize";
  private static final String BTN_SECURE_LINK = "secure";

  //General link
  private static final String ORIGINAL_LINK_BOX = "originalLink";
  private static final String SHORT_LINK_SUCESS_MSG = "Here is your shortened link!";
  
  //Temporary Link
  private static final String TEMPORARY_LINK_BOX = "temporarySlug";
  private static final String TEMPORARY_SLUG = "wakafoo";
  private static final String TEMPORARY_LIFETIME_SELECT = "lifetime";

  //Customized Link
  private static final String CUSTOMIZED_LINK_BOX = "customizedSlug";
  private static final String CUSTOMIZED_SLUG = "foobar";

  //Secure Link
  private static final String SECURE_LINK_BOX = "secureSlug";
  private static final String SECURE_SLUG = "foobar";
  private static final String SECURE_LINK_KEY_BOX = "goldenShovel";
  private static final String SECURE_LINK_KEY = "gnulinux";

  //Original links
  private static final String LINK_1 = "www.google.com.br";
  private static final String LINK_2 = "www.uol.com.br";
  private static final String LINK_3 = "www.globo.com.br";
  private static final String LINK_4 = "www.ig.com.br";
  private static final String LINK_5 = "www.facebook.com";

  public static WebDriver signinUsuarioCorreto() {
    
    // abre firefox
    WebDriver driver = new FirefoxDriver();
    String randomComplement = "-" + RandomStringUtils.randomAscii(4);
    
    // O usuario acessa a homepage
    driver.get(APPLICATION_URL);

    //O usuario clica no botao 'Sign up' para se cadastrar
    WebElement botaoSignup = driver.findElement(By.id(BTN_SIGNIN));
    botaoSignup.click();

    //O usuário preenche o formulario de login com username e senha
    WebElement name = driver.findElement(By.name(SIGN_UNAME));
    WebElement password = driver.findElement(By.name(SIGN_PSSW));
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));

    name.sendKeys(USERNAME);
    password.sendKeys(PSSW);

    //O usuario clica em submit
    btnSubmit.click();

    return driver;
  }

  @Test
  public void requisitaLinkEncurtado() {
    //Faz login no sistema
    WebDriver driver = signinUsuarioCorreto();

    //Clica no tipo de link que quer gerar
    WebElement linkBox = driver.findElement(By.id(BTN_SHORT_LINK));
    linkBox.click();

    //Fornece o link a ser encurtado
    WebElement originalLinkBox = driver.findElement(By.name(ORIGINAL_LINK_BOX));
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));

    originalLinkBox.sendKeys(LINK_1);
    btnSubmit.click();

    //O usuario recebe o seu link encurtado
    boolean shortlinkSucessMsg = driver.getPageSource().contains(SHORT_LINK_SUCESS_MSG);
    assertTrue(shortlinkSucessMsg);

    driver.quit();
  }
  
  @Test
  public void requisitaLinkTemporario() {
    //Faz login no sistema
    WebDriver driver = signinUsuarioCorreto();
    String randomComplement = "-" + RandomStringUtils.randomAscii(4);

    //Clica no tipo de link que quer gerar
    WebElement linkBox = driver.findElement(By.id(BTN_TEMPORARY_LINK));
    linkBox.click();

    //Fornece o link a ser encurtado
    WebElement originalLinkBox = driver.findElement(By.name(ORIGINAL_LINK_BOX));
    
    //Fornece um idenficador para o link temporario
    WebElement temporarylLinkBox = driver.findElement(By.name(TEMPORARY_LINK_BOX));
    //Fornece o tempo de vida que o link vai ter
    Select lifetime = new Select(driver.findElement(By.name(TEMPORARY_LIFETIME_SELECT)));
    //Submete o formulario
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));

    originalLinkBox.sendKeys(LINK_2);
    temporarylLinkBox.sendKeys(TEMPORARY_SLUG + randomComplement);
    lifetime.selectByVisibleText("2 minutes");
    btnSubmit.click();


    //O usuario recebe o seu link temporario encurtado
    boolean shortlinkSucessMsg = driver.getPageSource().contains(SHORT_LINK_SUCESS_MSG);
    assertTrue(shortlinkSucessMsg);

    driver.quit();
  }
  
  @Test
  public void requisitaLinkCustomizado() {
    //Faz login no sistema
    WebDriver driver = signinUsuarioCorreto();
    String randomComplement = "-" + RandomStringUtils.randomAscii(4);

    //Clica no tipo de link que quer gerar
    WebElement linkBox = driver.findElement(By.id(BTN_CUSTOMIZE_LINK));
    linkBox.click();

    //Fornece o link a ser encurtado
    WebElement originalLink = driver.findElement(By.name(ORIGINAL_LINK_BOX));
    //Fornece um idenficador para o link customizado
    WebElement customizedSlug = driver.findElement(By.name(CUSTOMIZED_LINK_BOX));
    //Submete o formulario
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));

    originalLink.sendKeys(LINK_3);
    customizedSlug.sendKeys(CUSTOMIZED_SLUG + randomComplement);
    btnSubmit.click();

    //O usuario recebe o seu link customizado
    boolean shortlinkSucessMsg = driver.getPageSource().contains(SHORT_LINK_SUCESS_MSG);
    assertTrue(shortlinkSucessMsg);

    driver.quit();
  }
  
  @Test
  public void requisitaLinkSeguro() {
    //Faz login no sistema
    WebDriver driver = signinUsuarioCorreto();
    String randomComplement = "-" + RandomStringUtils.randomAscii(4);

    //Clica no tipo de link que quer gerar
    WebElement linkBox = driver.findElement(By.id(BTN_SECURE_LINK));
    linkBox.click();

    //Fornece o link a ser encurtado
    WebElement originalLink = driver.findElement(By.name(ORIGINAL_LINK_BOX));
    //Fornece um idenficador para o link seguro
    WebElement secureSlug = driver.findElement(By.name(SECURE_LINK_BOX));
    //Fornece uma chave de seguranca para o link seguro
    WebElement secureLinkKey = driver.findElement(By.name(SECURE_LINK_KEY_BOX));
    //Submete o formulario
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));

    originalLink.sendKeys(LINK_4);
    secureSlug.sendKeys(SECURE_SLUG + randomComplement);
    secureLinkKey.sendKeys(SECURE_LINK_KEY);
    btnSubmit.click();

    //O usuario recebe o seu link seguro
    boolean shortlinkSucessMsg = driver.getPageSource().contains(SHORT_LINK_SUCESS_MSG);
    assertTrue(shortlinkSucessMsg);
    driver.quit();
  }

  @Test
  public void requisitarLinkNaoLogado() {
    // abre firefox
    WebDriver driver = new FirefoxDriver();
    
    // O usuario acessa a homepage
    driver.get(APPLICATION_URL);

    //Clica no tipo de link que quer gerar
    WebElement botaoStart = driver.findElement(By.id(BTN_START));
    botaoStart.click();

    //Fornece o link a ser encurtado
    WebElement originalLink = driver.findElement(By.name(ORIGINAL_LINK_BOX));
    //Submete o formulario
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));

    originalLink.sendKeys(LINK_5);
    btnSubmit.click();

    //O usuario nao logado recebe seu link encurtado
    boolean shortlinkSucessMsg = driver.getPageSource().contains(SHORT_LINK_SUCESS_MSG);
    assertTrue(shortlinkSucessMsg);
    driver.quit(); 

  }

}
