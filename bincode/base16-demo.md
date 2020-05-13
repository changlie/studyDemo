```java
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileConvert {
    static byte[] base16 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static byte getVal(byte b) {
        int len = base16.length;
        for (int i = 0; i < len; i++) {
            if (base16[i] == b) {
                return (byte) i;
            }
        }
        throw new RuntimeException("not found origin value map: " + b);
    }

    public static void main(String[] args) throws Exception {
        // split();
        merge();
    }

    static void merge() throws Exception {
        String sourceDir = "D:\\Work\\rTool";
        String outFile = "D:\\Work\\rTool1.exe";

        File dir = new File(sourceDir);
        File[] files = dir.listFiles();
        List<Byte> res = new ArrayList<Byte>();
        for (File f : files) {
            System.out.println(f.getName());
            FileInputStream fis = new FileInputStream(f);
            byte[] buf = new byte[2048];
            int len = 0;
            while ((len = fis.read(buf)) > 0) {
                for (int i = 0; i < len; i += 2) {
                    byte head = getVal(buf[i]);
                    byte tail = getVal(buf[i + 1]);
                    res.add((byte) (head << 4 | tail));
                }
            }
            fis.close();
        }
        byte[] listToArr = listToArr(res);
        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(listToArr);
        fos.flush();
        fos.close();
        System.out.println("merge finish!");
    }

    static void split() throws FileNotFoundException, IOException {
        String splitFile = "D:\\bakup\\rToolsSetup.exe";
        String outDir = "D:\\Work\\rTool\\";
        int block = 1024 * 1024;// 1mb

        RandomAccessFile file = new RandomAccessFile(splitFile, "r");

        byte[] buf = new byte[block];
        int len = 0;
        int index = 1;
        while ((len = file.read(buf)) > 0) {
            FileOutputStream fos = new FileOutputStream(outDir + index);
            byte[] res = new byte[len * 2];
            for (int i = 0; i < len; i++) {
                byte b = buf[i];
                byte head = (byte) (b >> 4 & 0x0f);
                byte tail = (byte) (b & 0x0f);
                res[i * 2] = base16[head];
                res[i * 2 + 1] = base16[tail];
            }
            fos.write(res);
            fos.close();
            index++;
        }
        file.close();
    }

    static byte[] listToArr(List<Byte> buf) {
        int len = buf.size();
        System.out.println("total: " + buf.size());
        byte[] res = new byte[len];
        for (int i = 0; i < len; i++) {
            if (i % 100000 == 99)
                System.out.println("listToArr index: " + i);
            res[i] = buf.get(i);
        }
        return res;
    }
}

```
