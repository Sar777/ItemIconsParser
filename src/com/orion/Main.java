package com.orion;

import com.orion.io.AsyncFileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    static final int CONSOLE_ARGUMENTS = 4;

    static final int CONSOLE_ARG_MIN = 0;
    static final int CONSOLE_ARG_MAX = 1;
    static final int CONSOLE_ARG_FILENAME = 2;
    static final int CONSOLE_ARG_THREADS = 3;

    static final String URL_WOWHEAD_ITEM = "http://wowhead.com/item=";

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
//        if (args.length != CONSOLE_MIN_ARGUMENTS) {
//            System.out.print("Fail input console arguments... Format: {min} {max} {filename} {threads}");
//            return;
//        }
//
//        int min = Integer.parseInt(args[CONSOLE_ARG_MIN]);
//        int max = Integer.parseInt(args[CONSOLE_ARG_MAX]);
//        threads = Integer.parseInt(args[CONSOLE_ARG_THREADS]);
//        String filename = args[CONSOLE_ARG_FILENAME];

        int min = 99999;
        int max = 180000;
        int threads = 10;

        String filename = "2.sql";

        System.out.println(String.format("Initial parsing. Min: %d, Max: %d, Filename: %s, Threads: %d", min, max, filename, threads));

        AsyncFileWriter fileWriter;
        try {
            fileWriter = new AsyncFileWriter(new File(filename));
        } catch (IOException e) {
            System.out.println("Error: file open " + filename);
            return;
        }

        fileWriter.open();
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = min; i < max; ++i) {
            service.submit(new ItemIconWorker(i, fileWriter));
        }

        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ignored) {
        }
        fileWriter.close();
    }
}
