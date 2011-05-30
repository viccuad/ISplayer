package is2011.reproductor.vista;

import is2011.app.controlador.IAppController;
import is2011.app.vista.VistaPrincipal;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;
import is2011.reproductor.modelo.listeners.BorrarCancionEvent;
import is2011.reproductor.modelo.listeners.ListaReproduccionListener;
import is2011.reproductor.modelo.listeners.NuevaCancionEvent;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 * Vista encargada de la reproduccion.
 * Mostrara todos lo botones relevantes para reproducir una cancion (play, puase
 * , stop, adelante, atras... ) asi como la barra de progreso.
 * 
 * Sera oyente del BasicPlayer, lo que le permitira estar actualizadose 
 * constantemente.
 *
 *
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class VistaReproduccion extends JPanel implements BasicPlayerListener,
ListaReproduccionListener{

	/**
	 * 
	 */
	private static final String BIG_PNG = "/Recursos/m_big.png";

	/**
	 * 
	 */
	private static final String SMALL_PNG = "/Recursos/m_small.png";

	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //

	/** Referencia al controlador de la aplicacion*/
	private IAppController controlador;

	/** Progreso*/
	private JSlider progreso;

	/** Muestra el estado de la reproduccion*/
	private JLabel labelEstado;

	/** Variable que indica que estamos buscando una posicion en el progreso */
	private boolean buscando;

	/** Posicion del scroll bar*/
	private int posicion;

	/** Slider del volumen */
	private JSlider volumen;

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
	private int tiempoTotal;

	/** Tiempo actual de reproduccion de esta cancion*/
	private int tiempoActual;

	/** Indica si se debe actualizar o no la vista*/
	private boolean actualizar;

	//--------------------------------------------------------------------------
	//Botones de la interfaz.

	private VistaPrincipal vPrincipal;
	//Botones de la reproduccion
	private JLabel playPause;
	private JLabel stop;
	private JLabel siguiente;
	private JLabel anterior;
	private JLabel mute;
	private JLabel repetir;
	private JLabel aleatorio;


	private OyenteEtiquetas oyenteEtqAleatorio;
	private OyenteEtiquetas oyenteEtqRepetir;

	private JLabel botonVistaCompacta;

	/** Oyenete encargado de cambias las etiquetas*/
	private OyenteEtiquetas oyenteEtqPlayPause;
	private OyentePlayPause oyenteAccionPlayPause;

	/** Paneles contenedores de las diferentes partes de la vista*/
	private JPanel panelBotones;
	private JPanel panelProg;
	private JLabel tiempoTranscurrido;
	private JLabel tiempoRestante;


	// Rutas constantes.

	private static final String SHUFFLE_PNG = "/Recursos/shuffle.png";
	private static final String REPEAT_PNG = "/Recursos/repeat.png";
	private static final String SHUFFLES_PNG = "/Recursos/shuffles.png";
	private static final String REPEATS_PNG = "/Recursos/repeats.png";
	private static final String VOL_PNG = "/Recursos/sound.png";
	private static final String VOL_MUTE_PNG = "/Recursos/mute.png";
	private static final String NEXT_SONG_PNG = "/Recursos/m_next.png";
	private static final String PLAY_PNG = "/Recursos/m_play.png";
	private static final String PAUSE_PNG = "/Recursos/m_pause.png";
	private static final String PRE_SONG_PNG = "/Recursos/m_previous.png";
	private static final String STOP_PNG = "/Recursos/m_stop.png";


	// ********************************************************************** //
	// *************              CONSTRUCTOR                   ************* //
	// ********************************************************************** //


	/** 
	 * Constructor por defecto.
	 */
	public VistaReproduccion(VistaPrincipal vp) {
		super();
		reset();
		this.vPrincipal = vp;
		//Establecemos un boxLayout.
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		//Añadimos la vista referente al progreso
		this.panelProg = new JPanel();
		this.panelProg.setLayout(new BorderLayout());

		//Labels que indican el tiempo restante y actual de la reproduccion.
		this.tiempoTranscurrido = new JLabel();
		this.tiempoRestante = new JLabel();
		this.resetLabelTiempos();

		//Añadimos las etiquetas del tiempo.
		panelProg.add(tiempoTranscurrido, BorderLayout.WEST);
		panelProg.add(tiempoRestante, BorderLayout.EAST);
		this.resetProgreso();

		this.panelProg.add(this.progreso, BorderLayout.CENTER);

		//Fianalmente añadimos el panel del progreso a la vista.
		this.add(this.panelProg);

		// Panel inferior (Info reproduccion, botones, boton de agrandar).
		// Creamos un panel auxiliar que contendra toda la informacion
		// citada anteriormente.
		JPanel panelAux = new JPanel();
		panelAux.setLayout(new BoxLayout(panelAux,BoxLayout.X_AXIS ));

		// Informacion.
		this.labelEstado = new JLabel();
		panelAux.add(this.labelEstado);
		labelEstado.setAlignmentX(LEFT_ALIGNMENT);

		//Ponemos un espacio
		panelAux.add(Box.createHorizontalGlue());

		//Añadimos el panel de los botones
		panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());
		panelBotones.setAlignmentX(CENTER_ALIGNMENT);
		//Iniciamos el panel de los botones
		this.initPanelBotones();

		panelAux.add(panelBotones);


		//Ponemos un espacio
		panelAux.add(Box.createHorizontalGlue());

		this.botonVistaCompacta = new JLabel();
		//botonVistaCompacta.setAlignmentX(RIGHT_ALIGNMENT);
		botonVistaCompacta.setAlignmentY(Component.TOP_ALIGNMENT);
		botonVistaCompacta.setIcon((new ImageIcon(getClass().getResource(
				SMALL_PNG))));
		panelAux.add(botonVistaCompacta);

		botonVistaCompacta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				vPrincipal.vistaCompacta();
			}
		});

		this.add(panelAux);
	}


	/**
	 * Inicia el panel de los botones con las acciones que debe realizar.
	 */
	private void initPanelBotones() {
		JLabel labelAux = new JLabel();
		labelAux.setPreferredSize(new Dimension(50,0));
		panelBotones.add(labelAux);

		repetir = new JLabel();
		repetir.setBorder(BorderFactory.createEmptyBorder());
		repetir.setIcon(new ImageIcon(getClass().getResource(REPEAT_PNG)));
		oyenteEtqRepetir = new OyenteEtiquetas(REPEAT_PNG, repetir);
		repetir.addMouseListener(this.oyenteEtqRepetir);
		panelBotones.add(repetir);

		aleatorio = new JLabel();
		aleatorio.setBorder(BorderFactory.createEmptyBorder());
		aleatorio.setIcon(new ImageIcon(getClass().getResource(SHUFFLE_PNG)));
		oyenteEtqAleatorio = new OyenteEtiquetas(SHUFFLE_PNG, aleatorio);
		aleatorio.addMouseListener(this.oyenteEtqAleatorio);
		panelBotones.add(aleatorio);

		stop  = new JLabel();
		stop.setBorder(BorderFactory.createEmptyBorder());
		stop.setIcon(new ImageIcon(getClass().getResource(STOP_PNG)));
		panelBotones.add(stop);
		stop.addMouseListener(new OyenteEtiquetas(STOP_PNG, stop));

		anterior  = new JLabel();
		anterior.setBorder(BorderFactory.createEmptyBorder());
		anterior.setIcon(new ImageIcon(getClass().getResource(PRE_SONG_PNG)));  
		anterior.addMouseListener(new OyenteEtiquetas(PRE_SONG_PNG , anterior));
		panelBotones.add(anterior);


		playPause = new JLabel();
		playPause.setBorder(BorderFactory.createEmptyBorder());
		playPause.setIcon(new ImageIcon(getClass().getResource(PLAY_PNG)));
		panelBotones.add(playPause);

		siguiente  = new JLabel();
		siguiente.setBorder(BorderFactory.createEmptyBorder());
		siguiente.setIcon(new ImageIcon(getClass().getResource(NEXT_SONG_PNG)));
		panelBotones.add(siguiente);
		siguiente.addMouseListener(new OyenteEtiquetas(
				NEXT_SONG_PNG , siguiente));

		mute  = new JLabel();
		mute.setBorder(BorderFactory.createEmptyBorder());
		mute.setIcon(new ImageIcon(getClass().getResource(VOL_PNG)));
		panelBotones.add(mute);


		// Creamos el volumen. Le ponemos un oyente para que se mueva correcta
		// mente al hacer click.
		this.volumen = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		this.volumen.setPreferredSize(new Dimension(100,20));
		this.volumen.setEnabled(false);
		this.panelBotones.add(this.volumen);
		this.volumen.removeMouseListener(volumen.getMouseListeners()[0]);
		this.volumen.addMouseListener(new MouseAdapter(){

			public void mouseReleased(MouseEvent e) {
				if(volumen.isEnabled()) {
					int ancho = volumen.getWidth();

					int x = e.getX();
					float porcentaje;

					if( x < 0) {
						x = 0;
					}

					if(x > ancho) {
						x = ancho;
					}
					porcentaje = ((float)x)/ancho;

					volumen.setValue((int)(100*porcentaje));
					controlador.setVolumen((porcentaje));
				}
			}
		});


		// Añadimos todos los oyentes encargados de realizar la accion.
		repetir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controlador.setModoReproduccion(
						ModoReproduccionEnum.REPETIR_TODOS);
			}
		});

		aleatorio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controlador.setModoReproduccion(ModoReproduccionEnum.ALEATORIO);
			}
		});

		stop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				controlador.stop();
			}
		});

		siguiente.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				controlador.siguienteCancion();				
			}

		});

		anterior.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controlador.cancionAnterior();
			}
		});		

		mute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controlador.mute();
			}
		});		


		this.oyenteEtqPlayPause = new OyenteEtiquetas(PLAY_PNG,  playPause);
		this.oyenteAccionPlayPause = new OyentePlayPause();

		playPause.addMouseListener(this.oyenteEtqPlayPause);
		playPause.addMouseListener(this.oyenteAccionPlayPause);

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
		this.tiempoTotal = 0;
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

	private void resetProgreso() {
		if(this.progreso != null) {
			//this.remove(progreso);
			this.progreso.setValue(0);
		} else {
			this.progreso = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
			this.progreso.setEnabled(false);
			panelProg.add(progreso, BorderLayout.CENTER);


			this.progreso.removeMouseListener(this.progreso.getMouseListeners()[0]);

			this.progreso.addMouseMotionListener(new MouseMotionAdapter(){
				@Override
				public void mouseDragged(MouseEvent e) {
					synchronized (progreso) {
						if(progreso.isEnabled()) {

							int ancho = progreso.getWidth();
							int x = e.getX();

							x = (x < 0) ? 0 : x;
							x = (x >= ancho) ? ancho-1 : x;
							float porcentaje = ((float)x)/ancho;
							controlador.irA(porcentaje);
						}
					}
				}
			});

			this.progreso.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					synchronized (progreso) {
						if(progreso.isEnabled()) {
							buscando = true;
						}
					}
				}

				public void mouseReleased(MouseEvent e) {

					synchronized (progreso) {
						if(progreso.isEnabled()) {
							int ancho = progreso.getWidth();
							int x = e.getX();

							float porcentaje;
							if(x < 0) {
								x = 0;
							}
							if(x <= ancho) {
								porcentaje = ((float)x)/ancho;
							}else {
								porcentaje = ((float)progreso.getValue())/1000;
							}

							buscando = false;
							controlador.irA(porcentaje);

						}
					}
				}

			});

		}
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
			String autor = (String)properties.get("author");
			
			if(autor == null) {
				autor = "";
			}
			if (autor.equals("")) {
				autor = "Desconocido";
			}
			
			String tema = (String)properties.get("title");
			if(tema == null) {
				tema = "";
			}
			if (tema.equals("")) {
				tema = "Desconocido";
			}
			
			//Todo
			vPrincipal.setInfo("Ahora suena: " + autor + "/" + tema);
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
				this.byteInicioMusica  = 1;	
				this.framerate = this.bitrate;
			}


			//Calculamos el numero de bytes dedicados a la musica
			this.bytesMusica = bytesArchivo - byteInicioMusica;

			//El tiempo que dura la cancion
			this.tiempoTotal =Math.round(((float)bytesMusica / (bitrate/8)));
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
		info += this.toHora(this.tiempoActual) + "/";
		info += toHora(tiempoTotal-tiempoActual) + " ♫♫♫ " ;
		info += toHora(tiempoTotal);

		this.tiempoTranscurrido.setText(this.toHora(this.tiempoActual));
		this.tiempoRestante.setText(toHora(tiempoTotal-tiempoActual));
		//this.labelEstado.setText(info);
	}

	/**
	 * Pone la etiqueta "00:00" en el label de los tiempos.
	 */
	private void resetLabelTiempos() {
		this.tiempoTranscurrido.setText("00:00");
		this.tiempoRestante.setText("00:00");
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
		this.volumen.setEnabled(true);

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
			synchronized (progreso) {
				this.reset();
				this.resetProgreso();
			}
			this.volumen.setEnabled(false);
			this.resetLabelTiempos();
			this.labelEstado.setText("");
			vPrincipal.setInfo("");
			controlador.siguienteCancion();

		}else if ( event.getCode() == BasicPlayerEvent.STOP ) {
			synchronized (progreso) {
				this.reset();
				this.volumen.setEnabled(false);
				this.progreso.setEnabled(false);
				this.progreso.setValue(0);
			}
			this.resetLabelTiempos();
			this.labelEstado.setText("");
			vPrincipal.setInfo("");
		}else if(event.getCode() == BasicPlayerEvent.SEEKED) {
			this.progreso.setValue((int)(((float)event.getPosition()
					/bytesMusica)*1000));
		}else if (event.getCode() == BasicPlayerEvent.GAIN) {
			this.volumen.setValue( (int) ((Math.sqrt(event.getValue()))*100));


		}else if(event.getCode() == BasicPlayerEvent.MUTE) {
			mute.setIcon(new ImageIcon(getClass().getResource(VOL_MUTE_PNG)));
		}else if (event.getCode() == BasicPlayerEvent.NOT_MUTE) {
			mute.setIcon(new ImageIcon(getClass().getResource(VOL_PNG)));
		}

		//Cambiamos la imagen al boton de play
		if(event.getCode() == BasicPlayerEvent.PLAYING
				|| event.getCode() == BasicPlayerEvent.RESUMED) {
			this.playPause.setIcon((new ImageIcon(getClass().getResource(
					PAUSE_PNG))));
			this.oyenteEtqPlayPause.setNombreImagen(PAUSE_PNG);
			this.oyenteAccionPlayPause.setPlaying(true);
		}
		else if (event.getCode() == BasicPlayerEvent.PAUSED){
			this.playPause.setIcon((new ImageIcon(getClass().getResource(
					PLAY_PNG))));
			this.oyenteEtqPlayPause.setNombreImagen(PLAY_PNG);
			this.oyenteAccionPlayPause.setPlaying(true);
		}
		else if (event.getCode() == BasicPlayerEvent.EOM 
				|| event.getCode() == BasicPlayerEvent.STOP 
				|| event.getCode() == BasicPlayerEvent.STOPPED ) {
			this.playPause.setIcon((new ImageIcon(getClass().getResource(
					PLAY_PNG))));
			this.oyenteEtqPlayPause.setNombreImagen(PLAY_PNG);
			this.oyenteAccionPlayPause.setPlaying(false);
		}
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
	public JSlider getVolumen() {
		return this.volumen;
	}


	/**
	 * Si le llega un true, el boton de la vista de reproduccion sera el de 
	 * compactar. (hacer pequeño), sino, lo contrario.
	 * @param True si queremos icuono de compacto, false en caso contrario.
	 */
	public void setBotonCompacto(boolean b) {
		if(b) {
			botonVistaCompacta.setIcon((new ImageIcon(getClass().getResource(
					SMALL_PNG))));
		}else {
			botonVistaCompacta.setIcon((new ImageIcon(getClass().getResource(
					BIG_PNG))));
		}

	}


	@Override
	public void cambioTipoReproduccion(ModoReproduccionEnum modo) {

		//Los ponemos por defecto
		repetir.setIcon(new ImageIcon(getClass().getResource(REPEAT_PNG)));
		aleatorio.setIcon(new ImageIcon(getClass().getResource(SHUFFLE_PNG)));

		oyenteEtqAleatorio.setNombreImagen(SHUFFLE_PNG);
		oyenteEtqRepetir.setNombreImagen(REPEAT_PNG);

		switch (modo) {
		case ALEATORIO:
			aleatorio.setIcon(new ImageIcon(getClass().getResource(
					SHUFFLES_PNG)));
			oyenteEtqAleatorio.setNombreImagen(SHUFFLES_PNG);
			break;
		case REPETIR_TODOS:
			repetir.setIcon(new ImageIcon(getClass().getResource(REPEATS_PNG)));
			oyenteEtqRepetir.setNombreImagen(REPEATS_PNG);
			break;
		case NORMAL:
		}


	}

	@Override
	public void borrarCancion(BorrarCancionEvent e) {
	}

	@Override
	public void nuevaCancion(NuevaCancionEvent e) {

	}

	@Override
	public void nuevaListaReproduccion(ArrayList<CancionContainer> c) {

	}

	@Override
	public void reinicia() {

	}

	@Override
	public void setActual(int actualNuevo, int actualViejo) {

	}
	@Override
	public void desactivaCancion(int indice) {
		
		
	}



	/**
	 * Clase oyente del raton que sera añadida a los diferentes botones para 
	 * cambiarles la apariencia cuando se hace click se seleccionan etc...
	 * 
	 * @author Administrator
	 *
	 */
	private class OyenteEtiquetas extends MouseAdapter {

		/** El nombre de esta imagen en concreto*/
		private String nombreImagen;

		/** El label del que es oyente esta clase*/
		private JLabel label;

		/** Constantes para cambiar el nombre de las etiquetas*/
		private static final String PULSADO = "push";
		private static final String SELECCIONADO = "ent";
		private static final String EXT = ".png";



		/**
		 * Constructor que establece la raiz de la ruta de la imgagen
		 * @param nombreImagen El nombre de la imagen
		 */
		public OyenteEtiquetas(String nombreImagen, JLabel label) {
			this.nombreImagen = nombreImagen.replaceAll(EXT,"");
			this.label = label;
		}


		@Override
		public void mouseEntered(MouseEvent arg0) {
			try{
				label.setIcon(new ImageIcon(getClass().
						getResource(nombreImagen + SELECCIONADO + EXT)));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			try {
				label.setIcon(new ImageIcon(getClass().
						getResource(nombreImagen + EXT)));	
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			try {	
				label.setIcon(new ImageIcon(getClass().
						getResource(nombreImagen + PULSADO + EXT)));	
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			try {
				label.setIcon(new ImageIcon(getClass().
						getResource(nombreImagen + SELECCIONADO + EXT)));	
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Cambia el nombre de la imagen
		 * @param s El nuevo onmbre de la imagen.
		 */
		public void setNombreImagen(String s) {
			this.nombreImagen = s.replaceAll(EXT, "");
		}

	}

	private class OyentePlayPause extends MouseAdapter {
		/** Si es true, hay una cancion sonando (pausada o no)*/
		boolean playing;

		public OyentePlayPause() {
			super();
			this.playing = false;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			if(playing) {
				controlador.pause();
			}else {
				int cancionSeleccionada = vPrincipal.getVistaListaReproduccion()
				.getCancionSeleccionada();
				controlador.play(cancionSeleccionada);
			}
		}

		public void setPlaying(boolean playing) {
			this.playing = playing;
		}
	}



}
