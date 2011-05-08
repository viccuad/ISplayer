package is2011.reproductor.vista;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import is2011.app.controlador.IAppController;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.search.*;
import is2011.reproductor.modelo.listeners.BibliotecaListener;
import is2011.reproductor.modelo.listeners.BorrarCancionEvent;
import is2011.reproductor.modelo.listeners.NuevaCancionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * Vista que implementa al listener de la lista de reproduccion y que se 
 * encargara de mostrarla por pantalla.
 *
 */
@SuppressWarnings("serial")
public class VistaBiblioteca extends JPanel implements
		BibliotecaListener {

	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	/** Referencia al controlador de la aplicacion*/
	private IAppController controlador;
	
	/** Atributo que contendra las canciones*/
	private JTable  tabla;
	
	/** Modelo de la tabla de la TS*/
	private DefaultTableModel modelo;
	
	/** Panel con scroll que contiene a la tabla */
	private JScrollPane panelScroll;
	
	/** Layout de JPanel principal de la vista */
	private BorderLayout border;
	
	/** Panel que contiene los elementos necesarios para realizar la busqueda */
	private JPanel panelBusqueda;
	
	/** Boton que genera la accion de buscar */
	private JButton buscar;
	
	/** Boton que genera la accion de buscar avanzada*/
	private JButton buscarAvanzada;
	
	/** Area de texto donde insertar los valores a buscar */
	private JRoundTextField textoBusqueda;
	
	/** Campo sobre el que quieres realizar la busqueda */
	private Choice tipoBusqueda;
	
	/** Array que contiene las canciones buscadas */
	private ArrayList<CancionContainer> busqueda;
	
	/**Label que contiene el valor de aleatorio*/
	//private JLabel modoReproduccion;
	
	
	/** Columna en la que se almacena titulo*/
	private static final int NUM_COLUMNA_TITULO =0; 
	
	/** Columna en la que se almacena genero*/
	private static final int NUM_COLUMNA_GENERO = 1;
	
	/** Columna en la que se almacena artista*/
	private static final int NUM_COLUMNA_ARTISTA = 2;
	
	/** Columna en la que se almacena album*/
	private static final int NUM_COLUMNA_ALBUM = 3;
	
	/** Columna en la que se almacena Trak Nº*/
	private static final int NUM_COLUMNA_TRACKNO = 4;
	
	/** Columna en la que se almacena duracion*/
	private static final int NUM_COLUMNA_DURACION = 5;

	/** Numero de campos*/
	private static final int NUM_CAMPOS = 6;
	
	/** Atributo que indica si lo que se esta mostrando es una busqueda o la biblioteca */
	boolean busquedaRealizada = false;
	
	/** Posición del choice para el titulo */
	private final int titulo = 0;
	
	/** Posición del choice para el genero */
	private final int genero = 1;
	
	/** Posición del choice para el artista */
	private final int artista = 2;
	
	/** Posición del choice para el album */
	private final int album = 3;
	
	/** Menu pop up */
	private JPopupMenu popup;
	
	int y;
	// ********************************************************************** //
	// *************                CONSTRUCTORES               ************* //
	// ********************************************************************** //
	
	/**
	 * Prepara a la vista de reproduccion para mostrar las canciones.
	 */
	public VistaBiblioteca() {
		
		panelScroll = new JScrollPane();
		border = new BorderLayout();
		this.setLayout(border);
		
		panelBusqueda = new JPanel();
		
		buscar = new JButton();
		buscar.setBorder(BorderFactory.createEmptyBorder());
		buscar.setIcon(new ImageIcon(getClass().getResource("/Recursos/search.png")));
		
		buscarAvanzada = new JButton();
		buscarAvanzada.setBorder(BorderFactory.createEmptyBorder());
		buscarAvanzada.setIcon(new ImageIcon(getClass().getResource("/Recursos/advanced_search.png")));
		
		buscar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (!busquedaRealizada){
					System.out.println("Mostrar búsqueda");

					buscar.setIcon(new ImageIcon(getClass().getResource("/Recursos/Delete.png")));
					buscarAvanzada.setEnabled(false);
					
					CriterioBusqueda criterio = null;
					
					switch (tipoBusqueda.getSelectedIndex()){
					case titulo:{
						criterio = new BuscarTitulo(textoBusqueda.getText());
						break;
					}
					case genero:{
						criterio = new BuscarGenero(textoBusqueda.getText());
						break;
					}
					case artista:{
						criterio = new BuscarArtista(textoBusqueda.getText());
						break;
					}
					case album: 
						criterio = new BuscarAlbum(textoBusqueda.getText());
					}

					busqueda = controlador.buscaBiblioteca(criterio);
					mostrarBusqueda(busqueda);
					busquedaRealizada = true;
				}
				else {
					System.out.println("Mostrar biblioteca");
					buscar.setIcon(new ImageIcon(getClass().getResource("/Recursos/Search.png")));
					buscarAvanzada.setEnabled(true);
					
					mostrarTodas();
					
					busquedaRealizada = false;
				}
			}
			
		});
		
		buscarAvanzada.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (!busquedaRealizada){
					System.out.println("Mostrar búsqueda");

					buscarAvanzada.setIcon(new ImageIcon(getClass().getResource("/Recursos/Delete.png")));
					buscar.setEnabled(false);
					
					CriterioBusqueda criterio = null;
					
					switch (tipoBusqueda.getSelectedIndex()){
					case titulo:{
						criterio = new BuscarTitulo(textoBusqueda.getText());
						break;
					}
					case genero:{
						criterio = new BuscarGenero(textoBusqueda.getText());
						break;
					}
					case artista:{
						criterio = new BuscarArtista(textoBusqueda.getText());
						break;
					}
					case album: 
						criterio = new BuscarAlbum(textoBusqueda.getText());
					}

					busqueda = controlador.buscaBibliotecaAvanzada(criterio);
					mostrarBusqueda(busqueda);
					busquedaRealizada = true;
				}
				else {
					System.out.println("Mostrar biblioteca");
					buscarAvanzada.setIcon(new ImageIcon(getClass().getResource("/Recursos/advanced_search.png")));
					buscar.setEnabled(true);
					
					mostrarTodas();
					
					busquedaRealizada = false;
				}
			}
			
		});
		
		
		// creamos el cuadro de texto para las búsquedas
		textoBusqueda = new JRoundTextField("Buscar...", 0);
		
		// añadimos el listener para el evento de pulsar teclas
		textoBusqueda.addKeyListener(new KeyListener(){
			
			/**
			 * No es necesario implementar este método
			 */
			@Override
			public void keyPressed(KeyEvent arg0) {
				//No es necesario implementarlo
			}

			/** 
			 * Cada vez que se libera una tecla se realiza la búsqueda
			 */
			@Override
			public void keyReleased(KeyEvent arg0) {				
				switch (tipoBusqueda.getSelectedIndex()) {
					case titulo  : busqueda = controlador.buscaListaReproduccionAvanzada(
											new BuscarTitulo(textoBusqueda.getText()));										   
											break;
					case genero  : busqueda = controlador.buscaListaReproduccionAvanzada(
											new BuscarGenero(textoBusqueda.getText()));
											break;
					case artista : busqueda = controlador.buscaListaReproduccionAvanzada(
											new BuscarArtista(textoBusqueda.getText()));
											break;
					case album   : busqueda = controlador.buscaListaReproduccionAvanzada(
										    new BuscarAlbum(textoBusqueda.getText()));
											break;
				}
				mostrarBusqueda(busqueda);
				busquedaRealizada = true;
			}

			/**
			 * No es necesario implementar este método
			 */
			@Override
			public void keyTyped(KeyEvent arg0) {
				//No es necesario implementarlo
			}
		});
		
		
		tipoBusqueda = new Choice();
		tipoBusqueda.add("TÍTULO");
		tipoBusqueda.add("GÉNERO");
		tipoBusqueda.add("ARTISTA");
		tipoBusqueda.add("ÁLBUM");
		
		panelBusqueda.add(textoBusqueda);
		panelBusqueda.add(tipoBusqueda);
		panelBusqueda.add(buscar);
		panelBusqueda.add(buscarAvanzada);
		
		modelo = new DefaultTableModel()
		{@Override     
			public boolean isCellEditable (int fila, int columna) {
				return false; 
			}
		}; 

		modelo = new DefaultTableModel()
		{@Override     
			public boolean isCellEditable (int fila, int columna) {
				return false; 
			}
		}; 

		//AÃ±adimos las columnas del modelo
		modelo.addColumn("Título");
		modelo.addColumn("Género");
		modelo.addColumn("Artista");
		modelo.addColumn("Álbum");
		modelo.addColumn("Pista");
		modelo.addColumn("Duración");
		
		//Creamos la tabla
		tabla  = new JTable(modelo);
		tabla.setShowHorizontalLines(true);	
		
		//Configuramos el tamaño
		TableColumnModel cm = tabla.getColumnModel();
		cm.getColumn(NUM_COLUMNA_TITULO).setPreferredWidth(250);
        cm.getColumn(NUM_COLUMNA_GENERO).setPreferredWidth(50);
        cm.getColumn(NUM_COLUMNA_ARTISTA).setPreferredWidth(150);
        cm.getColumn(NUM_COLUMNA_ALBUM).setPreferredWidth(150);
		cm.getColumn(NUM_COLUMNA_TRACKNO).setPreferredWidth(35);
		cm.getColumn(NUM_COLUMNA_DURACION).setPreferredWidth(50);
		
		
		//Le añadimos el scroll
		panelScroll.setViewportView(tabla);
		
		//this.modoReproduccion = new JLabel("Modo de reproduccion NORMAL");
		
		setVisible(true);
		tabla.setVisible(true);
		
		this.setBorder(new TitledBorder("Biblioteca"));
		
		this.popup = new JPopupMenu();
		
		JMenu ordenar = new JMenu("Ordenar");
		JMenuItem ordenarAlbum = new JMenuItem("Álbum");
		JMenuItem ordenarArtista = new JMenuItem("Artista");
		JMenuItem ordenarGenero = new JMenuItem("Género");
		JMenuItem ordenarDuracion  = new JMenuItem("Duración");
		JMenuItem ordenarTitulo  = new JMenuItem("Título");
		

		ordenar.add(ordenarAlbum);
		ordenar.add(ordenarArtista);
		ordenar.add(ordenarGenero);
		ordenar.add(ordenarDuracion);
		ordenar.add(ordenarTitulo);
		
		this.popup.add(ordenar);
		
		ordenarAlbum.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.ordenarPorAlbum();
			}
		});

		ordenarArtista.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.ordenarPorArtista();
			}
		});
		
		ordenarGenero.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.ordenarPorGenero();
			}
		});
		
		ordenarDuracion.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.ordenarPorDuracion();
			}
		});
		
		ordenarTitulo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.ordenarPorTitulo();
			}
		});
		
		
		JMenuItem aniadirLR = new JMenuItem("Añadir a la LR");
		aniadirLR.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] canciones = tabla.getSelectedRows();
				controlador.fromBibliotecaToListaReproduccion(canciones);
				
			}
			
		});
		
		popup.add(aniadirLR);
		
		
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//Si es boton derecho
				if ( SwingUtilities.isRightMouseButton(e)){
					popup.show(e.getComponent(), e.getX(), e.getY());
					
				}
				
				else if(e.getClickCount() == 2) {
					/*
					 * int row = e.getY()/tabla.getRowHeight();
					controlador.fromBibliotecaToListaReproduccion(row);
					 */
						int row = e.getY()/tabla.getRowHeight();
						//System.out.println(row);
						
						if (busquedaRealizada){
							String path = busqueda.get(row).getTotalPath();
							//System.out.println(path);
							controlador.fromBibliotecaToListaReproduccion(path);
						}else{
							String path = controlador.getCanciones().get(row).getTotalPath();
							//System.out.println(path);
							controlador.fromBibliotecaToListaReproduccion(path);
						}

					}
					
				
			}
		});

		this.add(panelBusqueda, BorderLayout.NORTH);
		this.add(panelScroll, BorderLayout.CENTER);
	}
	

	// ********************************************************************** //
	// *************                METODOS PRIVADOS            ************* //
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
	
	// ********************************************************************** //
	// *************                METODOS PUBLICOS            ************* //
	// ********************************************************************** //
	
	@Override
	public void borrarCancion(BorrarCancionEvent e) {
		modelo.removeRow(e.getPosicion());
	}

	@Override
	public void nuevaCancion(NuevaCancionEvent e) {
		int pos = e.getPosicion();
		
		Object [] rowData = new Object[NUM_CAMPOS];
		rowData[NUM_COLUMNA_TITULO] = e.getTitulo();
		rowData[NUM_COLUMNA_GENERO] = e.getGenero();
		rowData[NUM_COLUMNA_ARTISTA] = e.getArtista();
		rowData[NUM_COLUMNA_ALBUM] = e.getAlbum();
		rowData[NUM_COLUMNA_TRACKNO] = e.getPista();
		rowData[NUM_COLUMNA_DURACION] = toHora(e.getDuracion());
		
			
		modelo.insertRow(pos, rowData);
	}

	/**
	 * Reinicia todos los datos del modelo.
	 */
	@Override
	public void reinicia() {
		modelo.setNumRows(0);
	}


	
	// ********************************************************************** //
	// *************               GETTERS / SETTERS            ************* //
	// ********************************************************************** //

	/**
	 * Establece el controlador.
	 * @param contorlador El controlador
	 */
	public void setControlador(IAppController controlador) {
		this.controlador = controlador;
	}
	
	/**
	 * Devuelve la cancion seleccionada, empezando desde el 0.
	 * -1 si no hay nada seleccionado.
	 * @return
	 */
	public int getCancionSeleccionada() {
		return tabla.getSelectedRow();
	}





	@Override
	public void mostrarTodas() {
		
		ArrayList<CancionContainer> cancionesBib = controlador.getCanciones();
		
		Iterator<CancionContainer> itr = cancionesBib.iterator();
		
		CancionContainer aux=null;
		
		// Eliminamos lo que contiene la tabla para no mostrar lo anterior y lo nuevo
		for (int i = tabla.getRowCount()-1;i>=0;i--) modelo.removeRow(i);

		int pos = 0;
		while (itr.hasNext()){
			aux = itr.next();

			nuevaCancion(new NuevaCancionEvent(aux.getTitulo(), aux.getAlbum(), aux.getPista(), 
					     aux.getArtista(), aux.getGenero(), aux.getDuracion(), pos++));
			//System.out.println(aux.getTitulo());
		}
	}
	
	
	public void mostrarBusqueda(ArrayList<CancionContainer> buscados) {
		
		
		Iterator<CancionContainer> itr = buscados.iterator();
		
		CancionContainer aux=null;
		
		// Eliminamos lo que contiene la tabla para no mostrar lo anterior y lo nuevo
		for (int i = tabla.getRowCount()-1;i>=0;i--) modelo.removeRow(i);

		int pos = 0;
		while (itr.hasNext()){
			aux = itr.next();

			nuevaCancion(new NuevaCancionEvent(aux.getTitulo(), aux.getAlbum(), aux.getPista(), 
					     aux.getArtista(), aux.getGenero(), aux.getDuracion(), pos++));
			//System.out.println(aux.getTitulo());
		}
	}


	@Override
	public void nuevaListaCanciones(ArrayList<CancionContainer> canciones) {
		
		int pos = 0;
		
		for (CancionContainer c : canciones) {
			Object [] rowData = new Object[NUM_CAMPOS];
			rowData[NUM_COLUMNA_TITULO] = c.getTitulo();
			rowData[NUM_COLUMNA_GENERO] = c.getGenero();
			rowData[NUM_COLUMNA_ARTISTA] = c.getArtista();
			rowData[NUM_COLUMNA_ALBUM] = c.getAlbum();
			rowData[NUM_COLUMNA_TRACKNO] = c.getPista();
			rowData[NUM_COLUMNA_DURACION] = toHora(c.getDuracion());
			
				
			modelo.insertRow(pos, rowData);
			
			pos++;
		}
		
		
	};
	
}