package ca.mattpayne.jambase4j.interfaces;

import java.util.List;

public interface Jambase4JAPI
{
	public List<Event> search(SearchParams args);
	public List<Event> searchByBand(String band, SearchParams additionalArgs);
	public List<Event> searchByZipcode(String zip, SearchParams additionalArgs);
	public List<Event> searchByUser(String user, SearchParams additionalArgs);
	public List<Event> searchByBand(String band);
	public List<Event> searchByZipcode(String zip);
	public List<Event> searchByUser(String user);
	public void setAPIKey(String key);
	public String getAPIKey();
	public void setWebConnection(WebConnection connection);
	public WebConnection getWebConnection();
}
