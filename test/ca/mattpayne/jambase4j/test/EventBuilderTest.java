package ca.mattpayne.jambase4j.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.mattpayne.jambase4j.EventBuilderImpl;
import ca.mattpayne.jambase4j.interfaces.Event;
import ca.mattpayne.jambase4j.interfaces.EventBuilder;
import ca.mattpayne.jambase4j.interfaces.WebConnection;

public class EventBuilderTest
{
	private EventBuilder builder = null;
	
	@Before
	public void setup()
	{
		builder = new EventBuilderImpl();
	}
	
	@After
	public void teardown()
	{
		builder = null;
	}
	
	@Test
	public void itShouldReturnANullCollectionIfTheXmlPassedInIsNull()
	{
		assertNull(builder.build(null));
	}
	
	
	@Test
	public void itShouldReturnANullCollectionIfTheXmlPassedInIsEmpty()
	{
		assertNull(builder.build(""));
	}
	
	@Test
	public void itShouldBuildEvents()
	{
		WebConnection cnn = new MockWebConnection();
		String xml = cnn.getResponse("whatever");
		List<Event> events = builder.build(xml);
		assertTrue(events.size() == 29);
	}
}
