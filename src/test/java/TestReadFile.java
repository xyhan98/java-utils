import org.junit.jupiter.api.Test;
import org.xyhan98.utils.ReadFileUtil;

import java.io.File;
import java.io.IOException;

public class TestReadFile {

    @Test
    void testReadDocx() throws IOException {
        File file = new File("C:\\Users\\xinyi\\Desktop\\temp\\test\\1.docx");
        String content = ReadFileUtil.readWord(file);
        System.out.println(content);
    }

    @Test
    void testReadDoc() throws IOException {
        File file = new File("C:\\Users\\xinyi\\Desktop\\temp\\test\\6.doc");
        String content = ReadFileUtil.readWord(file);
        System.out.println(content);
    }

    @Test
    void testReadPdf() throws IOException {
        File file = new File("C:\\Users\\xinyi\\Desktop\\temp\\test\\4.pdf");
        String content = ReadFileUtil.readPDF(file);
        System.out.println(content);
    }
}
