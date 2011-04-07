/**
 * 
 */
package is2011.reproductor.vista;

import java.awt.event.MouseAdapter;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.plaf.ProgressBarUI;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 * @author Administrator
 *
 */
public class VistaReproduccion extends JPanel implements BasicPlayerListener  {

	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	
	/**Tiempo total de reproduccion de esta cancion*/
	private long tiempoTotal;
	
	/** Tiempo actual de reproduccion de esta cancion*/
	private int tiempoActual;
	
	/** Estado actual del reproductor*/
	private String estado;
	
	/** Progreso*/
	private JScrollBar progreso;
	
	/** KBps*/
	private int bitPerSample;
	
	/** Muestra el estado de la reproduccion*/
	private JLabel labelEstado;
	
	/** Posicion del scroll bar*/
	private int posicion;
	
	private int bytesTotales;

	// ********************************************************************** //
	// *************              CONSTRUCTOR                   ************* //
	// ********************************************************************** //
	public VistaReproduccion() {
		super();
		
		this.tiempoTotal = 0;
		this.tiempoActual = 0;
		this.estado = "";
		this.bitPerSample = 0;
		
		this.progreso = new JScrollBar(JScrollBar.HORIZONTAL, 0, 50, 0, 1000);
		this.add(this.progreso);
		
		this.labelEstado = new JLabel("");
		this.add(this.labelEstado);
	}
	
	
	// ********************************************************************** //
	// *************              MÉTODOS PÚBLICOS              ************* //
	// ********************************************************************** //
	/**
	 * Archivo abierto. Mostramos el tiempo total de la cancion, asi como el bitrate.
	 */
	public void opened(Object arg0, Map properties) {
		System.out.println("Archivo abierto");
		tiempoTotal = this.getTimeLengthEstimation(properties);
		System.out.println("Tiempo total estimado: " + tiempoTotal);
		
		posicion = 0;
		this.progreso.setValue(posicion);
		if (properties.containsKey("audio.samplesize.bits"))
        {
			bitPerSample = ((Integer) properties.get("audio.samplesize.bits")).intValue();
        }
		this.labelEstado.setText(tiempoTotal + "/" + bitPerSample + " kpbs");
	}

	/* (non-Javadoc)
	 * @see javazoom.jlgui.basicplayer.BasicPlayerListener#progress(int, long, byte[], java.util.Map)
	 */
	@Override
	public void progress(int bytesread, long arg1, byte[] arg2, Map properties) {
		
		int audioFramesize = (Integer)properties.get("mp3.frame.size.bytes");
		int audioFramerate = (Integer)properties.get("mp3.frame.bitrate");
		long mp3Frame = (Long)properties.get("mp3.frame");
		long mp3PositionByte = (Long)properties.get("mp3.position.byte");
		
		int nuevaPosicion = (int)(((double)bytesread / (double) bytesTotales)*1000.0f); 
		if(nuevaPosicion != posicion) {
			posicion = nuevaPosicion;
			this.progreso.setValue(posicion);
		}
		
		
		int tiempo = (int)( bytesread/ (audioFramerate/8));
		//int tiempo = (int) this.getActualTimeEstimated((long)bytesread, properties);
		if(tiempo != tiempoActual) {
			/*System.out.println("BytesRead" + bytesread);
			System.out.println("audioFramerate " + audioFramerate);
			System.out.println("audioFramesize " + audioFramesize);
			System.out.println("mp3Frame " + mp3Frame);
			System.out.println("mp3PositionByte " + mp3PositionByte);
			System.out.println("************************************\n");*/
			tiempoActual = tiempo;
			escribirInfo();
		}

	}
	
	private void escribirInfo() {
		//System.out.println("Progreso... " + tiempoActual + "/" + (tiempoTotal/1000)); 
		this.labelEstado.setText(toHora(tiempoActual) + " " + toHora((int)tiempoTotal) + "/" + bitPerSample + " kpbs");
	}

	private String toHora(int segundos) {
		int horas;
		int minutos;
		
		horas = segundos/ 3600;
		segundos -= horas*3600;
		
		minutos = segundos / 60;
		segundos -= minutos*60;
		
		
		return "" + ((horas > 0)? horas : "")+ ":" + ((minutos>9)? minutos : "0"
			+minutos)+ ":" + ((segundos > 9)? segundos : "0"+segundos);
	}
	@Override
	public void setController(BasicController arg0) {
	

	}

	
	@Override
	public void stateUpdated(BasicPlayerEvent event) {
		estado = event.getDescription().toString();
		this.labelEstado.setText(estado + " " + tiempoTotal + "/" + bitPerSample + " kpbs");
	}
	
	@SuppressWarnings("unchecked")
	private long getTimeLengthEstimation(Map properties)
    {
        long milliseconds = -1;
        int byteslength = -1;
        if (properties != null)
        {
            if (properties.containsKey("audio.length.bytes"))
            {
                byteslength = ((Integer) properties.get("audio.length.bytes")).intValue();
            }
            if (properties.containsKey("duration"))
            {
                milliseconds = (int) (((Long) properties.get("duration")).longValue()) / 1000;
            }
            else
            {
                // Try to compute duration
                int bitspersample = -1;
                int channels = -1;
                float samplerate = -1.0f;
                int framesize = -1;
                if (properties.containsKey("audio.samplesize.bits"))
                {
                    bitspersample = ((Integer) properties.get("audio.samplesize.bits")).intValue();
                }
                if (properties.containsKey("audio.channels"))
                {
                    channels = ((Integer) properties.get("audio.channels")).intValue();
                }
                if (properties.containsKey("audio.samplerate.hz"))
                {
                    samplerate = ((Float) properties.get("audio.samplerate.hz")).floatValue();
                }
                if (properties.containsKey("audio.framesize.bytes"))
                {
                    framesize = ((Integer) properties.get("audio.framesize.bytes")).intValue();
                }
                if (bitspersample > 0)
                {
                	//bytesPorSegundo = (int) (samplerate * channels * (bitspersample / 8));
                    milliseconds = (int) (1000.0f * byteslength / (samplerate * channels * (bitspersample / 8)));
                }
                else
                {
                	//bytesPorSegundo =  (int) (samplerate * framesize);
                    milliseconds = (int) (1000.0f * byteslength / (samplerate * framesize));
                }
            }
        }
        return milliseconds;
    }
	
	
	public void addListenerProgreso(MouseAdapter listener) {
		this.progreso.addMouseListener(listener);
	}


	/**
	 * @return
	 */
	public int getPos() {
		return posicion;
	}
	
	
}
