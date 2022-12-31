package com.github.mschmiudt42.cli.cat;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Hello world!
 *
 */
@Command(name = "cat", mixinStandardHelpOptions = true, version = "cat 0.1",
	description = "cat")
public class App  implements Callable<Integer> 
{
    public static void main( String[] args )
    {
    	int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    public Integer call() throws Exception {
        System.out.println( "Hello World!" );
        return 0;
    }
}
