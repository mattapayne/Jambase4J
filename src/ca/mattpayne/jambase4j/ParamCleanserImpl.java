package ca.mattpayne.jambase4j;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import ca.mattpayne.jambase4j.interfaces.ParamCleanser;

public class ParamCleanserImpl implements ParamCleanser
{
	private String[] params;
	private Dictionary<String, String> aliases;
	
	public ParamCleanserImpl(String[] params, Dictionary<String, String> aliases)
	{
		this.params = params;
		this.aliases = aliases;
	}
	
	public Dictionary<String, String> cleanse(Dictionary<String, String> args)
	{
		Dictionary<String, String> tmp = new Hashtable<String, String>();
		Enumeration<String> e = args.keys();
		
		while(e.hasMoreElements())
		{
			String key = e.nextElement();
			String value = args.get(key);
			//only deal with values that are non-empty
			if(value != null && value.trim().length() > 0)
			{
				//if the current key exists within the array of acceptable params, add it
				if(CollectionUtils.contains(params, key))
				{
					tmp.put(key, value.trim());
				}
			}
		}
		//process aliases
		Dictionary<String, String> retVal = new Hashtable<String, String>();
		Enumeration<String> e1 = tmp.keys();
		
		while(e1.hasMoreElements())
		{
			String key = e1.nextElement();
			String value = tmp.get(key);
			
			//not aliased
			if(!CollectionUtils.containsKey(aliases, key))
			{
				retVal.put(key, value);
			}
			else
			{
				//aliased
				String realParam = mapAliasedParamToRealParam(key);
				//try to get the value of the real param
				String realValue = tmp.get(realParam);
				//if it exists, add it
				if(realValue != null)
				{
					retVal.put(realParam, realValue);
				}
				else
				{
					//otherwise use the value of the aliased param with the real param as the key
					retVal.put(realParam, value);
				}
			}
		}
		return retVal;
	}

	private String mapAliasedParamToRealParam(String key)
	{
		Enumeration<String> e = aliases.keys();
		while(e.hasMoreElements())
		{
			if(e.nextElement().equals(key))
			{
				return aliases.get(key);
			}
		}
		return null;
	}
}
