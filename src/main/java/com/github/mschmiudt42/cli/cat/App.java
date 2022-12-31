package com.github.mschmiudt42.cli.cat;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.IOException;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.*;

@Command(name = "cat", mixinStandardHelpOptions = true, version = "cat 0.1",
	description = "cat")
public class App  implements Callable<Integer> 
{
    @Parameters(arity = "0..*", description = "input file(s)")
    String[] fileNames;

    @Option( names = {"-d", "--debug"}, description="print debug messages")
    boolean debug = false;


    public static void main( String[] args )
    {
    	int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    private void catFile(String fname){
        try {
            Stream<String> stream = Files.lines(Paths.get(fname));
            catFile(stream);
        } catch (IOException e) {
            if(debug) printErr("ERROR: " + e.getClass().getName() + " Msg: " + e.getMessage());
        }

    }

    private void catFile(Stream<String> stream){
        stream.forEach(l -> println(l));
    }

    public void println(String line){
        System.out.println(line);
    }

    public void printErr(String line){
        System.err.println(line);
    }

    public Integer call() throws Exception {
        System.out.println( "Hello World!" );

        if(fileNames != null) {
            for (String filename : fileNames) {
                catFile(filename);
            }
        }

        return 0;
    }
}
