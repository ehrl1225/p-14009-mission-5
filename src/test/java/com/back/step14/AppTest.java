package com.back.step14;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void add(){
        assertEquals(30,30);
    }

    public static void clear(){
        File json_dir = new File("./db/wiseSaying/");
        File[] files = json_dir.listFiles();
        for(File file : files){
            file.delete();
        }
    }

    public static String run(String input){
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        App app = new App();
        app.run();
        return outputStream.toString();
    }

}
