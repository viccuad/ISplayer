package is2011.reproductor.modelo;



import is2011.app.preferencias.Preferencias;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
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
	private final int BYTES_ADELANTAR = 64000;
	
	/** Bytes de musica */
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
			 if ((actualBytes + bytesAdelantar)< bytesMusica) {
				 this.buscar(actualBytes + bytesAdelantar);
			 }else {
				 notifyEvent(BasicPlayerEvent.EOM, getEncodedStreamPosition(), 
						 -1, null);
			}
		}
	}
	
	@Override
	public void rewind(int velocidad) throws BasicPlayerException {
		int actualBytes = getEncodedStreamPosition();
		
		if (velocidad > 0) {
			 velocidad  = (velocidad > 4) ? 4 : velocidad ;
			 int bytesAtrasar = velocidad *BYTES_ADELANTAR;
			 int posFinal = actualBytes - bytesAtrasar;
			 
			 if(posFinal > 0) {
				 this.buscar(actualBytes - bytesAtrasar);
			}else {
				this.buscar(0);
			}
			this.setVolumen(Preferencias.getInstancia().getVolumen());
			 
		}
		
	}
	
	
	@Override
	public void irA(float porcentaje) throws BasicPlayerException {
		if (porcentaje >= 0 && porcentaje <= 1) {
			//System.out.println(m_audioFileFormat.properties().toString().replace(",", "\n"));
			this.buscar((long)(bytesMusica*porcentaje));
		}
	}
	
	

	@Override
	public void reiniciar(boolean borrarOyentes) {
		super.reset();
	}

	
	@Override
	public void open(Cancion c) throws BasicPlayerException {
		this.bytesMusica = c.getBytesMusica();
		super.open(new File(c.getPath()));
	}

	@Override 
	public void stop() {
		notifyEvent(BasicPlayerEvent.STOP, getEncodedStreamPosition(), -1, null);
		try {
			super.stop();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isPaused() {
		if(m_status == PAUSED) {
			return true;
		}
		return false;
	}

	
	@Override
	public void setVolumen(float porcentaje) {
		try {
			setGain(porcentaje*porcentaje);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void buscar(long b) {
		try {
			super.seek(b);
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		this.setVolumen(Preferencias.getInstancia().getVolumen());
	}
	
}
