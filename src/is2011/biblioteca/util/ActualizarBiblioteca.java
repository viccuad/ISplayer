package is2011.biblioteca.util;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class ActualizarBiblioteca extends EstrategiaActualizacionBiblioteca {
	
	
	public ActualizarBiblioteca(BibliotecaContainer bib){
		super(bib);
	}
	

	/**
	 * Actualiza los directorios. Si estos ya existían los sobreescribe
	 */
	@Override
	public void actualiza(String filePath) {
		File fichero = new File(filePath);
		if(fichero.isDirectory()){
			//creamos el directorio en la biblioteca. Si ya existia se sobreescribe
			super.biblioteca.addDir(fichero.getAbsolutePath());
			String[] subFicheros = fichero.list();
			for( String nombre : subFicheros)
				actualiza(fichero.getAbsolutePath()+"//"+nombre);
	    } else if(super.esFicheroValido(fichero)){ 
			//añadimos la cancion a la biblioteca	    	
			try {
		    	AudioFile filemp3 = AudioFileIO.read(new File(fichero.getAbsolutePath()));
		    	Tag tag = filemp3.getTag();
				CancionContainer nuevaCancion = new CancionContainer(fichero.getName(), super.getName(tag) , super.getAlbum(tag),
												super.getGenero(tag), super.getCompositor(tag),  filemp3.getAudioHeader().getTrackLength());
				nuevaCancion.setTotalPath(fichero.getAbsolutePath());
				super.biblioteca.addCancion(nuevaCancion);
			} catch (Exception e) {
				e.printStackTrace();
			} 
	    }
	}
	


	

}
