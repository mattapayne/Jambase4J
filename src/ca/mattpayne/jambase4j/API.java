package ca.mattpayne.jambase4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import ca.mattpayne.jambase4j.interfaces.APIConfigurator;
import ca.mattpayne.jambase4j.interfaces.Event;
import ca.mattpayne.jambase4j.interfaces.EventBuilder;
import ca.mattpayne.jambase4j.interfaces.Jambase4JAPI;
import ca.mattpayne.jambase4j.interfaces.ParamCleanser;
import ca.mattpayne.jambase4j.interfaces.WebConnection;

public class API implements Jambase4JAPI
{
	private static final Jambase4JAPI instance = new API();
	private static final String API_URL = "http://api.jambase.com/search";
	private static String jambaseAPIKey = null;
	private static WebConnection connection = null;
	private static ParamCleanser paramCleanser = null;
	public static final String[] JAMBASE_PARAMS = {"zip", "band", "radius", "user", "artist"};
	public static final Dictionary<String, String> ALIASES = new Hashtable<String, String>();
	
	static
	{
		ALIASES.put("artist", "band");
		paramCleanser = new ParamCleanserImpl(JAMBASE_PARAMS, ALIASES);
	}
	
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

	public List<Event> search(Dictionary<String, String> args)
	{
		if(args == null)
		{
			return null;
		}
		
		args = paramCleanser.cleanse(args);
		
		if(args.size() == 0)
		{
			return null;
		}
		
		String queryString = constructQueryString(args);
		String completeUrl = API_URL + "?" + queryString;
		String xml = getWebConnection().getResponse(completeUrl);
		EventBuilder builder = new EventBuilderImpl();
		return builder.build(xml);
	}

	public List<Event> searchByArtist(String artist,
			Dictionary<String, String> args)
	{
		args.put("band", artist);
		if(keyExists(args, "artist"))
		{
			args.remove("artist");
		}
		return search(args);
	}

	public List<Event> searchByUser(String user, Dictionary<String, String> args)
	{
		args.put("user", user);
		return search(args);
	}

	public List<Event> searchByZipcode(String zip, Dictionary<String, String> args)
	{
		args.put("zip", zip);
		return search(args);
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
	
	private boolean keyExists(Dictionary<String, String> args, String key)
	{
		Hashtable<String, String> tmp = (Hashtable<String, String>)args;
		return tmp.containsKey(key);
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
}
