package com.diary.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    private final String customFilePath;
    public ImageService(@Value("${custom.file.path.background}") String customFilePath) {
        this.customFilePath = customFilePath;
    }

    public boolean saveImage(byte[] fileBytes, String fileName) throws IOException {
        String filePath = customFilePath + fileName;
        try(FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileBytes);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
