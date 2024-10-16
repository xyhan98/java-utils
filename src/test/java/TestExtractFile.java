import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xyhan98.utils.ExtractFileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class TestExtractFile {

    @Test
    void testExtractRar() throws IOException {
        String filePath = "C:\\Users\\xinyi\\Desktop\\temp\\test\\test.rar";
        Map<InputStream, String> extractedMap = ExtractFileUtil.extractRar(filePath, null);
        Assertions.assertEquals(2, extractedMap.size());
    }

    @Test
    void testExtractRarAndSaveFiles() throws IOException {
        String filePath = "C:\\Users\\xinyi\\Desktop\\temp\\test\\test.rar";
        String property = System.getProperty("user.dir");
        Path targetDir = Paths.get(property, "extract");
        Files.createDirectories(targetDir);
        List<String> filenames = ExtractFileUtil.extractRarAndSaveFiles(filePath, null, targetDir.toString());
        Assertions.assertEquals(2, filenames.size());
        System.out.println(filenames);
    }

    @Test
    void testExtractZipAndSaveFiles() throws IOException {
        String filePath = "C:\\Users\\xinyi\\Desktop\\temp\\test\\1.zip";
        String property = System.getProperty("user.dir");
        Path targetDir = Paths.get(property, "extract");
        Files.createDirectories(targetDir);
        List<String> filenames = ExtractFileUtil.extractZipAndSaveFiles(filePath, targetDir.toString());
        Assertions.assertEquals(2, filenames.size());
        System.out.println(filenames);
    }
}
