package is2011.biblioteca.util;

import is2011.biblioteca.contenedores.BibliotecaContainer;


public abstract class EstrategiaActualizacionBiblioteca {

	protected String[] tratarSoloFicheros = {"mp3", "ogg"};
	
	protected BibliotecaContainer biblioteca;
	
	
	public EstrategiaActualizacionBiblioteca(BibliotecaContainer bib){
		this.biblioteca = bib;
	}
	
	public abstract void actualiza(String filePath);
	
	
	
	/*
	public void trataFichero(File fichero);
	public void trataDirectorio(File directorio);
	*/
}
