package Test;

import java.util.ArrayList;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JUnitBibliotecaContainer extends TestCase{

	private BibliotecaContainer bib;
	private CancionContainer c1;
	
	private ArrayList<CancionContainer> listaCanciones;
	
	public JUnitBibliotecaContainer(String name) {
		super(name);
		this.setUp();
	}
	
	public void setUp(){
		
		String path = "C:/hlocal/01 Purple Haze.mp3";
		c1 = new CancionContainer(path);
	}
	
	public void testAddCancion(){
		
		listaCanciones.add(c1);
		bib.addCancion(c1);
		ArrayList<CancionContainer> obtenida = bib.getArrayListCanciones();
		assertEquals(listaCanciones, obtenida);
	}
	
	
	public static TestSuite suite(){
		TestSuite raiz=new TestSuite("raiz");
		
		TestSuite suite1=new TestSuite("Cancion vacia");
		
		TestSuite suite2=new TestSuite("Cancion");
		suite2.addTest(new JUnitBibliotecaContainer("testAddCancion"));
		
		raiz.addTest(suite1);
		raiz.addTest(suite2);
		return raiz;		
	}
	
	
}