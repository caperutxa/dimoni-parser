package caperutxa.dimoni.parser.constants;

/**
 * priority 1 is the highest
 * DEBUG priority is the default threshold
 * 
 * For each new level you need to define in Parser.setDeclarationLevel()
 * * If this new type counts in some category
 * * and if is the case set the correct test result declaration
 * 
 * And for testing
 * * modify allTypesOfLogs file to add the new types
 * * run the test ParserTest.allTypesOfLogs()
 * 
 * @author caperutxa
 *
 */
public enum ELogLevel {
	BLOCKER (1)
	, CONSOLE (11)
	, CONSOLE_WARNING (9)
	, CONSOLE_ERROR (6)
	, CRITICAL(2)
	, DEBUG (10)
	, ERROR (3)
	, INFO (5)
	, INTERNAL_ERROR (4)
	, MAJOR (6)
	, MINOR (7)
	, PICTURE (3)
	, STEP (3)
	, SUCCESS( 10)
	, TEST (1)
	, TIME (6)
	, TRIVIAL (11)
	, WARNING (4)
	, OTHERS (10);
	
	private final int priority;
	
	ELogLevel(int level) {
		this.priority = level;
	}
	
	public int priority() { return priority; }
}
