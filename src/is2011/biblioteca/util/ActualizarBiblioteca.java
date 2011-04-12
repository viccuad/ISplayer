package is2011.biblioteca.util;

import is2011.biblioteca.contenedores.BibliotecaContainer;

import java.io.File;

public class ActualizarBiblioteca extends EstrategiaActualizacionBiblioteca {
	
	
	public ActualizarBiblioteca(BibliotecaContainer bib){
		super(bib);
	}
	

	
	@Override
	public void actualiza(String filePath) {
		File fichero = new File(filePath);
		//Si es directorio
		if(fichero.isDirectory()){
			String[] s = fichero.list();
			for( String nombre : s)
				actualiza(nombre);
	    } else if(esFicheroValido(fichero)){ 
				//TODO: se añade la cancion
	    }
	    
		
	}

	private boolean esFicheroValido(File fichero) {
		if(super.tratarSoloFicheros != null) 
			for( String formatoFichero : super.tratarSoloFicheros) 
				if(fichero.getName().contains(formatoFichero))
					return true;
	
		return false;
	}

}
