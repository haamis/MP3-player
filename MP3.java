import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.advanced.*;
import javax.swing.UIManager;


public class MP3{
	
	private String filename;
	private AdvancedPlayer player; 
	private BufferedInputStream bis;
	private PlaybackListener playbackListener;
	
	
	public MP3(String filename){ 
		this.filename = filename;
		playbackListener = new PlaybackListener(){ 
				public void playbackStarted(PlaybackEvent playbackEvent){
				   
					System.out.println("Playback started..");
					
				}
 
				public void playbackFinished(PlaybackEvent playbackEvent){ 
					
					if(GUI.controls.stopped == false){ 
						if(GUI.controls.getRepeat() == false){
							GUI.controls.next();
							System.out.println("Kappale loppui, hyp‰t‰‰n seuraavaan..");
						}else{
							GUI.controls.stop();
							GUI.controls.play();
						}
					}
				
					
				}
		};
	}

	public void close(){ 
		if (player != null){
			player.close();
		}
	 }

	public void play(){
		try {
			bis = new BufferedInputStream(new FileInputStream(filename));
			player = new AdvancedPlayer(bis);
			player.setPlayBackListener(playbackListener);
			
		}catch (Exception e){
			System.out.println("Problem playing file " + filename);
			System.out.println(e);
		}

		new Thread(){
			public void run() {
				try{
					player.play();
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
		}.start();
	}

	public static void main(String[] args) {
		try {
        		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
   		}catch(Exception e){
			System.out.println(e);
		}

		new GUI();

	}

}