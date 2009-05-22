package ca.mattpayne.jambase4j;

import java.util.ArrayList;
import java.util.List;

import ca.mattpayne.jambase4j.interfaces.Artist;
import ca.mattpayne.jambase4j.interfaces.Event;
import ca.mattpayne.jambase4j.interfaces.Venue;

public class EventImpl implements Event
{
	private String url;
	private String ticketUrl;
	private String date;
	private String id;
	private List<Artist> artists;
	private Venue venue;
	
	public EventImpl(Event event)
	{
		artists = new ArrayList<Artist>();
		build(event);
	}
	
	public List<Artist> getArtists()
	{
		return artists;
	}

	public String getDate()
	{
		return date;
	}

	public String getTicketUrl()
	{
		return ticketUrl;
	}

	public String getUrl()
	{
		return url;
	}

	public Venue getVenue()
	{
		return venue;
	}

	public String getId()
	{
		return id;
	}
	
	private void build(Event e)
	{
		this.url = e.getUrl();
		this.ticketUrl = e.getTicketUrl();
		this.date = e.getDate();
		this.id = e.getId();
		this.venue = new VenueImpl(e.getVenue());
		for(Artist a : e.getArtists())
		{
			this.artists.add(new ArtistImpl(a));
		}
	}
}
