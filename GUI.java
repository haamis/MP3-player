import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.io.File;
import java.util.Vector;

public class GUI extends JFrame implements ActionListener, ListSelectionListener, ItemListener{
	
	private static final long serialVersionUID = 1L;
	public static JTextArea nowPlaying;
	private JMenu menu;
	private JMenuBar menubar;
	private static Vector<String> playlistNames = new Vector<String>();
	private static JList playlistScreen;
	private JButton play, stop, next, previous;
	private JCheckBox shuffle, repeat, repeatAll;
	public static JFileChooser fileChooser = new JFileChooser();
	private JMenuItem open, openPlaylist, savePlaylist;
	public static Playback controls = new Playback();
	private int temp;
	public static int returnVal;
	private JScrollPane pane;
	
	public GUI() { 

		fileChooser.setMultiSelectionEnabled(true);
				
		menubar = new JMenuBar();
		add(menubar);
		menu = new JMenu("File");
		menubar.add(menu);

		open = new JMenuItem("Open..");
		open.addActionListener(this);
		menu.add(open);
		
		openPlaylist = new JMenuItem("Open playlist");
		openPlaylist.addActionListener(this);
		menu.add(openPlaylist);

		savePlaylist = new JMenuItem("Save playlist");
		savePlaylist.addActionListener(this);
		menu.add(savePlaylist);

		previous = new JButton("Previous");
		previous.addActionListener(this);
		add(previous);

		play = new JButton("Play");
		play.addActionListener(this);
		add(play);
		
		stop = new JButton("Stop");
		stop.addActionListener(this);
		add(stop);

		next = new JButton("Next");
		next.addActionListener(this);
		add(next);

		nowPlaying = new JTextArea("Nyt soi:");
		add(nowPlaying);
		nowPlaying.setEditable(false);

		playlistScreen = new JList();
		add(playlistScreen);
		playlistScreen.setFixedCellWidth(300);
		playlistScreen.setFixedCellHeight(15);
		playlistScreen.setVisibleRowCount(15);
		playlistScreen.addListSelectionListener(this);
		playlistScreen.addMouseListener(new MouseAdapter() {
	        	public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
	                		controls.a=temp;
	                		controls.stop();
					play.doClick();
				}
			}
	        });
		
		pane = new JScrollPane(playlistScreen);
		add(pane);
		
		shuffle = new JCheckBox("Shuffle");
		shuffle.addItemListener(this);
		add(shuffle);	

		repeat = new JCheckBox("Repeat");
		repeat.addItemListener(this);
		add(repeat);

		repeatAll = new JCheckBox("Repeat all");
		repeatAll.addItemListener(this);
		add(repeatAll);
		
		setTitle("MP3-soitin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(350,340);
		setResizable(false);

		setLayout(new FlowLayout());

	}
	
	public static void updateNowPlaying(String s, int i){

	        if(i == 0){
                     nowPlaying.setText("Soitto pysäytetty.");
		}else{
		     nowPlaying.setText("Nyt soi: " + i + ". " + s);
		}
	}

	public static void updatePlaylistScreen(){

		playlistNames.clear();
		
		for(int i = 0;i<controls.playlist.size();i++){
			
			File temp = controls.playlist.get(i);
			playlistNames.add(i+1 + ". " + temp.getName());
			
		}
		playlistScreen.updateUI();
		playlistScreen.setListData(playlistNames);

	}
	
	public void actionPerformed(ActionEvent evt) {

		

		if(evt.getSource() == play){
		
			controls.play();
			
		}

		else if(evt.getSource() == stop){

			controls.stop();

		}

		else if(evt.getSource() == next){
	
			controls.next();
	
		}

		else if(evt.getSource() == previous){
			
			controls.previous();
			
		}
		
		else if(evt.getSource() == open){

			returnVal = fileChooser.showOpenDialog(this);
			controls.open();	

		}
		
		else if(evt.getSource() == savePlaylist){

			returnVal = fileChooser.showSaveDialog(this);
			controls.savePlaylist();
		}
		
		else if(evt.getSource() == openPlaylist){
			
			returnVal = fileChooser.showOpenDialog(this);
			controls.openPlaylist();
		}
	}
	
	public void valueChanged(ListSelectionEvent evt){
	
		if(evt.getSource() == playlistScreen){
			if(playlistScreen.getSelectedValue() != null){
				temp = playlistScreen.getSelectedIndex();
			}
				
		}
		
	}
	
	public void itemStateChanged(ItemEvent evt){
		
		if(evt.getItemSelectable() == shuffle){
			controls.shuffleSwitch();

		}else if(evt.getItemSelectable() == repeat){
			controls.repeatSwitch();

		}else if(evt.getItemSelectable() == repeatAll){
			controls.repeatAllSwitch();
		}

	}
	
	public boolean accept(File f){

	       return f.isDirectory() || f.getName().toLowerCase().endsWith(".mp3");

	}

	public String getDescription(){

	       return "MP3 files";

	}
}