/**
 * 
 */
package is2011.reproductor.vista;

import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 * @author Administrator
 *
 */
public class VistaReproduccion implements BasicPlayerListener {

	
	/**
	 * Archivo abierto. Mostramos el tiempo total de la cancion, asi como el bitrate.
	 */
	public void opened(Object arg0, Map properties) {
		System.out.println("Archivo abierto");
		System.out.println("Tiempo total estimado: " + this.getTimeLengthEstimation(properties));
		
		if (properties.containsKey("audio.samplesize.bits"))
        {
            int bitspersample = ((Integer) properties.get("audio.samplesize.bits")).intValue();
            System.out.println("Bitrate: " + bitspersample);
        }
		
	}

	/* (non-Javadoc)
	 * @see javazoom.jlgui.basicplayer.BasicPlayerListener#progress(int, long, byte[], java.util.Map)
	 */
	@Override
	public void progress(int bytesread, long arg1, byte[] arg2, Map properties) {
		
		int audioFramerate = (Integer)properties.get("mp3.frame.size.bytes");
		int audioFramesize = (Integer)properties.get("mp3.frame.bitrate");
		System.out.println("Tiempo..." + ((bytesread / audioFramerate*audioFramesize/8)/1000000));

	}

	/* (non-Javadoc)
	 * @see javazoom.jlgui.basicplayer.BasicPlayerListener#setController(javazoom.jlgui.basicplayer.BasicController)
	 */
	@Override
	public void setController(BasicController arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javazoom.jlgui.basicplayer.BasicPlayerListener#stateUpdated(javazoom.jlgui.basicplayer.BasicPlayerEvent)
	 */
	@Override
	public void stateUpdated(BasicPlayerEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private long getTimeLengthEstimation(Map properties)
    {
        long milliseconds = -1;
        int byteslength = -1;
        if (properties != null)
        {
            if (properties.containsKey("audio.length.bytes"))
            {
                byteslength = ((Integer) properties.get("audio.length.bytes")).intValue();
            }
            if (properties.containsKey("duration"))
            {
                milliseconds = (int) (((Long) properties.get("duration")).longValue()) / 1000;
            }
            else
            {
                // Try to compute duration
                int bitspersample = -1;
                int channels = -1;
                float samplerate = -1.0f;
                int framesize = -1;
                if (properties.containsKey("audio.samplesize.bits"))
                {
                    bitspersample = ((Integer) properties.get("audio.samplesize.bits")).intValue();
                }
                if (properties.containsKey("audio.channels"))
                {
                    channels = ((Integer) properties.get("audio.channels")).intValue();
                }
                if (properties.containsKey("audio.samplerate.hz"))
                {
                    samplerate = ((Float) properties.get("audio.samplerate.hz")).floatValue();
                }
                if (properties.containsKey("audio.framesize.bytes"))
                {
                    framesize = ((Integer) properties.get("audio.framesize.bytes")).intValue();
                }
                if (bitspersample > 0)
                {
                    milliseconds = (int) (1000.0f * byteslength / (samplerate * channels * (bitspersample / 8)));
                }
                else
                {
                    milliseconds = (int) (1000.0f * byteslength / (samplerate * framesize));
                }
            }
        }
        return milliseconds;
    }

}
