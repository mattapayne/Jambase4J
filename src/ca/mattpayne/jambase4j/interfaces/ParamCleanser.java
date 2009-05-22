package ca.mattpayne.jambase4j.interfaces;

import java.util.Dictionary;

public interface ParamCleanser
{
	public Dictionary<String, String> cleanse(Dictionary<String, String> args);
}
