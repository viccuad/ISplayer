package Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.contenedores.DirectorioContainer;

public class TestDirectorioContainerJUnit extends TestCase{

	@SuppressWarnings("unused")
	private AudioFile filemp3 = null;
	private String pathprueba,pathprueba2;
	private DirectorioContainer dir, dir1cancion, dirvacio;
	private CancionContainer c1;
	private ArrayList<CancionContainer> lista, lista1;

	public TestDirectorioContainerJUnit(String name) {
		super(name);
		this.setUp();
	}
	
	
	
	
	
	public void setUp() {
		
		
		pathprueba =  "src/Recursos/01 Purple Haze.mp3";    // no cambiar!
		
		pathprueba2 = "";
		try {
			
			filemp3 = AudioFileIO.read(new File(pathprueba));
			c1 = new CancionContainer(pathprueba);
			lista = new ArrayList<CancionContainer>();
			for (int i = 0;  i < 5; i++){
				c1.setPista(i);
				lista.add(c1);
			}
		

		} catch (CannotReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DirectorioContainer dirvacio = new DirectorioContainer(pathprueba);
		DirectorioContainer dir = new DirectorioContainer(pathprueba,lista);	
			
		lista1 = new ArrayList<CancionContainer>();
		lista1.add(c1);
		DirectorioContainer dir1cancion = new DirectorioContainer(pathprueba,lista1);
		
		
	}
	
	
	public void testConstvaciaDirectorioContainer() {
		DirectorioContainer dirprueba = new DirectorioContainer(pathprueba);
		assertEquals(dirprueba, dirvacio);
		
	}
	
	public void testConstDirectorioContainer() {
		ArrayList<CancionContainer> listaaux = new ArrayList<CancionContainer>();
		listaaux = lista;
		DirectorioContainer dirprueba = new DirectorioContainer(pathprueba, listaaux);
		assertEquals(dirprueba, dir);
		
	}
	
	public void testAddCancion() {
		DirectorioContainer dir1 = new DirectorioContainer(pathprueba);
		dir1.addCancion(c1);
		assertEquals(dir1, dir1cancion);
	}
	
	public void testActualizarPathCanciones() {
		DirectorioContainer dir1cancionprueba = new DirectorioContainer(pathprueba, lista1);
		DirectorioContainer dirprueba = new DirectorioContainer(pathprueba);
		dirprueba.actualizarPathCanciones();
		CancionContainer c2 = new CancionContainer(pathprueba); 
		c2.setTrackPath(pathprueba + File.separator + c1.getTrackPath());
		dir1cancionprueba.getListaCanciones().add(c1);
		assertEquals(dirprueba, dir1cancion);
	}
	
	public void testExisteCancion() {
		DirectorioContainer dirprueba = new DirectorioContainer(pathprueba,lista1);
		assertTrue(dirprueba.existeCancion(c1.getTrackPath()) == true);
		
		
	}
	
	public void testGetListaCanciones(){
		//assertEquals(lista, dir.getListaCanciones() );
	}
	
	
	public void testGetPath() {
		/*String pathaux = pathprueba;
		String pathaux2 = dir.getPath();
		assertTrue(pathaux2.compareTo(pathaux) == 0);*/
		/*DirectorioContainer dirprueba = new DirectorioContainer(pathprueba2,lista);
		dirprueba.setPath(dir.getPath());
		assertEquals(dirprueba,dir);*/
	
	}
	
	public void testSetListaCanciones(){
		DirectorioContainer dirprueba = new DirectorioContainer(pathprueba);
		dirprueba.setListaCanciones(lista);
		assertEquals(dirprueba, dir);
	}
	
	
	public void testSetPath() {
		String pathaux = pathprueba;  
		DirectorioContainer dirprueba = new DirectorioContainer(pathprueba2);
		dirprueba.setPath(pathaux);
		assertEquals(dirprueba, dir);
	}
	
	
	public static TestSuite suite(){
		//TestSuite raiz=new TestSuite("raiz");
		
		TestSuite suite=new TestSuite("Directorio");
		suite.addTest(new TestDirectorioContainerJUnit("testConstvaciaDirectorioContainer"));
		suite.addTest(new TestDirectorioContainerJUnit("testConstDirectorioContainer"));
		suite.addTest(new TestDirectorioContainerJUnit("testActualizarPathCanciones"));
		suite.addTest(new TestDirectorioContainerJUnit("testAddCancion"));
		suite.addTest(new TestDirectorioContainerJUnit("testExisteCancion"));
		suite.addTest(new TestDirectorioContainerJUnit("testGetListaCanciones"));
		suite.addTest(new TestDirectorioContainerJUnit("testGetPath"));
		suite.addTest(new TestDirectorioContainerJUnit("testSetListaCanciones"));
		suite.addTest(new TestDirectorioContainerJUnit("testSetPath"));
		//raiz.addTest(suite);
		return suite;
		
	}
	
	public boolean assertEquals(DirectorioContainer d1, DirectorioContainer d2){
		return d1.equals(d2);
	}
	
	public boolean assertEquals(CancionContainer c1, CancionContainer c2){
		return c1.equals(c2);
	}
		
	public boolean assertEquals(ArrayList<CancionContainer>  c1, ArrayList<CancionContainer>  c2){

		if (c1.size() != c2.size()) return false;
		
		for ( int i = 0; i == c1.size(); i++){
			
			if ( c1.get(i).equals(c2.get(i)) != true) return false;				
		}

		return true;
	}
	
	

		
	
	
}
