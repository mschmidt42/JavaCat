package com.github.mschmiudt42.cli;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Hello world!
 *
 */
@Command(name = "clidemo", mixinStandardHelpOptions = true, version = "clidemo 0.1",
	description = "just a demo.")
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
