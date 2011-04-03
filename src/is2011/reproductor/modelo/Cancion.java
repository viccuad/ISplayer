package is2011.reproductor.modelo;

public interface Cancion {
	public int getTrackLength();
	public String getMpegVersion();
	public String getMpegLayer();
	public boolean isOriginal();
	public boolean isCopyrighted();
	public boolean isPrivate();
	public boolean isProtected();
	public long getBitRate();
	public String getEncodingType();
	public long getNumeroFrames();
}
