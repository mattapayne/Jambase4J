package ca.mattpayne.jambase4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import ca.mattpayne.jambase4j.interfaces.Event;
import ca.mattpayne.jambase4j.interfaces.EventBuilder;

public class EventBuilderImpl implements EventBuilder
{
	
	public List<Event> build(String xml)
	{
		List<Event> events = null;
		try
		{
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			EventsHandler handler = new EventsHandler();
			parser.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")), handler);
			events = handler.getEvents();
		}
		catch(NullPointerException e)
		{
			
		}
		catch (ParserConfigurationException e)
		{
			
		} 
		catch (SAXException e)
		{
			
		} 
		catch (UnsupportedEncodingException e)
		{

		} 
		catch (IOException e)
		{
			
		}
		
		return events;
	}

}
