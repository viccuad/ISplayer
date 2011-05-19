package is2011.app.vista;



import is2011.app.controlador.IAppController;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;
import is2011.reproductor.vista.VistaBiblioteca;
import is2011.reproductor.vista.VistaListaReproduccion;
import is2011.reproductor.vista.VistaReproduccion;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;



import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;


import javax.swing.JFrame;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;



@SuppressWarnings("serial")
public class VistaPrincipal extends JFrame 	implements BasicPlayerListener
{
	
	private IAppController controlador;
	
	private JButton playPause;
	//private JButton play;
	//private JButton pause;
	private JButton stop;
	private JButton avanzar;
	private JButton atrasar;
	private JButton aniadir;
	
	private JButton siguiente;
	private JButton anterior;
	
	private JMenuBar menu;
	private JMenu menuArchivo;
	private JMenuItem menuItemAbrir;
	private JMenuItem menuItemAniadir;
	private JMenuItem menuItemGuardarLrXML;
	private JMenuItem menuItemAbrirLrXML;
	private JMenuItem menuPreferencias;
	
	private JMenu modoReproduccion;
	private JMenuItem aleatorio;
	private JMenuItem repetiruno;
	private JMenuItem repetirTodos;
	private JMenuItem normal;
	
	private JMenu modoVista;
	private JMenuItem verListaReporduccion;
	private JMenuItem ocultaListaReporduccion;
	
	private JMenu menuBiblioteca;
	private JMenuItem crearBiblioteca;
	private JMenuItem actualizarBiblioteca;
	private JMenuItem cargarBiblioteca;
	private JMenuItem guardarBiblioteca;
	private JMenuItem aniadirCancionBiblioteca;
	private JMenuItem aniadirCancionYCarpetaBiblioteca;
	
	GridBagConstraints grid;
	private VistaReproduccion vistaReproduccion;
	private VistaListaReproduccion vistaListaReproduccion;
	private VistaBiblioteca vistaBiblioteca;
	private VistaPreferencias vistaPreferencias;
	
	
	//private boolean reproduciendo;
	//private boolean reproduciendo2;

	

	public VistaPrincipal(){
		super();
		grid = new GridBagConstraints();
		initialize();
		
		
	}

	public JPanel getContenido(){
		
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new GridLayout(1,8));
		
		playPause = new JButton();
		playPause.setBorder(BorderFactory.createEmptyBorder());
		playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
		panelPpal.add(playPause);
		
		/*play = new JButton();
		play.setBorder(BorderFactory.createEmptyBorder());
		play.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
		panelPpal.add(play);*/

		
		/*pause = new JButton();
		pause.setBorder(BorderFactory.createEmptyBorder());
		pause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
		panelPpal.add(pause);*/
		
		
		stop = new JButton();
		stop.setBorder(BorderFactory.createEmptyBorder());
		stop.setIcon(new ImageIcon(getClass().getResource("/Recursos/stop.png")));
		panelPpal.add(stop);
		
		atrasar= new JButton();
		atrasar.setBorder(BorderFactory.createEmptyBorder());
		atrasar.setIcon(new ImageIcon(getClass().getResource("/Recursos/rew.png")));
		panelPpal.add(atrasar);	
		
		avanzar= new JButton();
		avanzar.setBorder(BorderFactory.createEmptyBorder());
		avanzar.setIcon(new ImageIcon(getClass().getResource("/Recursos/ff.png")));
		panelPpal.add(avanzar);
		
		aniadir= new JButton();
		aniadir.setBorder(BorderFactory.createEmptyBorder());
		aniadir.setIcon(new ImageIcon(getClass().getResource("/Recursos/add2.png")));
		panelPpal.add(aniadir);	
		
		anterior  = new JButton();
		anterior.setBorder(BorderFactory.createEmptyBorder());
		anterior.setIcon(new ImageIcon(getClass().getResource("/Recursos/previous_song.png")));
		panelPpal.add(anterior);
		
		siguiente  = new JButton();
		siguiente.setBorder(BorderFactory.createEmptyBorder());
		siguiente.setIcon(new ImageIcon(getClass().getResource("/Recursos/next_song.png")));
		panelPpal.add(siguiente);
	
		
		siguiente.addMouseListener(new MouseAdapter(){
			public void mousePressed (MouseEvent e) {
				siguiente.setBorder(BorderFactory.createEmptyBorder());
				siguiente.setIcon(new ImageIcon(getClass().getResource("/Recursos/next_songPush.png")));				
			}			
			
			public void mouseReleased (MouseEvent e) {
				siguiente.setBorder(BorderFactory.createEmptyBorder());
				siguiente.setIcon(new ImageIcon(getClass().getResource("/Recursos/next_songEnt.png")));				
			}
			
			public void mouseEntered  (MouseEvent e) { 
				siguiente.setBorder(BorderFactory.createEmptyBorder());
				siguiente.setIcon(new ImageIcon(getClass().getResource("/Recursos/next_songEnt.png")));
		      }

			public void mouseExited (MouseEvent e){
				siguiente.setBorder(BorderFactory.createEmptyBorder());
				siguiente.setIcon(new ImageIcon(getClass().getResource("/Recursos/next_song.png")));
			}
			
			
		});
		playPause.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int cancionSeleccionada = vistaListaReproduccion.getCancionSeleccionada();
				
				controlador.play(cancionSeleccionada);
			}
			
		});
		
		siguiente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.siguienteCancion();
				
			}}
			
		);
		
		anterior.addMouseListener(new MouseAdapter() {
			public void mousePressed (MouseEvent e) {
				anterior.setBorder(BorderFactory.createEmptyBorder());
				anterior.setIcon(new ImageIcon(getClass().getResource("/Recursos/previous_songPush.png")));				
			}			
			
			public void mouseReleased (MouseEvent e) {
				anterior.setBorder(BorderFactory.createEmptyBorder());
				anterior.setIcon(new ImageIcon(getClass().getResource("/Recursos/previous_songEnt.png")));				
			}
			public void mouseEntered  (MouseEvent e) { 
				anterior.setBorder(BorderFactory.createEmptyBorder());
				anterior.setIcon(new ImageIcon(getClass().getResource("/Recursos/previous_songEnt.png")));
		      }

			public void mouseExited (MouseEvent e) {
				anterior.setBorder(BorderFactory.createEmptyBorder());
				anterior.setIcon(new ImageIcon(getClass().getResource("/Recursos/previous_song.png")));
			}
		});		
		
		
		anterior.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.cancionAnterior();
				
			}}
			
		);
		
		
		aniadir.addMouseListener(new MouseAdapter(){
			public void mousePressed (MouseEvent e) {
				aniadir.setBorder(BorderFactory.createEmptyBorder());
				aniadir.setIcon(new ImageIcon(getClass().getResource("/Recursos/add2Push.png")));				
			}			
			
			public void mouseReleased (MouseEvent e) {
				aniadir.setBorder(BorderFactory.createEmptyBorder());
				aniadir.setIcon(new ImageIcon(getClass().getResource("/Recursos/add2Ent.png")));				
			}
			
			public void mouseEntered  (MouseEvent e) { 
				aniadir.setBorder(BorderFactory.createEmptyBorder());
				aniadir.setIcon(new ImageIcon(getClass().getResource("/Recursos/add2Ent.png")));
		      }

			public void mouseExited (MouseEvent e){
				aniadir.setBorder(BorderFactory.createEmptyBorder());
				aniadir.setIcon(new ImageIcon(getClass().getResource("/Recursos/add2.png")));
			}
			
			
		});
		
		aniadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.aniadir();
				
			}}
			
		);
		
		/*play.addMouseListener(new MouseAdapter() {
			public void mousePressed (MouseEvent e) {
				play.setBorder(BorderFactory.createEmptyBorder());
				play.setIcon(new ImageIcon(getClass().getResource("/Recursos/playPush.png")));				
			}			
			
			public void mouseReleased (MouseEvent e) {
				play.setBorder(BorderFactory.createEmptyBorder());
				play.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));				
			}
			
			public void mouseEntered  (MouseEvent e) { 
				play.setBorder(BorderFactory.createEmptyBorder());
				play.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));
		      }

			public void mouseExited (MouseEvent e) {
				play.setBorder(BorderFactory.createEmptyBorder());
				play.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
			}
		});*/
		
		
		/*play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int cancionSeleccionada = vistaListaReproduccion.getCancionSeleccionada();
    			
				controlador.play(cancionSeleccionada);
			}
		});*/
		
		
		playPause.addMouseListener(new MouseAdapter() {
			public void mousePressed (MouseEvent e) {
				playPause.setBorder(BorderFactory.createEmptyBorder());
				playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playPush.png")));				
			}			
			
			public void mouseReleased (MouseEvent e) {
				playPause.setBorder(BorderFactory.createEmptyBorder());
				playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));				
			}
			
			public void mouseEntered  (MouseEvent e) { 
				playPause.setBorder(BorderFactory.createEmptyBorder());
				playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));
		      }

			public void mouseExited (MouseEvent e) {
				playPause.setBorder(BorderFactory.createEmptyBorder());
				playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
			}
		});
		
		/*playPause.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!reproduciendo){
					
					playPause.addMouseListener(new MouseAdapter() {
						public void mousePressed (MouseEvent e) {
							playPause.setBorder(BorderFactory.createEmptyBorder());
							playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pausePush.png")));				
						}			
						
						public void mouseReleased (MouseEvent e) {
							playPause.setBorder(BorderFactory.createEmptyBorder());
							playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pauseEnt.png")));				
						}
						
						public void mouseEntered  (MouseEvent e) { 
							playPause.setBorder(BorderFactory.createEmptyBorder());
							playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pauseEnt.png")));
					      }

						public void mouseExited (MouseEvent e) {
							playPause.setBorder(BorderFactory.createEmptyBorder());
							playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
						}
					});
					reproduciendo = true;
					reproduciendo2 = true;
					int cancionSeleccionada = vistaListaReproduccion.getCancionSeleccionada();
				    			
					controlador.play(cancionSeleccionada);
							
				}
				else{
					
					controlador.pause();
					
					if(reproduciendo2){
					playPause.addMouseListener(new MouseAdapter() {
						public void mousePressed (MouseEvent e) {
							playPause.setBorder(BorderFactory.createEmptyBorder());
							playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playPush.png")));				
						}			
						
						public void mouseReleased (MouseEvent e) {
							playPause.setBorder(BorderFactory.createEmptyBorder());
							playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));				
						}
						
						public void mouseEntered  (MouseEvent e) { 
							playPause.setBorder(BorderFactory.createEmptyBorder());
							playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));
					      }

						public void mouseExited (MouseEvent e) {
							playPause.setBorder(BorderFactory.createEmptyBorder());
							playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
						}
					});
					reproduciendo2 = false;
					}
					else{
						playPause.addMouseListener(new MouseAdapter() {
							public void mousePressed (MouseEvent e) {
								playPause.setBorder(BorderFactory.createEmptyBorder());
								playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pausePush.png")));				
							}			
							
							public void mouseReleased (MouseEvent e) {
								playPause.setBorder(BorderFactory.createEmptyBorder());
								playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pauseEnt.png")));				
							}
							
							public void mouseEntered  (MouseEvent e) { 
								playPause.setBorder(BorderFactory.createEmptyBorder());
								playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pauseEnt.png")));
						      }

							public void mouseExited (MouseEvent e) {
								playPause.setBorder(BorderFactory.createEmptyBorder());
								playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
							}
						});
						reproduciendo2 = true;
					}
					//reproduciendo = false;
					
					
				}
			}
			}
		);*/
		
		/*pause.addMouseListener(new MouseAdapter(){
			public void mousePressed (MouseEvent e) {
				pause.setBorder(BorderFactory.createEmptyBorder());
				pause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pausePush.png")));				
			}			
			
			public void mouseReleased (MouseEvent e) {
				pause.setBorder(BorderFactory.createEmptyBorder());
				pause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pauseEnt.png")));				
			}
			public void mouseEntered  (MouseEvent e) { 
				pause.setBorder(BorderFactory.createEmptyBorder());
				pause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pauseEnt.png")));
		      }

			public void mouseExited (MouseEvent e){
				pause.setBorder(BorderFactory.createEmptyBorder());
				pause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
			}
		});

		pause.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.pause();
			}
		});*/
		
		stop.addMouseListener(new MouseAdapter(){
			public void mousePressed (MouseEvent e) {
				stop.setBorder(BorderFactory.createEmptyBorder());
				stop.setIcon(new ImageIcon(getClass().getResource("/Recursos/stopPush.png")));				
			}			
			
			public void mouseReleased (MouseEvent e) {
				stop.setBorder(BorderFactory.createEmptyBorder());
				stop.setIcon(new ImageIcon(getClass().getResource("/Recursos/stopEnt.png")));				
			}
			public void mouseEntered  (MouseEvent e) { 
				stop.setBorder(BorderFactory.createEmptyBorder());
				stop.setIcon(new ImageIcon(getClass().getResource("/Recursos/stopEnt.png")));
		      }

			public void mouseExited (MouseEvent e){
				stop.setBorder(BorderFactory.createEmptyBorder());
				stop.setIcon(new ImageIcon(getClass().getResource("/Recursos/stop.png")));
			}
		});
		
		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.stop();
				//reproduciendo = false;
				//reproduciendo2 = false;
			/*	
				playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
				playPause.addMouseListener(new MouseAdapter() {
					public void mousePressed (MouseEvent e) {
						playPause.setBorder(BorderFactory.createEmptyBorder());
						playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playPush.png")));				
					}			
					
					public void mouseReleased (MouseEvent e) {
						playPause.setBorder(BorderFactory.createEmptyBorder());
						playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));				
					}
					
					public void mouseEntered  (MouseEvent e) { 
						playPause.setBorder(BorderFactory.createEmptyBorder());
						playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));
				      }

					public void mouseExited (MouseEvent e) {
						playPause.setBorder(BorderFactory.createEmptyBorder());
						playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
					}
				});*/
			}			
		});
		
		avanzar.addMouseListener(new MouseAdapter(){
			public void mousePressed (MouseEvent e) {
				avanzar.setBorder(BorderFactory.createEmptyBorder());
				avanzar.setIcon(new ImageIcon(getClass().getResource("/Recursos/ffPush.png")));				
			}			
			
			public void mouseReleased (MouseEvent e) {
				avanzar.setBorder(BorderFactory.createEmptyBorder());
				avanzar.setIcon(new ImageIcon(getClass().getResource("/Recursos/ffEnt2.png")));				
			}
			public void mouseEntered  (MouseEvent e) { 
				avanzar.setBorder(BorderFactory.createEmptyBorder());
				avanzar.setIcon(new ImageIcon(getClass().getResource("/Recursos/ffEnt2.png")));
		      }

			public void mouseExited (MouseEvent e){
				avanzar.setBorder(BorderFactory.createEmptyBorder());
				avanzar.setIcon(new ImageIcon(getClass().getResource("/Recursos/ff.png")));
			}
		});

		avanzar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.fastForward();
			}
		});
		
		atrasar.addMouseListener(new MouseAdapter(){
			public void mousePressed (MouseEvent e) {
				atrasar.setBorder(BorderFactory.createEmptyBorder());
				atrasar.setIcon(new ImageIcon(getClass().getResource("/Recursos/rewPush.png")));				
			}			
			
			public void mouseReleased (MouseEvent e) {
				atrasar.setBorder(BorderFactory.createEmptyBorder());
				atrasar.setIcon(new ImageIcon(getClass().getResource("/Recursos/rewEnt.png")));				
			}
			public void mouseEntered  (MouseEvent e) { 
				atrasar.setBorder(BorderFactory.createEmptyBorder());
				atrasar.setIcon(new ImageIcon(getClass().getResource("/Recursos/rewEnt.png")));
		      }

			public void mouseExited (MouseEvent e){
				atrasar.setBorder(BorderFactory.createEmptyBorder());
				atrasar.setIcon(new ImageIcon(getClass().getResource("/Recursos/rew.png")));
			}
		});

		atrasar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

			controlador.rewind();	
			}}
		);

		this.setFocusable(true);
		this.addKeyListener(new KeyAdapter(){

			@Override
			public void keyPressed(KeyEvent event) {
				switch(event.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					controlador.fastForward();
					break;
				case KeyEvent.VK_LEFT:
					controlador.rewind();
					break;
				case KeyEvent.VK_DOWN:
					controlador.siguienteCancion();
					break;
				case KeyEvent.VK_UP:
					controlador.cancionAnterior();
					break;
				}
					
			}
			
		});
		
		return panelPpal;

	}

	public void setVistaReproductor( VistaReproduccion vistaRep) {
		vistaReproduccion = vistaRep;
		
		grid.gridx       = 0;
        grid.gridy       = 1;
        grid.gridheight  = 1;
        grid.gridwidth   = 2;
        grid.weightx     = 1;
        grid.weighty     = 0.15;
        grid.fill        = GridBagConstraints.BOTH;
		
        this.add(vistaRep,grid);

        JSlider volumen =  this.vistaReproduccion.getVolumen();
        volumen.setPreferredSize(new Dimension(30,100));
        grid.gridx       = 2;
        grid.gridy       = 0;
        grid.gridheight  = 2;
        grid.gridwidth   = 1;
        grid.weightx     = 0.0;
        grid.weighty     = 0.0;
        grid.fill        = GridBagConstraints.BOTH;

        this.add(volumen, grid);
	}
	
	
	public void setVistaListaReproduccion(VistaListaReproduccion vlr) {
		vistaListaReproduccion = vlr;
		grid.gridx       = 0;
        grid.gridy       = 2;
        grid.gridheight  = 1;
        grid.gridwidth   = 1;
        grid.weightx     = 1;
        grid.weighty     = 1;
        grid.fill        = GridBagConstraints.BOTH;
        
        this.add(vlr,grid);
        
        grid.gridx       = 0;
        grid.gridy       = 3;
        grid.gridheight  = 1;
        grid.gridwidth   = 2;
        grid.weightx     = 1;
        grid.weighty     = 0.05;
        grid.fill        = GridBagConstraints.BOTH;
        
        this.add(vlr.getInfoReproduccion(),grid);
		
       
		
	} 
	
	public void setVistaBiblioteca(VistaBiblioteca vb) {
		vistaBiblioteca = vb;
		grid.gridx       = 1;
        grid.gridy       = 2;
        grid.gridheight  = 1;
        grid.gridwidth   = 1;
        grid.weightx     = 1;
        grid.weighty     = 1;
        grid.fill        = GridBagConstraints.BOTH;
        
        this.add(vb,grid);
            				
	}
	
	public void setVistaPreferencias(VistaPreferencias vp) {
       vistaPreferencias = vp;             				
	}
	
	public void initialize()
	{
		this.setTitle("ISPlayer v0.1");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		//this.reproduciendo = false;
		//this.reproduciendo2 = false;
		
		grid.gridx       = 0;
        grid.gridy       = 0;
        grid.gridheight  = 1;
        grid.gridwidth   = 2;
        grid.weightx     = 0.15;
        grid.weighty     = 1;
        grid.fill        = GridBagConstraints.BOTH;
        
        this.add(getContenido(),grid);
		
        
        this.setVisible(true);
		this.setSize(1100,660);
		this.setResizable(false);
		
		this.menu = new JMenuBar();
		
		this.menuArchivo = new JMenu("Archivo");
		this.menuItemAbrir = new JMenuItem ("Abrir");
		this.menuItemAniadir = new JMenuItem ("Añadir");
		this.menuItemGuardarLrXML = new JMenuItem("Guardar Lista Reproduccion");
		this.menuItemAbrirLrXML = new JMenuItem("Abrir Lista Reproduccion");
		this.menuPreferencias = new JMenuItem("Preferencias");
		this.modoVista = new JMenu("Vista");
		this.menuArchivo.add(menuItemAbrir);
		this.menuArchivo.add(menuItemAniadir);
		this.menuArchivo.add(menuItemGuardarLrXML);
		this.menuArchivo.add(menuItemAbrirLrXML);
		this.menuArchivo.add(menuPreferencias);
		
		this.menu.add(menuArchivo);
		
		this.modoReproduccion = new JMenu("Modo reproduccion");
		this.aleatorio = new JMenuItem ("Aleatorio");
		this.repetiruno = new JMenuItem ("Repetir uno");
		this.repetirTodos = new JMenuItem ("Repetir todas");
		this.normal = new JMenuItem ("Normal");
				
		this.modoReproduccion.add(aleatorio);
		this.modoReproduccion.add(repetiruno);
		this.modoReproduccion.add(repetirTodos);
		this.modoReproduccion.add(normal);
		
		this.menu.add(modoReproduccion);
		
		this.modoVista = new JMenu("Vista");
		this.verListaReporduccion = new JMenuItem("Ver Lista de Reproduccion");
		this.ocultaListaReporduccion = new JMenuItem("Ocultar Lista de Reproduccion");
		
		this.modoVista.add(verListaReporduccion);
		this.modoVista.add(ocultaListaReporduccion);
		
		this.menu.add(modoVista);
		
		
		
		this.verListaReporduccion.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				vistaListaReproduccion.setVisible(true);
				//vistaBiblioteca.updateUI();
				//vistaListaReproduccion.updateUI();
				//setSize(800,650);
			}

		});
		this.ocultaListaReporduccion.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				vistaListaReproduccion.setVisible(false);
				update(getGraphics());
				vistaBiblioteca.setVisible(false);
				vistaBiblioteca.setVisible(true);
				//setSize(800,181);
			}

		});
		
		this.menuBiblioteca = new JMenu("Biblioteca");
		
		this.crearBiblioteca = new JMenuItem("Crear biblioteca");
		this.actualizarBiblioteca = new JMenuItem("Actualizar Biblioteca");
		this.aniadirCancionYCarpetaBiblioteca = new JMenuItem("Añadir canciones y carpetas");
		this.cargarBiblioteca = new JMenuItem("Cargar Biblioteca Existente");
		this.guardarBiblioteca = new JMenuItem("Guardar Biblioteca Actual");
		this.aniadirCancionBiblioteca = new JMenuItem("Añadir Cancion Biblioteca");
		
		this.menuBiblioteca.add(crearBiblioteca);
		this.menuBiblioteca.add(actualizarBiblioteca);
		this.menuBiblioteca.add(aniadirCancionYCarpetaBiblioteca);
		this.menuBiblioteca.add(aniadirCancionBiblioteca);
		this.menuBiblioteca.add(cargarBiblioteca);
		this.menuBiblioteca.add(guardarBiblioteca);
		
		this.menu.add(menuBiblioteca);
		
		this.crearBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.crearBiblioteca();
			}

		});
		this.actualizarBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.actualizarBiblioteca();
			}

		});
		
		this.cargarBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.cargarBiblioteca();
			}

		});
		
		this.guardarBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.guardarBiblioteca();
			}

		});
		
		this.aniadirCancionBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.aniadirCancionesBiblioteca();
			}

		});
		
		this.aniadirCancionYCarpetaBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.aniadirCancionesYCarpetasBiblioteca();
			}

		});
		
		
		this.aleatorio.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.setModoReproduccion(ModoReproduccionEnum.ALEATORIO);
			}

		});

		this.repetiruno.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.setModoReproduccion(ModoReproduccionEnum.REPETIR_UNO);
			}

		});
		this.repetirTodos.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.setModoReproduccion(ModoReproduccionEnum.REPETIR_TODOS);
			}

		});
		this.normal.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.setModoReproduccion(ModoReproduccionEnum.NORMAL);
			}

		});
		
		this.menuItemAbrir.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				controlador.abrirArchivos();
			}
			
		});
		
		this.menuItemAniadir.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				controlador.aniadir();
			}
			
		});
		
		this.menuItemGuardarLrXML.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				controlador.guardarListaReproduccion();
			}
			
		});
		
		this.menuItemAbrirLrXML.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				controlador.cargarListaReproduccion();
			}
			
		});
		
		this.menuPreferencias.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				vistaPreferencias.actualizarValores();
				vistaPreferencias.setVisible(true);
			}

		});
		this.setJMenuBar(menu);
		
		this.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                controlador.requestSalir();
            }
        });
	}

	 
	
	public void setControlador(IAppController appController) {
		this.controlador = appController;
		
	}

	
	
	@Override
	public void opened(Object stream, Map properties) {
		//No hace falta
	}

	
	@Override
	public void progress(int bytesread, long microseconds, byte[] pcmdata,
			Map properties) {
		//No hace falta
	}

	
	@Override
	public void setController(BasicController controller) {
		//No hace falta
	}

	
	@Override
	public void stateUpdated(BasicPlayerEvent event) {
		if(event.getCode() == BasicPlayerEvent.PLAYING
				|| event.getCode() == BasicPlayerEvent.RESUMED) {
			
			playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
			
			MouseListener[] listeners = playPause.getMouseListeners();
			for (MouseListener l : listeners) {
				playPause.removeMouseListener(l);
			}
			
			ActionListener[] alis = playPause.getActionListeners();
			for (ActionListener l : alis) {
				playPause.removeActionListener(l);
			}
			
			
			playPause.addActionListener(new ActionListener(){

				
				@Override
				public void actionPerformed(ActionEvent e) {
					controlador.pause();
					
				}
				
			});
			
			
			
			
			
			playPause.addMouseListener(new MouseAdapter() {
				public void mousePressed (MouseEvent e) {
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pausePush.png")));	
					controlador.pause();
				}			
				
				public void mouseReleased (MouseEvent e) {
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pauseEnt.png")));
					
				}
				
				public void mouseEntered  (MouseEvent e) { 
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pauseEnt.png")));
					
			      }

				public void mouseExited (MouseEvent e) {
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
					
				}
			});

		} else if (event.getCode() == BasicPlayerEvent.EOM 
				|| event.getCode() == BasicPlayerEvent.PAUSED
				
				
				|| event.getCode() == BasicPlayerEvent.OPENED) {
			
			MouseListener[] listeners = playPause.getMouseListeners();
			for (MouseListener l : listeners) {
				playPause.removeMouseListener(l);
			}
			
			
			ActionListener[] alis = playPause.getActionListeners();
			for (ActionListener l : alis) {
				playPause.removeActionListener(l);
			}
			

			
			playPause.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int cancionSeleccionada = vistaListaReproduccion.getCancionSeleccionada();
					
					controlador.pause();
					
				}
				
			});
			
			playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
			playPause.addMouseListener(new MouseAdapter() {
				public void mousePressed (MouseEvent e) {
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playPush.png")));		
					controlador.pause();
				}			
				
				public void mouseReleased (MouseEvent e) {
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));				
				}
				
				public void mouseEntered  (MouseEvent e) { 
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));
			      }

				public void mouseExited (MouseEvent e) {
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
				}
			});			
			
		} else if (event.getCode() == BasicPlayerEvent.STOP || event.getCode() == BasicPlayerEvent.STOPPED ) {
			MouseListener[] listeners = playPause.getMouseListeners();
			for (MouseListener l : listeners) {
				playPause.removeMouseListener(l);
			}
			
			
			ActionListener[] alis = playPause.getActionListeners();
			for (ActionListener l : alis) {
				playPause.removeActionListener(l);
			}
			

			playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
			playPause.addMouseListener(new MouseAdapter() {
				public void mousePressed (MouseEvent e) {
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playPush.png")));		
					int cancionSeleccionada = vistaListaReproduccion.getCancionSeleccionada();
					controlador.play(cancionSeleccionada);
				}			
				
				public void mouseReleased (MouseEvent e) {
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));				
				}
				
				public void mouseEntered  (MouseEvent e) { 
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/playEnt.png")));
			      }

				public void mouseExited (MouseEvent e) {
					playPause.setBorder(BorderFactory.createEmptyBorder());
					playPause.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
				}
			});		
		}
	}
	
	
	
}
