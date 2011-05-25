package is2011.reproductor.vista;

import is2011.app.preferencias.Preferencias;
import is2011.app.vista.VistaPrincipal;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
 * biblioteca asi como cargar listas de reproduccion.
 * @author Administrator
 *
 */
public class VistaLateral extends JPanel{

	/** Label que nos envia a la biblioteca*/
	private JLabel biblioteca;
	
	/** Label que nos envia a la lista de reproduccion*/
	private JLabel listaActual;
	
	/** Arbol que mostrara las listas de reproduccion*/
	private DefaultMutableTreeNode top;
	private DefaultTreeModel modelo;
	private JTree tree;
	
	/** ArrayList con las listas disponibles*/
	private String[] listas;
	
	/** Vista principal de la aplicacion */
	private VistaPrincipal vPrincipal;
	
	/** Menu pop up */
	private JPopupMenu popup;
	
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
		
		biblioteca = new JLabel("Biblioteca");
		biblioteca.setAlignmentX(Box.LEFT_ALIGNMENT);
		biblioteca.setIcon(new ImageIcon(getClass().getResource(
				"/Recursos/library.png")));
		this.add(biblioteca);
		biblioteca.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent arg0) {
			
				vPrincipal.mostrarBiblioteca();
			}
			
			
		});
		
		listaActual = new JLabel("Lista actual");
		listaActual.setAlignmentX(Box.LEFT_ALIGNMENT);
		this.add(listaActual);
		listaActual.setIcon(new ImageIcon(getClass().getResource(
		"/Recursos/playList.png")));
		listaActual.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent arg0) {
				vPrincipal.mostrarListaReproduccion();
			
			}
			
			
		});
		
		JScrollPane panel = new JScrollPane();
		JPanel panelArbol = new JPanel();
		top = new DefaultMutableTreeNode("Lista de Reproduccion");
		modelo = new DefaultTreeModel(top);
		tree = new JTree(modelo);
		panelArbol.add(tree);
		panel.setViewportView(panelArbol);
		
		tree.setAlignmentX(Box.LEFT_ALIGNMENT);
		tree.setBackground(this.getBackground());
		
		//panel.add(tree);
		this.add(panel);
		this.refrescar();
		
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
		
		JMenuItem favorita = new JMenuItem("Hacer favorita");
		favorita.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(elementoSeleccionado != null) {
					favorita();
				}
			}
		});
		popup.add(favorita);
		
		JMenuItem refrescar = new JMenuItem("Refrescar");
		popup.add(refrescar);
		refrescar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refrescar();
				
			}
		});
		tree.addMouseListener(new MouseAdapter(){ 
			

			public void mousePressed(MouseEvent e) 
			{
				if ( SwingUtilities.isRightMouseButton(e)){
					popup.show(e.getComponent(), e.getX(), e.getY());
					elementoSeleccionado = tree.getPathForLocation(e.getX(), e.getY());
				}else if(e.getClickCount() == 2) {
					// Se obtiene el path para esa fila. Con el path podemos obtener
					// los nodos.
					TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
					if(selPath != null) {
						vPrincipal.openLr(selPath.getLastPathComponent().toString());
						vPrincipal.mostrarListaReproduccion();
					}
				}
			}
		});

		
		
	}
	
	public void refrescar() {
		File f = new File(Preferencias.getInstance().getDirecctorioListasDeReproduccion());
		if(f.isDirectory()) {
			listas = f.list();
		}
		
		top.removeAllChildren();
		modelo.reload();
		
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
	
	private void borrar() {
		File f = new File(Preferencias.getInstance().getDirecctorioListasDeReproduccion() +
				File.separator + elementoSeleccionado.
				getLastPathComponent().toString() + ".xml");
		
		
		f.delete();
		refrescar();
	}
	
	private void favorita() {
		File f = new File(Preferencias.getInstance().getDirecctorioListasDeReproduccion() +
				File.separator + elementoSeleccionado.
				getLastPathComponent().toString() + ".xml");
		
		Preferencias.getInstance().setPathListaReproduccion(f.getAbsolutePath());
	}
}

