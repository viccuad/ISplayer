package is2011.reproductor.modelo;


public class prueba{

	public static void main (String[] argv){

		ListaReproduccion lr = new ListaReproduccion();
		
		lr.addCancion(new CancionMP3("/Users/carlos/Downloads/13 - Green Day - Whatsername.mp3"));
		lr.addCancion(new CancionMP3("/Users/carlos/Desktop/a.mp3"));
		//lr.addCancion(new CancionOGG("/Users/carlos/Downloads/Epoq-Lepidoptera-1.ogg"));
		lr.listarTodas();		
		
		lr.borrarCancion(0);
		lr.listarTodas();

	}
}
