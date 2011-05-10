package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.contenedores.DirectorioContainer;
import is2011.biblioteca.search.CriterioBusqueda;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JUnitBibliotecaContainer extends TestCase{

	private BibliotecaContainer bib;
	private CancionContainer c1;
	private DirectorioContainer dir;
	private HashMap<String, DirectorioContainer> hashmapdir;
	private String pathdir, path;
	
	private ArrayList<CancionContainer> listaCanciones;
	
	public JUnitBibliotecaContainer(String name) {
		super(name);
		this.setUp();
	}
	
	public void setUp(){
		String pathdir = "src/recursos/";
		String path = "src/recursos/01 Purple Haze.mp3";
		c1 = new CancionContainer(path);
		listaCanciones = new ArrayList<CancionContainer>();
		listaCanciones.add(c1);
		hashmapdir = new HashMap<String, DirectorioContainer>();
		BibliotecaContainer bib = new BibliotecaContainer(); 
		bib.addDir(path);
		dir = new DirectorioContainer(pathdir);

	}
	
	
	
	public void testAddCancion(){
		
		
		BibliotecaContainer bibprueba = new BibliotecaContainer(); // crea un directorio
		bibprueba.addDir(pathdir);
		bibprueba.addCancion(c1);
		
		assertEquals(bibprueba, bib);
	}
	/*
	public void testAddDir(){
		BibliotecaContainer bibprueba = new BibliotecaContainer(); // crea un directorio
		bibprueba.addDir(dir);
		
		assertEquals(bibprueba, bib);

	}
	
	

	public void testAddDir(){
		this.directorios.put(path, new DirectorioContainer(path));
		this.modificadoEscritura = true;
		this.modificadoParaMostrar = true;
	}
	
	

	public void testRemoveDir(){
		this.directorios.remove(path);
		this.modificadoEscritura = true;
		this.modificadoParaMostrar = true;
	}

	

	public void testExisteDirectorio(){
		return this.directorios.containsKey(path);
	}


	public void testIsModificado() {
		return modificadoEscritura;
	}
	
	public void testSetModificado(){
		modificadoEscritura = b;
	}
	
	

	public void testExisteCancion(){
		boolean existe = false;
		
		if(this.directorios.containsKey(parentPath))
			existe = this.directorios.get(parentPath).existeCancion(trackName);
		
		return existe;
	}

	

	public void testGetArrayListCanciones(){
		if(this.modificadoParaMostrar || this.listaCanciones == null)
			generarArrayListCanciones();
		
		return listaCanciones;
	}
	
	

	public void testGetListaBusqueda(){
		// si no se ha generado la lista de canciones la crea
		if(this.modificadoParaMostrar || this.listaCanciones == null)
			generarArrayListCanciones();
		
		return busqueda.buscar(this.listaCanciones);
	}
	

	public void testGetListaBusquedaAvanzada(){
		// si no se ha generado la lista de canciones la crea
		if(this.modificadoParaMostrar || this.listaCanciones == null)
			generarArrayListCanciones();
		
		return busqueda.buscarAvanzado(this.listaCanciones);
	}
	
	

	public void testGenerarRutasAbsolutas(){
		Iterator<Entry<String, DirectorioContainer>> it =  this.directorios.entrySet().iterator();
		while(it.hasNext())
			it.next().getValue().actualizarPathCanciones();
	}
	

	private void testGenerarArrayListCanciones(){
		ArrayList<CancionContainer> canciones = new ArrayList<CancionContainer>();
		
		Iterator<Entry<String, DirectorioContainer>> it =  this.directorios.entrySet().iterator();
		while(it.hasNext())
			canciones.addAll(it.next().getValue().getListaCanciones());
		
		listaCanciones = canciones;
	}
	


	public void testGuardado() {
		this.modificadoEscritura = false;
	}


	public void testGetDirectorios() {
		return new ArrayList<String>(this.directorios.keySet());
	}
	
	
	*/
	public static TestSuite suite(){
		TestSuite raiz=new TestSuite("Biblioteca Container");
		
		TestSuite suite1=new TestSuite("Cancion vacia");
		
		TestSuite suite2=new TestSuite("Cancion");
		suite2.addTest(new JUnitBibliotecaContainer("testAddCancion"));
		
		raiz.addTest(suite1);
		raiz.addTest(suite2);
		return raiz;		
	}
	
	
	public boolean assertEquals(CancionContainer c1, CancionContainer c2){
		return c1.equals(c2);
	}
	
	public boolean assertEquals(ArrayList<CancionContainer>  c1, ArrayList<CancionContainer>  c2){
		return c1.equals(c2);
	}
	
}
