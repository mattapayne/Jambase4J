package ca.mattpayne.jambase4j;

import ca.mattpayne.jambase4j.interfaces.Venue;

public class VenueImpl implements Venue
{
	private String city;
	private String name;
	private String state;
	private String zipCode;
	private String id;
	
	public VenueImpl(Venue v)
	{
		build(v);
	}
	
	public String getCity()
	{
		return city;
	}

	public String getName()
	{
		return name;
	}

	public String getState()
	{
		return state;
	}

	public String getZipCode()
	{
		return zipCode;
	}

	public String getId()
	{
		return id;
	}
	
	private void build(Venue v)
	{
		this.id = v.getId();
		this.name = v.getName();
		this.city = v.getCity();
		this.state = v.getState();
		this.zipCode = v.getZipCode();
	}
}
