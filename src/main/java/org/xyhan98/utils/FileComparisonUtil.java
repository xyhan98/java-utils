package org.xyhan98.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileComparisonUtil {

    /**
     * Generates the MD5 hash of a file
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getMD5(String filePath) throws IOException {
        try (InputStream fis = new FileInputStream(filePath)) {
            return DigestUtils.md5Hex(fis);
        }
    }

    /**
     * Compares two files to determine if they are the same based on their MD5 hash.
     * @param filePath1
     * @param filePath2
     * @return
     * @throws IOException
     */
    public static boolean compareFilesByMD5(String filePath1, String filePath2) throws IOException {
        String md5File1 = getMD5(filePath1);
        String md5File2 = getMD5(filePath2);
        return md5File1.equals(md5File2);
    }
}
