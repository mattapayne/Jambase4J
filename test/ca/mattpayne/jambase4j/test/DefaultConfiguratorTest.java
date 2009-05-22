package ca.mattpayne.jambase4j.test;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import ca.mattpayne.jambase4j.API;
import ca.mattpayne.jambase4j.DefaultConfigurator;
import ca.mattpayne.jambase4j.MalformedConfigException;
import ca.mattpayne.jambase4j.interfaces.APIConfigurator;


public class DefaultConfiguratorTest
{
	@Test
	public void itShouldThrowAnExceptionIfPassedANullAPIObject()
	{
		boolean nullException = false;
		APIConfigurator conf = new DefaultConfigurator();
		try
		{
			conf.configure(null);
		}
		catch(NullPointerException np)
		{
			nullException = true;
		}
		catch(Exception e)
		{
			
		}
		assertTrue(nullException);
	}
	
	public void itShouldThrowAnExceptionIfTheSpecifiedFileDoesNotExist()
	{
		boolean notFound = false;
		try
		{
			API.configure(new DefaultConfigurator("dfsfdsf.txt"));
			API.getInstance();
		} 
		catch (FileNotFoundException e)
		{
			notFound = true;
		} 
		catch (Exception e)
		{

		}
		assertTrue(notFound);
	}
	
	@Test
	public void itShouldThrowAnExceptionIfTheConfigFileIsMalformed()
	{
		boolean malformed = false;
		try
		{
			API.configure(new DefaultConfigurator("malformed.config"));
			API.getInstance();
		}
		catch(MalformedConfigException me)
		{
			malformed = true;
		}
		catch(Exception e)
		{
			
		}
		assertTrue(malformed);
	}
	
	@Test
	public void itShouldProperlyReadTheConfigFileUsingTheDefaultName()
	{
		boolean errorOccurred = false;
		try
		{
			API.configure();
			String key = null;
			key = API.getInstance().getAPIKey();
			assertEquals("This is the key", key);
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		assertFalse(errorOccurred);
	}
	
	@Test
	public void itShouldProperlyReadTheConfigFileUsingACustomFileName()
	{
		boolean errorOccurred = false;
		try
		{
			API.configure(new DefaultConfigurator("myrandomfile.txt"));
			String key = API.getInstance().getAPIKey();
			assertEquals("This is the key from the custom file", key);
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		assertFalse(errorOccurred);
	}
}
