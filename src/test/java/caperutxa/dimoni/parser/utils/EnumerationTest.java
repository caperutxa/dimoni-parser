package caperutxa.dimoni.parser.utils;

import org.junit.Test;

import caperutxa.dimoni.parser.constants.Constants;
import caperutxa.dimoni.parser.constants.ETimes;

public class EnumerationTest {

	@Test
	public void extratETimesEnumeration() {
		for(ETimes t : ETimes.values()) {
			System.out.println(t.name());
		}
	}
	
	@Test
	public void extractDateTimeFormat() {
		System.out.println(Constants.LOG_DATETIME_FORMAT.length());
	}
}
