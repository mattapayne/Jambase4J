package ca.mattpayne.jambase4j.test;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mattpayne.jambase4j.VenueImpl;
import ca.mattpayne.jambase4j.interfaces.Venue;

public class VenueImplTest
{
	private Venue venue = null;
	
	@Before
	public void setUp() throws Exception
	{
		venue = new VenueImpl(new Venue() {

			public String getCity()
			{
				return "Kansas City";
			}

			public String getName()
			{
				return "Some Ampitheater";
			}

			public String getState()
			{
				return "Kansas";
			}

			public String getZipCode()
			{
				return "90210";
			}

			public String getId()
			{
				return "1";
			}});
	}

	@After
	public void tearDown() throws Exception
	{
		venue = null;
	}
	
	@Test
	public void itShouldBuildItselfFromVenueData()
	{
		assertEquals("1", venue.getId());
		assertEquals("Kansas", venue.getState());
		assertEquals("Kansas City", venue.getCity());
		assertEquals("90210", venue.getZipCode());
		assertEquals("Some Ampitheater", venue.getName());
	}
	
	@Test(expected=NullPointerException.class)
	public void itShouldThrowAnExceptionIfConstructedWithNullVenueData()
	{
		new VenueImpl(null);
	}

}
