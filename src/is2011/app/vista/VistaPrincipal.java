package is2011.app.vista;


import is2011.app.controlador.AppController;
import is2011.app.controlador.IAppController;
import is2011.reproductor.vista.VistaReproduccion;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.JFrame;


public class VistaPrincipal extends JFrame 	
{
	private IAppController controlador;
	
	private String nombre;
	private int pos;
	
	private JButton play;
	private JButton pause;
	private JButton stop;
	private JButton avanzar;
	private JButton atrasar;
	
	private VistaReproduccion vistaReproduccion;
	

	public VistaPrincipal(){
		super();
		
		initialize();
		pos = 0;
		
	}

	public JPanel getContenido(){


		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new GridLayout(1,5));
		
		play = new JButton();
		play.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
		panelPpal.add(play);

		
		pause = new JButton();
		pause.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
		panelPpal.add(pause);
		
		stop = new JButton();
		stop.setIcon(new ImageIcon(getClass().getResource("/Recursos/stop.png")));
		panelPpal.add(stop);
		
		avanzar= new JButton();
		avanzar.setIcon(new ImageIcon(getClass().getResource("/Recursos/ff.png")));
		panelPpal.add(avanzar);
		
		atrasar= new JButton();
		atrasar.setIcon(new ImageIcon(getClass().getResource("/Recursos/rew.png")));
		panelPpal.add(atrasar);		
		
		
		
		play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nombre == null) {
					controlador.open(controlador.abrirArchivo());
				}
				
				controlador.play();
					
				}
			}
		);

		pause.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.pause();
			}
		});
		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.stop();
			}
		});

		avanzar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.fastForward();
			}
		});

		atrasar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

			controlador.rewind();	
			}}
		);

		return panelPpal;

	}

	public void setVistaReproductor( VistaReproduccion vistaRep) {
		vistaReproduccion = vistaRep;
		this.add(vistaRep);
		vistaReproduccion.addListenerProgreso (new MouseAdapter() {
			
		public void mouseReleased(MouseEvent arg0) {
					pos = vistaReproduccion.getPos();
					controlador.irA((float)pos/1000);
				}
		});
		
	}
	public void initialize()
	{
		this.setTitle("ISPlayer v0.1");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));
		this.add(getContenido());
		this.setVisible(true);
		this.setSize(800,200);
		
		
	
		
		
		


	}

	
	 
	/**
	 * @param appController
	 */
	public void setControlador(IAppController appController) {
		this.controlador = appController;
		
	}
}
