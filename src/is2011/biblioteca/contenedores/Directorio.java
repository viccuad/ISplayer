package is2011.biblioteca.contenedores;

import java.util.HashMap;

public class Directorio {
	
	private String path;
	private HashMap<String,Cancion> listaCanciones;
	
	public Directorio(String path, HashMap<String, Cancion> listaCanciones) {
		this.path = path;
		this.listaCanciones = listaCanciones;
	}

	public void addCancion(Cancion cancion){
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

	public HashMap<String, Cancion> getListaCanciones() {
		return listaCanciones;
	}

	public void setListaCanciones(HashMap<String, Cancion> listaCanciones) {
		this.listaCanciones = listaCanciones;
	}
	
	
	
}
