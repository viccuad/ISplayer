package is2011.biblioteca.sorts;

import is2011.biblioteca.contenedores.CancionContainer;

import java.util.Comparator;

public class SortTitulo implements Comparator<CancionContainer> {

	@Override
	public int compare(CancionContainer c1, CancionContainer c2) {
		return c1.getTitulo().compareToIgnoreCase(c2.getTitulo());
	}

}
