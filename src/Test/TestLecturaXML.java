package Test;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.Cancion;
import is2011.biblioteca.contenedores.Directorio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.thoughtworks.xstream.XStream;

public class TestLecturaXML {

	public static void main(String[] args) {
		BibliotecaContainer bib;
		
		XStream stream = new XStream();
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("cancion", Cancion.class);
		stream.alias("dir", Directorio.class);
		
		// IMPORTANTE para que no aparezca en el fichero XML
		stream.omitField(BibliotecaContainer.class, "modificado");
		
		
		try {
			//Leemos del fihero XML y lo cargamos en la estructura de biblioteca
			bib = (BibliotecaContainer) stream.fromXML(new FileInputStream("/Users/david/Documents/workspace/ISGrupo12/src/Recursos/testXML1.xml"));
			//==> si estas en windoes debes comentar la linea anterior y descomentar la siguiente,
			//==> sustituyendo la ruta hasta el workspace
			//bib = (BibliotecaContainer) stream.fromXML(new FileInputStream("c:// "tu ruta al workspace" /workspace/ISGrupo12/src/Recursos/testXML1.xml"));
			
			
			//Mostramos por patalla el objeto biblioteca reconvertido a XML
			System.out.println(stream.toXML(bib));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}
