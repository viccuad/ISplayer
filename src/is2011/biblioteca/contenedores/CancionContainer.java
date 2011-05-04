package is2011.biblioteca.contenedores;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class CancionContainer {
	
	/** Ruta completa de la cancion en el disco duro */
	@XStreamOmitField
	private String totalPath;	
	
	/** Nombre de la cancion en el disco duro */
	private String trackPath;
	
	/** Título de la canción */
	private String titulo;
	
	/** Album de la canción */
	private String album;
	
	/** Género musical */
	private String genero;
	
	/** Artista de la canción */
	private String artista;
	
	/** Tiempo de duración de la cancion en segundos */
	private int duracion;  
	
	/** pista  de la canción **/
	private int pista;
	
	
	
	/**
	 * Constructor parametrizado
	 * @param trackP
	 * @param tit
	 * @param alb
	 * @param gene
	 * @param art
	 * @param dur
	 */
	public CancionContainer(String trackP, String tit, String alb, String gene, String art,  int dur, int pist){
		trackPath = trackP;
		titulo = tit;
		album = alb;
		genero = gene;
		artista = art;
		duracion = dur;
		pista = pist;
	}
	
	
	/**
	 * Crea un objeto a partir de su ruta en disco
	 * @param absolutePath ruta absoluta del fichero canción
	 */
	public CancionContainer(String absolutePath){
		this.totalPath = absolutePath;
		File fichero = new File(absolutePath);
		
		if(fichero.isFile()){     	
			try {
		    	AudioFile filemp3 = AudioFileIO.read(new File(fichero.getAbsolutePath()));
		    	Tag tag = filemp3.getTag();
		    	
		    	trackPath = fichero.getName();
		    	
		    	if(tag == null){
		    		titulo  = "Desconocido";
					album   = "Desconocido";
					genero  = "Desconocido";
					artista = "Desconocido";
					artista = "Desconocido";
					pista   = 0;
		    	}
		    	else{
			    	if (tag.getFirstField(FieldKey.TITLE) == null) titulo = "Desconocido";
					else titulo = tag.getFirst(FieldKey.TITLE);				
					
					if (tag.getFirstField(FieldKey.ALBUM) == null) album = "Desconocido";
					else album = tag.getFirst(FieldKey.ALBUM);
					
					if (tag.getFirstField(FieldKey.GENRE) == null) genero = "Desconocido";
					else genero = tag.getFirst(FieldKey.GENRE);
					
					if (tag.getFirstField(FieldKey.ARTIST) == null) artista = "Desconocido";
					else artista = tag.getFirst(FieldKey.ARTIST);
					
					if (tag.getFirstField(FieldKey.TRACK) == null) artista = "Desconocido";
					else artista = tag.getFirst(FieldKey.TRACK);
					
					if (tag.getFirstField(FieldKey.TRACK) == null) pista = 0;
					else pista = Integer.parseInt(tag.getFirst(FieldKey.TRACK));
		    	}
				
				duracion = filemp3.getAudioHeader().getTrackLength();
				
			} catch (Exception e) {
				this.trackPath = "Desconocido";
				this.titulo    = "Desconocido";
				this.album     =  "Desconocido";
				this.genero    = "Desconocido";
				this.artista   = "Desconocido";
				this.duracion  = 0;
				this.pista     = 0;
				
				e.printStackTrace();
			} 
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getTrackPath() {
		return trackPath;
	}

	
	/**
	 * 
	 * @param trackPath
	 */
	public void setTrackPath(String trackPath) {
		this.trackPath = trackPath;
	}

	
	/**
	 * 
	 * @return
	 */
	public String getTitulo() {
		return titulo;
	}

	
	/**
	 * 
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	
	/**
	 * 
	 * @return
	 */
	public String getAlbum() {
		return album;
	}

	
	/**
	 * 
	 * @param album
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	
	/**
	 * 
	 * @return
	 */
	public String getGenero() {
		return genero;
	}

	
	/**
	 * 
	 * @param genero
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	
	/**
	 * 
	 * @return
	 */
	public String getArtista() {
		return artista;
	}

	
	/**
	 * 
	 * @param artista
	 */
	public void setArtista(String artista) {
		this.artista = artista;
	}

	
	/**
	 * 
	 * @return
	 */
	public int getDuracion() {
		return duracion;
	}

	
	/**
	 * 
	 * @param duracion
	 */
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public int getPista() {
		return pista;
	}

	
	/**
	 * 
	 * @param duracion
	 */
	public void setPista(int pista) {
		this.pista = pista;
	}
	
	
	/**
	 * 
	 * @param totpath
	 */
	public void setTotalPath(String totpath){
		this.totalPath = totpath;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getTotalPath(){
		return this.totalPath;
	}
	
	
	/**
	 * Devuelve los bytes de la canción. Esto es necesario para poder reproducir
	 * canciones desde la clase contenedora
	 * @return
	 */
	public int getBytesMusica() {
		AudioFile filemp3;
		MP3AudioHeader headermp3;
		
		try {
			filemp3 = AudioFileIO.read(new File(this.totalPath));
			headermp3 = (MP3AudioHeader) filemp3.getAudioHeader();
			
			return (int)( (new File(this.totalPath)).length() - headermp3.getMp3StartByte());
			
		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public boolean equals(CancionContainer s){
		if (s==null) return false;
		if (this.getClass()!= s.getClass()) return false;
		if (this.getTitulo() != s.getTitulo()) return false;
		if (this.getDuracion() != s.getDuracion()) return false;
		if (this.getAlbum() != s.getAlbum()) return false;
		if (this.getArtista() != s.getArtista()) return false;
		if (this.getGenero() != s.getGenero()) return false;
		if (this.getPista() != s.getPista()) return false;
		if (this.getTotalPath() != s.getTotalPath()) return false;
		if (this.getTrackPath() != s.getTrackPath()) return false;
		return true;
	}

}
