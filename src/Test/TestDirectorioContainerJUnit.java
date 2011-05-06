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
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.contenedores.DirectorioContainer;

public class TestDirectorioContainerJUnit extends TestCase{

	private AudioFile filemp3=null;
	//private MP3AudioHeader headermp3=null;
	private String path;
	//private Tag tag=null;
	private DirectorioContainer dirvacio;
	private DirectorioContainer dir;
	private CancionContainer c1;
	private ArrayList<CancionContainer> lista;

	public TestDirectorioContainerJUnit(String name) {
		super(name);
		this.setUp();
	}
	
	
	
	
	
	public void setUp() {
		DirectorioContainer dirvacio = new DirectorioContainer("path");
		
		path = "src/Recursos/01 Purple Haze.mp3";    
		
		try {
			filemp3 = AudioFileIO.read(new File(path));
			c1 = new CancionContainer(path);
			for (int i = 0;  i < 5; i++){
				c1.setPista(i);
				lista.add(c1);
			}
			
			DirectorioContainer dir = new DirectorioContainer("pathprueba",lista);
			
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
	
	public void TestConstructoraDirectorioContainer() {
		DirectorioContainer dir1 = new DirectorioContainer("pathprueba");
		dir1.setListaCanciones(dir.getListaCanciones());
		assertEquals(dir1, dir);
		
	}
	
	
}
