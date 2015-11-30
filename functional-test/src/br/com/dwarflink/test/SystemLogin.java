package br.com.dwarflink.test;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.apache.commons.lang3.RandomStringUtils;

public class SystemLogin {

  private static final String APPLICATION_URL = "http://localhost:9000";

    //User infos
  private static final String USERNAME = "johnLocke";
  private static final String PSSW = "4515162342";
  private static final String INCORRECT_PSSW = "4515162342XX";

    //HTML elements id
  private static final String CONTROL_PANEL_ID = "btnControlPanel";
  private static final String START_ID = "btnStart";
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


  @Test
  public void signupUsuarioCorreto() {
    
    // abre firefox
    WebDriver driver = new FirefoxDriver();
    String randomComplement = "-" + RandomStringUtils.randomAscii(4);
    
    // O usuario acessa a homepage
    driver.get(APPLICATION_URL);

    //O usuario clica no botao 'Sign up' para se cadastrar
    WebElement botaoSignup = driver.findElement(By.id(BTN_SIGNUP));
    botaoSignup.click();

    //O usuário preenche o formulario de cadastro com username e senha
    WebElement name = driver.findElement(By.name(SIGN_UNAME));
    WebElement password = driver.findElement(By.name(SIGN_PSSW));
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT)); 

    //name.sendKeys(USERNAME + randomComplement);
    name.sendKeys(USERNAME + randomComplement);
    password.sendKeys(PSSW);

    //O usuario clica em submit
    btnSubmit.click();

    //O usuario e redirecionado para pagina principal e o cadastro deu certo
    boolean mainPageSubtileMsg = driver.getPageSource().contains(SUB_TITLE);

    assertTrue(mainPageSubtileMsg);
    driver.quit();
  }

  @Test
  public void signupUsuarioExistente() {

    // abre firefox
    WebDriver driver = new FirefoxDriver();
    String randomComplement = "-" + RandomStringUtils.randomAscii(4);
  
    // O usuario acessa a homepage
    driver.get(APPLICATION_URL);

    //O usuario clica no botao 'Sign up' para se cadastrar
    WebElement botaoSignup = driver.findElement(By.id(BTN_SIGNUP));
    botaoSignup.click();

    //O usuário preenche o formulario de cadastro com username e senha
    WebElement name = driver.findElement(By.name(SIGN_UNAME));
    WebElement password = driver.findElement(By.name(SIGN_PSSW));
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT)); 

    name.sendKeys(USERNAME);
    password.sendKeys(PSSW);

    //O usuario clica em submit
    btnSubmit.click();

    //O usuario e redirecionado pagina de erro e o cadastro deu errado
    boolean mainPageSubtileMsg = driver.getPageSource().contains(SIGNUP_ERROR);

    assertTrue(mainPageSubtileMsg);
    driver.quit();
  }

  @Test
  public void signinUsuarioCorreto() {
    
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

    //O usuario e redirecionado pagina do painel de controle e o login deu certo
    boolean controlPanelWelcomeMessage = driver.getPageSource().contains(CONTROL_PANEL_WELCOME_MSG);

    assertTrue(controlPanelWelcomeMessage);
    driver.quit();
  }

  @Test
  public void signinUsuarioCorretoIncorreto() {
    
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
    password.sendKeys(INCORRECT_PSSW);

    //O usuario clica em submit
    btnSubmit.click();

    //O usuario e redirecionado pagina de erro e o login não deu certo
    boolean controlPanelWelcomeMessage = driver.getPageSource().contains(SIGNIN_ERROR);

    assertTrue(controlPanelWelcomeMessage);
    driver.quit();
  }
}