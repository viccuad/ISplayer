package is2011.reproductor.modelo;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;

public class CancionMP3 implements Cancion{

	private MP3AudioHeader mp3AudioHeader;
	private File cancion;
	
	public CancionMP3(String path){
		
		cancion = new File(path);
		
		try {
			mp3AudioHeader = new MP3AudioHeader(cancion);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
		}


	}
	public int getTrackLength() {
		return mp3AudioHeader.getTrackLength();
		
	}
	
	public String getMpegVersion() {
		return mp3AudioHeader.getMpegVersion();
	}

	public String getMpegLayer() {
		return mp3AudioHeader.getMpegLayer();	
	}

	public boolean isOriginal() {
		return mp3AudioHeader.isOriginal();
	}

	public boolean isCopyrighted() {
		return mp3AudioHeader.isCopyrighted();
	}

	public boolean isPrivate() {
		return mp3AudioHeader.isPrivate();
	}

	public boolean isProtected() {
		return mp3AudioHeader.isProtected();
	}

	public long getBitRate() {
		return mp3AudioHeader.getBitRateAsNumber();
	}

	public String getEncodingType() {
		return mp3AudioHeader.getEncodingType();
		
	}
	
	public long getNumeroFrames(){
		return mp3AudioHeader.getNumberOfFrames();
	}

}
