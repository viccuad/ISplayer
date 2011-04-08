/**
 * 
 */
package is2011.reproductor.vista;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;


import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JScrollBar;




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
	private int framerate;
	
	private float framesPorSegundo;
	
	private int framesTotales;
	
	/** Muestra el estado de la reproduccion*/
	private JLabel labelEstado;
	
	//TODO debug
	private JLabel ultimoClick;
	
	/** Posicion del scroll bar*/
	private int posicion;
	
	private int posicionDeseada;
	
	private int bytesMusica;
	
	private boolean buscando;

	private int bytesArchivo;

	private int byteInicioMusica;

	private int bitrate;

	// ********************************************************************** //
	// *************              CONSTRUCTOR                   ************* //
	// ********************************************************************** //
	public VistaReproduccion() {
		super();
		
		this.tiempoTotal = 0;
		this.tiempoActual = 0;
		this.estado = "";
		this.framerate = 0;
		
		this.progreso = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10, 0, 1000);
	
		this.setLayout(new GridLayout(3,1));
		this.add(this.progreso);
		
		this.labelEstado = new JLabel("");
		this.add(this.labelEstado);
		
		this.ultimoClick = new JLabel();
		this.add(this.ultimoClick);
		
		this.progreso.addMouseListener(new MouseAdapter() {
			
			
			@Override
			public void mousePressed(MouseEvent e) {
				buscando = true;
				ultimoClick.setText("Buscando...");
			}
			
			public void mouseReleased(MouseEvent arg0) {
				synchronized (progreso) {
					posicionDeseada = progreso.getValue();
					ultimoClick.setText("Posicion deseada -> " + posicionDeseada + "/1000");
					buscando = false;
				}
			}
		});
		
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
			framerate = ((Integer) properties.get("audio.samplesize.bits")).intValue();
			
        }
		this.labelEstado.setText(tiempoTotal + "/" + framerate + " kpbs");
		
		
		
	}

	/* (non-Javadoc)
	 * @see javazoom.jlgui.basicplayer.BasicPlayerListener#progress(int, long, byte[], java.util.Map)
	 */
	@Override
	public void progress(int bytesread, long arg1, byte[] arg2, Map properties) {
		//mp3.position.microseconds, mp3.equalizer, mp3.frame.size.bytes, 
		//mp3.frame, mp3.frame.bitrate, mp3.position.byte
		
		int audioFramesize = (Integer)properties.get("mp3.frame.size.bytes");
		framerate = (Integer)properties.get("mp3.frame.bitrate");
		long mp3Frame = (Long)properties.get("mp3.frame");
		long mp3PositionByte = (Long)properties.get("mp3.position.byte");
		
		int nuevaPosicion = Math.round((((float)bytesread - byteInicioMusica) / bytesMusica)*1000f);
		if(nuevaPosicion != posicion) {
			posicion = nuevaPosicion;
			synchronized (this.progreso) {
				if(!buscando) {
					this.progreso.setValue(posicion);
				}
			}
		}
		
		int tiempo =  Math.round(( (float)bytesread - byteInicioMusica) / (bitrate/8));
		if(tiempo != tiempoActual) {
			System.out.println("TamañoArchivo " + this.bytesArchivo);
			System.out.println("Tamaño Musica " + this.bytesMusica);
			System.out.println("Bytes leidos del archivo" + bytesread);
			System.out.println("Bytes leidos de musica" + (bytesread-byteInicioMusica));
			
			System.out.println("audioFramerate " + framerate);
			System.out.println("audioFramesize " + audioFramesize);
			System.out.println("mp3Frame " + mp3Frame);
			System.out.println("mp3PositionByte " + mp3PositionByte);
			System.out.println("************************************\n");
			tiempoActual = tiempo;
			escribirInfo();
		}

	}
	
	private void escribirInfo() {
		//System.out.println("Progreso... " + tiempoActual + "/" + (tiempoTotal/1000)); 
		this.labelEstado.setText(toHora(tiempoActual) + "/" + toHora((int)tiempoTotal) + "   " + framerate + " kpbs");
	}

	private String toHora(int segundos) {
		int horas;
		int minutos;
		
		horas = segundos/ 3600;
		segundos -= horas*3600;
		
		minutos = segundos / 60;
		segundos -= minutos*60;
		
		
		return "" + ((horas > 0)? horas+":" : "") + ((minutos>9)? minutos : "0"
			+minutos)+ ":" + ((segundos > 9)? segundos : "0"+segundos);
	}
	@Override
	public void setController(BasicController arg0) {
	

	}

	
	@Override
	public void stateUpdated(BasicPlayerEvent event) {
		
		//estado = event.getDescription().toString();
		//this.labelEstado.setText(estado + " " + tiempoTotal + "/" + bitPerSample + " kpbs");
	}
	
	@SuppressWarnings("unchecked")
	private long getTimeLengthEstimation(Map properties)
    // keys del map.
	//[mp3.id3tag.track, mp3.crc, mp3.copyright, album, audio.length.frames, 
	//mp3.channels, mp3.version.mpeg, date, mp3.id3tag.genre, 
	//mp3.framesize.bytes, author, title, mp3.version.layer, mp3.length.frames,
	//mp3.vbr.scale, mp3.bitrate.nominal.bps, mp3.version.encoding, audio.type, 
	//audio.length.bytes, vbr, mp3.padding, audio.framerate.fps, mp3.length.bytes, 
	//audio.channels, mp3.framerate.fps, duration, mp3.header.pos, mp3.frequency.hz,
	//basicplayer.sourcedataline, bitrate, mp3.mode, comment, mp3.vbr, 
	//audio.samplerate.hz, mp3.original
	{
		int tiempo = -1;
		System.out.println(properties.toString().replace(",", "\n"));
        if (properties != null)
        {	
            //Calculamos el tamaño del fichero
        	bytesArchivo = ((Integer) properties.get("mp3.length.bytes")).intValue();
            
        	//Calculamos en que byte se inicia la musica
        	byteInicioMusica    = ((Integer) properties.get("mp3.header.pos")).intValue();
            
        	//Calculamos el numero de bytes dedicados a la musica
        	bytesMusica = bytesArchivo - byteInicioMusica;
            
        	//Numero de bits por segundo.
            bitrate = ((Integer) properties.get("mp3.bitrate.nominal.bps")).intValue();
            
            tiempo = Math.round(( (float)bytesMusica / (bitrate/8)));
        }
        
        if (tiempo == -1) {
        	//System.out.println("");
        }
        return tiempo;
    }
	
	
	public void addListenerProgreso(MouseAdapter listener) {
		this.progreso.addMouseListener(listener);
	}


	
	/**
	 * 
	 * @return La posicion donde debemos colocar el reproductor.
	 */
	public int getPos() {
		return posicionDeseada;
	}

}
