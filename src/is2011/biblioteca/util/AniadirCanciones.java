package is2011.biblioteca.util;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;

/**
 * Esta clase guarda canciones y sólo canciones en la biblioteca musical.
 * Si recibe directorios o cualquier extensión de fichero no válido no hace nada
 *
 */
public class AniadirCanciones extends EstrategiaActualizacionBiblioteca {

	/**
	 * 
	 * @param bib
	 */
	public AniadirCanciones(BibliotecaContainer bib){
		super(bib);
	}
	
	
	/**
	 * Actualiza las canciones que recibe como parámetro. Si estas ya existían las sobreescribe.
	 * En caso de recibir otras rutas de directorios o canciones con formato no válido no hace nada
	 */
	@Override
	public void actualiza(String filePath) {
		File fichero = new File(filePath);
		// si es un fichero y tiene un formato permitido
		if(fichero.isFile() && super.esFicheroValido(fichero)){     	
			try {
		    	AudioFile filemp3 = AudioFileIO.read(new File(fichero.getAbsolutePath()));
		    	Tag tag = filemp3.getTag();
		    	// creamos la canción
				CancionContainer nuevaCancion = new CancionContainer(fichero.getName(), super.getName(tag) , super.getAlbum(tag),
												super.getGenero(tag), super.getCompositor(tag),  
												filemp3.getAudioHeader().getTrackLength(), super.getPista(tag));
				nuevaCancion.setTotalPath(fichero.getAbsolutePath());
				
				// si el directorio no existe lo creamos
				if(!super.biblioteca.existeDirectorio(fichero.getParent()))
					super.biblioteca.addDir(fichero.getParent());
				
				// si la canción no existe previamente la insertamos en la biblioteca
				if(!super.biblioteca.existeCancion(fichero.getParent(), fichero.getName()))
					super.biblioteca.addCancion(nuevaCancion, fichero.getParent());
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
	    }
	}

}
