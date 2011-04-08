/**
 * 
 */
package is2011.app.controlador;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import is2011.reproductor.controlador.Controlador;
import is2011.reproductor.modelo.*;
import is2011.reproductor.vista.VistaReproduccion;

/**
 * 
 * Controlador que recoje las ordenes de la vista
 * 
 * @author Administrator
 *
 */
public class AppController implements IAppController {

	private Controlador reproductor;
	
	public AppController(Controlador rep) {
		reproductor = rep;
	}
	
	public void abrirArchivos() {
		reproductor.reiniciaListaReproduccion();
		aniadir();
	}
	
	 public File[] abrirArchivo()
	    {
	        JFileChooser fileChooser = new JFileChooser();

	        fileChooser.setMultiSelectionEnabled(true);

	        //Lo configuramos para que solo permita la apertura de ficheros
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.setAcceptAllFileFilterUsed(false);

	        //Añadimos un filtro para permitir solo apertura de tipo plg
	        FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3 & ogg", "mp3", "ogg");
	        //FileNameExtensionFilter filter = new FileNameExtensionFilter("ogg", "ogg");
	        fileChooser.setFileFilter(filter);


	        //Abrimos el fichero
	        int seleccion;

	        seleccion =fileChooser.showOpenDialog(null);

	        if (seleccion == JFileChooser.APPROVE_OPTION)
	        {
	            File[] f = fileChooser.getSelectedFiles();
	            
	            boolean aceptado = true;
	            for(File file : f) {
	            	if (!(filter.accept(file)))
		                aceptado = false;
	            }
	            
	            if (aceptado)
	                return f;
	            else
	            	return null;
	        }
	        else
	        {
	            return null;
	        }
	    }
	 
	@Override
	public void fastForward() {
		reproductor.fastForward();
		
	}

	
	@Override
	public void play() {
		reproductor.play();
	}

	@Override
	public void rewind() {
		reproductor.rewind();
	}
	
	@Override
	public void stop() {
		reproductor.stop();
		
	}

	/* (non-Javadoc)
	 * @see is2011.app.controlador.iAppController#open(java.io.File)
	 */
	@Override
	public void open(File file) {
		reproductor.open(file);
		
	}


	/* (non-Javadoc)
	 * @see is2011.app.controlador.iAppController#irA(int)
	 */
	@Override
	public void irA(float i) {
		reproductor.irA(i);
		
	}

	/* (non-Javadoc)
	 * @see is2011.app.controlador.IAppController#pause()
	 */
	@Override
	public void pause() {
		reproductor.pausar();
		
	}

	@Override
	public void aniadir() {
		File[] files = abrirArchivo();
		if(files != null) {
			for (File f : files) {
				reproductor.aniadir(f.getAbsolutePath());
			}
		}
	}

	/* (non-Javadoc)
	 * @see is2011.app.controlador.IAppController#cancionAnterior()
	 */
	@Override
	public void cancionAnterior() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see is2011.app.controlador.IAppController#siguienteCancion()
	 */
	@Override
	public void siguienteCancion() {
		// TODO Auto-generated method stub
		
	}

}
