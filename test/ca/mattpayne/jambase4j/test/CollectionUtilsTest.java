package ca.mattpayne.jambase4j.test;

import static org.junit.Assert.*;
import java.util.Dictionary;
import java.util.Hashtable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mattpayne.jambase4j.CollectionUtils;

public class CollectionUtilsTest
{
	private String[] testArray = null;
	private Dictionary<String, String> testDict = null;
	
	@Before
	public void setup()
	{
		testArray = new String[] {"hi", "there", "this", "is", "a", "test"};
		testDict = new Hashtable<String, String>();
		testDict.put("key1", "value1");
		testDict.put("key2", "value2");
		testDict.put("key3", "value3");
	}
	
	@After
	public void teardown()
	{
		testArray = null;
		testDict = null;
	}
	
	@Test(expected=NullPointerException.class)
	public void itShouldRaiseANullPointerExceptionIfPassedANullArray()
	{
		CollectionUtils.contains(null, "something");
	}
	
	@Test(expected=NullPointerException.class)
	public void itShouldRaiseANullPointerExceptionIfPassedANullDictionary()
	{
		CollectionUtils.containsKey(null, "something");
	}
	
	@Test
	public void itShouldNotBlowUpWithAnEmptyArray()
	{
		boolean errorThrown = false;
		try
		{
			CollectionUtils.contains(new String[] {}, "key2");
		}
		catch(Exception e)
		{
			errorThrown = true;
		}
		
		assertFalse(errorThrown);
	}
	
	@Test
	public void itShouldNotBlowUpWithAnEmptyDictionary()
	{
		boolean errorThrown = false;
		try
		{
			CollectionUtils.containsKey(new Hashtable<String, String>(), "key2");
		}
		catch(Exception e)
		{
			errorThrown = true;
		}
		
		assertFalse(errorThrown);
	}
	
	@Test
	public void itShouldBeAbleToTellIfAKeyIsInADictionary()
	{
		boolean isContained = CollectionUtils.containsKey(testDict, "key2");
		assertTrue(isContained);
	}
	
	@Test
	public void itShouldBeAbleToTellIfAKeyIsNotInADictionary()
	{
		boolean isContained = CollectionUtils.containsKey(testDict, "xxx");
		assertFalse(isContained);
	}
	
	@Test
	public void itShouldBeAbleToTellIfAnItemIsInAnArray()
	{
		boolean isContained = CollectionUtils.contains(testArray, "test");
		assertTrue(isContained);
	}
	
	@Test
	public void itShouldBeAbleToTellIfAnItemIsNotInAnArray()
	{
		boolean isContained = CollectionUtils.contains(testArray, "monkey");
		assertFalse(isContained);
	}
}
