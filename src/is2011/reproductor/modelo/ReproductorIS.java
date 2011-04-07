package is2011.reproductor.modelo;



import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;


/**
 * Clase que hereda de basic player y añade la funcionalidad necesaria para
 * implementar la interfaz de reproductor.
 * Este reproductor reproduce canciones MP3.
 * 
 * @author Administrator
 *
 */
public class ReproductorIS extends BasicPlayer implements Reproductor {

	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	/** Los bits que se adelantaran o atrasaran en los metodos ff y rewind*/
	private final int BYTES_ADELANTAR = 16000;
	
	
	
	// ********************************************************************** //
	// *************              MÉTODOS PÚBLICOS              ************* //
	// ********************************************************************** //
	
	
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
		if (porcentaje >= 0 && porcentaje <= 1) {
			super.seek((long)(super.encodedLength*porcentaje));
		}
	}


}
