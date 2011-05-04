package is2011.biblioteca.search;

import is2011.biblioteca.contenedores.CancionContainer;

import java.util.ArrayList;
import java.util.Iterator;

public class BuscarArtista implements CriterioBusqueda {

	/** Es el elemento que se desea buscar */
	private String artista;
	
	
	/**
	 * Crea el criterio de búsqueda con el elemento que se desea buscar
	 * @param elemento es lo que se desea buscar
	 */
	public BuscarArtista(String elemento){
		this.artista = elemento;
	}
	
	
	/**
	 * Realiza la búsqueda soble la colección que recible como parámetro
	 * @param lista es la colección de elemenentos dónde se desea realizar la búsqueda
	 * @return una nueva colección con los elements que coinciden con el criterio de búsqueda
	 */
	@Override
	public ArrayList<CancionContainer> buscar(ArrayList<CancionContainer> lista) {
		ArrayList<CancionContainer> encontrados = new ArrayList<CancionContainer>();
		Iterator<CancionContainer> it = lista.iterator();
		
		while(it.hasNext()){
			CancionContainer actual = it.next();
			if(actual.getArtista().equalsIgnoreCase(artista))
				encontrados.add(actual);
		}
		
		return encontrados;
	}

}
