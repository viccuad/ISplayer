package is2011.reproductor.vista;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import is2011.app.controlador.IAppController;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.search.*;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;
import is2011.reproductor.modelo.listeners.BibliotecaListener;
import is2011.reproductor.modelo.listeners.BorrarCancionEvent;
import is2011.reproductor.modelo.listeners.NuevaCancionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * Vista que implementa al listener de la lista de reproduccion y que se 
 * encargara de mostrarla por pantalla.
 * 
 * @author Administrator
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
	private JTextField textoBusqueda;
	
	/** Campo sobre el que quieres realizar la busqueda */
	private Choice tipoBusqueda;
	
	/** Array que contiene las canciones buscadas */
	private ArrayList<CancionContainer> busqueda;
	
	/**Label que contiene el valor de aleatorio*/
	//private JLabel modoReproduccion;
	
	/** Columna en la que muestra si se esta reproduciendo la cancion*/
	private static final int NUM_COLUMNA_REPRODUCIENDO = 0;
	
	/** Columna en la que se almacena titulo*/
	private static final int NUM_COLUMNA_TITULO =1; 
	
	/** Columna en la que se almacena genero*/
	private static final int NUM_COLUMNA_GENERO = 2;
	
	/** Columna en la que se almacena artista*/
	private static final int NUM_COLUMNA_ARTISTA = 3;
	
	/** Columna en la que se almacena album*/
	private static final int NUM_COLUMNA_ALBUM = 4;
	
	/** Columna en la que se almacena Trak Nº*/
	private static final int NUM_COLUMNA_TRACKNO = 5;
	
	/** Columna en la que se almacena duracion*/
	private static final int NUM_COLUMNA_DURACION = 6;

	/** Numero de campos*/
	private static final int NUM_CAMPOS = 7;
	
	/** Atributo que indica si lo que se esta mostrando es una busqueda o la biblioteca */
	boolean busquedaRealizada = false;
	
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
					
					
					System.out.println("Mostrar busqueda");

					buscar.setIcon(new ImageIcon(getClass().getResource("/Recursos/Delete.png")));
					buscarAvanzada.setEnabled(false);
					
					CriterioBusqueda criterio = null;
					
					switch (tipoBusqueda.getSelectedIndex()){
					case 0:{
						criterio = new BuscarAlbum(textoBusqueda.getText());
						break;
					}
					case 1:{

						criterio = new BuscarArtista(textoBusqueda.getText());
						break;
					}
					case 2:{

						criterio = new BuscarGenero(textoBusqueda.getText());
						break;
					}
					case 3: 
						criterio = new BuscarTitulo(textoBusqueda.getText());
						
					}

					busqueda = controlador.buscaBiblioteca(criterio);
					mostrarBusqueda(busqueda);
					busquedaRealizada = true;
				}
				else {
					System.out.println("Mostar biblioteca");
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
					
					
					System.out.println("Mostrar busqueda");

					buscarAvanzada.setIcon(new ImageIcon(getClass().getResource("/Recursos/Delete.png")));
					buscar.setEnabled(false);
					
					CriterioBusqueda criterio = null;
					
					switch (tipoBusqueda.getSelectedIndex()){
					case 0:{
						criterio = new BuscarAlbum(textoBusqueda.getText());
						break;
					}
					case 1:{

						criterio = new BuscarArtista(textoBusqueda.getText());
						break;
					}
					case 2:{

						criterio = new BuscarGenero(textoBusqueda.getText());
						break;
					}
					case 3: 
						criterio = new BuscarTitulo(textoBusqueda.getText());
						
					}

					busqueda = controlador.buscaBibliotecaAvanzada(criterio);
					mostrarBusqueda(busqueda);
					busquedaRealizada = true;
				}
				else {
					System.out.println("Mostar biblioteca");
					buscarAvanzada.setIcon(new ImageIcon(getClass().getResource("/Recursos/advanced_search.png")));
					buscar.setEnabled(true);
					
					mostrarTodas();
					
					busquedaRealizada = false;
				}
			}
			
		});
		
		textoBusqueda = new JTextField("Busqueda...", 10);
		tipoBusqueda = new Choice();
		tipoBusqueda.add("ALBUM");
		tipoBusqueda.add("ARTISTA");
		tipoBusqueda.add("GENERO");
		tipoBusqueda.add("TITULO");
		
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

		//Añadimos las columnas del modelo
		modelo.addColumn("Actual");
		modelo.addColumn("Titulo");
		modelo.addColumn("Genero");
		modelo.addColumn("Artista");
		modelo.addColumn("Album");
		modelo.addColumn("Pista");
		modelo.addColumn("Duracion");
		
		//Creamos la tabla
		tabla  = new JTable(modelo);
		tabla.setShowHorizontalLines(true);	
		
		//Configuramos el tama�o
		TableColumnModel cm = tabla.getColumnModel();
		cm.getColumn(NUM_COLUMNA_REPRODUCIENDO).setPreferredWidth(35);
        cm.getColumn(NUM_COLUMNA_TITULO).setPreferredWidth(250);
        cm.getColumn(NUM_COLUMNA_GENERO).setPreferredWidth(50);
        cm.getColumn(NUM_COLUMNA_ARTISTA).setPreferredWidth(150);
        cm.getColumn(NUM_COLUMNA_ALBUM).setPreferredWidth(150);
		cm.getColumn(NUM_COLUMNA_TRACKNO).setPreferredWidth(35);
		cm.getColumn(NUM_COLUMNA_DURACION).setPreferredWidth(50);
		
		
		//Le a�adimos el scroll
		panelScroll.setViewportView(tabla);
		
		//this.modoReproduccion = new JLabel("Modo de reproduccion NORMAL");
		
		setVisible(true);
		tabla.setVisible(true);
		//Solo permite seleccionar una columna.
		tabla.setSelectionMode(0);
		
		this.setBorder(new TitledBorder("Biblioteca"));
		
		popup = new JPopupMenu();
		JMenuItem borrar = new JMenuItem("Borrar");
		popup.add(borrar);
		JMenuItem modificar = new JMenuItem("Modificar");
		popup.add(modificar);
		JMenuItem aniadirLR = new JMenuItem("Añadir a la LR");
		aniadirLR.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = y/tabla.getRowHeight();
				System.out.println(row);
				if (busquedaRealizada){
					String path = busqueda.get(row).getTotalPath();
					System.out.println(path);
					controlador.fromBibliotecaToListaReproduccion(path);
				}else{
					String path = controlador.getCanciones().get(row).getTotalPath();
					System.out.println(path);
					controlador.fromBibliotecaToListaReproduccion(path);
				}

			}
			
		});
		popup.add(aniadirLR);
		
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if ( SwingUtilities.isRightMouseButton(e)){
					
					// Para ver que fila ha sido seleccionada
		            y = e.getY();
		            int row = Math.round(y / tabla.getRowHeight());
		            tabla.setRowSelectionInterval(row, row);               
		            System.out.println("Row Selected = " + row);
		            
		            // Para mostrar el menu
		            popup.show(e.getComponent(), e.getX(), e.getY());

				}
				else if(e.getClickCount() == 2) {
					int row = e.getY()/tabla.getRowHeight();
					System.out.println(row);
					
					if (busquedaRealizada){
						String path = busqueda.get(row).getTotalPath();
						System.out.println(path);
						controlador.fromBibliotecaToListaReproduccion(path);
					}else{
						String path = controlador.getCanciones().get(row).getTotalPath();
						System.out.println(path);
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
			System.out.println(aux.getTitulo());
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
			System.out.println(aux.getTitulo());
		}
	}


	
}