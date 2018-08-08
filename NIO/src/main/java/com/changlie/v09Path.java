package com.changlie;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class v09Path {
    public static void main(String[] args) {
        //Creating an Absolute Path
        Path pathq = Paths.get("c:\\software\\qq.exe");
        System.out.println("absolute path1: "+pathq.toString());
        Path path2 = Paths.get("/home/changlie/nio-test.txt");
        System.out.println("absolute path1: "+path2.toString());
        System.out.println("----------------------------------");

        // Creating a Relative Path
        System.out.println("\n\nrelative path:");
        System.out.println(Paths.get("d:\\data", "projects"));
        System.out.println(Paths.get("d:\\data", "projects\\a-project\\myfile.txt"));

        System.out.println(Paths.get("d:\\data\\projects\\.\\a-project").normalize());
        System.out.println(Paths.get("d:\\data\\projects\\a-project\\..\\another-project").normalize());



        // normalize
        System.out.println("\n\ncurrent path: "+Paths.get(".").toAbsolutePath().normalize());
        System.out.println("parent path of parent: "+Paths.get("..").toAbsolutePath().normalize());


        System.out.println(File.separator+" <---");

    }
}
