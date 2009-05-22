package ca.mattpayne.jambase4j.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ca.mattpayne.jambase4j.interfaces.WebConnection;

public class MockWebConnection implements WebConnection
{
	private static final String FILE_NAME = "jambasedata.xml";
	
	public String getResponse(String url)
	{
		String result = "";
		try
		{
			BufferedReader reader = null;
			StringBuffer sb = new StringBuffer();
			try
			{
				reader = new BufferedReader(new FileReader(FILE_NAME));
				String line = null;
				while((line = reader.readLine()) != null)
				{
					sb.append(line);
				}
				result = sb.toString();
			}
			finally
			{
				if(reader != null)
				{
					reader.close();
				}
			}
		}
		catch (FileNotFoundException e)
		{
			
		}
		catch(IOException ex)
		{
			
		}
		return result;
	}

}
