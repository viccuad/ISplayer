package is2011.biblioteca.util;

import java.io.File;

import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import is2011.biblioteca.contenedores.BibliotecaContainer;


public abstract class EstrategiaActualizacionBiblioteca {

	/** extensiones de ficheros permitidos en el reproductor */
	protected final String[] tratarSoloFicheros = {".mp3"};
	
	/** Referencia al contenedor de biblioteca 
	 * para insertar canciones y directorios */
	protected BibliotecaContainer biblioteca;
	
	
	/**
	 * 
	 * @param bib
	 */
	public EstrategiaActualizacionBiblioteca(BibliotecaContainer bib){
		this.biblioteca = bib;
	}
	
	
	/**
	 * 
	 * @param filePath
	 */
	public abstract void actualiza(String filePath);
	
	
	/**
	 * 
	 * @param fichero
	 * @return
	 */
	protected boolean esFicheroValido(File fichero) {
		if(tratarSoloFicheros != null) 
			for( String formatoFichero : tratarSoloFicheros) 
				if(fichero.getName().toLowerCase().endsWith(formatoFichero))
					return true;
	
		return false;
	}
	
	
	/**
	 * 
	 * @param tag
	 * @return el titulo de la canción o "desconocido" si es null 
	 */
	protected String getName(Tag tag) {
		if(tag == null || tag.getFirstField(FieldKey.TITLE) == null)
			return "Desconocido";
		else 
			return tag.getFirst(FieldKey.TITLE);
	}
	
	
	/**
	 * 
	 * @param tag
	 * @return el album de la canción o "desconocido" si es null 
	 */
	public String getAlbum(Tag tag) {
		if (tag == null || tag.getFirstField(FieldKey.ALBUM) == null) 
			return "Desconocido";
		else return tag.getFirst(FieldKey.ALBUM);
	}
	
	
	/**
	 * 
	 * @param tag
	 * @return el artista de la canción o "desconocido" si es null 
	 */
	public String getCompositor(Tag tag) {
		if (tag == null || tag.getFirstField(FieldKey.ARTIST) == null) 
			return "Desconocido";
		else return tag.getFirst(FieldKey.ARTIST);

	}
	
	
	/**
	 * 
	 * @param tag
	 * @return el genero de la canción o "desconocido" si es null 
	 */
	public String getGenero(Tag tag) {
		if (tag == null || tag.getFirstField(FieldKey.GENRE) == null) 
			return "Desconocido";
		else return tag.getFirst(FieldKey.GENRE);

	}
	
	
	/**
	 * 
	 * @param tag
	 * @return el track de la canción o "desconocido" si es null 
	 */
	public int getPista(Tag tag) {
		if (tag == null || tag.getFirstField(FieldKey.TRACK) == null) 
			return 0;
		else return Integer.parseInt(tag.getFirst(FieldKey.TRACK));

	}
}
