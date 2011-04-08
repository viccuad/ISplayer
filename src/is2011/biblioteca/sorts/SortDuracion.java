package is2011.biblioteca.sorts;

import is2011.biblioteca.contenedores.Cancion;

import java.util.Comparator;

public class SortDuracion implements Comparator<Cancion> {

	@Override
	public int compare(Cancion c1, Cancion c2) {
		if(c1.getDuracion() > c2.getDuracion())
			return -1;
		else if(c1.getDuracion() < c2.getDuracion())
				return 1;
			else
					return 0;					
	}

}
