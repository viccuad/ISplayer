package is2011.reproductor.vista;


import is2011.app.controlador.IAppController;
import is2011.reproductor.modelo.listeners.BorrarCancionEvent;
import is2011.reproductor.modelo.listeners.ListaReproduccionListener;
import is2011.reproductor.modelo.listeners.NuevaCancionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Vista que implementa al listener de la lista de reproduccion y que se 
 * encargara de mostrarla por pantalla.
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class VistaListaReproduccion extends JScrollPane implements
		ListaReproduccionListener {

	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	/** Referencia al controlador de la aplicacion*/
	private IAppController controlador;
	
	/** Atributo que contendra las canciones*/
	private JTable  tabla;
	
	/** Modelo de la tabla de la TS*/
	private DefaultTableModel modelo;
	
	/** Label de aleatorio*/
	//private JLabel aleatorio;
	
	/**Label que contiene el valor de aleatorio*/
	//private JLabel aleatorioContenido;
	
	/** Columna en la que muestra si se esta reproduciendo la cancion*/
	private static final int NUM_COLUMNA_REPRODUCIENDO = 0;
	
	/** Columna en la que se almacena artista*/
	private static final int NUM_COLUMNA_ALBUM = 1;
	
	/** Columna en la que se almacena Trak Nº*/
	private static final int NUM_COLUMNA_TRACKNO = 2;
	
	/** Columna en la que se almacena titulo*/
	private static final int NUM_COLUMNA_TITULO =3; 
	
	/** Columna en la que se almacena duracion*/
	private static final int NUM_COLUMNA_DURACION = 4;

	/** Numero de campos*/
	private static final int NUM_CAMPOS = 5;
	
	
	
	
	// ********************************************************************** //
	// *************                CONSTRUCTORES               ************* //
	// ********************************************************************** //
	
	/**
	 * Prepara a la vista de reproduccion para mostrar las canciones.
	 */
	public VistaListaReproduccion() {
		modelo = new DefaultTableModel();
		
		//Añadimos las columnas del modelo
		modelo.addColumn("Reproduciendo");
		modelo.addColumn("Artista");
		modelo.addColumn("Numero de pista");
		modelo.addColumn("Titulo");
		modelo.addColumn("Duracion");
		
		//Creamos la tabla
		tabla  = new JTable(modelo);
		tabla.setShowHorizontalLines(true);	
		
		//Le añadimos el scroll
		setViewportView(tabla);
		
		setVisible(true);
		tabla.setVisible(true);
		
		tabla.setEnabled(false);
		
		this.setBorder(new TitledBorder("Lista de reproduccion"));
		
		setFocusable(false);
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
		rowData[NUM_COLUMNA_REPRODUCIENDO] = "";
		rowData[NUM_COLUMNA_ALBUM] = e.getAlbum();
		rowData[NUM_COLUMNA_TRACKNO] = e.getPista();
		rowData[NUM_COLUMNA_TITULO] = e.getTitulo();
		rowData[NUM_COLUMNA_DURACION] = e.getDuracion();
			
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
	public void setActual(int actualNuevo, int actualViejo) {
		if(modelo.getRowCount() >= (actualViejo) && actualViejo > 0 ) {
			modelo.setValueAt("", actualViejo-1, NUM_COLUMNA_REPRODUCIENDO);
		}
		
		if(modelo.getRowCount() >= (actualNuevo) && actualNuevo >0) {
			modelo.setValueAt("  ********   ", actualNuevo-1, NUM_COLUMNA_REPRODUCIENDO);
		}
	}

	
	@Override
	public void setAleatorio(boolean aleatorio) {
		
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
}
