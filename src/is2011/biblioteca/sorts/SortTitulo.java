package is2011.biblioteca.sorts;

import is2011.biblioteca.contenedores.Cancion;

import java.util.Comparator;

public class SortTitulo implements Comparator<Cancion> {

	@Override
	public int compare(Cancion c1, Cancion c2) {
		return c1.getTitulo().compareToIgnoreCase(c2.getTitulo());
	}

}
