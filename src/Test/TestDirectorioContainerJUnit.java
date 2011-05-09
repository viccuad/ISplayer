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

	private AudioFile filemp3=null;
	//private MP3AudioHeader headermp3=null;
	private String path,pathprueba;
	//private Tag tag=null;
	private DirectorioContainer dir, dir1cancion, dirvacio;
	private CancionContainer c1;
	private ArrayList<CancionContainer> lista, lista1;

	public TestDirectorioContainerJUnit(String name) {
		super(name);
		this.setUp();
	}
	
	
	
	
	
	public void setUp() {
		DirectorioContainer dirvacio = new DirectorioContainer("path");
		path = "src/Recursos/01 Purple Haze.mp3";    
		pathprueba ="prueba";
		try {
			lista = new ArrayList<CancionContainer>();
			filemp3 = AudioFileIO.read(new File(path));
			c1 = new CancionContainer(path);
			for (int i = 0;  i < 5; i++){
				c1.setPista(i);
				lista.add(c1);
			}
		
		DirectorioContainer dir = new DirectorioContainer(pathprueba,lista);
		dirvacio.setListaCanciones(lista);		
			
		lista1 = new ArrayList<CancionContainer>();
		lista1.add(c1);
		DirectorioContainer dir1cancion = new DirectorioContainer("path",lista1);
		
		
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
		
	}
	
	
	public void testConstvaciaDirectorioContainer() {
		DirectorioContainer dirprueba = new DirectorioContainer("pathprueba");
		dirprueba.setListaCanciones(dirvacio.getListaCanciones());
		assertEquals(dirprueba, dirvacio);
		
	}
	
	public void testConstDirectorioContainer() {
		DirectorioContainer dirprueba = new DirectorioContainer("pathprueba", dirvacio.getListaCanciones());
		assertEquals(dirprueba, dirvacio);
		
	}
	
	public void testAddCancion() {
		DirectorioContainer dir1 = new DirectorioContainer("pathprueba");
		dir1.addCancion(c1);
		assertEquals(dir1, dir1cancion);
	}
	
	public void testActualizarPathCanciones() {
		dir1cancion.actualizarPathCanciones();
		DirectorioContainer dirprueba = new DirectorioContainer("pathprueba");
		c1.setTrackPath(pathprueba + File.separator + c1.getTrackPath());
		dirprueba.getListaCanciones().add(c1);
		assertEquals(dirprueba, dir1cancion);
	}
	
	public void testExisteCancion() {
		DirectorioContainer dirprueba = new DirectorioContainer("pathprueba");
		dirprueba.getListaCanciones().add(c1);
		assertTrue(dirprueba.existeCancion(c1.getTitulo()));
		
		
	}
	
	public void testGetListaCanciones(){
		ArrayList<CancionContainer> listaprueba = new ArrayList<CancionContainer>();
		listaprueba.add(c1);
		assertEquals(listaprueba, dir1cancion.getListaCanciones());
	}
	
	
	public void testGetPath() {
		String pathaux = "prueba";
		assertEquals(pathaux, dir.getPath());
	}
	
	public void testGetListaCanciones(ArrayList<CancionContainer> listaCanciones) {
		DirectorioContainer dirprueba = new DirectorioContainer("pathprueba");
		dirprueba.setListaCanciones(lista);
		assertEquals(dirprueba.getListaCanciones(), lista);
	}
	
	
	public static TestSuite suite(){
		TestSuite raiz=new TestSuite("raiz");
		
		TestSuite suite=new TestSuite("Directorio");
		suite.addTest(new TestDirectorioContainerJUnit("testConstvaciaDirectorioContainer"));
		suite.addTest(new TestDirectorioContainerJUnit("testConstDirectorioContainer"));
		suite.addTest(new TestDirectorioContainerJUnit("testAddCancion"));
		suite.addTest(new TestDirectorioContainerJUnit("testActualizarPathCanciones"));
		suite.addTest(new TestDirectorioContainerJUnit("testExisteCancion"));
		suite.addTest(new TestDirectorioContainerJUnit("testGetListaCanciones"));
		suite.addTest(new TestDirectorioContainerJUnit("testGetPath"));
		raiz.addTest(suite);
		return raiz;
		
	}
	
	public boolean assertEquals(DirectorioContainer d1, DirectorioContainer d2){
		return d1.equals(d2);
	}
	
}
