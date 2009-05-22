package ca.mattpayne.jambase4j.interfaces;

import java.util.List;

public interface Event extends JambaseObject
{
	public List<Artist> getArtists();
	public Venue getVenue();
	public String getUrl();
	public String getTicketUrl();
	public String getDate();
}
