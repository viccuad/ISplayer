package is2011.reproductor.vista;

import is2011.app.preferencias.Preferencias;
import is2011.app.vista.VistaPrincipal;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Vista que permitira alternar entre la vista de reproduccion y la de
 * biblioteca.
 * 
 * Mostrara las listas de reproduccion que tenga guardadas el usuario, asi
 * como permitira crear o borrar dichas listas.
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class VistaLateral extends JPanel{

	/** Ruta de las imagenes*/
	private static final String LIBRARY_PNG   = "/Recursos/Library.png";
	private static final String PLAY_LIST_PNG = "/Recursos/playlist.png";
	
	/** Label que nos envia a la biblioteca*/
	private JLabel biblioteca;
	
	/** Label que nos envia a la lista de reproduccion*/
	private JLabel listaActual;
	
	/** Arbol que mostrara las listas de reproduccion*/
	private DefaultMutableTreeNode top;
	
	/** El modelo del JTree */
	private DefaultTreeModel modelo;
	
	/** El JTree*/
	private JTree tree;
	
	/** ArrayList con las listas disponibles*/
	private String[] listas;
	
	/** Vista principal de la aplicacion */
	private VistaPrincipal vPrincipal;
	
	/** Menu pop up */
	private JPopupMenu popup;
	
	/** El ultimo elemento del arbol seleccionado.*/
	private TreePath elementoSeleccionado;
	
	/**
	 * Constructor por defecto.
	 * @param vp La vista principal de la aplicacion
	 */
	public VistaLateral(VistaPrincipal vp){
		//Le ponemos borde.
		this.setBorder(new TitledBorder("Indice"));
		
		//Le asignamos la vista principal
		this.vPrincipal = vp;
		
		//Asignamos el layOut
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Cargamos el label de la biblioteca
		biblioteca = new JLabel("Biblioteca");
		biblioteca.setAlignmentX(Box.LEFT_ALIGNMENT);
		biblioteca.setIcon(new ImageIcon(getClass().getResource(LIBRARY_PNG)));
		this.add(biblioteca);
		
		
		listaActual = new JLabel("Lista actual");
		listaActual.setAlignmentX(Box.LEFT_ALIGNMENT);
		this.add(listaActual);
		listaActual.setIcon(new ImageIcon(getClass().getResource(PLAY_LIST_PNG)));
				
		JScrollPane panel = new JScrollPane();
		JPanel panelArbol = new JPanel();
		top = new DefaultMutableTreeNode("Listas guardadas");
		modelo = new DefaultTreeModel(top);
		tree = new JTree(modelo);
		panelArbol.add(tree);
		panel.setViewportView(panelArbol);
		
		
		tree.setAlignmentX(Box.LEFT_ALIGNMENT);
		tree.setBackground(this.getBackground());
		
		//panel.add(tree);
		this.add(panel);
		this.refrescar();
		
		// Creamos el popup
		this.popup = new JPopupMenu();
		
		JMenuItem borrar = new JMenuItem("Borrar");
		popup.add(borrar);
		borrar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(elementoSeleccionado != null) {
					borrar();
				}
			}
		});
		
		JMenu menuListaDefecto = new JMenu("Lista por defecto");
		
		JMenuItem favorita = new JMenuItem("Cargar esta");
		favorita.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(elementoSeleccionado != null) {
					favorita();
				}
			}
		});
		menuListaDefecto.add(favorita);
		
		JMenuItem defecto = new JMenuItem("Cargar última lista");
		defecto.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(elementoSeleccionado != null) {
					defecto();
				}
			}
		});
		menuListaDefecto.add(defecto);
		
		
		
		popup.add(menuListaDefecto);
		
		JMenuItem refrescar = new JMenuItem("Refrescar");
		popup.add(refrescar);
		refrescar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refrescar();
				
			}
		});
		

		
		this.initActions();
	}
	
	/**
	 * Inicializa las acciones d elos diferentes botones de la vista.
	 */
	public void initActions() {
		
		//Accion de pulsar sobre el boton de la biblioteca.
		biblioteca.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
			
				vPrincipal.mostrarBiblioteca();
			}
			
			
		});
		
		//Accion de pulsar sobre el boton de ir a lista actual
		listaActual.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent arg0) {
				vPrincipal.mostrarListaReproduccion();
			
			}
			
			
		});
		
		// Cuando hacemos click sobre los elementos del arbol
		tree.addMouseListener(new MouseAdapter(){ 
			@Override
			public void mousePressed(MouseEvent e) 
			{
				if ( SwingUtilities.isRightMouseButton(e)){
					popup.show(e.getComponent(), e.getX(), e.getY());
					elementoSeleccionado = tree.getPathForLocation(e.getX(),
							e.getY());
				}else if(e.getClickCount() == 2) {
					// Se obtiene el path para esa fila. Con el path podemos 
					// obtener los nodos.
					TreePath selPath = tree.getPathForLocation(e.getX(), 
							e.getY());
					if(selPath != null) {
						vPrincipal.openLr(selPath.getLastPathComponent().
								toString());
						vPrincipal.mostrarListaReproduccion();
						//vPrincipal.play();
					}
				}
			}
		});

	}
	
	/**
	 * Vuelve a recorrer el directorio donde se guardan las listas de 
	 * reproduccion para recargarlas.
	 */
	public void refrescar() {
		File f = new File(Preferencias.getInstance().
				getDirecctorioListasDeReproduccion());
		if(f.isDirectory()) {
			listas = f.list();
		}
		
		//Borramos los datos que tiene ya el JTree
		top.removeAllChildren();
		modelo.reload();
		
		//Le vamos añadiendo los hijos en el mismo orden que nos los encontramos
		if(listas != null) {
			int i = 0;
			for (String nombre : listas) {
				File fAux = new File(nombre);
				nombre = fAux.getName().replaceAll("\\.xml", "");;
				DefaultMutableTreeNode padre = new DefaultMutableTreeNode(nombre);
				modelo.insertNodeInto(padre, top, i++);
			}
		}
		
	}
	
	/**
	 * Borra el nodo del arbol sobre el que se hizo click la utlima vez
	 */
	private void borrar() {
		File f = new File(
				Preferencias.getInstance().getDirecctorioListasDeReproduccion()
				+ File.separator + elementoSeleccionado.
				getLastPathComponent().toString() + ".xml");
		
		if(Preferencias.getInstance().getPathListaReproduccionDefecto().
				equals(f.getAbsolutePath())) {
			defecto();
		}
		
		f.delete();
		refrescar();
	}
	
	/**
	 * Pone el ultimo nodo seleccionado del arbol como la lista de reproduccion
	 * por defecto.
	 */
	private void favorita() {
		File f = new File(
				Preferencias.getInstance().getDirecctorioListasDeReproduccion()
				+ File.separator + elementoSeleccionado.
				getLastPathComponent().toString() + ".xml");
		System.out.println(f);
		Preferencias.getInstance().setPathListaReproduccionDefecto(
				f.getAbsolutePath());
	}
	
	private void defecto() {
		Preferencias.getInstance().setPathListaReproduccionDefecto(
			Preferencias.getInstance().getPathUltimaLista());
	}
}

