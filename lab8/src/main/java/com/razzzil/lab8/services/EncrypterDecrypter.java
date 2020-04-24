package com.razzzil.lab8.services;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.AlgorithmParameters;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

@Service
public class EncrypterDecrypter {

    @Autowired
    private KeysGenerator keysGenerator;

    @Autowired
    private FileService fileService;

    private static final byte[] iv = { 11, 22, 33, 44, 99, 88, 77, 66 };

    @SneakyThrows
    public void encryptDes(InputStream inputStream, OutputStream outputStream, SecretKey key){
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        outputStream = new CipherOutputStream(outputStream, cipher);
        writeData(inputStream, outputStream);
    }

    @SneakyThrows
    public void decryptDes(InputStream inputStream, OutputStream outputStream, SecretKey key){
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        outputStream = new CipherOutputStream(outputStream, cipher);
        writeData(inputStream, outputStream);
    }

    @SneakyThrows
    public void encryptRsa(InputStream inputStream, OutputStream outputStream, PublicKey key){
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = encryptCipher.doFinal(IOUtils.toByteArray(inputStream));
        writeData(new ByteArrayInputStream(Base64.getEncoder().encode(cipherText)), outputStream);
    }

    @SneakyThrows
    public void decryptRsa(InputStream inputStream, OutputStream outputStream, PrivateKey key){
        byte[] bytes = Base64.getDecoder().decode(IOUtils.toByteArray(inputStream));
        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, key);
        writeData(new ByteArrayInputStream(decriptCipher.doFinal(bytes)), outputStream);
    }

    @SneakyThrows
    public void autoEncrypt(InputStream inputStream, OutputStream outputStream){
        SecretKey secretKey = keysGenerator.generateDesKey();
        File encryptedText = File.createTempFile("encryptedFile", ".txt");
        File encryptedKey = File.createTempFile("encryptedKey", ".txt");
        File privateKey = File.createTempFile("privateKey", ".txt");
        encryptDes(inputStream, new FileOutputStream(encryptedText), secretKey);
        KeyPair keyPair = keysGenerator.generateRSAKey();
        encryptRsa(new ByteArrayInputStream(secretKey.getEncoded()), new FileOutputStream(encryptedKey), keyPair.getPublic());
        keysGenerator.writeObject(new FileOutputStream(privateKey), keyPair.getPrivate());
        fileService.addFilesToZip(outputStream, encryptedText, encryptedKey, privateKey);
        encryptedText.delete();
        encryptedKey.delete();
        privateKey.delete();
    }

    @SneakyThrows
    public void autoDecrypt(InputStream encryptedText, InputStream encryptedKey, InputStream privateKey, OutputStream outputStream){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        decryptRsa(encryptedKey, byteArrayOutputStream, keysGenerator.readPrivateKey(privateKey));
        decryptDes(encryptedText, outputStream, keysGenerator.readDesKey(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
        byteArrayOutputStream.close();
    }

    private static void writeData(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        int numRead = 0;
        // read and write operation
        while ((numRead = is.read(buf)) >= 0) {
            os.write(buf, 0, numRead);
        }
        os.close();
        is.close();
    }

}
