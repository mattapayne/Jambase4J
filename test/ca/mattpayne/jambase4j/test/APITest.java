package ca.mattpayne.jambase4j.test;

import static org.junit.Assert.*;

import java.util.Dictionary;
import java.util.Hashtable;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Expectation;
import org.jmock.api.Invocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.mattpayne.jambase4j.API;
import ca.mattpayne.jambase4j.DefaultConfigurator;
import ca.mattpayne.jambase4j.DefaultWebConnection;
import ca.mattpayne.jambase4j.interfaces.APIConfigurator;
import ca.mattpayne.jambase4j.interfaces.WebConnection;
import ca.mattpayne.jambase4j.APINotConfiguredException;
import ca.mattpayne.jambase4j.interfaces.Jambase4JAPI;

public class APITest
{
	private Mockery mockery = null;
	
	@Before
	public void setUp() throws Exception
	{
		mockery = new Mockery();
		
		API.configure(new APIConfigurator() {

			public void configure(Jambase4JAPI api)
			{
				api.setAPIKey("key");
				api.setWebConnection(new MockWebConnection());
			}
			
		});
	}

	@After
	public void tearDown() throws Exception
	{
		mockery = null;

		API.configure(new APIConfigurator() {

			public void configure(Jambase4JAPI api)
			{
				api.setAPIKey(null);
				api.setWebConnection(null);
			}
			
		});
	}
	
	@Test
	public void itShouldReturnNullFromSearchIfNoSearchParamsGiven()
	{
		try
		{
			Dictionary<String, String> args = new Hashtable<String, String>();
			API.configure();
			assertNull(API.getInstance().search(args));
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void itShouldReturnNullFromSearchIfNullSearchParamsGiven()
	{
		try
		{
			API.configure();
			assertNull(API.getInstance().search(null));
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void itShouldUseTheWebConnectionFromSearchByArtist()
	{
		boolean errorOccurred = false;
		final WebConnection cnn = mockery.mock(WebConnection.class);
		try
		{
			API.configure(new DefaultConfigurator(cnn));
			mockery.checking(new Expectations() {{
				oneOf(cnn).getResponse("http://api.jambase.com/search?band=The+Dead&apikey=This is the key");
			}});
			API.getInstance().searchByArtist("The Dead", new Hashtable<String, String>());
			mockery.assertIsSatisfied();
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		
		assertFalse(errorOccurred);
	}
	
	@Test
	public void itShouldUseTheWebConnectionFromSearchByZipCode()
	{
		boolean errorOccurred = false;
		final WebConnection cnn = mockery.mock(WebConnection.class);
		try
		{
			API.configure(new DefaultConfigurator(cnn));
			mockery.checking(new Expectations() {{
				oneOf(cnn).getResponse("http://api.jambase.com/search?zip=90210&apikey=This is the key");
			}});
			API.getInstance().searchByZipcode("90210", new Hashtable<String, String>());
			mockery.assertIsSatisfied();
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		
		assertFalse(errorOccurred);
	}
	
	@Test
	public void itShouldUseTheWebConnectionFromSearch()
	{
		boolean errorOccurred = false;
		final WebConnection cnn = mockery.mock(WebConnection.class);
		try
		{
			API.configure(new DefaultConfigurator(cnn));
			mockery.checking(new Expectations() {{
				oneOf(cnn).getResponse("http://api.jambase.com/search?user=test&zip=90210&apikey=This is the key");
			}});
			Dictionary<String, String> args = new Hashtable<String, String>();
			args.put("zip", "90210");
			args.put("user", "test");
			API.getInstance().search(args);
			mockery.assertIsSatisfied();
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		
		assertFalse(errorOccurred);
	}
	
	@Test
	public void itShouldUseTheWebConnectionFromSearchByUser()
	{
		boolean errorOccurred = false;
		final WebConnection cnn = mockery.mock(WebConnection.class);
		try
		{
			API.configure(new DefaultConfigurator(cnn));
			mockery.checking(new Expectations() {{
				oneOf(cnn).getResponse("http://api.jambase.com/search?user=test&apikey=This is the key");
			}});
			API.getInstance().searchByUser("test", new Hashtable<String, String>());
			mockery.assertIsSatisfied();
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		
		assertFalse(errorOccurred);
	}
	
	@Test
	public void itShouldBeConfigurableWithoutHavingToSpecifyTheConfigurator()
	{
		boolean errorOccurred = false;
		try
		{
			API.configure();
			assertEquals("This is the key", API.getInstance().getAPIKey());
			assertEquals(DefaultWebConnection.class, API.getInstance().getWebConnection().getClass());
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		
		assertFalse(errorOccurred);
	}
	
	@Test
	public void itShouldAllowTheConfigFileNameToBeOverridden()
	{
		boolean errorOccurred = false;
		try
		{
			API.configure(new DefaultConfigurator("myrandomfile.txt"));
			assertEquals("This is the key from the custom file", API.getInstance().getAPIKey());
			assertEquals(DefaultWebConnection.class, API.getInstance().getWebConnection().getClass());
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		
		assertFalse(errorOccurred);
	}
	
	@Test
	public void itShouldAllowTheWebConnectionToBeOverridden()
	{
		boolean errorOccurred = false;
		try
		{
			API.configure(new DefaultConfigurator(new MockWebConnection()));
			assertEquals("This is the key", API.getInstance().getAPIKey());
			assertEquals(MockWebConnection.class, API.getInstance().getWebConnection().getClass());
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		
		assertFalse(errorOccurred);
	}
	
	@Test
	public void itShouldAllowTheWebConnectionAndFileNameToBeOverridden()
	{
		boolean errorOccurred = false;
		try
		{
			API.configure(new DefaultConfigurator("myrandomfile.txt", new MockWebConnection()));
			assertEquals("This is the key from the custom file", API.getInstance().getAPIKey());
			assertEquals(MockWebConnection.class, API.getInstance().getWebConnection().getClass());
		} 
		catch (Exception e)
		{
			errorOccurred = true;
		}
		
		assertFalse(errorOccurred);
	}
	
	@Test
	public void itShouldNotThrowAnExceptionIfConfigured()
	{		
		boolean exceptionThrown = false;
		
		try
		{
			API.configure(new APIConfigurator() {

				public void configure(Jambase4JAPI api)
				{
					api.setAPIKey("key");
					api.setWebConnection(new MockWebConnection());
					
				}
				
			});
			API.getInstance();
		} 
		catch (APINotConfiguredException e)
		{
			exceptionThrown = true;
		}
		catch(Exception e)
		{
			
		}
		
		assertFalse(exceptionThrown);
	}
	
	@Test
	public void itShouldThrowAnExceptionIfNotConfigured()
	{		
		boolean exceptionThrown = false;
		
		try
		{
			API.configure(new APIConfigurator() {

				public void configure(Jambase4JAPI api)
				{
					api.setAPIKey(null);
					api.setWebConnection(null);
					
				}
				
			});
			
			API.getInstance();
		} 
		catch (APINotConfiguredException e)
		{
			exceptionThrown = true;
		}
		catch(Exception e)
		{
			
		}
		
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void itShouldThrowAnExceptionIfConfiguredWithAnEmptyAPIKey()
	{		
		boolean exceptionThrown = false;
		
		try
		{
			API.configure(new APIConfigurator() {

				public void configure(Jambase4JAPI api)
				{
					api.setAPIKey("");
					api.setWebConnection(null);
					
				}
				
			});
			
			API.getInstance();
		} 
		catch (APINotConfiguredException e)
		{
			exceptionThrown = true;
		}
		catch(Exception e)
		{
			
		}
		
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void itShouldThrowAnExceptionIfConfiguredWithAPIKeyThatIsEmptySpace()
	{		
		boolean exceptionThrown = false;
		
		try
		{
			API.configure(new APIConfigurator() {

				public void configure(Jambase4JAPI api)
				{
					api.setAPIKey("  ");
					api.setWebConnection(null);
					
				}
				
			});
			
			API.getInstance();
		} 
		catch (APINotConfiguredException e)
		{
			exceptionThrown = true;
		}
		catch(Exception e)
		{
			
		}
		
		assertTrue(exceptionThrown);
	}

}
