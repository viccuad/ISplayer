package Reproductor;


import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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

public class Interfaz extends JFrame 	
{

	private String nombre;
	private ReproductorAvanzado player;
	private int pos;
	private int antPos;
	private JButton play;
	private JButton stop;
	private JButton avanzar;
	private JButton atrasar;
	private JScrollBar tiempo;
	private FileDialog fd;

	public Interfaz(){
		super();
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
		tiempo.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.print("Holaaaaaaaa");
				antPos = pos;
				pos = tiempo.getValue();
				// avanzar
				if (pos > antPos){
					if (player != null ) player.avanzar();
				}else{ //atrasar
					if (player != null){ 
						int frameActual = player.getFrame();
						int nuevoFrame = frameActual - (antPos-pos);

						player.parar();

						try {
							player = new ReproductorAvanzado(nombre, tiempo);
							Thread t = new Thread(player,"hilo reproductor");

							t.start();
						} catch (Exception e1) {

						}
						if (nuevoFrame < 0) player.comenzarDesde(0);
						else player.comenzarDesde(nuevoFrame);
					}
				}
			}
			
		});
		
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
				if(player == null) {
					try {
						
						play.setIcon(new ImageIcon(getClass().getResource("/Recursos/pause.png")));
				        fd.setVisible(true);
				        //File f = new File(fd.getDirectory(), fd.getFile());
				        System.out.println(fd.getDirectory()+fd.getFile());
				        nombre = fd.getDirectory()+fd.getFile();
						player = new ReproductorAvanzado(nombre, tiempo);

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
						player = new ReproductorAvanzado(nombre, tiempo);
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
		this.setSize(400,400);
		fd = new FileDialog(this, "Reproductor", FileDialog.LOAD);

	}
}
