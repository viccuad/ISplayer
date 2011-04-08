package Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

import com.thoughtworks.xstream.XStream;

import is2011.biblioteca.contenedores.*;

public class TestEscrituraXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XStream stream = new XStream();
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("cancion", Cancion.class);
		stream.alias("dir", Directorio.class);
		
		// IMPORTANTE para que no aparezca en el fichero XML
		stream.omitField(BibliotecaContainer.class, "modificado");
		
		BibliotecaContainer bib = new BibliotecaContainer();
		new Cancion("AAAA","AAAA","AAAA","AAAA","AAAA",0);
		HashMap<String,Cancion> listaCanciones = new HashMap<String,Cancion>();
		for(int i = 0; i< 10; i++)
			listaCanciones.put(""+i, new Cancion("AAAA","AAAA","AAAA","AAAA","AAAA",0));
		
		bib.addDir(new Directorio("ruta", listaCanciones));
		
		try {
			stream.toXML(bib, new FileOutputStream("/Users/david/Documents/workspace/ISGrupo12/src/Recursos/testXML1.xml"));
			//==> si estas en windoes debes comentar la linea anterior y descomentar la siguiente,
			//==> sustituyendo la ruta hasta el workspace
			//stream.toXML(bib, new FileOutputStream("c:/ "tu ruta al workspace" /workspace/ISGrupo12/src/Recursos/testXML1.xml"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

}
