# dimoni-parser
parse log files to create html reports

During my years of code automated testing I created several types of html reports. At the end, the best way that I found is that the robots just leave a text log file with several entries and an external tool (this tool) parse the log and create an html reports, leave logs in DB to create statistics about times, messages ...

### How to use
The key idea is just code the robot to leave a log file and focus on his functionality, performance or whatever other issues. Just take care that your logs lines are writen with this format:
* [date-time-format] - [LOG LEVEL] : [Message]

* The date format is configurable via parser.properties file. By default is "yyyy-MM-dd HH:mm:ss.SSS"
* The log level is an enum and the parser looks for this string "- LOG_LEVEL :" strictly ( IMPORTANTS! are the - and : )
* The message is your message

If a line does not contains this format is just added to the previous one.
Any error with the parser is added to the end of the html report

### Log Levels
* BLOCKER
* CONSOLE 
* CRITICAL
* DEBUG 
* ERROR 
* INFO
* INTERNAL_ERROR 
* MAJOR 
* MINOR 
* PICTURE
* STEP
* SUCCESS
* TEST
* TIME
* TRIVIAL 
* WARNING
* OTHERS 

#### Special cases
* TEST - This line is used to declare the test name but the style is the debug style
* TIME - The message will be parsed using a separator string (by default #@#) and the expected structure is *"time #@# [TIME_TYPE] #@# [message]"* . This seapartor is configured via parser.properties

See a log example 
