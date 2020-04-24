package com.razzzil.lab8.controllers;

import com.razzzil.lab8.services.EncrypterDecrypter;
import com.razzzil.lab8.services.KeysGenerator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private KeysGenerator generator;

    @Autowired
    private EncrypterDecrypter encrypterDecrypter;

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

    @GetMapping("/keys")
    public String keys() {
        return "/keys";
    }

    @PostMapping("/keys/des")
    @SneakyThrows
    public void getDesKey(HttpServletResponse response){
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"desKey.txt\"");
        generator.generateDesKey(response.getOutputStream());
    }

    @PostMapping("/keys/rsa")
    @SneakyThrows
    public void getRsaKey(HttpServletResponse response){
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rsaKeys.zip\"");
        generator.getSeparatelyRsaZip(response.getOutputStream());
    }

    @PostMapping("/encrypt/des")
    @SneakyThrows
    public void encryptDes(@RequestParam("file") MultipartFile file, @RequestParam("key") MultipartFile key, HttpServletResponse response) {
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"encryptedDes.txt\"");
        encrypterDecrypter.encryptDes(file.getInputStream(), response.getOutputStream(), generator.readDesKey(key.getInputStream()));
    }

    @PostMapping("/encrypt/rsa")
    @SneakyThrows
    public void encryptRsa(@RequestParam("file") MultipartFile file, @RequestParam("key") MultipartFile key, HttpServletResponse response) {
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"encryptedRsa.txt\"");
        encrypterDecrypter.encryptRsa(file.getInputStream(), response.getOutputStream(), generator.readPublicKey(key.getInputStream()));
    }

    @PostMapping("/encrypt/auto")
    @SneakyThrows
    public void encryptRsa(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"encrypted.zip\"");
        encrypterDecrypter.autoEncrypt(file.getInputStream(), response.getOutputStream());
    }

    @PostMapping("/decrypt/des")
    @SneakyThrows
    public void decryptDes(@RequestParam("file") MultipartFile file, @RequestParam("key") MultipartFile key, HttpServletResponse response) {
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"decryptedDes.txt\"");
        encrypterDecrypter.decryptDes(file.getInputStream(), response.getOutputStream(), generator.readDesKey(key.getInputStream()));
    }

    @PostMapping("/decrypt/rsa")
    @SneakyThrows
    public void decryptRsa(@RequestParam("file") MultipartFile file, @RequestParam("key") MultipartFile key, HttpServletResponse response) {
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"decryptedRsa.txt\"");
        encrypterDecrypter.decryptRsa(file.getInputStream(), response.getOutputStream(), generator.readPrivateKey(key.getInputStream()));
    }

    @PostMapping("/decrypt/auto")
    @SneakyThrows
    public void decryptRsa(@RequestParam("file") MultipartFile file, @RequestParam("encryptedKey") MultipartFile encryptedKey, @RequestParam("privateKey") MultipartFile privateKey, HttpServletResponse response) {
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"decrypted.txt\"");
        encrypterDecrypter.autoDecrypt(file.getInputStream(), encryptedKey.getInputStream(), privateKey.getInputStream(), response.getOutputStream());
    }

}
