package is2011.biblioteca.contenedores;

public class CancionContainer {
	
	private String trackPath;
	private String titulo;
	private String album;
	private String genero;
	private String artista;
	private int duracion;   //en segundos
	
	
	public CancionContainer(String trackP, String tit, String alb, String gene, String art,  int dur){
		trackPath = trackP;
		titulo = tit;
		album = alb;
		genero = gene;
		artista = art;
		duracion = dur;
	}

	public String getTrackPath() {
		return trackPath;
	}

	public void setTrackPath(String trackPath) {
		this.trackPath = trackPath;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	
	
}
