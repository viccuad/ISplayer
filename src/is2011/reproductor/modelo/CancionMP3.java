package is2011.reproductor.modelo;



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

public class CancionMP3 implements Cancion{
	
	private AudioFile filemp3=null;
	private MP3AudioHeader headermp3=null;
	private String path;
	private Tag tag=null;
	
	public CancionMP3(String p){
		
		//FileDialog fd = new FileDialog(new JFrame(), "Prueba", FileDialog.LOAD);
		//fd.setVisible(true);
		path = p;//fd.getDirectory()+fd.getFile();
		try {
			try {
				
				filemp3 = AudioFileIO.read(new File(path));
				tag = filemp3.getTag();
				headermp3 = (MP3AudioHeader) filemp3.getAudioHeader();

										
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TagException e) {
				e.printStackTrace();
			} catch (ReadOnlyFileException e) {
				e.printStackTrace();
			} catch (InvalidAudioFrameException e) {
				e.printStackTrace();
			}
		} catch (CannotReadException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isVariableBitRate() {
		return headermp3.isVariableBitRate();
	}

	@Override
	public int getTrackLength() {
		return headermp3.getTrackLength();
	}

	@Override
	public String getMpegVersion() {
		return headermp3.getMpegVersion();
	}

	@Override
	public String getMpegLayer() {
		return headermp3.getMpegLayer();
	}

	@Override
	public boolean isOriginal() {
		return headermp3.isOriginal();
	}

	@Override
	public boolean isCopyrighted() {
		return headermp3.isCopyrighted();
	}

	@Override
	public boolean isPrivate() {
		return headermp3.isPrivate();
	}

	@Override
	public boolean isProtected() {
		return headermp3.isProtected();	
	}

	@Override
	public long getBitRate() {
		return headermp3.getBitRateAsNumber();
	}

	@Override
	public String getEncodingType() {
		return headermp3.getEncodingType();	
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getName() {
		return tag.getFirst(FieldKey.TITLE);
		
	}

	@Override
	public String getAlbum() {
		return tag.getFirst(FieldKey.ALBUM);

	}

	@Override
	public String getCompositor() {

		return tag.getFirst(FieldKey.COMPOSER);

	}

	@Override
	public String getPista() {

		return tag.getFirst(FieldKey.TRACK);

	}

	@Override
	public long getFrames() {
		return headermp3.getNumberOfFrames();
	}

	@Override
	public String getInfo() {
		return 	"Titulo: "+getName()+"\n"+
				"Album: "+getAlbum()+"\n"+
				"Duracion (s): "+getTrackLength()+"\n"+
				"Nº Frames: "+getFrames()+"\n"+
				"Bit Rate: "+getBitRate()+"\n"+
				"Path: "+getPath();
	}

	@Override
	public int getBytesMusica() {
		return (int)( (new File(path)).length() - headermp3.getMp3StartByte());
	}

}
