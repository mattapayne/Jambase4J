package ca.mattpayne.jambase4j.interfaces;

import java.util.Dictionary;
import java.util.List;

public interface Jambase4JAPI
{
	public List<Event> search(Dictionary<String, String> args);
	public List<Event> searchByArtist(String artist, Dictionary<String, String> args);
	public List<Event> searchByZipcode(String zip, Dictionary<String, String> args);
	public List<Event> searchByUser(String user, Dictionary<String, String> args);
	public void setAPIKey(String key);
	public String getAPIKey();
	public void setWebConnection(WebConnection connection);
	public WebConnection getWebConnection();
}
