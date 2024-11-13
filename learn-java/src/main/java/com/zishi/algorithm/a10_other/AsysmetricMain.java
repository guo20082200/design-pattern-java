package com.zishi.algorithm.a10_other;


import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

/**
 * @author zishi
 */
public class AsysmetricMain {
    public static void main(String[] args) throws Exception {
        // 明文:
        byte[] plain = "Hello, encrypt use RSA".getBytes(StandardCharsets.UTF_8);
        // 创建公钥／私钥对:
        Person alice = new Person("Alice");
        // 用Alice的公钥加密:
        byte[] pk = alice.getPublicKey();
        System.out.println("public key: " + HexFormat.of().formatHex(pk));
        byte[] encrypted = alice.encrypt(plain);
        System.out.println("encrypted: " + HexFormat.of().formatHex(encrypted));
        // 用Alice的私钥解密:
        byte[] sk = alice.getPrivateKey();
        System.out.println("private key: " + HexFormat.of().formatHex(sk));
        byte[] decrypted = alice.decrypt(encrypted);
        System.out.println(new String(decrypted, StandardCharsets.UTF_8));
    }
}
