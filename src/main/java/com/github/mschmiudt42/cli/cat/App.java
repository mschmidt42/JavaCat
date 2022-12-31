package com.github.mschmiudt42.cli.cat;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.*;

@Command(name = "cat", mixinStandardHelpOptions = true, version = "cat 1.0",
	description = "cat")
public class App  implements Callable<Integer> 
{
    @Parameters(arity = "0..*", description = "input file(s)")
    String[] fileNames;

    @Option( names = {"-d", "--debug"}, description="print debug messages")
    boolean debug = false;

    @Option( names = {"-n", "--number"}, description="number all output lines")
    boolean numberAll = false;

    @Option( names = {"-b", "--number-nonblank"}, description="number nonempty output lines, overrides -n")
    boolean numberNonblank = false;


    private long lineNr = 0;

    public static void main( String[] args )
    {
    	int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    private void catFile(String fname){
        try {
            if("-".equals(fname)) {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                catStream(in.lines());
            } else {
                Stream<String> stream = Files.lines(Paths.get(fname));
                catStream(stream);
            }
        } catch (IOException e) {
            if(debug) printErr("ERROR: " + e.getClass().getName() + " Msg: " + e.getMessage());
        }
    }

    private void catStream(Stream<String> stream){
        stream.forEach(l -> println(l));
    }

    public void println(String line){
        if(numberNonblank){
            if(line.trim().length()>0){
                lineNr++;
                System.out.println(String.format("%6d  %s", lineNr, line));
            } else{
                System.out.println("");
            }
        } else if(numberAll){
            lineNr++;
            System.out.println(String.format("%6d  %s", lineNr, line));
        } else {
            System.out.println(line);
        }
    }

    public void printErr(String line){
        System.err.println(line);
    }

    public Integer call() throws Exception {
        if(fileNames != null) {
            for (String filename : fileNames) {
                catFile(filename);
            }
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            catStream(in.lines());
        }

        return 0;
    }
}
