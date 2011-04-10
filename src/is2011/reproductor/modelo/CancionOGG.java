package is2011.reproductor.modelo;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class CancionOGG implements Cancion {

	private AudioFile fileogg=null;
	private String path;
	private Tag tag=null;
	
	public CancionOGG(String p){
		path = p;
		try {
			fileogg = AudioFileIO.read(new File(path));
			tag = fileogg.getTag();
			System.out.println(tag);
			System.out.println(fileogg);
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
	public String getPath() {
		return path;
	}

	@Override
	public String getEncodingType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMpegVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMpegLayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOriginal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCopyrighted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPrivate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isProtected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVariableBitRate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getBitRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getFrames() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTrackLength() {
		return fileogg.getAudioHeader().getTrackLength();
	}

	@Override
	public int getBytesMusica() {
		return (int)(new File(path)).length();
	}
	
	@Override
	public String getName() {
		if (tag.getFirst(FieldKey.TITLE) == "") return "Desconocido";
		else return tag.getFirst(FieldKey.TITLE);
		
	}

	@Override
	public String getAlbum() {
		if (tag.getFirst(FieldKey.ALBUM) == "") return "Desconocido";
		else return tag.getFirst(FieldKey.ALBUM);
	}

	@Override
	public String getCompositor() {

		if (tag.getFirst(FieldKey.COMPOSER) == "") return "Desconocido";
		else return tag.getFirst(FieldKey.COMPOSER);

	}

	@Override
	public String getPista() {

		if (tag.getFirst(FieldKey.TRACK) == "") return "Desconocido";
		else return tag.getFirst(FieldKey.TRACK);

	}


}
