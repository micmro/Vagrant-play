package br.com.dwarflink.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.lang3.RandomStringUtils;

public class AcessLink {
  private static final String APPLICATION_URL = "http://localhost:9000";

  //User infos
  private static final String USERNAME = "johnLocke";
  private static final String PSSW = "4515162342";

  //HTML elements id
  private static final String BTN_SIGNIN = "btnSignin";
  private static final String W_USERNAME = "username";
  private static final String CONTROL_PANEL_ID = "btnControlPanel";

  private static final String BTN_START = "btnStart";
  private static final String BTN_SIGNUP = "btnSignup";
  private static final String BTN_LOGOUT = "btnLogout";

  //Sign form
  private static final String SIGN_UNAME = "username";
  private static final String SIGN_PSSW = "password";
  private static final String BTN_SUBMIT = "submitLink";

  //Control Panel Page
  private static final String BTN_SHORT_LINK = "shorten";
  private static final String BTN_SECURE_LINK = "secure";
  private static final String BTN_LIST_ALL = "btnListAll";

  //General link
  private static final String ORIGINAL_LINK_BOX = "originalLink";
  private static final String RESULT_LINK = "resultLink";

  //Secure Link
  private static final String SECURE_LINK_BOX = "secureSlug";
  private static final String SECURE_LINK_KEY_BOX = "goldenShovel";
  private static final String SECURE_SLUG = "foobar";
  private static final String SECURE_LINK_KEY = "gnulinux";

  //Original links
  private static final String LINK_1 = "http://www.ime.usp.br/";
  private static final String LINK_2 = "http://www.uol.com.br/";
  private static final String LINK_3 = "www.globo.com.br";

  public static WebDriver signinUsuarioCorreto() {
    
    // abre firefox
    WebDriver driver = new FirefoxDriver();
    driver.manage().window().maximize();
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

  public WebDriver requisitaLinkEncurtado(String link) {
    WebDriver driver = signinUsuarioCorreto();

    WebElement linkBox = driver.findElement(By.id(BTN_SHORT_LINK));
    linkBox.click();

    WebElement originalLinkBox = driver.findElement(By.name(ORIGINAL_LINK_BOX));
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));

    originalLinkBox.sendKeys(link);
    btnSubmit.click();

    return driver;
  }

  public WebDriver requisitaLinkSeguro(String link, String secureKey) {
    WebDriver driver = signinUsuarioCorreto();
    String randomComplement = "-" + RandomStringUtils.randomAscii(4);

    WebElement linkBox = driver.findElement(By.id(BTN_SECURE_LINK));
    linkBox.click();

    WebElement originalLink = driver.findElement(By.name(ORIGINAL_LINK_BOX));
    WebElement secureSlug = driver.findElement(By.name(SECURE_LINK_BOX));
    WebElement secureLinkKey = driver.findElement(By.name(SECURE_LINK_KEY_BOX));
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));

    originalLink.sendKeys(link);
    secureSlug.sendKeys(SECURE_SLUG + randomComplement);
    secureLinkKey.sendKeys(secureKey);
    btnSubmit.click();

    return driver;
  }

  @Test
  public void acessaUmLinkEncurtado() {
    //Cria um link encurtado simples, com o usuario logado
    WebDriver driver = requisitaLinkEncurtado(LINK_1);

    //Acessa o link encurtado
    WebElement resultLink = driver.findElement(By.id(RESULT_LINK));
    String shortenedLink = resultLink.getText();

    driver.get(shortenedLink);

    //Compara o resultado do link encurtado com o link original e da sucesso caso for o mesmo
    String url = driver.getCurrentUrl();
    
    assertEquals(LINK_1, url);
    driver.quit();
  }

  @Test
  public void acessaUmLinkProtegido() {
    //Cria um link encurtado seguro, com o usuario logado
    WebDriver driver = requisitaLinkSeguro(LINK_2, SECURE_LINK_KEY);
    
    //Acessa o link encurtado
    WebElement resultLink = driver.findElement(By.id(RESULT_LINK));
    String shortenedLink = resultLink.getText();

    driver.get(shortenedLink);

    //Fornece o nome do criado e senha do link
    WebElement creatorName = driver.findElement(By.name(W_USERNAME));
    WebElement secureLinkKey = driver.findElement(By.name(SECURE_LINK_KEY_BOX));
    //Submete o formulario
    WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));
    
    creatorName.sendKeys(USERNAME);
    secureLinkKey.sendKeys(SECURE_LINK_KEY);
    btnSubmit.click();

    //Compara o resultado do link encurtado com o link original e da sucesso caso for o mesmo
    String url = driver.getCurrentUrl();
    
    assertEquals(LINK_2, url);
    driver.quit();
  }

  @Test
  public void encurtaLinkEncontraNaListagem() throws InterruptedException {
    //Cria um link encurtado seguro, com o usuario logado
    WebDriver driver = requisitaLinkEncurtado(LINK_3);

    //Salva o link encurtado
    WebElement resultLink = driver.findElement(By.id(RESULT_LINK));
    String shortenedLink = resultLink.getText();
    
    //Acessa o painel de controle
    driver.findElement(By.id(CONTROL_PANEL_ID)).click();
    //Acessa a listagem de links encurtados
    driver.findElement(By.id(BTN_LIST_ALL)).click();

    //Procura o link encurtado e o original na listagem, caso ache é sucesso
    boolean hasOriginalLink = driver.getPageSource().contains(LINK_2);
    boolean hasResultLink = driver.getPageSource().contains(shortenedLink);

    assertTrue(hasOriginalLink);
    assertTrue(hasResultLink);
    
    driver.quit();  
  }
}
