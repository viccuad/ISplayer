package is2011.biblioteca.search;

import is2011.biblioteca.contenedores.CancionContainer;

import java.util.ArrayList;
import java.util.Iterator;

public class BuscarArtista implements CriterioBusqueda {

	/** Es el elemento que se desea buscar */
	private String artista;
	
	
	/**
	 * Crea el criterio de búsqueda con el elemento que se desea buscar
	 * 
	 * @param elemento es lo que se desea buscar
	 */
	public BuscarArtista(String elemento){
		this.artista = elemento;
	}
	/**
	 * Devuelve la cadena sobre la que se busca
	 */
	public String getCadena(){
		return artista;
	}
	
	/**
	 * Realiza la búsqueda soble la colección que recible como parámetro
	 * 
	 * @param lista colección de elemenentos dónde se desea realizar la búsqueda
	 * 
	 * @return nueva colección con los elements que coinciden con el criterio 
	 */
	@Override
	public ArrayList<CancionContainer>buscar(ArrayList<CancionContainer>lista) {
		ArrayList<CancionContainer> encontrados = 
			new ArrayList<CancionContainer>();
		Iterator<CancionContainer> it = lista.iterator();
		
		while(it.hasNext()){
			CancionContainer actual = it.next();
			if(actual.getArtista().equalsIgnoreCase(artista))
				encontrados.add(actual);
		}
		
		return encontrados;
	}


	/**
	 * Realiza la búsqueda avanzada (coincidencias de cadenas) soble la 
	 * colección que recible como parámetro.
	 * 
	 * @param lista colección de elemenentos dónde se desea realizar la búsqueda
	 * 
	 * @return nueva colección con los elements que coinciden con el criterio 
	 */
	@Override
	public ArrayList<CancionContainer> buscarAvanzado(ArrayList<CancionContainer> 
	lista) {
		ArrayList<CancionContainer> encontrados = 
			new ArrayList<CancionContainer>();
		Iterator<CancionContainer> it = lista.iterator();
		
		while(it.hasNext()){
			CancionContainer actual = it.next();
			if(actual.getArtista().
					toLowerCase().contains(artista.toLowerCase()))
				encontrados.add(actual);
		}
		
		return encontrados;
	}

}
