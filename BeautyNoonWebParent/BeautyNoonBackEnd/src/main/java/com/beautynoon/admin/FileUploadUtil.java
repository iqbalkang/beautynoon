package com.beautynoon.admin;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileUploadUtil {

    public static void saveFile(MultipartFile file, String dirPath, String fileName) throws IOException {
        Path uploadPath = Paths.get(dirPath);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new IOException("Could not save file " + fileName, exception);
        }

    }

    public static void cleanDir(String dirName) {
        Path dirPath = Paths.get(dirName);

        try(Stream<Path> files = Files.list(dirPath)) {
            files.forEach(file -> {
                if(Files.isDirectory(file)) return;

                try {
                    Files.delete(file);
                } catch (IOException exception) {
                    System.out.println("Could not delete " + file.getFileName());
                }
            });
        } catch (IOException exception) {
            System.out.println("Could not list directory " + dirPath);
        }

    }
}
