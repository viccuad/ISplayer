package is2011.app.vista;

import is2011.app.controlador.IAppController;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;



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
	private Choice selectorModoReproduccion;
	private JButton aceptar;
	
	public VistaPreferencias(){
		super();	
		initialize();
	}
	
	public void setControlador(IAppController c){
		controlador = c;
	}
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
        
		pathListaReproduccion = new JLabel("Path Lista Reproduccion");
        spring.putConstraint(SpringLayout.WEST, pathListaReproduccion, 5, SpringLayout.WEST, panel);
        spring.putConstraint(SpringLayout.NORTH, pathListaReproduccion, 35, SpringLayout.NORTH, panel);
        panel.add(pathListaReproduccion);
        
		textPathListaReproduccion = new JTextField("functionality in progress", 30);	
        spring.putConstraint(SpringLayout.WEST, textPathListaReproduccion, 155, SpringLayout.WEST, panel);
        spring.putConstraint(SpringLayout.NORTH, textPathListaReproduccion, 35, SpringLayout.NORTH, panel);
        panel.add(textPathListaReproduccion);
        
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
				controlador.actualizaPreferencias(textPathBiblioteca.getText(), textPathListaReproduccion.getText(), modo);
				
			}
			
		});
        spring.putConstraint(SpringLayout.WEST, aceptar, 5, SpringLayout.WEST, panel);
        spring.putConstraint(SpringLayout.NORTH, aceptar, 95, SpringLayout.NORTH, panel);
		panel.add(aceptar);
		this.setContentPane(panel);
	}
	

}
