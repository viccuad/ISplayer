package Test;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.contenedores.DirectorioContainer;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class testEscrituraXMLJUnit extends TestCase{
	
	private CancionContainer c1;
	
	public testEscrituraXMLJUnit(String name) {
		super(name);
		this.setUp();
	}
	
	public void setUp(){
		
		String path = "C:/hlocal/01 Purple Haze.mp3";
		BibliotecaContainer bib = new BibliotecaContainer();
		bib.addDir(new DirectorioContainer("C:/hlocal/"));
		c1 = new CancionContainer(path);		
		
	}
	
	public void testEscrituraXML(){
		
		String actual = "Prueba";
		String obtenida = "Prueba";
		assertEquals(actual, obtenida);
	}
	
	public static TestSuite suite(){
		TestSuite raiz=new TestSuite("escritura XML");
		
		TestSuite suite1=new TestSuite("Cancion vacia");
		
		TestSuite suite2=new TestSuite("Cancion");
		suite2.addTest(new testEscrituraXMLJUnit("testEscrituraXML"));
		
		raiz.addTest(suite1);
		raiz.addTest(suite2);
		return raiz;
		
	}
	
	/*public boolean assertEquals(CancionContainer c1, CancionContainer c2){
		return c1.equals(c2);
	}*/
	
}
