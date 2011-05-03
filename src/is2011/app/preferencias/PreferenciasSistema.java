package is2011.app.preferencias;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;

public class PreferenciasSistema {
	
	public String pathBiblioteca;
	public String pathListaReproduccion;
	public ModoReproduccionEnum modoReproduccion;
	
	/** Flujo de lectura/escritura para ficheros XML */
	
	private XStream stream;
	
	public PreferenciasSistema(){
		pathBiblioteca = null;
		pathListaReproduccion = null;
		modoReproduccion = null;
		stream = new XStream(new DomDriver());
		stream.alias("Preferencias", PreferenciasSistema.class);
		stream.omitField(PreferenciasSistema.class, "stream");
	}
	
	public PreferenciasSistema(String bib, String listaRep, ModoReproduccionEnum modoRep){
		pathBiblioteca = bib;
		pathListaReproduccion = listaRep;
		modoReproduccion = modoRep;
		stream = new XStream(new DomDriver());
		stream.alias("Preferencias", PreferenciasSistema.class);
		stream.omitField(PreferenciasSistema.class, "stream");
	}
	

	public void cargarXML(String pathYfichero) throws FileNotFoundException{
		PreferenciasSistema aux = (PreferenciasSistema) stream.fromXML(new FileInputStream(pathYfichero));
		setPathBiblioteca(aux.getPathBiblioteca());
		setPathListaReproduccion(aux.getPathListaReproduccion());
		setModoReproduccion(aux.getModoReproduccion());
	}
	
	
	public void guardarXML(String pathYfichero) throws FileNotFoundException{

		stream.toXML(this, new FileOutputStream(pathYfichero));
	}
	
	public String getPathBiblioteca(){
		return pathBiblioteca;
	}
	
	public String getPathListaReproduccion(){
		return pathBiblioteca;
	}
	
	public ModoReproduccionEnum getModoReproduccion(){
		return modoReproduccion;
	}
	
	public void setPathBiblioteca(String input){
		pathBiblioteca = input;
	}
	
	public void setPathListaReproduccion(String input){
		pathBiblioteca = input;
	}
	
	public void setModoReproduccion(ModoReproduccionEnum input){
		modoReproduccion = input;
	}
}
