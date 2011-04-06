/**
 * 
 */
package is2011.reproductor.vista;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.plaf.ProgressBarUI;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 * @author Administrator
 *
 */
public class VistaReproduccion implements BasicPlayerListener {

	private long tiempoTotal;
	private int tiempoActual = 0;
	private int bytesPorSegundo;
	
	private JProgressBar progreso;
	
	private int rate;
	/**
	 * Archivo abierto. Mostramos el tiempo total de la cancion, asi como el bitrate.
	 */
	public void opened(Object arg0, Map properties) {
		System.out.println("Archivo abierto");
		tiempoTotal = this.getTimeLengthEstimation(properties);
		System.out.println("Tiempo total estimado: " + tiempoTotal);
		
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
		
		int audioFramesize = (Integer)properties.get("mp3.frame.size.bytes");
		int audioFramerate = (Integer)properties.get("mp3.frame.bitrate");
		long mp3Frame = (Long)properties.get("mp3.frame");
		long mp3PositionByte = (Long)properties.get("mp3.position.byte");
		
		
		
		
		int tiempo = (int)( mp3PositionByte/ (audioFramerate/8));
		//int tiempo = (int) this.getActualTimeEstimated((long)bytesread, properties);
		if(tiempo != tiempoActual) {
			System.out.println("BytesRead" + bytesread);
			System.out.println("audioFramerate " + audioFramerate);
			System.out.println("audioFramesize " + audioFramesize);
			System.out.println("mp3Frame " + mp3Frame);
			System.out.println("mp3PositionByte " + mp3PositionByte);
			System.out.println("************************************\n");
			tiempoActual = tiempo;
			escribirInfo();
		}

	}
	
	private void escribirInfo() {
		System.out.println("Progreso... " + tiempoActual + "/" + (tiempoTotal/1000)); 
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
                	bytesPorSegundo = (int) (samplerate * channels * (bitspersample / 8));
                    milliseconds = (int) (1000.0f * byteslength / (samplerate * channels * (bitspersample / 8)));
                }
                else
                {
                	bytesPorSegundo =  (int) (samplerate * framesize);
                    milliseconds = (int) (1000.0f * byteslength / (samplerate * framesize));
                }
            }
        }
        return milliseconds;
    }
	
	public static void main(String args[]) {
		JFrame v = new JFrame();
		
		v.setVisible(true);
		v.setSize(300,300);
		
		JProgressBar pb = new JProgressBar();
		pb.setVisible(true);
		pb.setSize(200,200);
		v.add(pb);		
	}


}
