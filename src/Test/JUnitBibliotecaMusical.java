package Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import is2011.biblioteca.BibliotecaMusical;
import is2011.biblioteca.contenedores.CancionContainer;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JUnitBibliotecaMusical extends TestCase{

	private BibliotecaMusical bib;
	private CancionContainer c;
	
	
	public JUnitBibliotecaMusical(String name) {
		super(name);
		this.setUp();
	}
	
	public void setUp(){
		bib = BibliotecaMusical.getInstacia();
		c = null;
		
	}
	public void testBiblioteca(){ 
		
		try {
			//bib.guardarXML("src/Recursos/testXML2.xml");
			bib.cargarXML("src/Recursos/testXML2.xml");			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<CancionContainer> it = bib.getCanciones().iterator();
		while(it.hasNext()){
			c = it.next();				
		}
		String titulo = "Jimmy";
		String obtenida = c.getArtista();
		assertEquals(titulo, obtenida);
		
	}
	
	public static TestSuite suite(){
		TestSuite raiz=new TestSuite("Biblioteca Musical");
		
		TestSuite suite1=new TestSuite("Cancion vacia");
		
		TestSuite suite2=new TestSuite("Cancion");
		suite2.addTest(new JUnitBibliotecaMusical("testBiblioteca"));
		
		
		raiz.addTest(suite1);
		raiz.addTest(suite2);
		return raiz;		
	}
	
	public boolean assertEquals(CancionContainer c1, CancionContainer c2){
		return c1.equals(c2);
	}

	public boolean assertEquals(ArrayList<CancionContainer>  c1, ArrayList<CancionContainer>  c2){
		return c1.equals(c2);
	}


}
