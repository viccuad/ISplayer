package Test;

import is2011.reproductor.modelo.CancionMP3;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class testCancionMP3JUnit extends TestCase{
	
	private AudioFile filemp3=null;
	private MP3AudioHeader headermp3=null;
	private String path;
	private Tag tag=null;
	private CancionMP3 c1;
	private CancionMP3 c2;

	public testCancionMP3JUnit(String name) {
		super(name);
		this.setUp();
	}
	
	public void setUp() {
		
		path = "C:/hlocal/01 Purple Haze.mp3";
		try {
			filemp3 = AudioFileIO.read(new File(path));

			tag = filemp3.getTag();
			headermp3 = (MP3AudioHeader) filemp3.getAudioHeader();
			
			c1 = new CancionMP3(path);
			c2 = new CancionMP3(path);
			
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
		String obtenida = c1.getName();
		assertEquals(name, obtenida);
	}
	
	public void testGetAlbum(){
		String album = "Experience Hendrix";
		String obtenida = c1.getAlbum();
		assertEquals(album, obtenida);
	}
	
	public void testGetCompositor(){
		String compositor = "Jimmy Hendrix";
		String obtenida = c1.getCompositor();
		assertEquals(compositor, obtenida);
	}
	
	public void testGetGenero(){
		String genero = "Rock ";
		String obtenida = c1.getGenero();
		assertEquals(genero, obtenida);
	}
	
	public void testGetPista(){
		String pista = "1";
		String obtenida = c1.getPista();
		assertEquals(pista, obtenida);
	}
	
	
	public static TestSuite suite(){
		TestSuite raiz=new TestSuite("raíz");
		
		TestSuite suite1=new TestSuite("Cancion vacia");
		
		TestSuite suite2=new TestSuite("Cancion");
		suite2.addTest(new testCancionMP3JUnit("testGetName"));
		suite2.addTest(new testCancionMP3JUnit("testGetAlbum"));
		suite2.addTest(new testCancionMP3JUnit("testGetCompositor"));
		suite2.addTest(new testCancionMP3JUnit("testGetGenero"));
		suite2.addTest(new testCancionMP3JUnit("testGetPista"));
		
		raiz.addTest(suite1);
		raiz.addTest(suite2);
		return raiz;
		
	}
	
	public boolean assertEquals(CancionMP3 c1, CancionMP3 c2){
		return c1.equals(c2);
	}
	
	
}
