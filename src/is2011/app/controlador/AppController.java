package is2011.app.controlador;


import java.awt.FileDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import is2011.app.preferencias.PreferenciasSistema;
import is2011.biblioteca.BibliotecaMusical;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.search.CriterioBusqueda;
import is2011.biblioteca.sorts.SortAlbum;
import is2011.biblioteca.sorts.SortArtista;
import is2011.biblioteca.sorts.SortDuracion;
import is2011.biblioteca.sorts.SortGenero;
import is2011.biblioteca.sorts.SortTitulo;
import is2011.reproductor.controlador.ControladorReproductor;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;


/**
 * @see IAppController
 */
public class AppController implements IAppController {
	
	// ********************************************************************** //
	// *************           ATRIBUTOS Y CONSTANTES           ************* //
	// ********************************************************************** //
	
	/**Reproductor de la aplicacion*/
	private ControladorReproductor reproductor;
	/** Biblioteca de la aplicacion */
	private BibliotecaMusical biblioteca;
	/** Archivo de preferencias del sistema */
	private PreferenciasSistema preferencias;
	
	// ********************************************************************** //
	// *************                CONSTRUCTOR                 ************* //
	// ********************************************************************** //
	/**
	 * Constructor por defecto.
	 */
	public AppController(ControladorReproductor rep, BibliotecaMusical bib) {
		reproductor = rep;
		try{
			
			String home = System.getProperty("user.home");
			home = home+File.separator+"ISPlayer";
			
			File f = new File(home);
			f.mkdir();
			
			home = home+File.separator;
			
			File pref = new File(home+"ISPlayerPreferences.xml");
			
			if (pref.canRead()){
				// Si ya tienes fichero de preferencias
				preferencias = new PreferenciasSistema();
				preferencias.cargarXML(home+"ISPlayerPreferences.xml");
			}else{
				// Si todavia no tienes fichero de preferencias
				preferencias = new PreferenciasSistema(home+"ISPlayerBiblioteca.xml", home+"ISPlayerListaReproduccion.xml", ModoReproduccionEnum.NORMAL);
				preferencias.guardarXML(home+"ISPlayerPreferences.xml");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		biblioteca = bib;
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
	        //FileDialog fileChooser = new FileDialog(new JFrame(), "Cargar", FileDialog.LOAD);
	        //Lo configuramos para permitir apertura multiple
	        fileChooser.setMultiSelectionEnabled(true);
	        
	        //Lo configuramos para que solo permita la apertura de ficheros
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.setAcceptAllFileFilterUsed(true);
	        
	        //Añadimos un filtro para permitir solo apertura de tipo plg
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        		"mp3 & ogg", "mp3", "ogg","MP3", "Mp3", "mP3", "OGG" , "OGg" 
	        				,"oGG" ,"oGg", "ogG");
	        
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
	public boolean play(int cancionSeleccionada) {
		reproductor.play(cancionSeleccionada);
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
		this.reproductor.anterior();
	}

	@Override
	public void siguienteCancion() {
		this.reproductor.siguiente();
	}

	@Override
	public void aniadir() {
		File[] files = abrirArchivo();
		if(files != null) {
			for (File f : files) {
				if (f != null) {
					reproductor.aniadirCancion(f.getAbsolutePath());
				}
			}
		}
	}

	@Override
	public void abrirArchivos() {
		File[] files = abrirArchivo();
		if(files != null) {
			reproductor.stop();
			reproductor.reiniciaListaReproduccion(false);
			for (File f : files) {
				if (f != null) {
					reproductor.aniadirCancion(f.getAbsolutePath());
				}
			}
			reproductor.play(-1);
		}
		
	}
	
	
	@Override
	public void setModoReproduccion(ModoReproduccionEnum modo) {
		this.reproductor.setModoRepdroduccion(modo);
	}
	
	@Override
	public void borrarCanciones() {
		// TODO Implementar
		
	}

	@Override
	public void reproducirSeleccionada() {
		// TODO Implementar
		
	}

	@Override
	public boolean listaReproduccionVacia() {
		return reproductor.listaReproduccionVacia();
	}
	
	@Override
	public void crearBiblioteca() {
		
		String ruta = "";
		ArrayList<String> dir = new ArrayList<String>();
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int seleccion;
		seleccion =fileChooser.showOpenDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){			
			File[] files = fileChooser.getSelectedFiles();
			for (File f : files) {		
				ruta = f.getAbsolutePath();
				dir.add(ruta);
				System.out.println(f.getAbsolutePath());
			}			
		}
		
		biblioteca.actualizarDirectorios(dir);
	}

	@Override
	public void actualizarBiblioteca() {
		
		biblioteca.actualizar();
	}

	@Override
	public void cargarBiblioteca() {

		String ruta = "";
		
		ArrayList<String> dir = new ArrayList<String>();

		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		int seleccion;
		seleccion =fileChooser.showOpenDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){					
			ruta = fileChooser.getSelectedFile().getAbsolutePath();					
		}
		try {
			biblioteca.cargarXML(ruta);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void guardarBiblioteca() {
		String ruta = "";
		
		ArrayList<String> dir = new ArrayList<String>();
		
		JFileChooser fileChooser = new JFileChooser();
		
		int seleccion;
		seleccion =fileChooser.showSaveDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){					
			ruta = fileChooser.getSelectedFile().getAbsolutePath();					
		}
		
		try {
			biblioteca.guardarXML(ruta);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void aniadirCancionesBiblioteca() {
		
		ArrayList<String> dir = new ArrayList<String>();
		File[] files = abrirArchivo();
		if(files != null) {
			for (File f : files) {
				if (f != null) {
					dir.add(f.getAbsolutePath());
				}
			}
		}
		biblioteca.aniadirCanciones(dir);		
	}

	@Override
	public void aniadirCancionesYCarpetasBiblioteca() {

		String ruta = "";
		ArrayList<String> dir = new ArrayList<String>();
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		
		int seleccion;
		seleccion =fileChooser.showOpenDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){			
			File[] files = fileChooser.getSelectedFiles();
			for (File f : files) {		
				ruta = f.getAbsolutePath();
				dir.add(ruta);
				System.out.println(f.getAbsolutePath());
			}			
		}

		biblioteca.aniadir(dir);	
	}
	@Override
	public ArrayList<CancionContainer> getCanciones() {
		return biblioteca.getCanciones();
	}


	@Override
	public void setVolumen(float porcentaje) {
		this.reproductor.setVolumen(porcentaje);
	}

	public void fromBibliotecaToListaReproduccion(String path){
		reproductor.aniadirCancion(path);
	}
	
	public void actualizaPreferencias(String bib, String listaRep, ModoReproduccionEnum modo){
		
		preferencias.setPathBiblioteca(bib);
		preferencias.setPathListaReproduccion(listaRep);
		preferencias.setModoReproduccion(modo);
		
		System.out.println("Modificado archivo de preferencias");
		
		try {
			preferencias.guardarXML(preferencias.getPathPreferenciasSistema());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void cargarArchivosPreferencias(){
		
		// Carga la biblioteca si exite
		File bibXML = new File(preferencias.getPathBiblioteca());
		if (bibXML.canRead()) {
			try {
				biblioteca.cargarXML(preferencias.getPathBiblioteca());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		// Carga la lista de reproduccion si existe
		File listaRepXML = new File(preferencias.getPathListaReproduccion());
		if (listaRepXML.canRead()) {

			reproductor.cargarListaReproduccion(preferencias.getPathListaReproduccion());

		}
	}

	@Override
	public PreferenciasSistema getPreferencias() {
		return preferencias;
	}

	@Override
	public void borrarCancion(int numCancion) {
		this.reproductor.borrarCancion(numCancion);
		
	}

	
	@Override
	public void ordenarPorAlbum() {
		this.reproductor.sort(new SortAlbum());
	}

	@Override
	public void ordenarPorArtista() {
		this.reproductor.sort(new SortArtista());
	}

	
	@Override
	public void ordenarPorDuracion() {
		this.reproductor.sort(new SortDuracion());
	}
	
	@Override
	public void ordenarPorGenero() {
		this.reproductor.sort(new SortGenero());
	}

	@Override
	public void ordenarPorTitulo() {
		this.reproductor.sort(new SortTitulo());
	}

	@Override
	public void guardarListaReproduccion() {
		String ruta = "";
		
		ArrayList<String> dir = new ArrayList<String>();
		
		JFileChooser fileChooser = new JFileChooser();
		
		int seleccion;
		seleccion =fileChooser.showSaveDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){					
			ruta = fileChooser.getSelectedFile().getAbsolutePath();					
		}

		reproductor.guardarListaReproduccion(ruta);
		
	}

	@Override
	public void cargarListaReproduccion() {
		String ruta = "";
		
		ArrayList<String> dir = new ArrayList<String>();

		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		int seleccion;
		seleccion =fileChooser.showOpenDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){					
			ruta = fileChooser.getSelectedFile().getAbsolutePath();					
		}
		
		reproductor.cargarListaReproduccion(ruta);
	}

	public ArrayList<CancionContainer> buscaBiblioteca(CriterioBusqueda criterio){
		return biblioteca.getBusqueda(criterio);
	}

	public ArrayList<CancionContainer> buscaBibliotecaAvanzada(CriterioBusqueda criterio){
		return biblioteca.getBusquedaAvanzada(criterio);
	}

	public ArrayList<CancionContainer> buscaListaReproduccion(CriterioBusqueda criterio){
		return reproductor.getBusqueda(criterio);
	}
	public ArrayList<CancionContainer> buscaListaReproduccionAvanzada(CriterioBusqueda criterio){
		return reproductor.getBusquedaAvanzada(criterio);
	}

	@Override
	public ArrayList<CancionContainer> getCancionesListaReproduccion() {
		return reproductor.getCancionesListaReproduccion();
	}
}
