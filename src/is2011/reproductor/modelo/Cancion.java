package is2011.reproductor.modelo;

public interface Cancion {
	
	public String getPath();
	public String getName();
	public String getAlbum();
	public String getGenero();
	public String getCompositor();
	public String getPista();
	public String getEncodingType();
	public String getInfo();

	public boolean isVariableBitRate();
	public long getBitRate();
	public long getFrames();
	public int getTrackLength();
	public int getBytesMusica();
	

}
