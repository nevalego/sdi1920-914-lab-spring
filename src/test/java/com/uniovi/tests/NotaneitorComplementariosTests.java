package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

// Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class NotaneitorComplementariosTests {
	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)): //static String PathFirefox65 = "C:\\Program Files\\Mozilla
	// Firefox\\firefox.exe";
	// static String Geckdriver024 = "C:\\Path\\geckodriver024win64.exe"; //En
	// MACOSX (Debe ser la versión 65.0.1
	// y desactivar las actualizacioens automáticas):
	static String PathFirefox65 = "/Archivos de programa/Mozilla Firefox/firefox.exe";
	static String Geckdriver024 = "/Users/nerea/Documents/2 SEMESTRE/SDI/5. Web testing con Selenium/PL-SDI-Sesión5-material/PL-SDI-Sesión5-material/geckodriver024win64.exe";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() throws Exception {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {

	}

	// Logearse como administrador
	// Prueba1 Registro de profesores con datos válidos
	@Test
	public void Prueba1() {

		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "/login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999988F", "123456");
		// COmprobamos que entramos en la pagina privada del administrador
		PO_View.checkElement(driver, "text", "Notas del usuario");

		// Pinchamos en la opción de menu de Profesores: //li[contains(@id,
		// 'professors-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'professors-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de añadir profesor
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'professor/add')]");
		elementos.get(0).click();
		// Rellenamos el formulario
		PO_PrivateView.fillFormAddProfessor(driver, "11111111R", "Pepin", "Mandina Suarez", "Senior");

		// Esperamos a que se muestren los enlaces de paginación la lista de profesores
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Nos vamos a la última página
		elementos.get(elementos.size() - 1).click();
		// Comprobamos que ina
		elementos = PO_View.checkElement(driver, "text", "11111111R");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "/logout", "text", "Identifícate");

	}

	// Prueba2 Registro de profesores con datos inválidos
	@Test
	public void Prueba2() {

		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "/login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999988F", "123456");
		// COmprobamos que entramos en la pagina privada del administrador
		PO_View.checkElement(driver, "text", "Notas del usuario");

		// Pinchamos en la opción de menu de Profesores: //li[contains(@id,
		// 'professors-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'professors-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de añadir profesor
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'professor/add')]");
		elementos.get(0).click();

		// Rellenamos el formulario.
		PO_PrivateView.fillFormAddProfessor(driver, "0899308", "Josefo", "Perez", "Senior");
		PO_View.getP();
		// COmprobamos el error de Nombre corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Comprobamos el error de categoria
		PO_RegisterView.checkKey(driver, "Error.register.cathegory", PO_Properties.getSPANISH());

	}

	// Prueba3
	@Test
	public void Prueba3() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "/login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999988F", "123456");
		// COmprobamos que entramos en la pagina privada del administrador
		PO_View.checkElement(driver, "text", "Notas del usuario");

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'professors-menu')]/a");
		elementos.get(0).click();
		assertTrue(elementos.size() == 2);
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "/logout", "text", "Identifícate");

		// Vamos al formulario de logueo de nueevo
		PO_HomeView.clickOption(driver, "/login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999993D", "123456");
		// COmprobamos que entramos en la pagina privada del profesor
		PO_View.checkElement(driver, "text", "Notas del usuario");

		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'professors-menu')]/a");
		elementos.get(0).click();
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "/logout", "text", "Identifícate");

		// Vamos al formulario de logueo de nueevo
		PO_HomeView.clickOption(driver, "/login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123456");
		// COmprobamos que entramos en la pagina privada del alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");

		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'professors-menu')]/a");
		elementos.get(0).click();
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "/logout", "text", "Identifícate");

	}

	// Prueba4 Ejercicio3 -> Listado de profesores
	@Test
	public void Prueba4() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "/login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999988F", "123456");
		// COmprobamos que entramos en la pagina privada del administrador
		PO_View.checkElement(driver, "text", "Notas del usuario");

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'professors-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de listar profesores
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/professor/list')]");
		elementos.get(0).click();
		
		int size=0;
		for(WebElement elem : elementos) {
			List<WebElement> elems = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
					PO_View.getTimeout());
			size += elems.size();
		}
		assertTrue(size == 4);
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}
}