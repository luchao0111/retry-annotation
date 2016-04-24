# retry-annotation
This is a simple utility to configure retry mechanism unintrusively.

Synopsis

This simple utility provides a means to configure retry mechanism at a method level by using the Retry annotation

Code Example

Given a piece of code that throws an exception for some reason, the retry annotation will ensure that the method will be executed 
configurable number of times (default 3 incase limit is not set). The caller of this method is not aware of this.

    @Retry
    public void print() throws Exception {
        if (++counter < 3)
            throw new SocketException("Exception occured in service, after " + (counter) + " attempts");
        System.err.println("print completed successfully in " + counter + " attempts");
    }

API Reference

Check out retry.service.ConsoleService.java for the full list of capabilities.

Tests

No unit tests. 

Check out the #getInstance() in retry.service.ConsoleService on how to incorporate the retry mechanism in your project

Contributors

Inspired from spring batch
