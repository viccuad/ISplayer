package is2011.biblioteca.util;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import org.jaudiotagger.tag.Tag;

/**
 * Esta clase añade canciones y directorios a la biblioteca musical.
 * Los directorios sólo se añaden si tienen canciones dentro y las canciones se añaden siempre
 * creando el directorio en el que se encuentran si no existía previamente
 *
 */
public class CrearBiblioteca extends EstrategiaActualizacionBiblioteca {
	
	/**
	 * 
	 * @param bib
	 */
	public CrearBiblioteca(BibliotecaContainer bib){
		super(bib);
	}
	

	/**
	 * Actualiza los directorios y las canciones. 
	 * Si estos ya existían los sobreescribe.
	 */
	@Override
	public void actualiza(String filePath) {
		File fichero = new File(filePath);
		if(fichero.isDirectory()){
			//borramos el directorio de la biblioteca. 
			//En caso de que no exista no pasa nada
			super.biblioteca.removeDir(fichero.getAbsolutePath());
			
			String[] subFicheros = fichero.list(); 
			for( String nombre : subFicheros)
				actualiza(fichero.getAbsolutePath()+ File.separator +nombre);
			
	    } else if(super.esFicheroValido(fichero)){ 
			//añadimos la cancion a la biblioteca	    	
			try {
		    	AudioFile filemp3 = 
		    		AudioFileIO.read(new File(fichero.getAbsolutePath()));
		    	Tag tag = filemp3.getTag();
				CancionContainer nuevaCancion = 
					new CancionContainer(fichero.getName(), super.getName(tag) , 
							super.getAlbum(tag),super.getGenero(tag), 
							super.getCompositor(tag),  
							filemp3.getAudioHeader().getTrackLength(), 
							super.getPista(tag));
				nuevaCancion.setTotalPath(fichero.getAbsolutePath());
				
				// si el directorio no existe lo creamos
				if(!super.biblioteca.existeDirectorio(fichero.getParent()))
					super.biblioteca.addDir(fichero.getParent());
				
				// si la canción no existe previamente la 
				// insertamos en la biblioteca
				if(!super.biblioteca.existeCancion(fichero.getParent(), 
						fichero.getName()))
					super.biblioteca.
					addCancion(nuevaCancion, fichero.getParent());
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
	    }
	}
	
}
