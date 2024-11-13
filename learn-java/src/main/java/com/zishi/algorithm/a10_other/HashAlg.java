package com.zishi.algorithm.a10_other;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Map;

/**
 * @author zishi
 */
public class HashAlg {

    


    public static boolean validHmacMd5(String saltKey, String origin, String dest) {
        String algorithm = "HmacMD5";
        try {
            return validHmac(saltKey, algorithm, origin, dest);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(algorithm + "加密字符串失败...");
        }
    }

    public static boolean validHmacSHA256(String saltKey, String origin, String dest) {
        String algorithm = "HmacSha256";
        try {
            return validHmac(saltKey, algorithm, origin, dest);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(algorithm + "加密字符串失败...");
        }
    }

    public static boolean validHmacSHA512(String saltKey, String origin, String dest) {
        String algorithm = "HmacSha512";
        try {
            return validHmac(saltKey, algorithm, origin, dest);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(algorithm + "加密字符串失败...");
        }
    }


    private static boolean validHmac(String saltKey, String algorithm, String origin, String dest) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] saltKeyArr = HexFormat.of().parseHex(saltKey);
        SecretKey key = new SecretKeySpec(saltKeyArr, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        mac.update(origin.getBytes(StandardCharsets.UTF_8));
        byte[] result = mac.doFinal();
        return HexFormat.of().formatHex(result).equals(dest);
    }

    public static Map<String, String> hmacMd5(String origin) {
        String algorithm = "HmacMD5";
        try {
            return hmac(algorithm, origin);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(algorithm + "加密字符串失败...");
        }
    }

    public static Map<String, String> hmacSha256(String origin) {
        String algorithm = "HmacSha256";
        try {
            return hmac(algorithm, origin);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(algorithm + "加密字符串失败...");
        }
    }


    public static Map<String, String> hmacSha512(String origin) {
        String algorithm = "HmacSha512";
        try {
            return hmac(algorithm, origin);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(algorithm + "加密字符串失败...");
        }
    }

    /**
     * Hmac算法就是一种基于密钥的消息认证码算法，它的全称是Hash-based Message Authentication Code，是一种更安全的消息摘要算法。
     * Hmac算法总是和某种哈希算法配合起来用的。例如，我们使用MD5算法，对应的就是HmacMD5算法，它相当于“加盐”的MD5：
     * <p>
     * 因此，HmacMD5可以看作带有一个安全的key的MD5。使用HmacMD5而不是用MD5加salt，有如下好处：
     * <p>
     * HmacMD5使用的key长度是64字节，更安全；
     * Hmac是标准算法，同样适用于SHA-1等其他哈希算法；
     * Hmac输出和原有的哈希算法长度一致。
     *
     * @param algorithm 加密算法
     * @param origin    源字符串
     * @return 加密字符串
     */
    private static Map<String, String> hmac(String algorithm, String origin) throws NoSuchAlgorithmException, InvalidKeyException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        SecretKey key = keyGen.generateKey();
        // 打印随机生成的key:
        byte[] saltKey = key.getEncoded();
        //System.out.println("saltKey:" + HexFormat.of().formatHex(saltKey));
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        mac.update(origin.getBytes(StandardCharsets.UTF_8));
        byte[] result = mac.doFinal();
        return Map.of("hmac", HexFormat.of().formatHex(result), "saltKey", HexFormat.of().formatHex(saltKey));
    }


    public static String md5(String origin) {
        try {
            return hash("MD5", origin);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 加密字符串失败...");
        }
    }

    public static String sha256(String origin) {
        try {
            return hash("SHA256", origin);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA256 加密字符串失败...");
        }
    }

    public static String sha512(String origin) {
        try {
            return hash("SHA512", origin);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA512 加密字符串失败...");
        }
    }


    private static String hash(String algorithm, String origin) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(origin.getBytes(StandardCharsets.UTF_8));
        byte[] result = md.digest();
        return HexFormat.of().formatHex(result);
    }


    public static void main(String[] args) throws Exception {

        // cannot access class sun.security.jca.Providers (in module java.base) because module java.base does not export
        //Provider[] array = Providers.getFullProviderList().toArray();


//        String origin = "hello";
//        System.out.println(md5(origin));
//        System.out.println(sha256(origin));
//        System.out.println(sha512(origin));

        //String origin2 = "HelloWorld";
        //Map<String, String> stringStringMap = hmacMd5(origin2);
        //Map<String, String> stringStringMap = hmacSha256(origin2);
        //Map<String, String> stringStringMap = hmacSha512(origin2);
        //String saltKey = stringStringMap.get("saltKey");
        //String dest = stringStringMap.get("hmac");
        //System.out.println(hmacSha256(origin2));
        //System.out.println(hmacSha512(origin2));


        //System.out.println(validHmacSHA512(saltKey, origin2, dest));

        
        // ============测试对称加密
        String message = "Hello, world!";
        System.out.println("Message: " + message);
        // 128位密钥 = 16 bytes Key:
        byte[] key = "1234567890abcdef".getBytes(StandardCharsets.UTF_8);
        // 加密:
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = encrypt(key, data);
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));
        // 解密:
        byte[] decrypted = decrypt(key, encrypted);
        System.out.println("Decrypted: " + new String(decrypted, StandardCharsets.UTF_8));
    }


    //--------------------对称加密算法和解密 hutool工具包可以使用
    // 加密:
    public static byte[] encrypt(byte[] key, byte[] input) throws GeneralSecurityException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    // 解密:
    public static byte[] decrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

}
