package is2011.utilidades.estrategias;


import java.io.File;


public class MuestraNombres implements EstrategiaFichero {

	@Override
	public void trataFichero(File fichero) {
		
		System.out.println("Llegamos al fichero (ruta absoluta) " 
				+ fichero.getAbsolutePath());
		System.out.println("Nombre " + fichero.getName());
	}

	/*
	@Override
	public void trataDirectorio(File directorio) {
		System.out.println("Investigando el directorio " + 
		directorio.getAbsolutePath());
	}
	*/

}
