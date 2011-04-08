package is2011.biblioteca.sorts;

import is2011.biblioteca.contenedores.Cancion;

import java.util.Comparator;

public class SortAlbum implements Comparator<Cancion> {

	@Override
	public int compare(Cancion c1, Cancion c2) {
		return c1.getAlbum().compareToIgnoreCase(c2.getAlbum());
	}

}
