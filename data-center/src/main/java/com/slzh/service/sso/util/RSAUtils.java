package com.slzh.service.sso.util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {

    /**
     * RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024
     */
    public static final int KEY_SIZE = 1024;

    /**
     * 默认私钥
     */
    public static final String DEFAULT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIeTqCIh0TFY3l+b3TAMxNETTDoIiiYToPKT+xftP43mEtTo1yhcLY6WS0iqNKESHFXYRS43KI3LAa/9xifPePPFq2xLfe5iuwAGR1XuXwsUT6yoVPsmye6FkBrroC5wnNcn/2vi1um8fLz7MhTQSuy75KRf8prsjrF4R94YKyEnAgMBAAECgYAvrF9R6TxLMum3CvLDQ6otrrB+M6SIcRyGUDzaFAXmGsICdgG6YndujTDZ02gAP4MROKRS9Phgq8LjJMR6GT15TvMqnXiIZzQHe2UjE7XDvShyTBaLnTuY6ajoQUax1Cg64dPSedmzrNO7ckru7ER2PqKOwGAJ1faAOyLyJ4FbUQJBANAzes2WkY34mNkZZ+v+uT60LrFgJbiyxfopuB2I7A3R5GoKAa90cXDvdOol1yDDDA75ZYFZ4i2eJ7gSZDlFah8CQQCms9t9UDgTSRPKNIMOcSqkzggVlbaVabScfLRL4uchLEsWwPehFMI6j+lGmUCduvjTH7ravoLYr5J/VQWDXff5AkEAnAV4CYjEGvgo0shPMHL6rxqnwxSiL/8Ijael6bwp6VFZMn29NZa1TESmALHKvSptZutOPdj1vem/KhneMWzrUQJAY55/v/c8S2oURXX0C6FdFnd5JUz+e3Nb11g+dkr2TECn7sUKNlytZUmy4dmbX6boI2KGy0ml78+cADPSw5pYIQJBALwgjhLGlhPwYyBnnJkxj1Wv7LqebppOze92RgLkO7Av3F9UKl+eoGO1roIzL+g7aIkekhJWd56fa2xtXlcLmC0=";

    /**
     * 生成公钥、私钥对(keysize=1024)
     */
    public static KeyPairInfo getKeyPair() {
        return getKeyPair(KEY_SIZE);
    }

    /**
     * 生成公钥、私钥对
     *
     * @param keySize
     * @return
     */
    public static KeyPairInfo getKeyPair(int keySize) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(keySize);
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥
            RSAPrivateKey oraprivateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey orapublicKey = (RSAPublicKey) keyPair.getPublic();

            KeyPairInfo pairInfo = new KeyPairInfo(keySize);
            //公钥
            byte[] publicKeybyte = orapublicKey.getEncoded();
            String publicKeyString = Base64.encodeBase64String(publicKeybyte);
            pairInfo.setPublicKey(publicKeyString);
            //私钥
            byte[] privateKeybyte = oraprivateKey.getEncoded();
            String privateKeyString = Base64.encodeBase64String(privateKeybyte);
            pairInfo.setPrivateKey(privateKeyString);
            return pairInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取公钥对象
     *
     * @param publicKeyBase64
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static PublicKey getPublicKey(String publicKeyBase64) throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicpkcs8KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyBase64));
        PublicKey publicKey = keyFactory.generatePublic(publicpkcs8KeySpec);
        return publicKey;
    }

    /**
     * 获取私钥对象
     *
     * @param privateKeyBase64
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String privateKeyBase64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privatekcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase64));
        PrivateKey privateKey = keyFactory.generatePrivate(privatekcs8KeySpec);
        return privateKey;
    }

    /**
     * 使用公钥加密
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @return 经过 base64 编码后的字符串
     */
    public static String encipherPublicKey(String content, String publicKeyBase64) {
        return encipherPublicKey(content, publicKeyBase64, KEY_SIZE / 8 - 11);
    }

    /**
     * 使用公钥加密（分段加密）
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @param segmentSize     分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密）
     * @return 经过 base64 编码后的字符串
     */
    public static String encipherPublicKey(String content, String publicKeyBase64, int segmentSize) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyBase64);
            return encipher(content, publicKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  使用私钥加密
     * @param content  待加密内容
     * @param publicKeyBase64
     * @return
     */
    public static String encipherPrivateKey(String content, String publicKeyBase64) {
        return encipherPrivateKey(content, publicKeyBase64, KEY_SIZE / 8 - 11);
    }

    /**
     * 使用私钥加密（分段加密）
     *
     * @param content         待加密内容
     * @param privateKeyBase64 私钥 base64 编码
     * @param segmentSize     分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密）
     * @return 经过 base64 编码后的字符串
     */
    public static String encipherPrivateKey(String content, String privateKeyBase64, int segmentSize) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyBase64);
            return encipher(content, privateKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段加密
     *
     * @param ciphertext  密文
     * @param key         加密秘钥
     * @param segmentSize 分段大小，<=0 不分段
     * @return
     */
    public static String encipher(String ciphertext, Key key, int segmentSize) {
        try {
            // 用公钥加密
            byte[] srcBytes = ciphertext.getBytes();
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] resultBytes = null;
            if (segmentSize > 0)
                resultBytes = cipherDoFinal(cipher, srcBytes, segmentSize); //分段加密
            else
                resultBytes = cipher.doFinal(srcBytes);
            String base64Str = Base64Utils.encodeToString(resultBytes);
            return base64Str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段大小
     *
     * @param cipher
     * @param srcBytes
     * @param segmentSize
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        if (segmentSize <= 0)
            throw new RuntimeException("分段大小必须大于0");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = srcBytes.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > segmentSize) {
                cache = cipher.doFinal(srcBytes, offSet, segmentSize);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * segmentSize;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }

    /**
     * 使用私钥解密
     *
     * @param contentBase64    待加密内容,base64 编码
     * @param privateKeyBase64 私钥 base64 编码
     * @return
     * @segmentSize 分段大小
     */
    public static String decipherPrivateKey(String contentBase64, String privateKeyBase64) {
        return decipherPrivateKey(contentBase64, privateKeyBase64, KEY_SIZE / 8);
    }

    /**
     * 使用私钥解密
     *
     * @param contentBase64    待加密内容,base64 编码
     * @param publicKeyBase64 私钥 base64 编码
     * @return
     * @segmentSize 分段大小
     */
    public static String decipherPublicKey(String contentBase64, String publicKeyBase64) {
        return decipherPublicKey(contentBase64, publicKeyBase64, KEY_SIZE / 8);
    }

    /**
     * 使用私钥解密（分段解密）
     *
     * @param contentBase64    待加密内容,base64 编码
     * @param privateKeyBase64 私钥 base64 编码
     * @return
     * @segmentSize 分段大小
     */
    public static String decipherPrivateKey(String contentBase64, String privateKeyBase64, int segmentSize) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyBase64);
            return decipher(contentBase64, privateKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用公钥解密（分段解密）
     *
     * @param contentBase64    待加密内容,base64 编码
     * @param privateKeyBase64 公钥 base64 编码
     * @return
     * @segmentSize 分段大小
     */
    public static String decipherPublicKey(String contentBase64, String privateKeyBase64, int segmentSize) {
        try {
            PublicKey publicKey = getPublicKey(privateKeyBase64);
            return decipher(contentBase64, publicKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段解密
     *
     * @param contentBase64 密文
     * @param key           解密秘钥
     * @param segmentSize   分段大小（小于等于0不分段）
     * @return
     */
    public static String decipher(String contentBase64, Key key, int segmentSize) {
        try {
            // 用私钥解密
            byte[] srcBytes = Base64Utils.decodeFromString(contentBase64);
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher deCipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            deCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decBytes = null;
            if (segmentSize > 0)
                decBytes = cipherDoFinal(deCipher, srcBytes, segmentSize); //分段加密
            else
                decBytes = deCipher.doFinal(srcBytes);

            String decrytStr = new String(decBytes);
            return decrytStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 秘钥对
     */
    public static class KeyPairInfo {
        String privateKey;
        String publicKey;
        int keySize = 0;

        public KeyPairInfo(int keySize) {
            setKeySize(keySize);
        }

        public KeyPairInfo(String publicKey, String privateKey) {
            setPrivateKey(privateKey);
            setPublicKey(publicKey);
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public int getKeySize() {
            return keySize;
        }

        public void setKeySize(int keySize) {
            this.keySize = keySize;
        }
    }

    public static void main(String[] args) {
        KeyPairInfo info = RSAUtils.getKeyPair();
        System.out.println("============================生成公钥和私钥==================================");
        System.out.println("私钥:\n"+info.privateKey);
        System.out.println("公钥:\n"+info.publicKey);

        System.out.println("===============================私钥解密====================================");
        String str = "{\"CPU序列号\":\"M7063FMQ\",\"硬盘序列号\":\"WD-WCC6Y5YUNKE8\",\"计算机名\":\"KM-PC\",\"计算机全名\":\"KM-PC\",\"DNS\":\"[]\",\"ipv4地址\":\"35.48.152.254\",\"ipv6地址\":\"\"}";
        System.out.println("加密前,内容为：" + str);
        String sss = RSAUtils.encipherPublicKey(str,info.getPublicKey());
        System.out.println("加密后,内容为：" + sss);

        System.out.println("===============================私钥解密====================================");
        String sa = RSAUtils.decipherPrivateKey("ZnhniHj4OtTYCzsUQ8vWzD4LLIjBsBZq+gcIdd53rY8x0VYBE/sD15M72sBiqAFkziSu+Faq/cSnFecDNmvP59tgURl0wR+7qbI4SRkea/SdqldFBvC/3oQK5QytYHi4dLRxhEhuPLVSP+hPpim4ouXOczxe++Q4fkvp68OYu6yGA4Jtxb5NI4xXp1OL97OeTD5IAWxpChTwbtf6NqijzPZWmblKkrBupEV4wPZYKou4OrmP6VDqN5ur4+hsmfhxIyRnCMwk5yk0+mdtJEt6eOWeNLs1gXyxwIpcY9CTtendK/BPxG3c4l8+du7CD9MCI+PMj9Fu0CjBlsqttyhubA==","MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALZcn+AcEM/mzTtNUryWfGuQ4NjsBYw2eZJ7p2sA91HHJcJ6rRrpsgFfMJZzm428qnCVwazd20UxOSXXlDYikosvckP55L0+yT+3mmoWgsPGc/enr0hm49AhZ5ngwjIuS0aJlVUwvL72X0j6WQ4fNEFby635vNLx2pt3OG4xr6AnAgMBAAECgYA8gdUmNgm6Abmc8JYALupPgn2oW/+nOzfxS4gMMADfqHU15yBiqqG+ZMgpU2iMicn/myDs65vihGeSz8nEgIoghpH5mmYQGefh/KWpILHuBmJZaBjqUC2HD7EEvKgS27SjaxkswyDMyrxNYxs/x/3AtRsTGbdF4RUNPpcdDdPrIQJBAOs83qxwhQ1b4ARJsNbIwZifjrLbKFVbeywZNT0/LQ7QKDLy3InII8sx28YthAGxE53fLrWzDabxQNaibk23Z1cCQQDGdQmchjDS2zovne60gyaEnVPRosfRfYKENy8QfrqIcQodpa/Z8uBf8S8Azz3uNvcJBnQqncKCkwIfmUPL/RuxAkEAqP/LDeGshQNrdkkmxbfm9eozj6VJXcFiRTU6/Wxkx46eQrQd4tKpfHKubLsIalq4OCQ+H4WqmjQam9iIHEdV8wJBAKLwlOOeiZeVx0AxOzuaMpXPVnJYZBbsJazIYiv44M4FgXi2AJvBfddH/n97qC1dk5YlZltXC8yJRsUK4U8qkQECQQCHCoGPjJVo6UgodyInJHjTXyb6jg7aMhyOajgjzJqRaEwGl6hwZxYtVXLLpS8uqZe5Sen80HpzsCdnPTkAvVFQ");
        System.out.println("解密后,内容为：" +sa);

        System.out.println("===============================私钥加密====================================");
        str = "{\"CPU序列号\":\"M7063FMQ\",\"硬盘序列号\":\"WD-WCC6Y5YUNKE8\",\"计算机名\":\"KM-PC\",\"计算机全名\":\"KM-PC\",\"DNS\":\"[]\",\"ipv4地址\":\"35.48.152.254\",\"ipv6地址\":\"\"}";
        System.out.println("加密前,内容为：" + str);
        String gy = RSAUtils.encipherPrivateKey(str,info.getPrivateKey());
        System.out.println("加密后,内容为：" + gy);
        System.out.println("===============================公钥解密====================================");
//        sa = RSAUtils.decipherPublicKey("ZnhniHj4OtTYCzsUQ8vWzD4LLIjBsBZq+gcIdd53rY8x0VYBE/sD15M72sBiqAFkziSu+Faq/cSnFecDNmvP59tgURl0wR+7qbI4SRkea/SdqldFBvC/3oQK5QytYHi4dLRxhEhuPLVSP+hPpim4ouXOczxe++Q4fkvp68OYu6yGA4Jtxb5NI4xXp1OL97OeTD5IAWxpChTwbtf6NqijzPZWmblKkrBupEV4wPZYKou4OrmP6VDqN5ur4+hsmfhxIyRnCMwk5yk0+mdtJEt6eOWeNLs1gXyxwIpcY9CTtendK/BPxG3c4l8+du7CD9MCI+PMj9Fu0CjBlsqttyhubA==","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2XJ/gHBDP5s07TVK8lnxrkODY7AWMNnmSe6drAPdRxyXCeq0a6bIBXzCWc5uNvKpwlcGs3dtFMTkl15Q2IpKLL3JD+eS9Psk/t5pqFoLDxnP3p69IZuPQIWeZ4MIyLktGiZVVMLy+9l9I+lkOHzRBW8ut+bzS8dqbdzhuMa+gJwIDAQAB");
        sa = RSAUtils.decipherPublicKey(gy,info.getPublicKey());
        System.out.println("解密后,内容为：" +sa);
    }
}