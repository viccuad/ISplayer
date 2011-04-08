package is2011.biblioteca.sorts;

import is2011.biblioteca.contenedores.CancionContainer;

import java.util.Comparator;

public class SortDuracion implements Comparator<CancionContainer> {

	@Override
	public int compare(CancionContainer c1, CancionContainer c2) {
		if(c1.getDuracion() > c2.getDuracion())
			return -1;
		else if(c1.getDuracion() < c2.getDuracion())
				return 1;
			else
					return 0;					
	}

}
