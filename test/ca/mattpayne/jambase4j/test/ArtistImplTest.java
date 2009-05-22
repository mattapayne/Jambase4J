package ca.mattpayne.jambase4j.test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mattpayne.jambase4j.ArtistImpl;
import ca.mattpayne.jambase4j.interfaces.Artist;

public class ArtistImplTest
{
	private Artist artist = null;
	
	@Before
	public void setup()
	{
		artist = new ArtistImpl(new Artist() {

			public String getName()
			{
				return "test";
			}

			public String getId()
			{
				return "1";
			}});
	}
	
	@After
	public void teardown()
	{
		artist = null;
	}
	
	@Test
	public void itShouldBuildItselfFromTheArtistData()
	{
		assertEquals("test", artist.getName());
		assertEquals("1", artist.getId());
	}
	
	@Test(expected=NullPointerException.class)
	public void itShouldThrowAnExceptionIfPassedANullArtist()
	{
		new ArtistImpl(null);
	}
}
