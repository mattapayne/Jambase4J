package ca.mattpayne.jambase4j;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class CollectionUtils
{
	public static final <T, K> boolean containsKey(Dictionary<T, K> collection, T key)
	{
		Enumeration<T> e = collection.keys();
		while(e.hasMoreElements())
		{
			if(e.nextElement().equals(key))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static final <T> boolean contains(T[] args, T item)
	{
		for(T t : args)
		{
			if(t.equals(item))
			{
				return true;
			}
		}
		
		return false;
	}
}
