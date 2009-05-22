package ca.mattpayne.jambase4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import ca.mattpayne.jambase4j.interfaces.WebConnection;

public class DefaultWebConnection implements WebConnection
{
	public String getResponse(String url)
	{
		String result = null;
		try
		{
			URL jambaseUrl = new URL(url);
			BufferedReader reader = null;
			try
			{
				reader = new BufferedReader(new InputStreamReader(jambaseUrl.openStream()));
				String line = null;
				StringBuffer sb = new StringBuffer();
				while((line = reader.readLine()) != null)
				{
					sb.append(line);
				}
				result = sb.toString();
			}
			finally
			{
				reader.close();
			}
		} 
		catch (MalformedURLException e)
		{
			
		} 
		catch (IOException e)
		{

		}
		return result;
	}

}
