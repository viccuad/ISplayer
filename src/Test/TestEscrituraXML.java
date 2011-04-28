package Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.thoughtworks.xstream.XStream;
import is2011.biblioteca.contenedores.*;

public class testEscrituraXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XStream stream = new XStream();
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("cancion", CancionContainer.class);
		stream.alias("dir", DirectorioContainer.class);
		
		// IMPORTANTE para que no aparezca en el fichero XML
		stream.omitField(BibliotecaContainer.class, "modificado");
		stream.omitField(CancionContainer.class, "totalPath");

		
		BibliotecaContainer bib = new BibliotecaContainer();
		bib.addDir(new DirectorioContainer("C:/hlocal/"));
		for(int i = 0; i< 10; i++)
			bib.addCancion(new CancionContainer("trackpath"+i,"tit","alb","gen","art",0), "C:/hlocal/");
			try {
			stream.toXML(bib, new FileOutputStream("src/Recursos/testXML1.xml"));
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
		System.out.println("escritura acabada");
	}

}
