package ca.mattpayne.jambase4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import ca.mattpayne.jambase4j.interfaces.APIConfigurator;
import ca.mattpayne.jambase4j.interfaces.Event;
import ca.mattpayne.jambase4j.interfaces.EventBuilder;
import ca.mattpayne.jambase4j.interfaces.Jambase4JAPI;
import ca.mattpayne.jambase4j.interfaces.SearchParams;
import ca.mattpayne.jambase4j.interfaces.WebConnection;

public class API implements Jambase4JAPI
{
	private static final Jambase4JAPI instance = new API();
	private static final String API_URL = "http://api.jambase.com/search";
	private static String jambaseAPIKey = null;
	private static WebConnection connection = null;
	
	public static Jambase4JAPI getInstance() throws APINotConfiguredException
	{
		ensureConfigured();
		return instance;
	}
	
	public static void configure(APIConfigurator configurator) throws MalformedConfigException, FileNotFoundException, IOException
	{
		configurator.configure(instance);
	}
	
	public static void configure() throws MalformedConfigException, FileNotFoundException, IOException
	{
		configure(new DefaultConfigurator());
	}

	public String getAPIKey()
	{
		return jambaseAPIKey;
	}

	public void setAPIKey(String key)
	{
		jambaseAPIKey = key;
	}
	
	public WebConnection getWebConnection()
	{
		if(connection == null)
		{
			connection = new DefaultWebConnection();
		}
		
		return connection;
	}

	public void setWebConnection(WebConnection connection)
	{
		this.connection = connection;
	}
	
	private static void ensureConfigured() throws APINotConfiguredException
	{
		if(jambaseAPIKey == null || jambaseAPIKey.trim().length() == 0)
		{
			throw new APINotConfiguredException("The API key is not set");
		}
	}
	
	private String constructQueryString(Dictionary<String, String> args)
	{
		StringBuffer sb = new StringBuffer(); 
		Enumeration<String> e = args.keys();
		while(e.hasMoreElements())
		{
			String key;
			try
			{
				key = e.nextElement();
				String value = URLEncoder.encode(args.get(key), "UTF-8");
				key = URLEncoder.encode(key, "UTF-8");
				sb.append(key + "=" + value + "&");
			} 
			catch (UnsupportedEncodingException e1)
			{
			}
		}
		String tmp = sb.toString();
		return tmp + "apikey=" + jambaseAPIKey;
	}
	
	private List<Event> innerSearch(Dictionary<String, String> args)
	{
		List<Event> events = new ArrayList<Event>();
		if(args == null || args.size() == 0)
		{
			return events;
		}
		
		String queryString = constructQueryString(args);
		String completeUrl = API_URL + "?" + queryString;
		String xml = getWebConnection().getResponse(completeUrl);
		EventBuilder builder = new EventBuilderImpl();
		events = builder.build(xml);
		
		return events;
	}

	public List<Event> search(SearchParams args)
	{
		return innerSearch(args.toArgs());
	}

	public List<Event> searchByBand(String band, SearchParams additionalArgs)
	{
		return innerSearch(additionalArgs.byBand(band).toArgs());
	}

	public List<Event> searchByBand(String band)
	{
		return innerSearch(new SearchParamsImpl().byBand(band).toArgs());
	}

	public List<Event> searchByUser(String user, SearchParams additionalArgs)
	{
		return innerSearch(additionalArgs.byUser(user).toArgs());
	}

	public List<Event> searchByUser(String user)
	{
		return innerSearch(new SearchParamsImpl().byUser(user).toArgs());
	}

	public List<Event> searchByZipcode(String zip, SearchParams additionalArgs)
	{
		return innerSearch(additionalArgs.byZipcode(zip).toArgs());
	}

	public List<Event> searchByZipcode(String zip)
	{
		return innerSearch(new SearchParamsImpl().byZipcode(zip).toArgs());
	}
}
