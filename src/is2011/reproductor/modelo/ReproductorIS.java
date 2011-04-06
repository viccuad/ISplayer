/**
 * 
 */
package is2011.reproductor.modelo;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 * 
 * 
 * @author Administrator
 *
 */
public class ReproductorIS extends BasicPlayer implements Reproductor {

	
	private final int BYTES_ADELANTAR = 500;
	
	/**
	 * @param velocidad Admite los valores 1 , 2 , 3 ,4. Mayor a 4 se tomara como cuatro.
	 */
	@Override
	public void fastForward(int velocidad) throws BasicPlayerException {
		int actualBytes = getEncodedStreamPosition();
		
		if (velocidad > 0) {
			 velocidad  = (velocidad > 4) ? 4 : velocidad ;
			 int bytesAdelantar = velocidad * BYTES_ADELANTAR;
			 
			 super.seek(actualBytes + bytesAdelantar);
		}
	}
	
	@Override
	public void rewind(int velocidad) throws BasicPlayerException {
		int actualBytes = getEncodedStreamPosition();
		
		if (velocidad > 0) {
			 velocidad  = (velocidad > 4) ? 4 : velocidad ;
			 int bytesAtrasar = velocidad * BYTES_ADELANTAR;
			 
			 super.seek(actualBytes - bytesAtrasar);
		}
		
	}
	
	
	@Override
	public void irA(float porcentaje) throws BasicPlayerException {
		if ((int)porcentaje > 0 && (int)porcentaje < 1) {
			super.seek((long)(super.encodedLength*porcentaje));
		}
	}

}
