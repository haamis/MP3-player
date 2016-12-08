import java.io.*;
import javax.swing.*;
import java.util.Vector;
import java.util.Random;

public class Playback{

	private boolean alreadyPlaying = false;
	public int a;
	public Vector<File> playlist = new Vector<File>();
	private File[] files;
	public boolean stopped;
	private MP3 mp3;
	private boolean shuffle, repeat, repeatAll;
	private Random rnd = new Random(1);

	public Playback(){
		a = 0;
	}

	public void play(){
		if(alreadyPlaying == false){
			try{

				File temp = playlist.get(a);
				mp3 = new MP3(temp.getCanonicalPath());
				alreadyPlaying = true;
				stopped = false;
				GUI.updateNowPlaying(temp.getName(), a+1);
			}catch(ArrayIndexOutOfBoundsException aioobe){
		
				System.out.println("ArrayIndexOutOfBounds, playlist probably empty.");

			}catch(IOException e){
				System.out.println(e);
			}
			try{

				mp3.play();

			}catch(NullPointerException nue){
		
				System.out.println("NullPointerException, playlist is empty.");		

			}
		}

	
	}

	public void stop(){
		stopped = true;
		if(mp3 != null){
			mp3.close();
		}
		GUI.updateNowPlaying("", 0);
		alreadyPlaying = false;
	}

	public void setAlreadyPlaying(boolean bool){
		alreadyPlaying = bool;
	}
	
	public void next(){	
		if(a<playlist.size()-1){
			if(shuffle == true){
				a = rnd.nextInt(playlist.size());
			}else{
				a+=1;
			}
			stop();
			if(playlist.get(a)!=null){
				play();
			}else{
				a-=1;
			}
		}
	}
	
	public void previous(){
	
		if(a!=0){
			a-=1;
			stop();
			play();
		}
	
	}
	
	public void shuffleSwitch(){
		if(shuffle == false){
			shuffle = true;
		}else{
			shuffle = false;
		}
		System.out.println("Shuffle: " + shuffle);
	}

	public void repeatSwitch(){
		if(repeat == false){
			repeat = true;
		}else{
			repeat = false;
		}
		System.out.println("Repeat: " + repeat);
	}

	public void repeatAllSwitch(){
		if(repeatAll == false){
			repeatAll = true;
		}else{
			repeatAll = false;
		}System.out.println("Repeat all: " + repeatAll);	
	}

	public boolean getShuffle(){
		return shuffle;
	}

	public boolean getRepeat(){
		return repeat;
	}

	public boolean getRepeatAll(){
		return repeatAll;
	}

	public void open(){
		
		if (GUI.returnVal == JFileChooser.APPROVE_OPTION){ 
		        //GUI.fileChooser.setFileFilter(new GUI()); keskeneräistä koodia.
			files = GUI.fileChooser.getSelectedFiles();
			int i=0;
			while(i<files.length){
				playlist.add(files[i]);
				i+=1;
			}
			GUI.updatePlaylistScreen();
		}
	}

	public void savePlaylist(){
		
		File file;

		if (GUI.returnVal == JFileChooser.APPROVE_OPTION){
			file = GUI.fileChooser.getSelectedFile();
			try{
   			    	  File temp;
				  BufferedWriter out = new BufferedWriter(new FileWriter(file));
				  for(int i=0;i<playlist.size();i++){
				  	  temp=playlist.get(i);
					  out.write(temp.getCanonicalPath() + "\n");
				  }
				  out.close();
	                }catch (IOException e){
		     	          System.out.println(e);
			}
		}

	}

	public void openPlaylist(){

		File file;

		if (GUI.returnVal == JFileChooser.APPROVE_OPTION){
			file = GUI.fileChooser.getSelectedFile();
		       	try{
				System.out.println("Trying to open..");
				BufferedReader in = new BufferedReader(new FileReader(file));
				playlist.clear();
				a=0;
				File temp = new File(in.readLine());
				boolean eof = false;
				for(int i=0;eof != true;i++){
					playlist.add(i, temp);
					try{
					    temp = new File(in.readLine());
					}catch(NullPointerException npe){
     					    eof = true;
 					}
				}
			in.close();
			}catch (IOException e){
   			       System.out.println(e);
      			}
		GUI.updatePlaylistScreen();
	 	}
	}

}