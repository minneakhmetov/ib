package com.razzzil.lab8.services;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;

@Service
public class KeysGenerator {

    @Autowired
    private FileService fileService;

    private static final int RSA_KEY_SIZE = 2048;

    @SneakyThrows
    public SecretKey generateDesKey(){
        return KeyGenerator.getInstance("DES").generateKey();
    }

    @SneakyThrows
    public void generateDesKey(OutputStream outputStream){
        outputStream.write(this.generateDesKey().getEncoded());
    }

    @SneakyThrows
    public SecretKey readDesKey(InputStream inputStream){
        byte[] bytes = IOUtils.toByteArray(inputStream);
        return new SecretKeySpec(bytes, 0, bytes.length, "DES");
    }

    @SneakyThrows
    public void generateRSAKey(OutputStream outputStream){
        writeObject(outputStream, this.generateRSAKey());
    }

    @SneakyThrows
    public KeyPair generateRSAKey(){
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(RSA_KEY_SIZE, new SecureRandom());
        return generator.generateKeyPair();
    }

    @SneakyThrows
    public KeyPair readRSAKey(InputStream inputStream){
        ObjectInputStream oin = new ObjectInputStream(inputStream);
        KeyPair key = (KeyPair) oin.readObject();
        oin.close();
        return key;
    }

    @SneakyThrows
    public void getSeparatelyRsaZip(OutputStream outputStream){
        KeyPair keyPair = this.generateRSAKey();
        File privateKey = File.createTempFile("private", ".txt");
        File publicKey = File.createTempFile("public", ".txt");
        writeObject(new FileOutputStream(privateKey), keyPair.getPrivate());
        writeObject(new FileOutputStream(publicKey), keyPair.getPublic());
        fileService.addFilesToZip(outputStream, privateKey, publicKey);
        privateKey.delete();
        publicKey.delete();
    }

    @SneakyThrows
    public PrivateKey readPrivateKey(InputStream inputStream){
        ObjectInputStream oin = new ObjectInputStream(inputStream);
        PrivateKey key = (PrivateKey) oin.readObject();
        oin.close();
        return key;
    }

    @SneakyThrows
    public PublicKey readPublicKey(InputStream inputStream){
        ObjectInputStream oin = new ObjectInputStream(inputStream);
        PublicKey key = (PublicKey) oin.readObject();
        oin.close();
        return key;
    }

    @SneakyThrows
    public void writeObject(OutputStream outputStream, Object object){
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
    }


}
