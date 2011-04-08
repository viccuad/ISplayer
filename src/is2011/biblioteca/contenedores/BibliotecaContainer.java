package is2011.biblioteca.contenedores;

import java.util.HashMap;


public class BibliotecaContainer {

	private HashMap<String, Directorio> directorios;
	
	
	public BibliotecaContainer(){
		this.directorios = new HashMap<String, Directorio>();
	}
	
	
	public void addDir(Directorio dir){
		// si el directorio existia, se reemplaza
		this.directorios.put(dir.getPath(), dir);
		
	}
	
	
	
	

	
	
	
}
