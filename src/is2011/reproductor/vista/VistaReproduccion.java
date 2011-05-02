package is2011.reproductor.vista;

import is2011.app.controlador.IAppController;
import is2011.app.preferencias.Preferencias;

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
 * Vista que muestra los datos de reproduccion.
 * El avance, el tiempo, el bitrate...
 * Por ahora solo funciona para MP3.
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class VistaReproduccion extends JPanel implements BasicPlayerListener  {

	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	
	/** Referencia al controlador de la aplicacion*/
	private IAppController controlador;
	
	/** Progreso*/
	private JScrollBar progreso;
	
	/** Muestra el estado de la reproduccion*/
	private JLabel labelEstado;
	
	/** 
	 * Variable que indica que estamos buscando una posicion en la barra de 
	 * progreso
	 */
	private boolean buscando;
	
	/** Posicion del scroll bar*/
	private int posicion;
	
	private JScrollBar volumen;
	
	//--------------------------------------------------------------------------
	//Atributos para conocer el tiempo de reproduccion.
	
	/**Los bytes totales de musica.*/
	private int bytesMusica;
	
	/** Posicion donde se inicia la musica*/
	private int byteInicioMusica;

	/** El bit rate medio kpbs*/
	private int bitrate;
	
	//--------------------------------------------------------------------------
	//Informacion de la cancion.
	
	/** String que contiene el formato... MP3, ogg...*/
	private String formato;
	
	/** El bitrate instantaneo*/
	private int framerate;
	
	/** La frecuencia con la que se ha sampleado la cancion*/
	private int sampleRate;
	
	/** Contiene el modo de audio... Estereo, mono...*/
	private String modoAudio;
	
	/**Tiempo total de reproduccion de esta cancion*/
	private String tiempoTotal;
	
	/** Tiempo actual de reproduccion de esta cancion*/
	private int tiempoActual;

	private boolean actualizar;
	// ********************************************************************** //
	// *************              CONSTRUCTOR                   ************* //
	// ********************************************************************** //
	
	/** 
	 * Constructor por defecto.
	 */
	public VistaReproduccion() {
		super();
		
		reset();
		
		this.progreso = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
		this.progreso.setEnabled(false);
		
		this.setLayout(new GridLayout(3,1));
		this.add(this.progreso);
		
		this.labelEstado = new JLabel("");
		this.add(this.labelEstado);
		
		this.volumen = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 100);
		this.volumen.addMouseListener(new MouseAdapter(){
			
			public void mouseReleased(MouseEvent e) {
				if(volumen.isEnabled()) {
					
					int altoReal = volumen.getHeight() - volumen.getWidth()*2;
					int yReal = e.getY() - volumen.getWidth();

					float porcentaje;
					if(yReal <= altoReal && yReal > 0) {
						porcentaje = ((float)yReal)/altoReal;
					}else {
						porcentaje = ((float)volumen.getValue())/100;
					}
					
					volumen.setValue((int)(100*porcentaje));
					/*
					System.out.println("putno" + e.getPoint());
					System.out.println("yReal" + yReal);
					System.out.println("altura" + altoReal);
					
					System.out.println(porcentaje);*/
					
					controlador.setVolumen((1-porcentaje));
				}
			}
		});	
		
		this.progreso.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(progreso.isEnabled()) {
					buscando = true;
				}
				
			}
			
			/*@Override
			public void mouseClicked(MouseEvent e) {
				System.out.print("Cliked");
				int anchoReal = progreso.getWidth() -1 - progreso.getHeight();
				int xReal = e.getX() - progreso.getHeight();
				float porcentaje = ((float)xReal)/anchoReal;
				buscando = false;
				controlador.irA(porcentaje);
			}*/
			
			public void mouseReleased(MouseEvent e) {
				if(progreso.isEnabled()) {
					buscando = true;

					int anchoReal = progreso.getWidth()  -progreso.getHeight()*2;
					int xReal = e.getX() - progreso.getHeight();

					float porcentaje;
					if(xReal <= anchoReal) {
						porcentaje = ((float)xReal)/anchoReal;
					}else {
						porcentaje = ((float)progreso.getValue())/1000;
					}

					buscando = false;
					controlador.irA(porcentaje);
				}
				/*if(buscando) {
					int posicionDeseada;

					synchronized (progreso) {
						posicionDeseada = progreso.getValue();
					}
					buscando = false;
					controlador.irA((float)posicionDeseada/1000);
				
				}*/
				
			}
		});
		
	}
	
	/**
	 * Pone todos los valores a 0
	 */
	public void reset() {
		this.buscando = false;
		this.actualizar = false;
		this.posicion = 0;
		this.bytesMusica = 0;
		this.byteInicioMusica = 0;
		this.bitrate = 0;
		this.formato = "";
		this.framerate = 0;
		this.sampleRate = 0;
		this.modoAudio = "";
		this.tiempoTotal = "";
		this.tiempoActual = 0;
	}
	
	// ********************************************************************** //
	// *************              MÉTODOS PRIVADOS              ************* //
	// ********************************************************************** //
	
	/**
	 * Recibe un numero de segundos y lo transforma a un string de HH:MM:SS
	 * @param segundos el numero de segundos.
	 * @return El estring con formato HH:MM:SS
	 */
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
	
	/**
	 * Recoge toda la informacion de la cancion que estamos reproduciendo.
	 * @param properties
	 */
	@SuppressWarnings("unchecked")
	private void getInfo(Map properties)
    {
		//System.out.println(properties.toString().replace(",", "\n"));
		if (properties != null)
        {	
			//Vemos en que formato esta el fichero.
			this.formato = 	(String) properties.get("audio.type");
			
			//Numero de bits por segundo.
            this.bitrate = ((Integer) properties.get("bitrate")).intValue();
			
            //La frecuencia de sampleado.
            this.sampleRate = (
            		(Float)properties.get("audio.samplerate.hz")).intValue();
			
            // El modo de audio.
            int canales = (
            		(Integer) properties.get("audio.channels")).intValue();
            this.modoAudio = canales == 2 ? "estereo" : "mono";
            
            //Calculamos el tamaño del fichero
        	int bytesArchivo = (
        			(Integer) properties.get("audio.length.bytes")).intValue();
            
            if(this.formato.equalsIgnoreCase("mp3")) {
            	//Calculamos en que byte se inicia la musica
            	this.byteInicioMusica    = (
            			(Integer) properties.get("mp3.header.pos")).intValue();
            } else if( this.formato.equalsIgnoreCase("ogg")) {
            	//Calculamos en que byte se inicia la musica
            	//TODO esto no va asi.
            	this.byteInicioMusica  = 1;	
            	this.framerate = this.bitrate;
            }
            
            
        	//Calculamos el numero de bytes dedicados a la musica
        	this.bytesMusica = bytesArchivo - byteInicioMusica;
            
        	//El tiempo que dura la cancion
        	this.tiempoTotal = toHora(Math.round(
        			( (float)bytesMusica / (bitrate/8))));
        }
    }
	
	/**
	 * Escribe la informacion de reproduccion.
	 */
	private void escribirInfo() {
		String info = "";
		
		info += this.formato +" ";
		info += this.framerate + "bps ";
		info += this.sampleRate + "hz ";
		info += " ♣♣ ";
		info += " sonido " + this.modoAudio + " ";
		info += this.toHora(this.tiempoActual) + "/" + tiempoTotal;
		
		this.labelEstado.setText(info);
	}
	
	// ********************************************************************** //
	// *************              MÉTODOS PÚBLICOS              ************* //
	// ********************************************************************** //
	
	/**
	 * Archivo abierto. 
	 * Recogemos toda la informacion del archivo y la mostramos.
	 */
	@SuppressWarnings("unchecked")
	public void opened(Object arg0, Map properties) {
		//Ponemos toda la informacion a la de por defecto.
		this.reset();
		
		//Recojemos la informacion que nos proporcionan las propiedades.
		this.getInfo(properties);
		
		this.actualizar = true;
		
		if (this.formato.equalsIgnoreCase("ogg")){
			this.progreso.setEnabled(false);
		} else {
			this.progreso.setEnabled(true);
		}
		
		
	}
	
	
	/**
	 * Se encarga de actualizar el tiempo.
	 * Para ello utiliza los bytes que se han leido y el bitrate.
	 * Para actualizar la barra de progreso, utiliza los bits totales y los que
	 * se han leido hasta el momento. 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void progress(int bytesread, long arg1, byte[] arg2, Map properties){
		if(this.actualizar) {
			//mp3.position.microseconds, mp3.equalizer, mp3.frame.size.bytes, 
			//mp3.frame, mp3.frame.bitrate, mp3.position.byte

			//System.out.println(properties.toString().replace(",", "\n"));
			
			//Calculamos la nueva posicion de la barra de desplazamiento
			int nuevaPosicion = Math.round(
					(((float)bytesread - byteInicioMusica)/ bytesMusica)*1000f);
			//Si la nueva posicion es diferente a la anterior.
			if(nuevaPosicion != posicion) {
				posicion = nuevaPosicion;
				//synchronized (this.progreso) {
					//Actualizamos la barra de progreso si no estamos buscando 
					//una posicion donde desplazar la cancion.
					if(!buscando) {
						this.progreso.setValue(posicion);
					}
				//}
			}

			//Calculamos el tiempo actual.
			int tiempo =  Math.round(( (float)bytesread - byteInicioMusica) / 
					(bitrate/8));

			//Si el tiempo actual es diferente, actualizamos la vista.
			if(tiempo != tiempoActual) {
				tiempoActual = tiempo;
				if(this.formato.equalsIgnoreCase("mp3")) {
					framerate = (Integer)properties.get("mp3.frame.bitrate");
				}else if (this.formato.equalsIgnoreCase("ogg")){
					
				}
				escribirInfo();
			}
		}
	}
	
	@Override
	public void setController(BasicController arg0) {
	

	}

	
	@Override
	public void stateUpdated(BasicPlayerEvent event) {
		
		//Si es el final de la cancion, Reseteamos los datos y los escribimos
		//por pantalla.
		//pasamos a la siguiente.
		if(event.getCode() == BasicPlayerEvent.EOM ) {
			this.reset();
			this.progreso.setEnabled(false);
			this.progreso.setValue(this.posicion);
			this.labelEstado.setText("");
			controlador.siguienteCancion();
		}else if ( event.getCode() == BasicPlayerEvent.STOP ) {
			this.reset();
			this.progreso.setEnabled(false);
			this.progreso.setValue(this.posicion);
			this.labelEstado.setText("");
		}else if(event.getCode() == BasicPlayerEvent.SEEKED) {
			this.progreso.setValue((int)(((float)event.getPosition()
					/bytesMusica)*1000));
		}else if (event.getCode() == BasicPlayerEvent.GAIN) {
			this.volumen.setValue( (int) ((1- Math.sqrt(event.getValue()))*100));
			Preferencias.getInstancia().setVolumen(Math.sqrt(event.getValue()));
		
		}
		
		//estado = event.getDescription().toString();
		//this.labelEstado.setText(estado + " " + tiempoTotal + "/" + 
		//bitPerSample + " kpbs");
	}
	
	
	/**
	 * Establece el controlador.
	 * @param contorlador El controlador
	 */
	public void setControlador(IAppController controlador) {
		this.controlador = controlador;
	}

	/**
	 * Devuelve el volumen
	 * @return El volumen
	 */
	public JScrollBar getVolumen() {
		return this.volumen;
	}
}
