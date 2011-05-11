package Test;

import is2011.biblioteca.contenedores.CancionContainer;
import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

import org.jaudiotagger.tag.TagException;

public class testCancionMP3JUnit extends TestCase{
	
	@SuppressWarnings("unused")
	private AudioFile filemp3=null;
	//private MP3AudioHeader headermp3=null;
	private String path;
	//private Tag tag=null;
	private CancionContainer c1;
	//private CancionContainer c2;

	public testCancionMP3JUnit(String name) {
		super(name);
		this.setUp();
	}
	
	public void setUp() {
		
		
		path = "src/Recursos/01 Purple Haze.mp3";    
		try {
			filemp3 = AudioFileIO.read(new File(path));

			//tag = filemp3.getTag();
			//headermp3 = (MP3AudioHeader) filemp3.getAudioHeader();
			
			c1 = new CancionContainer(path);
			
			//c2 = new CancionContainer(path);
			
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
	
	public void testGetName(){
		String name = "Purple Haze";
		String obtenida = c1.getTitulo();
		assertEquals(name, obtenida);
	}
	
	public void testGetAlbum(){
		String album = "Experience Hendrix";
		String obtenida = c1.getAlbum();
		assertEquals(album, obtenida);
	}
	
	public void testGetArtista(){
		String artista = "Jimmy Hendrix";
		String obtenida = c1.getArtista();		
		assertEquals(artista, obtenida);
	}
	
	public void testGetGenero(){
		String genero = "Rock ";
		String obtenida = c1.getGenero();
		assertEquals(genero, obtenida);
	}
	
	public void testGetPista(){
		int pista = 1;
		int obtenida = c1.getPista();
		assertEquals(pista, obtenida);
	}
	
	public void testSetName(){
		String name = "PURPLE HAZE";
		c1.setTitulo(name);
		assertEquals(name, c1.getTitulo());
	}
	
	public void testSetAlbum(){
		String album = "EXPERIENCE HENDRIX";
		c1.setAlbum(album);
		assertEquals(album, c1.getAlbum());
	}
	
	public void testSetArtista(){
		String artista = "JIMMY HENDRIX";
		c1.setArtista(artista);
		assertEquals(artista, c1.getArtista());
	}
	
	public void testSetGenero(){
		String genero = "ROCK";
		c1.setGenero(genero);
		assertEquals(genero, c1.getGenero());
	}
	
	public void testSetPista(){
		int pista = 10;
		c1.setPista(pista);
		assertEquals(pista, c1.getPista());
	}
	
	public static TestSuite suite(){
		//TestSuite raiz=new TestSuite("raiz");
		
		//TestSuite suite1=new TestSuite("Cancion vacia");
		
		TestSuite suite2=new TestSuite("Cancion");
		suite2.addTest(new testCancionMP3JUnit("testGetName"));
		suite2.addTest(new testCancionMP3JUnit("testGetAlbum"));
		suite2.addTest(new testCancionMP3JUnit("testGetArtista"));
		suite2.addTest(new testCancionMP3JUnit("testGetGenero"));
		suite2.addTest(new testCancionMP3JUnit("testGetPista"));
		suite2.addTest(new testCancionMP3JUnit("testSetName"));
		suite2.addTest(new testCancionMP3JUnit("testSetAlbum"));
		suite2.addTest(new testCancionMP3JUnit("testSetArtista"));
		suite2.addTest(new testCancionMP3JUnit("testSetGenero"));
		suite2.addTest(new testCancionMP3JUnit("testSetPista"));
		
		//raiz.addTest(suite1);
		//raiz.addTest(suite2);
		return suite2;
		
	}
	
	public boolean assertEquals(CancionContainer c1, CancionContainer c2){
		return c1.equals(c2);
	}
	
	
}
