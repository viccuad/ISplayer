package is2011.biblioteca.util;

import java.io.File;

import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import is2011.biblioteca.contenedores.BibliotecaContainer;


public abstract class EstrategiaActualizacionBiblioteca {

	protected String[] tratarSoloFicheros = {"mp3", "ogg"};
	
	protected BibliotecaContainer biblioteca;
	
	
	public EstrategiaActualizacionBiblioteca(BibliotecaContainer bib){
		this.biblioteca = bib;
	}
	
	public abstract void actualiza(String filePath);
	
	
	protected boolean esFicheroValido(File fichero) {
		if(tratarSoloFicheros != null) 
			for( String formatoFichero : tratarSoloFicheros) 
				if(fichero.getName().contains(formatoFichero))
					return true;
	
		return false;
	}
	
	
	
	protected String getName(Tag tag) {
		if (tag.getFirstField(FieldKey.TITLE) == null) return "Desconocido";
		else return tag.getFirst(FieldKey.TITLE);
		
	}
	
	
	public String getAlbum(Tag tag) {
		if (tag.getFirstField(FieldKey.ALBUM) == null) return "Desconocido";
		else return tag.getFirst(FieldKey.ALBUM);
	}
	
	
	public String getCompositor(Tag tag) {
		if (tag.getFirstField(FieldKey.COMPOSER) == null) return "Desconocido";
		else return tag.getFirst(FieldKey.COMPOSER);

	}
	
	
	public String getGenero(Tag tag) {
		if (tag.getFirstField(FieldKey.GENRE) == null) return "Desconocido";
		else return tag.getFirst(FieldKey.GENRE);

	}
	
	
	/*
	public void trataFichero(File fichero);
	public void trataDirectorio(File directorio);
	*/
}
