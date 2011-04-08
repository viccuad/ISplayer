package is2011.reproductor.modelo;



import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.spi.PropertiesContainer;


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
	
	private int bytesMusica = 0;
	
	// ********************************************************************** //
	// *************              MÉTODOS PÚBLICOS              ************* //
	// ********************************************************************** //
	
	
	@Override
	public void fastForward(int velocidad) throws BasicPlayerException {
		int actualBytes = getEncodedStreamPosition();
		
		if (velocidad > 0) {
			 velocidad  = (velocidad > 4) ? 4 : velocidad ;
			 int bytesAdelantar = velocidad * BYTES_ADELANTAR;
			 if ((actualBytes + bytesAdelantar)< bytesMusica)
				 super.seek(actualBytes + bytesAdelantar);
		}
	}
	
	@Override
	public void rewind(int velocidad) throws BasicPlayerException {
		int actualBytes = getEncodedStreamPosition();
		
		if (velocidad > 0) {
			 velocidad  = (velocidad > 4) ? 4 : velocidad ;
			 int bytesAtrasar = velocidad * BYTES_ADELANTAR;
			 int posFinal = actualBytes - bytesAtrasar;
			 
			 if(posFinal > 0) {
				 
				 super.seek(actualBytes - bytesAtrasar);
			}
			 
		}
		
	}
	
	
	@Override
	public void irA(float porcentaje) throws BasicPlayerException {
		if (porcentaje >= 0 && porcentaje <= 1) {
			//System.out.println(m_audioFileFormat.properties().toString().replace(",", "\n"));
			super.seek((long)(bytesMusica*porcentaje));
			
			//TODO esto dara excepcion
		}
	}
	
	@Override
	public void setBytesMusica(int bytes) {
		this.bytesMusica = bytes;
	}	
}
