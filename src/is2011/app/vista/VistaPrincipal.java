package is2011.app.vista;



import is2011.app.controlador.IAppController;
import is2011.app.preferencias.Preferencias;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;
import is2011.reproductor.vista.VistaBiblioteca;
import is2011.reproductor.vista.VistaLateral;
import is2011.reproductor.vista.VistaListaReproduccion;
import is2011.reproductor.vista.VistaReproduccion;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;



import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;



import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VistaPrincipal extends JFrame 
{
	
	private IAppController controlador;
	

	
	private JMenuBar menu;
	
	private JMenu menuArchivo;
	private JMenuItem menuItemAbrir;
	private JMenuItem menuItemAniadir;
	private JMenuItem menuItemGuardarLrXML;
	private JMenuItem menuItemAbrirLrXML;
	//private JMenuItem menuPreferencias;
	
	private JMenu menuApariencia;
	private JMenuItem SyntheticaSimple2DLookAndFeel;
	private JMenuItem SyntheticaBlackEyeLookAndFeel;
	private JMenuItem SyntheticaBlackMoonLookAndFeel;
	private JMenuItem SyntheticaBlackStarLookAndFeel;
	private JMenuItem  SyntheticaBlueIceLookAndFeel;
	private JMenuItem SyntheticaBlueMoonLookAndFeel;
	private JMenuItem SyntheticaClassyLookAndFeel;
	private JMenuItem SyntheticaGreenDreamLookAndFeel;
	private JMenuItem SyntheticaMauveMetallicLookAndFeel;
	private JMenuItem SyntheticaOrangeMetallicLookAndFeel;
	private JMenuItem SyntheticaSilverMoonLookAndFeel;
	private JMenuItem SyntheticaSkyMetallicLookAndFeel;
	private JMenuItem SyntheticaWhiteVisionLookAndFeel;
	
	private JMenu ayuda;
	private JMenuItem ayudaOnLine;
	
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
	//private JMenuItem cargarBiblioteca;
	//private JMenuItem guardarBiblioteca;
	private JMenuItem aniadirCancionBiblioteca;
	private JMenuItem aniadirCancionYCarpetaBiblioteca;
	
	
	
	GridBagConstraints grid;
	private VistaReproduccion vistaReproduccion;
	private VistaListaReproduccion vistaListaReproduccion;
	private VistaBiblioteca vistaBiblioteca;
	//private VistaPreferencias vistaPreferencias;
	
	private VistaLateral vistaLateral;
	
	//private boolean reproduciendo;
	//private boolean reproduciendo2;

	

	public VistaPrincipal(){
		super();
		grid = new GridBagConstraints();
		initialize();
		
		this.addVistaLateral();
	}
	
	public void addVistaLateral() {
		this.vistaLateral = new VistaLateral(this);
		
		grid.gridx       = 0; //0
        grid.gridy       = 0; //1
        grid.gridheight  = 1;
        grid.gridwidth   = 1;
        grid.weightx     = 0.1;
        grid.weighty     = 1;
        grid.fill        = GridBagConstraints.BOTH;
		
        this.vistaLateral.setVisible(true);
        this.add(vistaLateral,grid);
	}

	public void setVistaReproductor( VistaReproduccion vistaRep) {
		vistaReproduccion = vistaRep;
		
		grid.gridx       = 0; //0
        grid.gridy       = 1; //1
        grid.gridheight  = 1;
        grid.gridwidth   = 2;
        grid.weightx     = 1;
        grid.weighty     = 0.1;
        grid.fill        = GridBagConstraints.BOTH;
		
        this.add(vistaRep,grid);
       

	}
	
	
	public void setVistaListaReproduccion(VistaListaReproduccion vlr) {
		vistaListaReproduccion = vlr;
		grid.gridx       = 1; //0
        grid.gridy       = 0; // 2
        grid.gridheight  = 1;
        grid.gridwidth   = 1;
        grid.weightx     = 0.75;
        grid.weighty     = 1;
        grid.fill        = GridBagConstraints.BOTH;
        
        this.add(vlr,grid);
        vlr.setVisible(false);
        
        /*
        grid.gridx       = 0;
        grid.gridy       = 3;
        grid.gridheight  = 1;
        grid.gridwidth   = 2;
        grid.weightx     = 1;
        grid.weighty     = 0.05;
        grid.fill        = GridBagConstraints.BOTH;
        
        this.add(vlr.getInfoReproduccion(),grid);
		
       */
		
	} 
	
	public void setVistaBiblioteca(VistaBiblioteca vb) {
		vistaBiblioteca = vb;
		grid.gridx       = 1; // 1
        grid.gridy       = 0; // 2
        grid.gridheight  = 1;
        grid.gridwidth   = 1;
        grid.weightx     = 0.75;
        grid.weighty     = 1;
        grid.fill        = GridBagConstraints.BOTH;
        
        this.add(vb,grid);
            				
	}
	
	/*
	public void setVistaPreferencias(VistaPreferencias vp) {
       vistaPreferencias = vp;             				
	}*/
	
	public void initialize()
	{
		this.setTitle("ISPlayer v0.1");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		//this.reproduciendo = false;
		//this.reproduciendo2 = false;

        
        this.setVisible(true);
		this.setSize(1100,660);
		//this.setResizable(false);
		
		this.menu = new JMenuBar();
	  
	  
		this.menuArchivo = new JMenu("Archivo");
		this.menuItemAbrir = new JMenuItem ("Abrir");
		this.menuItemAniadir = new JMenuItem ("Añadir");
		this.menuItemGuardarLrXML = new JMenuItem("Guardar Lista Reproduccion");
		this.menuItemAbrirLrXML = new JMenuItem("Abrir Lista Reproduccion");
		//this.menuPreferencias = new JMenuItem("Preferencias");
		this.modoVista = new JMenu("Vista");
		this.menuArchivo.add(menuItemAbrir);
		this.menuArchivo.add(menuItemAniadir);
		this.menuArchivo.add(menuItemGuardarLrXML);
		this.menuArchivo.add(menuItemAbrirLrXML);
		//this.menuArchivo.add(menuPreferencias);
		
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
		//this.cargarBiblioteca = new JMenuItem("Cargar Biblioteca Existente");
		//this.guardarBiblioteca = new JMenuItem("Guardar Biblioteca Actual");
		this.aniadirCancionBiblioteca = new JMenuItem("Añadir Cancion Biblioteca");
		
		this.menuBiblioteca.add(crearBiblioteca);
		this.menuBiblioteca.add(actualizarBiblioteca);
		this.menuBiblioteca.add(aniadirCancionYCarpetaBiblioteca);
		this.menuBiblioteca.add(aniadirCancionBiblioteca);
		//this.menuBiblioteca.add(cargarBiblioteca);
		//this.menuBiblioteca.add(guardarBiblioteca);
		
		this.menu.add(menuBiblioteca);
		
		
		this.ayuda = new JMenu("Ayuda");
		this.ayudaOnLine = new JMenuItem("Mostrar ayuda online");
		ayuda.add(ayudaOnLine);
		ayudaOnLine.addActionListener(new ActionListener(){

			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
				    String osName = System.getProperty("os.name");
				    System.out.println(osName);
				    
				    if(osName.startsWith("Windows")) {
				      


				

				    	//String url = "src/Recursos/manual/Introduccion.html";
				    	File f = new File("src/Recursos/manual/Introduccion.html");
				    	//File f = new File("C:/Users/carlosmoya/Desktop/Grupo31_Practica7.pdf");
				    	//String url = f.getAbsolutePath();

				    	//java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				    	java.awt.Desktop.getDesktop().open(f);
				    	//Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler src/Recursos/manual/Introduccion.html");
				    	//Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler src/Recursos/manual/Introduccion.html");

				
				    } else if (osName.startsWith("Mac OS X")) {
				  	  
						
						Runtime.getRuntime().exec("open -a Safari src/Recursos/index.html");

				
				    }else if (osName.startsWith("Linux")) {
				  	  
						
						Runtime.getRuntime().exec("./firefox src/Recursos/index.html");
				
				    }
			    
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		this.menu.add(ayuda);
		
		this.menuApariencia = new JMenu("Apariencia");
		this.SyntheticaSimple2DLookAndFeel= new JMenuItem("Simple2D");
		this.SyntheticaBlackEyeLookAndFeel= new JMenuItem("BlackEye");
		this.SyntheticaBlackMoonLookAndFeel= new JMenuItem("BlackMoon");
		this.SyntheticaBlackStarLookAndFeel= new JMenuItem("BlackStar");
		this.SyntheticaBlueIceLookAndFeel= new JMenuItem("BlueIce");
		this.SyntheticaBlueMoonLookAndFeel= new JMenuItem("BlueMoon");
		this.SyntheticaClassyLookAndFeel= new JMenuItem("ClassyLook");
		this.SyntheticaGreenDreamLookAndFeel= new JMenuItem("GreenDream");
		this.SyntheticaMauveMetallicLookAndFeel= new JMenuItem("MauveMetallic");
		this.SyntheticaOrangeMetallicLookAndFeel= new JMenuItem("OrangeMetallic");
		this.SyntheticaSilverMoonLookAndFeel= new JMenuItem("SilverMoon");
		this.SyntheticaSkyMetallicLookAndFeel= new JMenuItem("SkyMetallic");
		this.SyntheticaWhiteVisionLookAndFeel= new JMenuItem("WhiteVision");
		  
		this.menuApariencia.add(SyntheticaSimple2DLookAndFeel);
		this.menuApariencia.add(SyntheticaBlackEyeLookAndFeel);
		this.menuApariencia.add(SyntheticaBlackMoonLookAndFeel);
		this.menuApariencia.add(SyntheticaBlackStarLookAndFeel);
		this.menuApariencia.add(SyntheticaBlueIceLookAndFeel);
		this.menuApariencia.add(SyntheticaBlueMoonLookAndFeel);
		this.menuApariencia.add(SyntheticaClassyLookAndFeel);
		this.menuApariencia.add(SyntheticaGreenDreamLookAndFeel);
		this.menuApariencia.add(SyntheticaMauveMetallicLookAndFeel);
		this.menuApariencia.add(SyntheticaOrangeMetallicLookAndFeel);
		this.menuApariencia.add(SyntheticaSilverMoonLookAndFeel);
		this.menuApariencia.add(SyntheticaSkyMetallicLookAndFeel);
		this.menuApariencia.add(SyntheticaWhiteVisionLookAndFeel);
		
		this.menu.add(menuApariencia);
		
		
		this.SyntheticaSimple2DLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaSimple2DLookAndFeel"));

		this.SyntheticaBlackEyeLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaBlackEyeLookAndFeel"));
		
		this.SyntheticaBlackMoonLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaBlackMoonLookAndFeel"));

		this.SyntheticaBlackStarLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaBlackStarLookAndFeel"));

		
		this.SyntheticaBlueIceLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaBlueIceLookAndFeel"));

		
		this.SyntheticaBlueMoonLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaBlueMoonLookAndFeel"));

			
		
		this.SyntheticaClassyLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaClassyLookAndFeel"));

		
		
		this.SyntheticaGreenDreamLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaGreenDreamLookAndFeel"));
			
		
		this.SyntheticaMauveMetallicLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaMauveMetallicLookAndFeel"));
		
		this.SyntheticaOrangeMetallicLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaOrangeMetallicLookAndFeel"));
		
		this.SyntheticaSilverMoonLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaSilverMoonLookAndFeel"));

		
		this.SyntheticaSkyMetallicLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaSkyMetallicLookAndFeel"));

		
		this.SyntheticaWhiteVisionLookAndFeel.addActionListener(new 
				OyenteLookAndFeel("SyntheticaWhiteVisionLookAndFeel"));


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
		
		/*this.cargarBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.cargarBiblioteca();
			}

		});*/
		
		/*
		this.guardarBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.guardarBiblioteca();
			}

		});*/
		
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
				vistaLateral.refrescar();
			}
			
		});
		
		this.menuItemAbrirLrXML.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				controlador.cargarListaReproduccion();
			}
			
		});
		
		/*
		this.menuPreferencias.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				vistaPreferencias.actualizarValores();
				vistaPreferencias.setVisible(true);
			}

		});*/
		this.setJMenuBar(menu);
		
		this.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                controlador.requestSalir();
            }
        });
		
		//Panel lateral
		
	}

	 
	public VistaListaReproduccion getVistaListaReproduccion() {
		return this.vistaListaReproduccion;
	}
	public void setControlador(IAppController appController) {
		this.controlador = appController;
		
	}

	

	/**
	 * Cambia alternativamente la vista compacta. 
	 */
	public void vistaCompacta() {
		if(vistaListaReproduccion.isVisible()) {
			//Cambiamos a ivsta compacta.
			this.vistaListaReproduccion.setVisible(false);
			this.vistaBiblioteca.setVisible(false);
			this.vistaReproduccion.setBotonCompacto(false);
			this.menu.setVisible(false);
			this.vistaLateral.setVisible(false);
			this.pack();
		}else {

			this.vistaListaReproduccion.setVisible(true);
			this.vistaBiblioteca.setVisible(true);
			this.vistaReproduccion.setBotonCompacto(true);
			this.menu.setVisible(true);
			this.vistaLateral.setVisible(true);
			this.setSize(1100,660);
		}
	}
	
	public void mostrarBiblioteca() {
		this.vistaBiblioteca.setVisible(true);
		this.vistaListaReproduccion.setVisible(false);
	}
	
	public void mostrarListaReproduccion() {
		this.vistaBiblioteca.setVisible(false);
		this.vistaListaReproduccion.setVisible(true);
	}

	/**
	 * @param string
	 */
	public void openLr(String string) {
		controlador.openLR(string);
		
	}
	
	
	private class OyenteLookAndFeel implements ActionListener {

		private String nombreLook; 
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Preferencias.getInstance().setLookAndFeel(this.nombreLook);
			 JOptionPane.showMessageDialog(	 
					 null,"Los cambios no surtiran efecto "
					 + "hasta que vuelva a abrir el reproductor.","Apariencia",
                     JOptionPane.WARNING_MESSAGE);
		}
	
		public OyenteLookAndFeel(String nombre) {
			this.nombreLook = nombre;
		}
	
	}
	
}
