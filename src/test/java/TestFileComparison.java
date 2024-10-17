import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xyhan98.utils.FileComparisonUtil;

import java.io.IOException;

public class TestFileComparison {

    @Test
    void testFileEquals() throws IOException {
        boolean b = FileComparisonUtil.compareFilesByMD5("C:\\Users\\xinyi\\Desktop\\temp\\test\\txt\\1.txt",
                "C:\\Users\\xinyi\\Desktop\\temp\\test\\txt\\2.txt");
        Assertions.assertTrue(b);
    }
}
