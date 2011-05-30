package is2011.app.vista;



import is2011.app.controlador.IAppController;
import is2011.app.preferencias.Preferencias;

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




import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;



import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VistaPrincipal extends JFrame 
{
	
	/**
	 * 
	 */
	private static final String ISPLAYER = "ISPlayer";



	private IAppController controlador;
	

	
	private JMenuBar menu;
	
	private JMenu menuArchivo;
	private JMenuItem menuItemAbrir;
	private JMenuItem menuItemAniadir;
	private JMenuItem menuItemGuardarLrXML;

	
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
	
	
	private JMenu menuBiblioteca;
	private JMenuItem crearBiblioteca;
	private JMenuItem actualizarBiblioteca;
	private JMenuItem aniadirCancionBiblioteca;
	private JMenuItem aniadirCancionYCarpetaBiblioteca;
	
	private String infoCancion = "";
	
	GridBagConstraints grid;
	private VistaReproduccion vistaReproduccion;
	private VistaListaReproduccion vistaListaReproduccion;
	private VistaBiblioteca vistaBiblioteca;
	
	private VistaLateral vistaLateral;
	
	private boolean vistaCompacta;
	
	private boolean vistaBibliotecaVisible = false;
	
	

	public VistaPrincipal(){
		super();
		vistaCompacta = false;
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
        vistaRep.setVisible(true);
       

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
        vlr.setVisible(true);
        
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
        vb.setVisible(false);
            				
	}
	
	/*
	public void setVistaPreferencias(VistaPreferencias vp) {
       vistaPreferencias = vp;             				
	}*/
	
	public void mostrar() {
		this.setVisible(true);
        cargarPosicion();
	}
	
	public void initialize()
	{
		this.setTitle(ISPLAYER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		//this.reproduciendo = false;
		//this.reproduciendo2 = false;

        
        this.setVisible(false);
        
        
       
		//this.setResizable(false);
		
		this.menu = new JMenuBar();
	  
	  
		this.menuArchivo = new JMenu("Archivo");
		this.menuItemAbrir = new JMenuItem ("Abrir");
		this.menuItemAniadir = new JMenuItem ("Añadir");
		this.menuItemGuardarLrXML = new JMenuItem("Guardar Lista Reproduccion");
		
		//this.menuPreferencias = new JMenuItem("Preferencias");
		
		this.menuArchivo.add(menuItemAbrir);
		this.menuArchivo.add(menuItemAniadir);
		this.menuArchivo.add(menuItemGuardarLrXML);
		//this.menuArchivo.add(menuItemAbrirLrXML);
		//this.menuArchivo.add(menuPreferencias);
		
		this.menu.add(menuArchivo);
		
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
		this.ayudaOnLine = new JMenuItem("Mostrar ayuda");
		ayuda.add(ayudaOnLine);
		ayudaOnLine.addActionListener(new ActionListener(){

			
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.lanzarAyuda();
				
			}
			
		});
		
		this.menu.add(ayuda);
		
		this.menuApariencia = new JMenu("Apariencia");
		this.SyntheticaSimple2DLookAndFeel= new JMenuItem("Frio");
		this.SyntheticaBlackEyeLookAndFeel= new JMenuItem("Oscuridad");
		this.SyntheticaBlackMoonLookAndFeel= new JMenuItem("Clasico");
		this.SyntheticaBlackStarLookAndFeel= new JMenuItem("Estrella negra");
		this.SyntheticaBlueIceLookAndFeel= new JMenuItem("Hielo azul");
		this.SyntheticaBlueMoonLookAndFeel= new JMenuItem("Luna azul");
		this.SyntheticaClassyLookAndFeel= new JMenuItem("Aluminio");
		this.SyntheticaGreenDreamLookAndFeel= new JMenuItem("Primavera");
		this.SyntheticaMauveMetallicLookAndFeel= new JMenuItem("Femenino");
		this.SyntheticaOrangeMetallicLookAndFeel= new JMenuItem("Naranja");
		this.SyntheticaSilverMoonLookAndFeel= new JMenuItem("Luna");
		this.SyntheticaSkyMetallicLookAndFeel= new JMenuItem("Glaciar");
		this.SyntheticaWhiteVisionLookAndFeel= new JMenuItem("Nube");
		  
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
				mostrarBiblioteca();
			}

		});
		this.actualizarBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.actualizarBiblioteca();
				mostrarBiblioteca();
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
				mostrarBiblioteca();
			}

		});
		
		this.aniadirCancionYCarpetaBiblioteca.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				controlador.aniadirCancionesYCarpetasBiblioteca();
				mostrarBiblioteca();
			}

		});
		
		
		this.menuItemAbrir.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				controlador.abrirArchivos();
				mostrarListaReproduccion();
				
			}
			
		});
		
		this.menuItemAniadir.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				controlador.aniadir();
				mostrarListaReproduccion();
			}
			
		});
		
		this.menuItemGuardarLrXML.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				controlador.guardarListaReproduccion();
			}
			
		});
		

		this.setJMenuBar(menu);
		
		this.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                //Guaradmos en las preferencias el tamaño y la posicion de la v
            	guardarPosicion();
            	controlador.requestSalir();
            }

			
        });
		
		//Panel lateral
		
	}

	 
	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void cargarPosicion() {
		 Preferencias p = Preferencias.getInstance();
			this.setSize(p.getAncho(),p.getAlto());
			this.move(p.getPosX(),p.getPosY());
			
			if(p.isVistaCompacta()) {
				this.vistaCompacta();
			}
	}

	public VistaListaReproduccion getVistaListaReproduccion() {
		return this.vistaListaReproduccion;
	}
	public void setControlador(IAppController appController) {
		this.controlador = appController;
		
	}

	private void guardarPosicion() {
		if(!vistaCompacta) {
			Preferencias.getInstance().setPosX(getX());
	    	Preferencias.getInstance().setPosY(getY());
	    	Preferencias.getInstance().setAlto(getHeight());
	    	Preferencias.getInstance().setAncho(getWidth());
		}
		Preferencias.getInstance().setCompacta(vistaCompacta);

		
	}

	/**
	 * Cambia alternativamente la vista compacta. 
	 */
	public void vistaCompacta() {
		if(!this.vistaCompacta) {
			//Cambiamos a ivsta compacta.
			if(vistaBiblioteca.isVisible()){
				vistaBibliotecaVisible = true;
			}else {
				vistaBibliotecaVisible = false;
			}
			this.guardarPosicion();
			this.vistaListaReproduccion.setVisible(false);
			this.vistaBiblioteca.setVisible(false);
			this.vistaReproduccion.setBotonCompacto(false);
			this.menu.setVisible(false);
			this.vistaLateral.setVisible(false);
			this.pack();
			this.setResizable(false);
			this.vistaCompacta = true;
			this.setInfo(this.infoCancion);
			
		}else{
			if(vistaBibliotecaVisible) {
				this.vistaBiblioteca.setVisible(true);
			}else {
				this.vistaListaReproduccion.setVisible(true);
			}
			this.vistaReproduccion.setBotonCompacto(true);
			this.menu.setVisible(true);
			this.vistaLateral.setVisible(true);
			this.setResizable(true);
			this.cargarPosicion();
			this.vistaCompacta = false;
			this.setInfo(this.infoCancion);
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
		controlador.stop();
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


	/**
	 * 
	 */
	public void muestraListaRep() {
		if( !this.vistaCompacta) {
			this.vistaBiblioteca.setVisible(false);
			this.vistaListaReproduccion.setVisible(true);
		}
		
	}

	/**
	 * 
	 */
	public void play() {
		this.controlador.play(-1);
		
	}
	
	public void setInfo(String s) {
		this.infoCancion = s;
		if(s!= null && !s.equals("") && vistaCompacta) {
			this.setTitle(this.infoCancion);
		}else {
			this.setTitle(ISPLAYER);
		}
		
	}

	/**
	 * 
	 */
	public void bloqueaBiblioteca() {
		this.menuBiblioteca.setEnabled(false);
		this.vistaBiblioteca.setEnabled(false);
		
	}

	/**
	 * 
	 */
	public void activaBiblioteca() {
		this.menuBiblioteca.setEnabled(true);
		this.vistaBiblioteca.setEnabled(true);
	}
	
	public void refrescarVistaLateral() {
		vistaLateral.refrescar();
	}
}
