package ca.mattpayne.jambase4j;

import ca.mattpayne.jambase4j.interfaces.Artist;

public class ArtistImpl implements Artist
{
	private String name;
	private String id;
	
	public ArtistImpl(Artist a)
	{
		build(a);
	}
	
	public String getName()
	{
		return name;
	}

	public String getId()
	{
		return id;
	}

	private void build(Artist a)
	{
		this.id = a.getId();
		this.name = a.getName();
	}
}
