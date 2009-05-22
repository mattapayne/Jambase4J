package ca.mattpayne.jambase4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ca.mattpayne.jambase4j.interfaces.Artist;
import ca.mattpayne.jambase4j.interfaces.Event;
import ca.mattpayne.jambase4j.interfaces.Venue;

public class EventsHandler extends DefaultHandler
{
	private static final String EVENT_ELEMENT = "event";
	private List<Event> events = null;
	private EventData currentEvent = null;
	
	private class ElementProcessor
	{
		protected Stack<String> elements;
		
		protected ElementProcessor()
		{
			elements = new Stack<String>();
		}
		
		public void processEnd(String name)
		{
			if(elements.size() > 0)
			{
				elements.pop();
			}
		}

		public void processStart(String name)
		{
			elements.push(name);
		}

		public void processText(char[] text, int start, int length)
		{
			if(elements.size() > 0)
			{
				char[] actual = new char[length];
				System.arraycopy(text, start, actual, 0, length);
				String lastProperty = elements.peek();
				Class klazz = this.getClass();
				Field setter;
				try
				{
					setter = klazz.getField(lastProperty);
					setter.set(this, removeBadWhiteSpace(new String(actual)));
				} 
				catch(Exception e)
				{
					
				}
			}
		}
		
		private String removeBadWhiteSpace(String str)
		{
			return str.replaceAll("\\t", "");
		}
	}
	
	private class EventData extends ElementProcessor implements Event
	{
		private static final String VENUE = "venue";
		private static final String ARTIST = "artist";
		private static final String ARTISTS = "artists";
		
		private List<ArtistData> artists;
		private VenueData venue;
		public String event_id;
		public String event_url;
		public String ticket_url;
		public String event_date;
		
		private boolean workingOnVenue = false;
		private boolean workingOnArtist = false;
		
		public EventData()
		{
			elements = new Stack<String>();
		}
		
		public List<Artist> getArtists()
		{
			List<Artist> tmp = new ArrayList<Artist>();
			tmp.addAll(artists);
			return tmp;
		}

		public String getDate()
		{
			return event_date.replaceAll("", "");
		}

		public String getTicketUrl()
		{
			return ticket_url;
		}

		public String getUrl()
		{
			return event_url;
		}

		public Venue getVenue()
		{
			return venue;
		}

		public String getId()
		{
			return event_id;
		}

		public void processEnd(String name)
		{
			if(name.equals(ARTISTS))
			{
				return;
			}
			
			if(name.equals(ARTIST))
			{
				workingOnArtist = false;
			}
			else if(name.equals(VENUE))
			{
				workingOnVenue = false;
			}
			else
			{
				if(workingOnArtist)
				{
					artists.get(artists.size() - 1).processEnd(name);
				}
				else if(workingOnVenue)
				{
					venue.processEnd(name);
				}
				else
				{
					super.processEnd(name);
				}
			}
		}

		public void processStart(String name)
		{
			if(name.equals(VENUE))
			{
				this.venue = new VenueData();
				workingOnVenue = true;
			}
			else if(name.equals(ARTISTS))
			{
				this.artists = new ArrayList<ArtistData>();
			}
			else if(name.equals(ARTIST))
			{
				this.artists.add(new ArtistData());
				workingOnArtist = true;
			}
			else
			{
				if(workingOnArtist)
				{
					artists.get(artists.size() - 1).processStart(name);
				}
				else if(workingOnVenue)
				{
					venue.processStart(name);
				}
				else
				{
					super.processStart(name);
				}
			}
		}

		public void processText(char[] text, int start, int length)
		{
			if(workingOnVenue)
			{
				venue.processText(text, start, length);
			}
			else if(workingOnArtist)
			{
				artists.get(artists.size() - 1).processText(text, start, length);
			}
			else
			{
				super.processText(text, start, length);
			}
		}
	}
	
	private class ArtistData extends ElementProcessor implements Artist
	{
		public String artist_id;
		public String artist_name;
		
		public String getName()
		{
			return artist_name;
		}

		public String getId()
		{
			return artist_id;
		}
	}
	
	private class VenueData extends ElementProcessor implements Venue
	{
		public String venue_id;
		public String venue_name;
		public String venue_city;
		public String venue_state;
		public String venue_zip;

		public String getName()
		{
			return venue_name;
		}

		public String getState()
		{
			return venue_state;
		}

		public String getZipCode()
		{
			return venue_zip;
		}

		public String getId()
		{
			return venue_id;
		}
		
		public String getCity()
		{
			return venue_city;
		}
	}
	
	public EventsHandler()
	{
		events = new ArrayList<Event>();
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if(qName.equals(EVENT_ELEMENT))
		{
			currentEvent = new EventData();
		}
		else if(currentEvent != null)
		{
			currentEvent.processStart(qName);
		}
	}
	
	public void characters(char[] chars, int start, int length)
	{
		if(currentEvent != null)
		{
			currentEvent.processText(chars, start, length);
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if(qName.equals(EVENT_ELEMENT))
		{
			events.add(new EventImpl(currentEvent));
			currentEvent = null;
		}
		else if(currentEvent != null)
		{
			currentEvent.processEnd(qName);
		}
	}
	
	public List<Event> getEvents()
	{
		return events;
	}
}
