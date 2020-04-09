package com.razzzil.lab6.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class MainController {

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/encrypt")
    public String encrypt() {
        return "encrypt";
    }

    @GetMapping("/decrypt")
    public String decrypt() {
        return "decrypt";
    }

    @PostMapping("/encrypt")
    public void encrypt(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        String text = new String(file.getBytes(), StandardCharsets.UTF_8);
        char[] achText = text.toCharArray();
        char[] achKey = new char[achText.length];
        char[] achResult = new char[achText.length];

        Random random = new Random();
        for (int i = 0; i < achText.length; i++) {
            achKey[i] = (char) random.nextInt(Character.MAX_VALUE);
            achResult[i] = (char) (achText[i] ^ achKey[i]);
        }
        File cipher = File.createTempFile("cipher", ".txt");
        File key = File.createTempFile("key", ".txt");
        PrintWriter cipherWriter = new PrintWriter(cipher);
        cipherWriter.print(achResult);
        cipherWriter.close();
        PrintWriter keyWriter = new PrintWriter(key);
        keyWriter.print(achKey);
        keyWriter.close();
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"enryptedWithKey.zip\"");
        this.addFilesToZip(response.getOutputStream(), cipher, key);
        cipher.delete();
        key.delete();
    }



    @PostMapping("/decrypt")
    public void decrypt(@RequestParam("file") MultipartFile file, @RequestParam("key") MultipartFile key, HttpServletResponse response) throws IOException {
        String text = new String(file.getBytes(), StandardCharsets.UTF_8);
        String keyText = new String(key.getBytes(), StandardCharsets.UTF_8);
        char[] achText = text.toCharArray();
        char[] achKey = keyText.toCharArray();
        char[] achDecrypt = new char[achText.length];

        for (int i = 0; i < achText.length; i++) {
            achDecrypt[i] = (char) (achText[i] ^ achKey[i]);
        }
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"decryped.txt\"");
        PrintWriter cipherWriter = new PrintWriter(response.getOutputStream());
        cipherWriter.print(achDecrypt);
        cipherWriter.close();
    }

    private void addFilesToZip(ServletOutputStream outputStream, File...filesToZip) throws IOException {
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
