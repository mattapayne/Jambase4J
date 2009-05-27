package ca.mattpayne.jambase4j;

import java.util.Dictionary;
import java.util.Hashtable;

import ca.mattpayne.jambase4j.interfaces.SearchParams;

public class SearchParamsImpl implements SearchParams
{
	private static final String BAND = "band";
	private static final String ZIP = "zip";
	private static final String USER = "user";
	private static final String RADIUS = "radius";
	private String band;
	private String zipCode;
	private String radius;
	private String user;
	
	public SearchParams byBand(String band)
	{
		this.band = band;
		return this;
	}

	public SearchParams byRadius(String radius)
	{
		this.radius = radius;
		return this;
	}

	public SearchParams byUser(String user)
	{
		this.user = user;
		return this;
	}

	public SearchParams byZipcode(String zipCode)
	{
		this.zipCode = zipCode;
		return this;
	}
	
	public boolean isEmpty()
	{
		return toArgs().isEmpty();
	}

	public Dictionary<String, String> toArgs()
	{
		Dictionary<String, String> tmp = new Hashtable<String, String>();
		if(this.user != null && this.user.trim().length() > 0)
		{
			tmp.put(USER, this.user.trim());
		}
		if(this.zipCode != null && this.zipCode.trim().length() > 0)
		{
			tmp.put(ZIP, this.zipCode.trim());
		}
		if(this.radius != null && this.radius.trim().length() > 0)
		{
			tmp.put(RADIUS, this.radius.trim());
		}
		if(this.band != null && this.band.trim().length() > 0)
		{
			tmp.put(BAND, this.band.trim());
		}
		return tmp;
	}

}
