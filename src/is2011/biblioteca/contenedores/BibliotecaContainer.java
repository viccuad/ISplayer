package is2011.biblioteca.contenedores;

import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamOmitField;




public class BibliotecaContainer {
	
	@XStreamOmitField
	private boolean modificado;
	
	private HashMap<String, DirectorioContainer> directorios;
	
	
	public BibliotecaContainer(){
		this.modificado = false;
		this.directorios = new HashMap<String, DirectorioContainer>();
	}
	
	
	public void addDir(DirectorioContainer dir){
		// si el directorio existia, se reemplaza
		this.directorios.put(dir.getPath(), dir);
		this.modificado = true;
		
	}

	
	public boolean isModificado() {
		return modificado;
	}
	
	
	
	
	

	
	
	
}
