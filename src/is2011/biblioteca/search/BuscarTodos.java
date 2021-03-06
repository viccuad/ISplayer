package is2011.biblioteca.search;

import is2011.biblioteca.contenedores.CancionContainer;

import java.util.ArrayList;
import java.util.Iterator;

public class BuscarTodos implements CriterioBusqueda {

	/** Es el elemento que se desea buscar */
	private String cadena;
	
	
	/**
	 * Crea el criterio de búsqueda con el elemento que se desea buscar
	 * @param elemento es lo que se desea buscar
	 */
	public BuscarTodos(String elemento){
		this.cadena = elemento;
	}
	
	/**
	 * Devuelve la cadena sobre la que se busca
	 */
	public String getCadena(){
		return cadena;
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
			if(actual.getTitulo().equalsIgnoreCase(cadena)
				||actual.getArtista().equalsIgnoreCase(cadena)
				||actual.getAlbum().equalsIgnoreCase(cadena)
				||actual.getGenero().equalsIgnoreCase(cadena))
				encontrados.add(actual);
		}
		
		return encontrados;
	}


	/**
	 * Realiza la búsqueda avanzada (coincidencias de cadenas) soble la colección que recible como parámetro.
	 * @param lista es la colección de elemenentos dónde se desea realizar la búsqueda
	 * @return una nueva colección con los elements que coinciden con el criterio de búsqueda
	 */
	@Override
	public ArrayList<CancionContainer> buscarAvanzado(ArrayList<CancionContainer> lista) {
		ArrayList<CancionContainer> encontrados = new ArrayList<CancionContainer>();
		Iterator<CancionContainer> it = lista.iterator();
		
		while(it.hasNext()){
			CancionContainer actual = it.next();
			if(actual.getTitulo() != null && ( 
					actual.getTitulo().toLowerCase().contains(cadena.toLowerCase())
					||actual.getArtista().toLowerCase().contains(cadena.toLowerCase())
					||actual.getAlbum().toLowerCase().contains(cadena.toLowerCase())
					||actual.getGenero().toLowerCase().contains(cadena.toLowerCase())))
				encontrados.add(actual);
		}
		
		return encontrados;
	}

}
