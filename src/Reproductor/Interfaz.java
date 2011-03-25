package Reproductor;


import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import javazoom.jl.decoder.JavaLayerException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class Interfaz extends JFrame 	
{

	private String nombre;
	private ReproductorAvanzado player;
	JButton play;
	JButton stop;
	JButton avanzar;
	JButton atrasar;
	FileDialog fd;

	public Interfaz(){
		super();
		initialize();
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


		panelPpal.add(play);
		panelPpal.add(stop);
		panelPpal.add(atrasar);
		panelPpal.add(avanzar);
		

		play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(player == null) {
					try {
						
						play.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
				        fd.setVisible(true);
				        File f = new File(fd.getDirectory(), fd.getFile());
				        System.out.println(fd.getDirectory()+fd.getFile());
				        nombre = fd.getDirectory()+fd.getFile();
						player = new ReproductorAvanzado(nombre);

						Thread t = new Thread(player,"hilo reproductor");

						t.start();

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player.play();
				}else{
					if (player.getEnPausa()){
						play.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
						player.retomar();
					}
					else{
						play.setIcon(new ImageIcon(getClass().getResource("/Recursos/play.png")));
						player.pausar();
					}
				}
			}
		});

		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (player != null )player.parar();
				player = null;
			}
		});

		avanzar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (player != null ) player.avanzar();
			}
		});

		atrasar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

				if (player != null){ 
					int frameActual = player.getFrame();
					int nuevoFrame = frameActual - 500;

					player.parar();

					try {
						player = new ReproductorAvanzado(nombre);
						Thread t = new Thread(player,"hilo reproductor");

						t.start();
					} catch (Exception e1) {

					}
					player.comenzarDesde(nuevoFrame);
				}
			}	
		});

		return panelPpal;

	}


	public void initialize()
	{
		this.setTitle("ISPlayer v0.1");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(getContenido());
		this.setVisible(true);
		this.setSize(200,82);
		fd = new FileDialog(this, "Reproductor", FileDialog.LOAD);

	}
}
