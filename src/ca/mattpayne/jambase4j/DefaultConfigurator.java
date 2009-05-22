package ca.mattpayne.jambase4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ca.mattpayne.jambase4j.interfaces.APIConfigurator;
import ca.mattpayne.jambase4j.interfaces.Jambase4JAPI;
import ca.mattpayne.jambase4j.interfaces.WebConnection;

/*
 * The file should look like
 * 
 * jambase4r-api-key=dssdffdsdfsfd
 */
public class DefaultConfigurator implements APIConfigurator
{
	private static final String FILE_NAME = "jambase4j.config";
	private String alternateFileName = null;
	private WebConnection alternateWebConnection = null;
	
	/**
	 * Default Constructor
	 */
	public DefaultConfigurator()
	{
		
	}
	
	/**
	 * Constructor
	 * @param fileName - The name of the config file to read
	 */
	public DefaultConfigurator(String fileName)
	{
		alternateFileName = fileName;
	}
	
	/**
	 * Construct with alternate connection
	 * @param alternateConnection
	 */
	public DefaultConfigurator(WebConnection alternateConnection)
	{
		alternateWebConnection = alternateConnection;
	}
	
	/**
	 * Construct with alternate file name and web connection
	 * @param fileName
	 * @param alternateConnection
	 */
	public DefaultConfigurator(String fileName, WebConnection alternateConnection)
	{
		alternateFileName = fileName;
		alternateWebConnection = alternateConnection;
	}
	
	public void configure(Jambase4JAPI api) throws MalformedConfigException, FileNotFoundException, IOException
	{
		api.setAPIKey(getAPIKey());
		api.setWebConnection(alternateWebConnection == null ? 
				new DefaultWebConnection() : alternateWebConnection);
	}
	
	private String getAPIKey() throws MalformedConfigException, FileNotFoundException, IOException
	{
		StringBuffer sb = new StringBuffer();
		String key = null;
		
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(
					((alternateFileName == null || alternateFileName.trim().length() == 0) ? 
							FILE_NAME : alternateFileName.trim())));
			String line = null;
			while((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
			
		}
		finally
		{
			reader.close();
		}
		
		String[] tokens = sb.toString().split("=");
		if(tokens.length == 2)
		{
			key = tokens[1].trim();
		}
		else
		{
			throw new MalformedConfigException("Your Jambase API key is malformed.");
		}
		
		return key;
	}

}
