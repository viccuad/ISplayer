package is2011.biblioteca.util;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import org.jaudiotagger.tag.Tag;


public class ActualizarBiblioteca extends EstrategiaActualizacionBiblioteca {
	
	/**
	 * 
	 * @param bib
	 */
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
				
				//TODO quitar esta linea si funciona el otro addCancion
				//super.biblioteca.addCancion(nuevaCancion);
				
				super.biblioteca.addCancion(nuevaCancion, fichero.getParent());
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
	    }
	}
	


	

}
