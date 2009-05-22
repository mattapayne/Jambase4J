package ca.mattpayne.jambase4j.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import ca.mattpayne.jambase4j.MalformedConfigException;

public interface APIConfigurator
{
	public void configure(Jambase4JAPI api) throws MalformedConfigException, FileNotFoundException, IOException;
}
