/**
 * 
 */
package is2011.reproductor.modelo.listeners;

/**
 * 
 * @author Administrator
 */
public class NuevaCancionEvent {

	private String titulo;
	private String album;
	private String artista;
	private int duracion;
	private int posicion;
	private String pista;
	private String genero;
	


	public NuevaCancionEvent(String titulo, String album, String pista,
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
	
	public String getPista() {
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
