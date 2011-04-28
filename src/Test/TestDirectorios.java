package Test;

import is2011.utilidades.RecorreFicheros;
import is2011.utilidades.estrategias.MuestraNombres;

public class testDirectorios {

	
	public static void main(String args[]) {
		RecorreFicheros recorre = new RecorreFicheros("J://WOXTER//MUSICA");
		recorre.setEstrategia(new MuestraNombres());
		recorre.recorre();
	}
	
}
