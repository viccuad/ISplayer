package is2011.reproductor.modelo;

public interface Cancion {
	
	public String getPath();
	public String getName();
	public String getAlbum();
	public String getCompositor();
	public String getPista();
	public String getEncodingType();
	public String getInfo();
	public String getMpegVersion();
	public String getMpegLayer();
	public boolean isOriginal();
	public boolean isCopyrighted();
	public boolean isPrivate();
	public boolean isProtected();
	public boolean isVariableBitRate();
	public long getBitRate();
	public long getFrames();
	public int getTrackLength();
	public int getBytesMusica();

}
