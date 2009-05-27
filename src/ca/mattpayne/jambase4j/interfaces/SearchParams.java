package ca.mattpayne.jambase4j.interfaces;

import java.util.Dictionary;

public interface SearchParams
{
	public SearchParams byBand(String band);
	public SearchParams byUser(String user);
	public SearchParams byZipcode(String zipCode);
	public SearchParams byRadius(String radius);
	public Dictionary<String, String> toArgs();
}
