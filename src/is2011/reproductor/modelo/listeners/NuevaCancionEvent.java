/**
 * Este paquete contiene las interfaces del modelo del reproductor, las cuales
 * se deberan implementar por las vistas pra poder visualizar los datos del
 * modelo (El play list, cancion actual...)
 */
package is2011.reproductor.modelo.listeners;

/**
 * Evento que alamacena toda la informacion de una cancion.
 * @author Administrator
 */
public class NuevaCancionEvent {

	private String titulo;
	private String album;
	private String artista;
	private int duracion;
	private int posicion;
	private int pista;
	private String genero;
	


	public NuevaCancionEvent(String titulo, String album, int pista,
			String artista,String genero,int duracion, int posicion) {
		this.titulo = titulo;
		this.album = album;
		this.artista = artista;
		this.genero = genero;
		this.duracion = duracion;
		this.posicion = posicion;
		this.pista = pista;
	}

	
	
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	
	public int getPista() {
		return pista;
	}
	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}
	/**
	 * @return the artista
	 */
	public String getArtista() {
		return artista;
	}
	/**
	 * @return the duracion
	 */
	public int getDuracion() {
		return duracion;
	}



	/**
	 * @return the posicion
	 */
	public int getPosicion() {
		return posicion;
	}
	
	
	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}


	
}
