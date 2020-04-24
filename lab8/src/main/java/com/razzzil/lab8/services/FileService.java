package com.razzzil.lab8.services;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    @SneakyThrows
    public void addFilesToZip(OutputStream outputStream, File...filesToZip)  {
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        for (File file : filesToZip) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(zipEntry);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buf)) > 0) {
                zipOutputStream.write(buf, 0, bytesRead);
            }
            zipOutputStream.closeEntry();
        }
        zipOutputStream.close();
    }

}
