/**
 * 
 */
package Reproductor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JScrollBar;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * @author Administrator
 *
 */
public class ReproductorAvanzado extends AdvancedPlayer implements Runnable {

	
	private int frameActual;
	
	private boolean enReproduccion;
	
	private boolean enPausa;
	

	private String filename;
	
	private int framesTotales;
	

	private JScrollBar progreso;

	
	
	/**
	 * @param arg0
	 * @throws JavaLayerException
	 * @throws FileNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public ReproductorAvanzado(String filename, JScrollBar p) throws JavaLayerException, FileNotFoundException {
		
		super(new FileInputStream( filename ));
		frameActual = 0;

		this.filename = filename;
		

		progreso = p;

	}
	
public ReproductorAvanzado(String filename,int framesTotales) throws JavaLayerException, FileNotFoundException {
		
		super(new FileInputStream( filename ));
		frameActual = 0;
		this.filename = filename;
		framesTotales = framesTotales;
	}
	
	public boolean getEnPausa(){
		return enPausa;
	}
	
	public void pausar(){
		enPausa = true;
	}
	
	public void retomar(){
		enPausa = false;
	}
	
	public void parar() {
		enReproduccion = false;
		
	}
	
	public void play(){
		enReproduccion = true;
		
	}
	
	public void getFramesTotales() {
		
	}
	
	public void avanzar(){
		for (int i = 0; i< 500 ; i++) {
			try {
				//synchronized (this) {
					skipFrame();
					frameActual++;
					
				//}
				
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void run()
	{
			enReproduccion = true;
			while(enReproduccion ) {
				if (enPausa) {
					try {
						Thread.currentThread();
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					try {
						//synchronized (this) {
							decodeFrame();
							if ((progreso.getValue() < 99) && ((frameActual % 50) == 0)) progreso.setValue(progreso.getValue()+1);
							//System.out.println(frameActual);
						//}
						
					} catch (JavaLayerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					frameActual++;
				}
			}
		
	}
	
	public int getFrame() {
		return frameActual;
	}


	/**
	 * @param nuevoFrame
	 * @throws JavaLayerException 
	 */
	public void comenzarDesde(int nuevoFrame) {
		
		if (nuevoFrame <= 0) {
			nuevoFrame = 0;
		}
		for(int i = 0 ; i<= nuevoFrame; i++) {
			try {
				skipFrame();
				frameActual++;
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
