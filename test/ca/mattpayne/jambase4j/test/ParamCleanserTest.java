package ca.mattpayne.jambase4j.test;

import static org.junit.Assert.*;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mattpayne.jambase4j.API;
import ca.mattpayne.jambase4j.CollectionUtils;
import ca.mattpayne.jambase4j.ParamCleanserImpl;
import ca.mattpayne.jambase4j.interfaces.ParamCleanser;

public class ParamCleanserTest
{
	ParamCleanser cleanser = null;
	
	@Before
	public void setup()
	{
		cleanser = new ParamCleanserImpl(API.JAMBASE_PARAMS, API.ALIASES);
	}
	
	@After
	public void teardown()
	{
		cleanser = null;
	}
	
	@Test(expected=NullPointerException.class)
	public void itShouldRaiseANullPointerExceptionIfPassedANullDictionary()
	{
		cleanser.cleanse(null);
	}
	
	@Test
	public void itShouldRemoveAllEmptyValues()
	{
		Dictionary<String, String> initial = new Hashtable<String, String>();
		initial.put("band", "");
		initial.put("user", "");
		initial.put("zip", "90210");
		initial.put("radius", "50");
		
		Dictionary<String, String> result = cleanser.cleanse(initial);
	
		assertTrue(CollectionUtils.containsKey(result, "zip"));
		assertTrue(CollectionUtils.containsKey(result, "radius"));
		
		assertFalse(CollectionUtils.containsKey(result, "band"));
		assertFalse(CollectionUtils.containsKey(result, "user"));
	}
	
	@Test
	public void itShouldRemoveAllNonJambaseParams()
	{
		Dictionary<String, String> initial = new Hashtable<String, String>();
		initial.put("artist", "The Dead");
		initial.put("band", "The Dead");
		initial.put("user", "test user");
		initial.put("zip", "90210");
		initial.put("radius", "50");
		initial.put("monkey", "banana");
		initial.put("butler", "monocle");
		
		Dictionary<String, String> result = cleanser.cleanse(initial);
		
		assertTrue(CollectionUtils.containsKey(result, "band"));
		assertTrue(CollectionUtils.containsKey(result, "user"));
		assertTrue(CollectionUtils.containsKey(result, "zip"));
		assertTrue(CollectionUtils.containsKey(result, "radius"));
		
		assertFalse(CollectionUtils.containsKey(result, "monkey"));
		assertFalse(CollectionUtils.containsKey(result, "butler"));
		assertFalse(CollectionUtils.containsKey(result, "artist"));
	}
}
