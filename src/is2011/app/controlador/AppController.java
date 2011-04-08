package is2011.app.controlador;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import is2011.reproductor.controlador.ControladorReproductor;



public class AppController implements IAppController {
	
	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	
	/**Reproductor de la aplicacion*/
	private ControladorReproductor reproductor;
	
	// ********************************************************************** //
	// *************                CONSTRUCTOR                 ************* //
	// ********************************************************************** //
	/**
	 * Constructor por defecto.
	 */
	public AppController(ControladorReproductor rep) {
		reproductor = rep;
	}
	
	// ********************************************************************** //
	// *************              METODOS PRIVADOS              ************* //
	// ********************************************************************** //
	
	/**
	 * Muestra un dialogo para abrir archivos mp3 y ogg.
	 * @return Devuelve un array con todos los archivos seleccionados. Si algun
	 * archivo no esta soportado, lo devuelve como null.
	 */
	private File[] abrirArchivo()
	    {
	        JFileChooser fileChooser = new JFileChooser();

	        //Lo configuramos para permitir apertura multiple
	        fileChooser.setMultiSelectionEnabled(true);

	        //Lo configuramos para que solo permita la apertura de ficheros
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.setAcceptAllFileFilterUsed(true);
	        
	        //Añadimos un filtro para permitir solo apertura de tipo plg
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        		"mp3 & ogg", "mp3", "ogg");
	        //FileNameExtensionFilter filter = new FileNameExtensionFilter("ogg", "ogg");
	        fileChooser.setFileFilter(filter);

	        
	        //Abrimos el fichero
	        int seleccion;

	        seleccion =fileChooser.showOpenDialog(null);

	        if (seleccion == JFileChooser.APPROVE_OPTION)
	        {
	            File[] files = fileChooser.getSelectedFiles();
	            
	            //Si algun fichero no esta soportado, lo quitamos de la 
	            //seleccion.
	            for (int i = 0 ; i < files.length ; i++) {
	            	if(!(filter.accept(files[i]))){
	            		files[i] = null;
	            	}
	            }
	            return files;
	        }
	        else
	        {
	            return null;
	        }
	    }
	
	// ********************************************************************** //
	// *************              METODOS PUBLICOS             ************* //
	// ********************************************************************** //
	@Override
	public boolean play() {
		reproductor.play();
		return true;
	}
	
	@Override
	public void pause() {
		reproductor.pausar();
		
	}
	
	@Override
	public void stop() {
		reproductor.stop();
		
	}
	
	@Override
	public void fastForward() {
		reproductor.fastForward();
		
	}

	@Override
	public void rewind() {
		reproductor.rewind();
	}
	

	@Override
	public void irA(float i) {
		reproductor.irA(i);
		
	}
	
	@Override
	public void cancionAnterior() {
		this.reproductor.siguiente();
	}

	@Override
	public void siguienteCancion() {
		this.reproductor.anterior();
	}

	@Override
	public void añadir() {
		File[] files = abrirArchivo();
		if(files != null) {
			for (File f : files) {
				if (f != null) {
					reproductor.aniadir(f.getAbsolutePath());
				}
			}
		}
	}

	@Override
	public void abrirArchivos() {
		File[] files = abrirArchivo();
		if(files != null) {
			reproductor.stop();
			reproductor.reiniciaListaReproduccion();
			for (File f : files) {
				if (f != null) {
					reproductor.aniadir(f.getAbsolutePath());
				}
			}
		}
		reproductor.play();
	}
	

	@Override
	public void borrarCanciones() {
		// TODO Implementar
		
	}

	@Override
	public void reproducirSeleccionada() {
		// TODO Implementar
		
	}

}
