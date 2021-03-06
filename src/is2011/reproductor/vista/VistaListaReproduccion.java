package is2011.reproductor.vista;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import is2011.app.controlador.IAppController;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.search.*;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;
import is2011.reproductor.modelo.listeners.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * Vista que implementa al listener de la lista de reproduccion y que se 
 * encargara de mostrarla la misma por pantalla. Esto es, la cancion actual,
 * y las canciones con sus campos.
 *
 */
@SuppressWarnings("serial")
public class VistaListaReproduccion extends JPanel implements
		ListaReproduccionListener {

	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	
	/** Rutas de las imagene*/
	private static final String SEARCH_PNG = "/Recursos/search.png";
	
	/** Referencia al controlador de la aplicacion*/
	private IAppController controlador;
	
	/** Atributo que contendra las canciones*/
	private JTable  tabla;
	
	/** Panel con scroll que contiene a la tabla */
	private JScrollPane panelScroll;
	
	/** Layout de JPanel principal de la vista */
	private BorderLayout border;
	
	/** Panel que contiene los elementos necesarios para realizar la busqueda */
	private JPanel panelBusqueda;
	
	/** Boton que genera la accion de buscar */
	private JButton buscar;
	
	
	
	/** Area de texto donde insertar los valores a buscar */
	private JRoundTextField textoBusqueda;
	
	/** Campo sobre el que quieres realizar la busqueda */
	private Choice tipoBusqueda;
	
	/** Modelo de la tabla*/
	private DefaultTableModel modelo;
	
	/**Label que contiene el valor de aleatorio*/
	private JLabel modoReproduccion;
	
	/** El menu popUp*/
	private JPopupMenu popup;
	
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

	
	/** Posición del choice para el titulo */
	private final int titulo = 0;
	
	/** Posición del choice para el genero */
	private final int genero = 1;
	
	/** Posición del choice para el artista */
	private final int artista = 2;
	
	/** Posición del choice para el album */
	private final int album = 3;
	
	
	// ********************************************************************** //
	// *************                CONSTRUCTORES               ************* //
	// ********************************************************************** //
	
	/**
	 * Prepara a la vista de reproduccion para mostrar las canciones.
	 */
	public VistaListaReproduccion() {
		
		panelScroll = new JScrollPane();
		border = new BorderLayout();
		this.setLayout(border);
		
		panelBusqueda = new JPanel();
		buscar = new JButton();
		buscar.setBorder(BorderFactory.createEmptyBorder());
		buscar.setIcon(new ImageIcon(getClass().getResource(SEARCH_PNG)));
		
		
		
	
		
		// Creamos el campo del texto de búsqueda
		textoBusqueda = new JRoundTextField("Buscar...",0);
		
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
					case titulo  : controlador.buscaListaReproduccionAvanzada(
									new BuscarTitulo(textoBusqueda.getText()));										   
									break;
					case genero  : controlador.buscaListaReproduccionAvanzada(
									new BuscarGenero(textoBusqueda.getText()));
									break;
					case artista : controlador.buscaListaReproduccionAvanzada(
									new BuscarArtista(textoBusqueda.getText()));
									break;
					case album   : controlador.buscaListaReproduccionAvanzada(
									new BuscarAlbum(textoBusqueda.getText()));
									break;
				}
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
		tipoBusqueda.add("Título");
		tipoBusqueda.add("Género");
		tipoBusqueda.add("Artista");
		tipoBusqueda.add("Álbum");
		
		panelBusqueda.add(textoBusqueda);
		panelBusqueda.add(tipoBusqueda);
		//panelBusqueda.add(buscar);
		//panelBusqueda.add(buscarAvanzada);
		modelo = new DefaultTableModel()
		{@Override     
			public boolean isCellEditable (int fila, int columna) {
				return false; 
			}
		}; 

		//Añadimos las columnas del modelo
		modelo.addColumn("Actual");
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
        cm.getColumn(NUM_COLUMNA_REPRODUCIENDO).setPreferredWidth(75);
        cm.getColumn(NUM_COLUMNA_TITULO).setPreferredWidth(170);
        cm.getColumn(NUM_COLUMNA_GENERO).setPreferredWidth(81);
        cm.getColumn(NUM_COLUMNA_ARTISTA).setPreferredWidth(150);
        cm.getColumn(NUM_COLUMNA_ALBUM).setPreferredWidth(150);
		cm.getColumn(NUM_COLUMNA_TRACKNO).setPreferredWidth(75);
		cm.getColumn(NUM_COLUMNA_DURACION).setPreferredWidth(75);
		
		
		//Le añadimos el scroll
		panelScroll.setViewportView(tabla);
		
		JTableHeader header = tabla.getTableHeader() ; 

		header.addMouseListener( 
		  new MouseAdapter() 
		  { 
		    public void mouseClicked(MouseEvent e) 
		    { 
		      JTableHeader h = (JTableHeader)e.getSource() ; 
		      int nColumn = h.columnAtPoint(e.getPoint());
		      switch (nColumn) {
		      case NUM_COLUMNA_ALBUM:
		    	  controlador.ordenarPorAlbum();
		    	  break;
		      case NUM_COLUMNA_ARTISTA:
		    	  controlador.ordenarPorArtista();
		    	  break;
		      case NUM_COLUMNA_DURACION:
		    	  controlador.ordenarPorDuracion();
		    	  break;
		      case NUM_COLUMNA_GENERO:
		    	  controlador.ordenarPorGenero();
		    	  break;
		      case NUM_COLUMNA_TITULO:
		    	  controlador.ordenarPorTitulo();
		    	  break;
		      }
		    } 
		  } 
		) ; 


		//La cabecera de la tabla.
		this.modoReproduccion = new JLabel("Modo de reproducción NORMAL");
		
		setVisible(true);
		tabla.setVisible(true);
		
		this.setBorder(new TitledBorder("Está sonando"));
		
		//Menu desplegable
		
		popup = new JPopupMenu();
		
		JMenuItem guardar = new JMenuItem("Guardar");
		popup.add(guardar);
		
		JMenuItem borrar = new JMenuItem("Borrar");
		popup.add(borrar);
		
		
		
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
		
		popup.add(ordenar);
		
		guardar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.guardarListaReproduccion();
			}
		});
		
		borrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				

					int[] rows = tabla.getSelectedRows();
					
					//Hay que notificar de la cancion mayor a la menor para 
					//poder borrar en bloques.
					ArrayList<Integer> rowsOrdenadas = new ArrayList<Integer>();
					for (int row: rows){
						rowsOrdenadas.add(row);
					}
					
					Collections.sort(rowsOrdenadas);
					
					for (int i = rowsOrdenadas.size()-1 ; i >= 0; i--) {
						controlador.borrarCancion(rowsOrdenadas.get(i));
					}
				
				
			}
			
		});
		
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
		
		
		//Añadimos el oyente del raton
		tabla.addMouseListener(new MouseAdapter() {
			

			@Override
			public void mouseClicked(MouseEvent e) {
				
				//Si es boton derecho
				if ( SwingUtilities.isRightMouseButton(e)){
					//int row = Math.round(e.getY() / tabla.getRowHeight());
		            //tabla.setRowSelectionInterval(row, row);
		            popup.show(e.getComponent(), e.getX(), e.getY());
					
				}
				else if(e.getClickCount() == 2) {
					int cancionDeseada = e.getY()/tabla.getRowHeight();

						controlador.play(cancionDeseada);
					
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
	/**
	 * quita la cancion e de la tabla
	 */
	public void borrarCancion(BorrarCancionEvent e) {
		modelo.removeRow(e.getPosicion());
	}

	@Override
	
	/**
	 * añade una cancion a la tabla
	 */
	public void nuevaCancion(NuevaCancionEvent e) {
		int pos = e.getPosicion();
		
		Object [] rowData = new Object[NUM_CAMPOS];
		rowData[NUM_COLUMNA_REPRODUCIENDO] = "";
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

	@Override
	
	/**
	 *  informa en la tabla de que cancion está reproduciendose
	 */
	public void setActual(int actualNuevo, int actualViejo) {
		if(modelo.getRowCount() >= (actualViejo) && actualViejo > 0 ) {
			modelo.setValueAt("", actualViejo-1, NUM_COLUMNA_REPRODUCIENDO);
		}
		
		if(modelo.getRowCount() >= (actualNuevo) && actualNuevo >0) {
			modelo.setValueAt("   -> ", actualNuevo-1, 
					NUM_COLUMNA_REPRODUCIENDO);

		}
	}

	
	@Override
	/**
	 * informa del tipo de reproduccion
	 */
	public void cambioTipoReproduccion(ModoReproduccionEnum modo) {
		this.modoReproduccion.setText("Modo de reproducción " + modo);
	}

	public JLabel getInfoReproduccion() {
		return this.modoReproduccion;
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
	/**
	 *  añade una lista de reproduccion a la tabla
	 */
	public void nuevaListaReproduccion
	(ArrayList<CancionContainer> listaCanciones) {
		
		int pos = 0;
		
		for(CancionContainer cancion : listaCanciones) {

			Object [] rowData = new Object[NUM_CAMPOS];
			rowData[NUM_COLUMNA_REPRODUCIENDO] = "";
			rowData[NUM_COLUMNA_TITULO] = cancion.getTitulo();
			rowData[NUM_COLUMNA_GENERO] = cancion.getGenero();
			rowData[NUM_COLUMNA_ARTISTA] = cancion.getArtista();
			rowData[NUM_COLUMNA_ALBUM] = cancion.getAlbum();
			rowData[NUM_COLUMNA_TRACKNO] = cancion.getPista();
			rowData[NUM_COLUMNA_DURACION] = toHora(cancion.getDuracion());
			
				
			modelo.insertRow(pos, rowData);
			pos++;
		}
	}

	/**
	 *  muestra todas las canciones de la lista de reproduccion en la tabla,
	 *  la limpiamos antes para no mostrar las anteriores
	 */
	public void mostrarTodas() {
		
		ArrayList<CancionContainer> cancionesLr = controlador.
		getCancionesListaReproduccion();
		
		Iterator<CancionContainer> itr = cancionesLr.iterator();
		
		CancionContainer aux=null;
		
		// Eliminamos lo que contiene la tabla para no mostrar lo anterior 
		// y lo nuevo
		for (int i = tabla.getRowCount()-1 ; i>=0 ; i--) {
			modelo.removeRow(i);
		}

		int pos = 0;
		while (itr.hasNext()){
			aux = itr.next();

			nuevaCancion(new NuevaCancionEvent(aux.getTitulo(), aux.getAlbum(), 
					aux.getPista(), aux.getArtista(), aux.getGenero(), 
					aux.getDuracion(), pos++));
		}
	}


	@Override
	public void desactivaCancion(int indice) {
		if(indice < tabla.getRowCount() && indice >= 0){
			modelo.setValueAt("00:00", indice,NUM_COLUMNA_DURACION);
		}
	}


	
}
