package com.changlie;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class v10Files {
    static String home = "/home/changlie";

    @Test
    public void walkFileTree2() {
        //todo Deleting Directories Recursively

        Path rootPath = Paths.get(home, "data");

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("delete file: " + file.toString());
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    System.out.println("delete dir: " + dir.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void walkFileTree1() {
        //todo Searching For Files

        Path rootPath = Paths.get(home);
        String fileToFind = File.separator + "readme";

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    String fileString = dir.toAbsolutePath().toString();

                    if(fileString.contains("/.")){
                        System.out.println("pathString = " + fileString);
                        return FileVisitResult.SKIP_SUBTREE;
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();



                    if(fileString.endsWith(fileToFind)){
                        System.out.println("file found at path: " + file.toAbsolutePath());
                        return FileVisitResult.TERMINATE;
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void stringMath() {
        System.out.println("/home/changlie/.cache".contains("/."));
    }

    @Test
    public void delete1() {
        Path path = Paths.get(home,"data/nio-toFile.txt");

        try {
            Files.delete(path);
        } catch (IOException e) {
            //deleting file failed
            e.printStackTrace();
        }
    }

    @Test
    public void move1() {
        Path sourcePath      = Paths.get(home,"demo");
        Path destinationPath = Paths.get(home,"data/nio-toFile-moved.txt");

        try {
            Files.move(sourcePath, destinationPath,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            //moving file failed.
            e.printStackTrace();
        }
    }

    @Test
    public void overwritingExistingFiles() {
        Path sourcePath      = Paths.get(home,"nio-toFile.txt");
        Path destinationPath = Paths.get(home,"nio-toFile-copy.txt");

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(FileAlreadyExistsException e) {
            //destination file already exists
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }
    }

    @Test
    public void copy1() {
        Path sourcePath      = Paths.get(home,"nio-toFile.txt");
        Path destinationPath = Paths.get(home,"nio-toFile-copy.txt");

        try {
            Files.copy(sourcePath, destinationPath);
        } catch(FileAlreadyExistsException e) {
            //destination file already exists
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }


    }

    @Test
    public void createDirectory1() {
        Path path = Paths.get("/home/changlie/data");

        try {
            Path newDir = Files.createDirectory(path);
        } catch(FileAlreadyExistsException e){
            // the directory already exists.
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }


        try {
            Path newDir = Files.createDirectory(path);
        } catch(FileAlreadyExistsException e){
            // the directory already exists.
            System.out.println(path+ "已存在!");
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }
    }

    @Test
    public void exists1() {
        Path path = Paths.get("data/logging.properties");
        System.out.println(path+" : "+Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS}));

        Path path1 = Paths.get("/home/changlie/nio-data.txt");
        System.out.println(path1+" : "+Files.exists(path1, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS}));
    }
}
