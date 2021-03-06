package is2011.reproductor.vista;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import is2011.app.controlador.IAppController;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.search.*;
import is2011.reproductor.modelo.listeners.*;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * Vista que implementa al listener de la bibliteca y la cual se encargara
 * de mostrar toda la informacion por pantalla. Esto es, las busquedas,
 * las canciones....
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
	
	/** Area de texto donde insertar los valores a buscar */
	private JRoundTextField textoBusqueda;
	
	/** Campo sobre el que quieres realizar la busqueda */
	private Choice tipoBusqueda;
	
	private boolean enabled;
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
	
	/** Posición del choice para el titulo */
	private final int todos = 0;
	
	/** Posición del choice para el titulo */
	private final int titulo = 1;
	
	/** Posición del choice para el genero */
	private final int genero = 2;
	
	/** Posición del choice para el artista */
	private final int artista = 3;
	
	/** Posición del choice para el album */
	private final int album = 4;
	
	/** Menu pop up */
	private JPopupMenu popup;
	
	
	// ********************************************************************** //
	// *************                CONSTRUCTORES               ************* //
	// ********************************************************************** //
	
	/**
	 * Prepara a la vista de reproduccion para mostrar las canciones.
	 */
	public VistaBiblioteca() {
		enabled = true;
		panelScroll = new JScrollPane();
		border = new BorderLayout();
		this.setLayout(border);
		
		panelBusqueda = new JPanel();
		
		// creamos el cuadro de texto para las búsquedas
		textoBusqueda = new JRoundTextField("Buscar...", 0);
		
		// añadimos el listener para el evento de pulsar teclas
		textoBusqueda.addKeyListener(new KeyAdapter(){
			/** 
			 * Cada vez que se libera una tecla se realiza la búsqueda
			 */
			@Override
			public void keyReleased(KeyEvent arg0) {				
				if(enabled) {
					switch (tipoBusqueda.getSelectedIndex()) {
					case todos  : controlador.buscaBibliotecaAvanzada(
							new BuscarTodos(textoBusqueda.getText()));										   
					break;
					case titulo  : controlador.buscaBibliotecaAvanzada(
							new BuscarTitulo(textoBusqueda.getText()));										   
					break;
					case genero  : controlador.buscaBibliotecaAvanzada(
							new BuscarGenero(textoBusqueda.getText()));
					break;
					case artista : controlador.buscaBibliotecaAvanzada(
							new BuscarArtista(textoBusqueda.getText()));
					break;
					case album   : controlador.buscaBibliotecaAvanzada(
							new BuscarAlbum(textoBusqueda.getText()));
					break;
					}
				}

			}
		});
		
		
		tipoBusqueda = new Choice();
		tipoBusqueda.add("Todos");
		tipoBusqueda.add("Título");
		tipoBusqueda.add("Género");
		tipoBusqueda.add("Artista");
		tipoBusqueda.add("Álbum");
		
		panelBusqueda.add(textoBusqueda);
		panelBusqueda.add(tipoBusqueda);

		
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

		//Añadimos las columnas del modelo
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
		    	  controlador.ordenarBibliotecaPorAlbum();
		    	  break;
		      case NUM_COLUMNA_ARTISTA:
		    	  controlador.ordenarBibliotecaPorArtista();
		    	  break;
		      case NUM_COLUMNA_DURACION:
		    	  controlador.ordenarBibliotecaPorDuracion();
		    	  break;
		      case NUM_COLUMNA_GENERO:
		    	  controlador.ordenarBibliotecaPorGenero();
		    	  break;
		      case NUM_COLUMNA_TITULO:
		    	  controlador.ordenarBibliotecaPorTitulo();
		    	  break;
		      }
		    } 
		  } 
		) ; 
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
				controlador.ordenarBibliotecaPorAlbum();
			}
		});

		ordenarArtista.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.ordenarBibliotecaPorArtista();
			}
		});
		
		ordenarGenero.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.ordenarBibliotecaPorGenero();
			}
		});
		
		ordenarDuracion.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.ordenarBibliotecaPorDuracion();
			}
		});
		
		ordenarTitulo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.ordenarBibliotecaPorTitulo();
			}
		});
		
		
		JMenuItem aniadirLR = new JMenuItem("Añadir a está sonando");
		
	
		aniadirLR.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] canciones = tabla.getSelectedRows();
				
					for(int i : canciones) {
						CancionContainer c = controlador.getCanciones().get(i);
				
						controlador.fromBibliotecaToListaReproduccion(c);
						controlador.muestraListaReproduccion();
					}
				
				
				
			}
			
		});
		
		popup.add(aniadirLR);
		
		JMenuItem reproducir = new JMenuItem("Reproducir");
		reproducir.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int[] canciones = tabla.getSelectedRows();
				
				if (canciones.length > 1) {
					controlador.borrarListaReproduccion();
					for(int i : canciones) {
						CancionContainer c = controlador.getCanciones().get(i);
						controlador.fromBibliotecaToListaReproduccion(c);
						
		
					}
					controlador.muestraListaReproduccion();

					//controlador.play(-1);
					controlador.siguienteCancion();
				}
								
			}
			
		});
		
		popup.add(reproducir);
		
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(enabled) {
					//Si es boton derecho
					if ( SwingUtilities.isRightMouseButton(e)){
						popup.show(e.getComponent(), e.getX(), e.getY());

					}

					else if(e.getClickCount() == 2) {

						controlador.borrarListaReproduccion();
						int row = e.getY()/tabla.getRowHeight();
						CancionContainer c = controlador.getCanciones().get(row);
						controlador.stop();
						controlador.fromBibliotecaToListaReproduccion(c);

						controlador.muestraListaReproduccion();
						controlador.siguienteCancion();

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
	/**
	 * Actualiza el contenido de la tabla de la biblioteca, limpiandola primero
	 * para no mostrar lo antiguo. 
	 * 
	 */
	public void mostrarTodas() {
		
		ArrayList<CancionContainer> cancionesBib = controlador.getCanciones();
		
		Iterator<CancionContainer> itr = cancionesBib.iterator();
		
		CancionContainer aux=null;
		
		// Eliminamos lo que contiene la tabla para no mostrar lo anterior 
		// y lo nuevo
		for (int i = tabla.getRowCount()-1;i>=0;i--) modelo.removeRow(i);

		int pos = 0;
		while (itr.hasNext()){
			aux = itr.next();

			nuevaCancion(new NuevaCancionEvent(aux.getTitulo(), aux.getAlbum(), 
					aux.getPista(), aux.getArtista(), aux.getGenero(),
					aux.getDuracion(), pos++));
			//System.out.println(aux.getTitulo());
		}
	}
	



	@Override
	/**
	 *  Añade un array de canciones a la tabla de la biblioteca
	 */
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
	}


	/**
	 * Activa la tabla y el texto de las busquedas
	 * 
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		this.tabla.setEnabled(enabled);
		this.textoBusqueda.setEnabled(enabled);
	}

	
}