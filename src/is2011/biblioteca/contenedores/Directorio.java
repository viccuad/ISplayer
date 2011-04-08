package is2011.biblioteca.contenedores;

import java.util.HashMap;

public class Directorio {
	
	private String path;
	private HashMap<String,CancionContainer> listaCanciones;
	
	public Directorio(String path, HashMap<String, CancionContainer> listaCanciones) {
		this.path = path;
		this.listaCanciones = listaCanciones;
	}

	public void addCancion(CancionContainer cancion){
		// no pueden existir dos canciones con el mismo trackPath (nombre de fichero .mp3)
		// se sobreescribe de todas formas
		this.listaCanciones.put(cancion.getTrackPath(), cancion);
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HashMap<String, CancionContainer> getListaCanciones() {
		return listaCanciones;
	}

	public void setListaCanciones(HashMap<String, CancionContainer> listaCanciones) {
		this.listaCanciones = listaCanciones;
	}
	
	
	
}
