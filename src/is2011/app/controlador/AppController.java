package is2011.app.controlador;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import is2011.app.preferencias.Preferencias;
import is2011.app.vista.VistaPrincipal;
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
	private Preferencias preferencias;
	/** Referencia a la vista principal*/
	private VistaPrincipal vPrincipal;
	
	// ********************************************************************** //
	// *************                CONSTRUCTOR                 ************* //
	// ********************************************************************** //
	/**
	 * Constructor por defecto.
	 */
	public AppController(ControladorReproductor rep, BibliotecaMusical bib, 
			VistaPrincipal vp) {
		reproductor = rep;
		biblioteca = bib;
		preferencias = Preferencias.getInstance();
		vPrincipal = vp;
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
	        JFileChooser fileChooser = 
	        	new JFileChooser(Preferencias.getInstance().
	        			getUltimoDirectorioAbierto());

	        //Lo configuramos para permitir apertura multiple
	        fileChooser.setMultiSelectionEnabled(true);
	        
	        //Lo configuramos para que solo permita la apertura de ficheros
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.setAcceptAllFileFilterUsed(true);
	        
	        //Añadimos un filtro para permitir solo apertura de tipo plg
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        		"mp3", "mp3","MP3", "Mp3", "mP3");
	        

	        fileChooser.setFileFilter(filter);

	        
	        //Abrimos el fichero
	        int seleccion;

	        seleccion =fileChooser.showOpenDialog(null);

	        if (seleccion == JFileChooser.APPROVE_OPTION)
	        {
	            File[] files = fileChooser.getSelectedFiles();
	            if(files.length>0) {
	            	this.preferencias.setUltimoDirectorioAbierto(
	            			files[0].getParentFile().getAbsolutePath());
	            }
	            
	            //Si algun fichero no esta soportado, lo quitamos de la 
	            //seleccion.
	            ArrayList<File> filesAux = new ArrayList<File>();
	            for (int i = 0 ; i < files.length ; i++) {
	            	if(filter.accept(files[i])){
	            		filesAux.add(files[i]);
	            	}
	            }
	            
	            File[] aux = new File[filesAux.size()];
	            
	            int i = 0;
	            for(File f : filesAux) {
	            	aux[i++] = f;
	            }
	            
	            return aux;
	        }
	        else
	        {
	            return null;
	        }
	    }
	
	// ********************************************************************** //
	// *************              METODOS PUBLICOS              ************* //
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
	public void mute() {
		this.reproductor.mute();
		
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
		if( (files != null) && (files.length > 0) ) {
			reproductor.stop();
			reproductor.reiniciaListaReproduccion(false);
			for (File f : files) {
				if (f != null) {
					reproductor.aniadirCancion(f.getAbsolutePath());
				}
			}
			this.siguienteCancion();
		}
		
	}
	
	
	@Override
	public void setModoReproduccion(ModoReproduccionEnum modo) {
		this.reproductor.setModoRepdroduccion(modo);
	}
	
	@Override
	public boolean listaReproduccionVacia() {
		return reproductor.listaReproduccionVacia();
	}
	
	@Override
	public void crearBiblioteca() {
		
		int seleccion;
		
		//Miramos is ya existe una biblioteca
		
		File fAux = new File (Preferencias.getInstance().getPathBiblioteca());
		
		//Si existe avisamos que se va a borrar
		if(fAux.exists() && !biblioteca.isVacia()) {
			seleccion = JOptionPane.showConfirmDialog(null, 
					"Al crear una biblioteca se borrara la existente\n" +
					"¿Desea continuar?", "Crear biblioteca", 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE);
		} else {
			seleccion = JOptionPane.YES_OPTION;
		}
		

		if(seleccion == JOptionPane.YES_OPTION) {
			String ruta = "";
			ArrayList<String> dir = new ArrayList<String>();


			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(true);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			seleccion =fileChooser.showOpenDialog(null);
			if (seleccion == JFileChooser.APPROVE_OPTION){			
				File[] files = fileChooser.getSelectedFiles();
				for (File f : files) {		
					ruta = f.getAbsolutePath();
					dir.add(ruta);
					
				}			
			}

			Thread t = new Thread(new CrearBiblioteca(dir));
			t.start();
		}
	}

	@Override
	public void actualizarBiblioteca() {
		Thread t = new Thread(new ActualizarBiblioteca());
		t.start();
	}

	@Override
	public void cargarBiblioteca() {

		String ruta = "";
		
		//ArrayList<String> dir = new ArrayList<String>();

		
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
		try {
			//File f = new File(Preferencias.getInstance().getPathBiblioteca());
			//f.mkdirs();
			biblioteca.guardarXML(Preferencias.getInstance().getPathBiblioteca());
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
		this.guardarBiblioteca();
	}

	@Override
	public void aniadirCancionesYCarpetasBiblioteca() {

		String ruta = "";
		ArrayList<String> dir = new ArrayList<String>();
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int seleccion;
		seleccion =fileChooser.showOpenDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){			
			File[] files = fileChooser.getSelectedFiles();
			for (File f : files) {		
				ruta = f.getAbsolutePath();
				dir.add(ruta);
				
			}			
		}

		Thread t = new Thread(new AniadirDirectorios(dir));
		t.start();
		
		
	}
	@Override
	public ArrayList<CancionContainer> getCanciones() {
		
		if (biblioteca.getBusquedaRealizada()) 
			return biblioteca.getCancionesBuscadas();
		else return biblioteca.getCanciones();
	}


	@Override
	public void setVolumen(float porcentaje) {
		this.reproductor.setVolumen(porcentaje);
	}

	public void fromBibliotecaToListaReproduccion(CancionContainer c){
		reproductor.aniadirCancion(c);
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
		String path;
		
		if(!preferencias.getPathListaReproduccionDefecto().equals("")) {
			path = preferencias.getPathListaReproduccionDefecto();
			
			File listaRepXML = new File(path);
			if (listaRepXML.canRead()) {
				
				reproductor.cargarListaReproduccion(path);

			}

		}
	}

	@Override
	public Preferencias getPreferencias() {
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
		String s = (String)JOptionPane.showInputDialog(
				null,
				"Introduzca el nombre de la lista de reproduccion","",
				JOptionPane.PLAIN_MESSAGE);

		//If a string was returned, say so.
		if ((s != null && !s.equals(""))){
			ruta = Preferencias.getInstance().
			 getDirecctorioListasDeReproduccion() + File.separator + s + ".xml";
			
			reproductor.guardarListaReproduccion(ruta);
			vPrincipal.refrescarVistaLateral();
		}

	}

	@Override
	public void cargarListaReproduccion() {
		String ruta = "";
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		int seleccion;
		seleccion =fileChooser.showOpenDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){					
			ruta = fileChooser.getSelectedFile().getAbsolutePath();					
		}
		
		reproductor.cargarListaReproduccion(ruta);
	}


	public void buscaBibliotecaAvanzada(CriterioBusqueda criterio){
		 biblioteca.realizaBusquedaAvanzada(criterio);
	}

	
	public void buscaListaReproduccionAvanzada(CriterioBusqueda criterio){
		reproductor.getBusquedaAvanzada(criterio);
	}

	@Override
	public ArrayList<CancionContainer> getCancionesListaReproduccion() {
		return reproductor.getCancionesListaReproduccion();
	}
	
	@Override
	public void fromBibliotecaToListaReproduccion(int posicion) {
		this.reproductor.aniadirCancion(
				this.biblioteca.getCanciones().get(posicion).getTotalPath());
		;
	}

	@Override
	public void fromBibliotecaToListaReproduccion(int[] posiciones) {
		for (int i : posiciones) {
			this.reproductor.aniadirCancion(
					this.biblioteca.getCanciones().get(i).getTotalPath());
			
		}
		
	}

	
	@Override
	public void ordenarBibliotecaPorAlbum() {
		this.biblioteca.ordenar(new SortAlbum());
	}

	@Override
	public void ordenarBibliotecaPorArtista() {
		this.biblioteca.ordenar(new SortArtista());
		
	}


	@Override
	public void ordenarBibliotecaPorDuracion() {
		this.biblioteca.ordenar(new SortDuracion());
	}

	@Override
	public void ordenarBibliotecaPorGenero() {
		this.biblioteca.ordenar(new SortGenero());
	}

	@Override
	public void ordenarBibliotecaPorTitulo() {
		this.biblioteca.ordenar(new SortTitulo());
	}

	@Override
	public void requestSalir() {
		//Guardamos la lista actual.
		String ruta = Preferencias.getInstance().getPathUltimaLista();
		reproductor.guardarListaReproduccion(ruta);
		
		
		if(Preferencias.getInstance().isHayCambios()) {
			Preferencias.getInstance().guardarXML();

		}
	}

	@Override
	public void openLR(String string) {
		String ruta = Preferencias.getInstance().getDirecctorioListasDeReproduccion() + 
		File.separator + string + ".xml";
		reproductor.cargarListaReproduccion(ruta);
	}

	/* (non-Javadoc)
	 * @see is2011.app.controlador.IAppController#muestraListaReproduccion()
	 */
	@Override
	public void muestraListaReproduccion() {
		this.vPrincipal.muestraListaRep();
		
	}

	/* (non-Javadoc)
	 * @see is2011.app.controlador.IAppController#borrarListaReproduccion()
	 */
	@Override
	public void borrarListaReproduccion() {
		reproductor.reiniciaListaReproduccion(false);
	}

	@Override
	public void lanzarAyuda() {
		try{
		    String osName = System.getProperty("os.name");
		    
		    
		    if(osName.startsWith("Windows")) {
		      
		    	File f = new File("src/Recursos/manual.pdf");
		    	java.awt.Desktop.getDesktop().open(f);
	

		
		    } else if (osName.startsWith("Mac OS X")) {
		  	  
				
		  //Runtime.getRuntime().exec("open -a Safari src/Recursos/index.html");
		    	File f = new File("src/Recursos/manual.pdf");
		    	java.awt.Desktop.getDesktop().open(f);

		
		    }else if (osName.startsWith("Linux")) {
		  	  
				
		       //Runtime.getRuntime().exec("./firefox src/Recursos/index.html");
				File f = new File("src/Recursos/manual.pdf");
		    	java.awt.Desktop.getDesktop().open(f);
		
		    }
	    
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(vPrincipal, 
					"Necesita tener instalado software " +
					"para abrir archivos con extension .pdf", 
					"Error", JOptionPane.ERROR_MESSAGE);

		}
		
	}



	private class CrearBiblioteca implements Runnable {

		ArrayList<String> dir;

		public CrearBiblioteca(ArrayList<String> dir) {
			this.dir = dir;
		}

		public void run() {
			vPrincipal.bloqueaBiblioteca();

			biblioteca.creaDirectorios(dir);
			guardarBiblioteca();

			vPrincipal.activaBiblioteca();
			vPrincipal.mostrarBiblioteca();
		}
	}

	private class ActualizarBiblioteca implements Runnable {

		public void run() {
			vPrincipal.bloqueaBiblioteca();
			biblioteca.actualizar();
			guardarBiblioteca();

			vPrincipal.activaBiblioteca();
			vPrincipal.mostrarBiblioteca();
		}
	}

	private class AniadirDirectorios implements Runnable {
		ArrayList<String> dir;

		public AniadirDirectorios(ArrayList<String> dir) {
			this.dir = dir;
		}

		public void run() {
			vPrincipal.bloqueaBiblioteca();
			
			
			biblioteca.aniadir(dir);	
			guardarBiblioteca();
			
			vPrincipal.activaBiblioteca();
			vPrincipal.mostrarBiblioteca();
		}
	}




}
