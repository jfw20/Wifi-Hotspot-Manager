public class NetSettings{
	//Network Settings data fields
	private boolean _mode;
	private String _ssidName;
	private int _maxClients;
	private String _authentication;
	private String _cipher;
	private String _status;
	private String _key;
	
	//Constructor
	public NetSettings(boolean mode, String ssidName, int maxClients, String auth, String cipher, String status, String key){
		_mode = mode;
		_ssidName = ssidName;
		_maxClients = maxClients;
		_authentication = auth;
		_cipher = cipher;
		_status = status;
		_key = key;
	}
	
	//accessor methods
	public boolean getMode(){
		return _mode;
	}
	
	public String getName(){
		return _ssidName;
	}
	
	public int getMax(){
		return _maxClients;
	}
	
	public String getAuth(){
		return _authentication;
	}
	
	public String getCiph(){
		return _cipher;
	}
	
	public String getStat(){
		return _status;
	}
	
	public String getKey(){
		return _key;
	}
}
