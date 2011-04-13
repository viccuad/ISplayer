package is2011.reproductor.modelo;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class CancionOGG implements Cancion {

	private AudioFile fileogg=null;
	private String path;
	private AudioHeader headerogg=null;
	private Tag tag=null;
	
	public CancionOGG(String p){
		path = p;
		try {
			fileogg = AudioFileIO.read(new File(path));
			tag = fileogg.getTag();
			headerogg = fileogg.getAudioHeader();
			
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
		
	}
	@Override
	public boolean isVariableBitRate() {
		return headerogg.isVariableBitRate();
	}

	@Override
	public int getTrackLength() {
		return headerogg.getTrackLength();
	}

	@Override
	public long getBitRate() {
		return headerogg.getBitRateAsNumber();
	}

	@Override
	public String getEncodingType() {
		return headerogg.getEncodingType();	
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public int getBytesMusica() {
		return (int)(new File(path)).length();
	}
	
	@Override
	public String getName() {
		if (tag.getFirstField(FieldKey.TITLE) == null) return "Desconocido";
		else return tag.getFirst(FieldKey.TITLE);
		
	}

	@Override
	public String getAlbum() {
		if (tag.getFirstField(FieldKey.ALBUM) == null) return "Desconocido";
		else return tag.getFirst(FieldKey.ALBUM);
	}

	@Override
	public String getCompositor() {

		if (tag.getFirstField(FieldKey.COMPOSER) == null) return "Desconocido";
		else return tag.getFirst(FieldKey.COMPOSER);

	}

	@Override
	public String getGenero() {

		if (tag.getFirstField(FieldKey.GENRE) == null) return "Desconocido";
		else return tag.getFirst(FieldKey.GENRE);

	}

	
	@Override
	public String getPista() {

		if (tag.getFirstField(FieldKey.TRACK) == null) return "0";
		else return tag.getFirst(FieldKey.TRACK);

	}
	@Override
	public String getInfo() {
		System.out.println(headerogg.toString());
		return 	"Titulo: "+getName()+"\n"+
				"Album: "+getAlbum()+"\n"+
				"Duracion (s): "+getTrackLength()+"\n"+
				"Nº Frames: "+getFrames()+"\n"+
				"Bit Rate: "+getBitRate()+"\n"+
				"Path: "+getPath();
	}
	@Override
	public long getFrames() {
		return 0;
	}


}
