package is2011.app.vista;


import is2011.app.controlador.Controlador;
import is2011.app.controlador.iAppController;

import java.awt.Dimension;
import java.awt.FileDialog;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SpringLayout;

import javazoom.jl.decoder.JavaLayerException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class VistaPrincipal extends JFrame 	
{
	private iAppController controlador;
	
	private String nombre;
	private int pos;
	private int antPos;
	private JButton play;
	private JButton stop;
	private JButton avanzar;
	private JButton atrasar;
	private JScrollBar tiempo;
	private FileDialog fd;

	public VistaPrincipal(){
		super();
		//TODO poner esto en su sitio
		controlador = new Controlador();
		
		initialize();
		pos = 0;
		antPos = 0;
	}

	public JPanel getContenido(){


		JPanel panelPpal = new JPanel();
		SpringLayout layout = new SpringLayout();
		panelPpal.setLayout(layout);
		
		
		play = new JButton();
		play.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
		play.setBorder(null);
		layout.putConstraint(SpringLayout.WEST, play,0,SpringLayout.WEST, panelPpal);
		layout.putConstraint(SpringLayout.NORTH, play,0,SpringLayout.NORTH, panelPpal);

		stop = new JButton();
		stop.setIcon(new ImageIcon(getClass().getResource("/Recursos/stop.png")));
		stop.setBorder(null);
		layout.putConstraint(SpringLayout.WEST, stop,48,SpringLayout.WEST, panelPpal);
		layout.putConstraint(SpringLayout.NORTH, stop,0,SpringLayout.NORTH, panelPpal);

		avanzar= new JButton();
		avanzar.setIcon(new ImageIcon(getClass().getResource("/Recursos/ff.png")));
		avanzar.setBorder(null);
		layout.putConstraint(SpringLayout.WEST, avanzar,144,SpringLayout.WEST, panelPpal);
		layout.putConstraint(SpringLayout.NORTH, avanzar,0,SpringLayout.NORTH, panelPpal);
		
		atrasar= new JButton();
		atrasar.setIcon(new ImageIcon(getClass().getResource("/Recursos/rew.png")));
		atrasar.setBorder(null);
		layout.putConstraint(SpringLayout.WEST, atrasar,96,SpringLayout.WEST, panelPpal);
		layout.putConstraint(SpringLayout.NORTH, atrasar,0,SpringLayout.NORTH, panelPpal);

		tiempo = new JScrollBar(JScrollBar.HORIZONTAL, 0, 20, 0, 100);
		layout.putConstraint(SpringLayout.WEST, tiempo,5,SpringLayout.EAST, avanzar);
		layout.putConstraint(SpringLayout.NORTH, tiempo,20,SpringLayout.NORTH, panelPpal);
		
		tiempo.setPreferredSize(new Dimension(100, 20));
		tiempo.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
					}
				
			}
			
		
		);
		
		/*
		tiempo.addAdjustmentListener (new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("afasdglf");
				antPos = pos;
				pos += 1;
				
				
			}
			
		});*/
		
		panelPpal.add(play);
		panelPpal.add(stop);
		panelPpal.add(atrasar);
		panelPpal.add(avanzar);
		panelPpal.add(tiempo);

		play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.play();
					
				}
			}
		);

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


	public void initialize()
	{
		this.setTitle("ISPlayer v0.1");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(getContenido());
		this.setVisible(true);
		this.setSize(400,400);
		fd = new FileDialog(this, "Reproductor", FileDialog.LOAD);
		
		
		
		
		controlador.open(new File ("c:\\ka2.mp3"));

	}
}
