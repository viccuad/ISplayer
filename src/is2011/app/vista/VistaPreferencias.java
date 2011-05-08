package is2011.app.vista;

import is2011.app.controlador.IAppController;
import is2011.app.preferencias.Preferencias;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;



@SuppressWarnings("serial")
public class VistaPreferencias extends JFrame{

	IAppController controlador;

	private JPanel panel;
	private SpringLayout spring;
	private JLabel pathBiblioteca;
	private JLabel pathListaReproduccion;
	private JLabel modoReproduccion;
	private JTextField textPathBiblioteca;
	private JTextField textPathListaReproduccion;
	private JButton examinarBiblioteca;
	private JButton examinarListaReproduccion;

	private Choice selectorModoReproduccion;
	private JButton aceptar;

	/**
	 * Constructor de la clase VistaPrincipal
	 */
	public VistaPreferencias(){
		super();	
		initialize();
	}

	/**
	 * Permite vincular un controlador con la vista
	 * @param c
	 */
	public void setControlador(IAppController c){
		controlador = c;
	}

	/**
	 * Metodo que inicializa la interfaz. Crea variables y les asigna una posicion en el panel
	 */
	public void initialize(){
		this.setTitle("Preferencias del Sistema");
		this.setMinimumSize(new Dimension(550, 150));
		this.setResizable(false);

		panel = new JPanel();
		spring = new SpringLayout();
		panel.setLayout(spring);

		pathBiblioteca = new JLabel("Path Biblioteca");
		spring.putConstraint(SpringLayout.WEST, pathBiblioteca, 5, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, pathBiblioteca, 5, SpringLayout.NORTH, panel);
		panel.add(pathBiblioteca);

		textPathBiblioteca = new JTextField("src/Recursos/testXML2", 30);
		spring.putConstraint(SpringLayout.WEST, textPathBiblioteca, 155, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, textPathBiblioteca, 5, SpringLayout.NORTH, panel);
		panel.add(textPathBiblioteca);

		examinarBiblioteca = new JButton("cambiar");
		spring.putConstraint(SpringLayout.WEST, examinarBiblioteca, 430, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, examinarBiblioteca,5, SpringLayout.NORTH, panel);
		panel.add(examinarBiblioteca);
		examinarBiblioteca.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = openXML();
				if(f!= null) {
					Preferencias.getInstance().setPathBiblioteca(f.getAbsolutePath());
					textPathBiblioteca.setText(Preferencias.getInstance().getPathBiblioteca());
				}
				
			}
		});
		
		
		pathListaReproduccion = new JLabel("Path Lista Reproduccion");
		spring.putConstraint(SpringLayout.WEST, pathListaReproduccion, 5, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, pathListaReproduccion, 35, SpringLayout.NORTH, panel);
		panel.add(pathListaReproduccion);

		textPathListaReproduccion = new JTextField("functionality in progress", 30);	
		spring.putConstraint(SpringLayout.WEST, textPathListaReproduccion, 155, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, textPathListaReproduccion, 35, SpringLayout.NORTH, panel);
		panel.add(textPathListaReproduccion);
		
		examinarListaReproduccion = new JButton("cambiar");
		spring.putConstraint(SpringLayout.WEST, examinarListaReproduccion, 430, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, examinarListaReproduccion,35, SpringLayout.NORTH, panel);
		panel.add(examinarListaReproduccion);
		examinarListaReproduccion.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = openXML();
				if(f!= null) {
					Preferencias.getInstance().setPathListaReproduccion(f.getAbsolutePath());
					textPathListaReproduccion.setText(Preferencias.getInstance().getPathListaReproduccion());
				}
			}
		});
		
		modoReproduccion = new JLabel("Modo Reproduccion");	
		spring.putConstraint(SpringLayout.WEST, modoReproduccion, 5, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, modoReproduccion, 65, SpringLayout.NORTH, panel);
		panel.add(modoReproduccion);

		selectorModoReproduccion = new Choice();
		selectorModoReproduccion.add("NORMAL");
		selectorModoReproduccion.add("ALEATORIO");
		selectorModoReproduccion.add("REPETIR TODAS");
		selectorModoReproduccion.add("REPETIR UNA");
		spring.putConstraint(SpringLayout.WEST, selectorModoReproduccion, 155, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, selectorModoReproduccion, 65, SpringLayout.NORTH, panel);
		panel.add(selectorModoReproduccion);

		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ModoReproduccionEnum modo=null;
				switch (selectorModoReproduccion.getSelectedIndex()){
				case 0:{
					modo = ModoReproduccionEnum.NORMAL;
					break;
				}
				case 1:{
					modo = ModoReproduccionEnum.ALEATORIO;
					break;
				}
				case 2:{
					modo = ModoReproduccionEnum.REPETIR_UNO;
					break;
				}
				case 3: modo = ModoReproduccionEnum.REPETIR_TODOS;
				}
				setVisible(false);
			}

		});
		spring.putConstraint(SpringLayout.WEST, aceptar, 5, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, aceptar, 95, SpringLayout.NORTH, panel);
		panel.add(aceptar);
		this.setContentPane(panel);
	}

	public void actualizarValores(){
		Preferencias pref = Preferencias.getInstance();
		textPathBiblioteca.setText(pref.getPathBiblioteca());
		textPathListaReproduccion.setText(pref.getPathListaReproduccion());

		int modo=0;
		switch (pref.getModoReproduccion()){
		case NORMAL:{
			modo = 0;
			break;
		}
		case ALEATORIO:{
			modo = 1;
			break;
		}
		case REPETIR_UNO:{
			modo = 2;
			break;
		}
		case REPETIR_TODOS: modo = 3;
		}
		selectorModoReproduccion.select(modo);
	}

	private File openXML(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);

		//Lo configuramos para que solo permita la apertura de ficheros
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(true);

		//Añadimos un filtro para permitir solo apertura de tipo plg
		FileNameExtensionFilter filter = new FileNameExtensionFilter("xml","xml");

		//FileNameExtensionFilter filter = new FileNameExtensionFilter("ogg", "ogg");
		fileChooser.setFileFilter(filter);


		//Abrimos el fichero
		int seleccion;

		seleccion =fileChooser.showOpenDialog(null);

		if (seleccion == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			return file;
			
		}
		else
		{
			return null;
		}
	}
	
		
	
		
	
}


