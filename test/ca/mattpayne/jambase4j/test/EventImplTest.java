package ca.mattpayne.jambase4j.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.mattpayne.jambase4j.EventImpl;
import ca.mattpayne.jambase4j.interfaces.Artist;
import ca.mattpayne.jambase4j.interfaces.Event;
import ca.mattpayne.jambase4j.interfaces.Venue;


public class EventImplTest
{
	Event event = null;
	
	private class EventDataHolder implements Event
	{
		public List<Artist> getArtists()
		{
			List<Artist> tmp = new ArrayList<Artist>();
			tmp.add(new Artist() {

				public String getName()
				{
					return "The Dead";
				}

				public String getId()
				{
					return "1";
				}});
			
			return tmp;
		}

		public String getDate()
		{
			return "10/21/2009";
		}

		public String getTicketUrl()
		{
			return "http://test.ca?tickets";
		}

		public String getUrl()
		{
			return "http://test.ca";
		}

		public Venue getVenue()
		{
			return new Venue() {

				public String getCity()
				{
					return "Albequerque";
				}

				public String getName()
				{
					return "The Starlight";
				}

				public String getState()
				{
					return "New Mexico";
				}

				public String getZipCode()
				{
					return "90210";
				}

				public String getId()
				{
					return "1";
				}
			};
		}

		public String getId()
		{
			return "1";
		}
		
	}
	
	@Before
	public void setup()
	{
		event = new EventImpl(new EventDataHolder());
	}
	
	@After
	public void teardown()
	{
		event = null;
	}
	
	@Test
	public void itShouldBuildItselfFromTheEventData()
	{
		assertEquals("1", event.getId());
		assertEquals("http://test.ca", event.getUrl());
		assertEquals("http://test.ca?tickets", event.getTicketUrl());
		assertEquals("10/21/2009", event.getDate());
		assertTrue(event.getArtists().size() == 1);
		assertNotNull(event.getVenue());
	}
	
	@Test(expected=NullPointerException.class)
	public void itShouldThrowAnExceptionIfPassedNullEventData()
	{
		new EventImpl(null);
	}
}
