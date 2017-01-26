import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.*;

public class WifiHotspot {

	private static JFrame _frame;
	
	private JPanel _wifiPanel;
	private JPanel _buttonPanel;
	private JPanel _mainPanel;
	private JPanel _wPanel;
	private JPanel _ePanel;
	
	private static JTextField _mode;
	private static JTextField _ssidName;
	private static JTextField _maxClients;
	private static JTextField _auth;
	private static JTextField _cipher;
	private static JTextField _status;
	private static JTextField _userKey;
	
	private static JLabel _lmode;
	private static JLabel _lssid;
	private static JLabel _lmaxClients;
	private static JLabel _lauth;
	private static JLabel _lciph;
	private static JLabel _lstat;
	private static JLabel _lukey;

	
	private static NetSettings settings;

	private static JButton _start;
	private static JButton _stop;
	private JButton _refresh;
	
	private static String[] _networkSettings = new String[5];
	private static String _name;
	private static String _password;
	
	public static void main(String[] args) throws IOException{
		
		boolean _compatible = false;
		boolean mode = false;
		int max = 0;
		
		Process p;
		
		try{
			
			p = Runtime.getRuntime().exec("netsh wlan show drivers");
			p.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line = "";
				
			while((line = reader.readLine())!= null){
				if(line.contains("Hosted network supported")){
					if(line.endsWith("Yes")){
						_compatible = true;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		refresh(false);
		
		System.out.println(mode);
		System.out.println(_networkSettings[0]);
		System.out.println(max);
		System.out.println(_networkSettings[1]);
		System.out.println(_networkSettings[2]);
		System.out.println(_networkSettings[3]);
		System.out.println(_networkSettings[4]);
		
		NetSettings settings = new NetSettings(mode,_networkSettings[0],max,
													_networkSettings[1],_networkSettings[2],
													_networkSettings[3],_networkSettings[4]);

		
		
		new WifiHotspot(settings);
	}
	
	public WifiHotspot(NetSettings settings){
		
		CmdListener listener = new CmdListener();
		
		_mainPanel = new JPanel();
		_mainPanel.setLayout(new FlowLayout());
		_mainPanel.setPreferredSize( new Dimension( 325, 360));
		
		_wPanel = new JPanel();
		_ePanel = new JPanel();
		
		//control buttons
		_start = new JButton("Start Hotspot");
		_stop = new JButton("Stop Hotspot");
		_refresh = new JButton("Refresh");
		
		//text fields
		if(settings.getMode()){
			_mode = new JTextField("Allowed");
		}else{
			_mode = new JTextField("Not allowed");
		}
		_ssidName = new JTextField(settings.getName());
		_maxClients = new JTextField(settings.getMax());
		_auth = new JTextField(settings.getAuth());
		_cipher = new JTextField(settings.getCiph());
		_status = new JTextField(settings.getStat());
		_userKey = new JTextField(settings.getKey());
		
		_lmode = new JLabel("Mode:");
		_lssid = new JLabel("Name: <32 Chars");
		_lmaxClients = new JLabel("Max clients:");
		_lauth = new JLabel("Authentication:");
		_lciph = new JLabel("Cipher:");
		_lstat = new JLabel("Status:");
		_lukey = new JLabel("Password:");
		
		//set field sizes
		_mode.setPreferredSize( new Dimension(100, 30));
		_ssidName.setPreferredSize( new Dimension(115, 30));
		_maxClients.setPreferredSize( new Dimension(100, 30));
		_auth.setPreferredSize( new Dimension(100, 30));
		_cipher.setPreferredSize( new Dimension(100, 30));
		_status.setPreferredSize( new Dimension(100, 30));
		_userKey.setPreferredSize( new Dimension(100, 30));
		
		//set label sizes
		_lmode.setPreferredSize( new Dimension(100, 30));
		_lssid.setPreferredSize( new Dimension(100, 30));
		_lmaxClients.setPreferredSize( new Dimension(100, 30));
		_lauth.setPreferredSize( new Dimension(100, 30));
		_lciph.setPreferredSize( new Dimension(100, 30));
		_lstat.setPreferredSize( new Dimension(100, 30));
		_lukey.setPreferredSize( new Dimension(100, 30));


		_mode.setEditable(false);
		_ssidName.setEditable(true);
		_maxClients.setEditable(false);
		_auth.setEditable(false);
		_cipher.setEditable(false);
		_status.setEditable(false);
		_userKey.setEditable(true);
		
		_mode.setBorder(null);
		_ssidName.setBorder(null);
		_maxClients.setBorder(null);
		_auth.setBorder(null);
		_cipher.setBorder(null);
		_status.setBorder(null);
		_userKey.setBorder(null);
		
		_start.setEnabled(true);
		_stop.setEnabled(true);
		_refresh.setEnabled(true);
		
		_start.addActionListener(listener);
		_stop.addActionListener(listener);
		_refresh.addActionListener(listener);
		
		_frame = new JFrame("Wifi HotSpot");
		
		//initialize and add login/cast buttons to panels
		_buttonPanel = new JPanel();
		_wifiPanel = new JPanel();
		_wifiPanel.setLayout(new BorderLayout());
		_wifiPanel.setPreferredSize( new Dimension(325, 325));
		_buttonPanel.add(_start);
		_buttonPanel.add(_stop);
		_buttonPanel.add(_refresh);
		_buttonPanel.setPreferredSize( new Dimension(325, 35));
		
		_ePanel.setPreferredSize( new Dimension (161, 325));
		
		_ePanel.add(_mode);
		_ePanel.add(_ssidName);
		_ePanel.add(_maxClients);
		_ePanel.add(_auth);
		_ePanel.add(_cipher);
		_ePanel.add(_status);
		_ePanel.add(_userKey);
		
		_wPanel.setPreferredSize( new Dimension(161, 325));
		
		_wPanel.add(_lmode);
		_wPanel.add(_lssid);
		_wPanel.add(_lmaxClients);
		_wPanel.add(_lauth);
		_wPanel.add(_lciph);
		_wPanel.add(_lstat);
		_wPanel.add(_lukey);

		
		_wifiPanel.add(_wPanel, BorderLayout.WEST);
		_wifiPanel.add(_ePanel, BorderLayout.EAST);

		
		//add login/cast panels to mainpanel
		_mainPanel.add(_buttonPanel);
		_mainPanel.add(_wifiPanel);
		
		WindowListener exitListener = new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e){
				Process p;
				try{
					p = Runtime.getRuntime().exec("netsh wlan set hostednetwork mode=disallow");
					p.waitFor();
					System.exit(0);
				}catch(Exception er){
					er.printStackTrace();
				}
			}
		};

		//add mainpanel to frame and set defaultcloseop to do nothing
		_frame.add(_mainPanel);
		_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		_frame.addWindowListener(exitListener);
		_frame.setResizable(false);
		
		//pack frame and make visible
		_frame.pack();
		_frame.setVisible(true);
		refresh(true);


	}
	
	public void start(){
		Process p;
		if(_ssidName.getText().length() > 32){
			JOptionPane.showMessageDialog(_frame, "SSID names cannot exceed 32 characters.");
			return;
		}
		
		try{
			if(!settings.getMode()){
				p = Runtime.getRuntime().exec("netsh wlan set hostednetwork mode=allow");
				p.waitFor();
			}
			if(_ssidName.getText()!=settings.getName()){
				p = Runtime.getRuntime().exec("netsh wlan set hostednetwork ssid=" + _ssidName.getText());
				p.waitFor();
			}
			if(_userKey.getText()!=settings.getKey()){
				p = Runtime.getRuntime().exec("netsh wlan set hostednetwork key=" + _userKey.getText());
				p.waitFor();
			}
			p = Runtime.getRuntime().exec("netsh wlan start hostednetwork");
			p.waitFor();
			refresh(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void stop(){
		Process p;
		
		try{
			p = Runtime.getRuntime().exec("netsh wlan stop hostednetwork");
			p.waitFor();
			refresh(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void refresh(boolean pack){
		
		boolean mode = false;
		int max = 0;
		
		Process p;
		try{
			
			p = Runtime.getRuntime().exec("netsh wlan show hostednetwork");
			p.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line = "";
			
			while((line = reader.readLine()) != null){
				if(line.contains(":")){
					for(int i = 0; i < line.length(); i++){
						char c = line.charAt(i);
						if(c == ':'){
							if(line.contains("Mode")){
								if(line.contains("Allowed")){
									mode = true;
								}
							}else if(line.contains("SSID name")){
								_networkSettings[0] = line.substring(i+3, line.length()-1);
							}else if(line.contains("Max number")){
								max = Integer.parseInt(line.substring(i+2));
							}else if(line.contains("Authentication")){
								_networkSettings[1] = line.substring(i+2);
							}else if(line.contains("Cipher")){
								_networkSettings[2] = line.substring(i+2);
							}else if(line.contains("Status")){
								_networkSettings[3] = line.substring(i+2);
							}
						}
					}
				}
			}
			
			p = Runtime.getRuntime().exec("netsh wlan show hostednetwork setting=security");
			p.waitFor();
			reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));
			while((line = reader.readLine())!= null){
				if(line.contains("User security key") && !line.contains("usage")){
					for(int i = 0; i < line.length(); i++){
						char c = line.charAt(i);
						if(c == ':'){
							_networkSettings[4] = line.substring(i+2);
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		settings = new NetSettings(mode,_networkSettings[0],max,
													_networkSettings[1],_networkSettings[2],
													_networkSettings[3],_networkSettings[4]);
		
		if(pack){
			if(settings.getMode()){
				_mode.setText("Allowed");;
			}else{
				_mode.setText("Not allowed");
			}
			_ssidName.setText(settings.getName());
			_maxClients.setText("" + settings.getMax());
			_auth.setText(settings.getAuth());
			_cipher.setText(settings.getCiph());
			_status.setText(settings.getStat());
			_userKey.setText(settings.getKey());
			
			if(settings.getStat().equals("Started")){
				_start.setEnabled(false);
				_stop.setEnabled(true);
			}else if(settings.getStat().equals("Not available")){	
				try{
					p = Runtime.getRuntime().exec("netsh wlan set hostednetwork mode=allow");
					p.waitFor();
					_stop.setEnabled(false);
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				_start.setEnabled(true);
				_stop.setEnabled(false);
			}
			
			_frame.pack();
			_frame.setVisible(true);
		}
		
		
	}
	
	//runs stop or login methods
    class CmdListener implements ActionListener {
    	
		public void actionPerformed(ActionEvent e) {
		    if (e.getSource() == _start) {
		    	start();
		    } else if (e.getSource() == _stop) {
		    	stop();
		    } else if (e.getSource() == _refresh) {
		    	refresh(true);
		    }
		}
    }
}
