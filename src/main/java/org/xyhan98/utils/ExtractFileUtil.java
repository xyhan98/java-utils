package org.xyhan98.utils;

import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ExtractFileUtil {

    private static final int BUFFER_SIZE = 1024;

    /**
     * Extracts files from archive with the RAR extension
     * @param filePath
     * @param password
     * @return map of extracted file with filename
     * @throws IOException
     */
    public static Map<InputStream, String> extractRar(String filePath, String password) throws IOException {
        Map<InputStream, String> extractedMap = new HashMap<>();

        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
        RandomAccessFileInStream randomAccessFileStream = new RandomAccessFileInStream(randomAccessFile);
        IInArchive inArchive = SevenZip.openInArchive(null, randomAccessFileStream);

        for (ISimpleInArchiveItem item : inArchive.getSimpleInterface().getArchiveItems()) {
            if (!item.isFolder()) {
                ExtractOperationResult result = item.extractSlow(data -> {
                    extractedMap.put(new BufferedInputStream(new ByteArrayInputStream(data)), item.getPath());

                    return data.length;
                }, password);

                if (result != ExtractOperationResult.OK) {
                    throw new RuntimeException(
                            String.format("Error extracting archive. Extracting error: %s", result));
                }
            }
        }

        return extractedMap;
    }

    /**
     * Extracts files from archive with the RAR extension and save files to the target directory
     * @param filePath
     * @param password
     * @param targetDir
     * @throws IOException
     */
    public static List<String> extractRarAndSaveFiles(String filePath, String password, String targetDir) throws IOException {
        List<String> filenames = new ArrayList<>();

        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
        RandomAccessFileInStream randomAccessFileStream = new RandomAccessFileInStream(randomAccessFile);
        IInArchive inArchive = SevenZip.openInArchive(null, randomAccessFileStream);

        for (ISimpleInArchiveItem item : inArchive.getSimpleInterface().getArchiveItems()) {
            if (!item.isFolder()) {
                ExtractOperationResult result = item.extractSlow(data -> {
                    File outputFile = new File(targetDir, item.getPath());
                    outputFile.getParentFile().mkdirs();

                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        fos.write(data);
                    } catch (IOException e) {
                        throw new RuntimeException("Error writing file: " + outputFile.getPath(), e);
                    }

                    filenames.add(outputFile.getPath());

                    return data.length;
                }, password);

                if (result != ExtractOperationResult.OK) {
                    throw new RuntimeException(
                            String.format("Error extracting archive. Extracting error: %s", result));
                }
            }
        }

        return filenames;
    }

    public static List<String> extractZipAndSaveFiles(String filePath, String targetDir) throws IOException {
        List<String> filenames = new ArrayList<>();

        Path targetPath = Paths.get(targetDir);
        Files.createDirectories(targetPath);

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(filePath))) {

            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path extractedPath = targetPath.resolve(entry.getName());

                if (entry.isDirectory()) {
                    Files.createDirectories(extractedPath);
                } else {
                    if (Files.notExists(extractedPath.getParent())) {
                        Files.createDirectories(extractedPath.getParent());
                    }

                    try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(extractedPath))) {
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int bytesRead;
                        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                            bos.write(buffer, 0, bytesRead);
                        }
                    }
                    filenames.add(extractedPath.toString());
                }

                zipInputStream.closeEntry();
            }
        }

        return filenames;
    }
}
