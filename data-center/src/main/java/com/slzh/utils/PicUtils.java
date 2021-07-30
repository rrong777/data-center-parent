package com.slzh.utils;

import javassist.bytecode.ByteArray;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class PicUtils {


    /**
     * 根据网友进行的程序比对，用java8.base64这种方法做的base64解码和编码最快
     * 将byte 进行base64编码，返回String
     *
     * @param src
     * @return
     */
    public static String base64Encode(byte[] src) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(src);
    }

    /**
     * 将stringbyte 进行base64解码，返回byte
     *
     * @param src
     * @return
     */
    public static byte[] base64Decode(String src) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(src);
    }


    public static void base64ToImg(String src, String path, String format) {
        byte bytes[] = base64Decode(src);
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage bi1 = ImageIO.read(bais);
            File f1 = new File(path);
            ImageIO.write(bi1, format, f1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] urlToByte(String imgURL) {
        byte[] data = null;
        byte[] realData = new byte[0];
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = null;
            InputStream inStream = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                inStream = conn.getInputStream();
            } catch (Exception e) {
                Thread.sleep(5000);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                inStream = conn.getInputStream();
            }
            int coun = conn.getContentLength();
            data = new byte[102400];
            int readCoun = -1;
            int totalLenth = 0;
            int round = 0;
            //不知道总长度的情况下，把byte流读取出来
            while ((readCoun = inStream.read(data)) != -1 && round < 3000) {
                round++;
                totalLenth += readCoun;
                byte[] tempByte = new byte[totalLenth];
                System.arraycopy(realData, 0, tempByte, 0,
                        realData.length);
                System.arraycopy(data, 0, tempByte, realData.length, readCoun);
                realData = Arrays.copyOf(tempByte, tempByte.length);
                tempByte = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return realData;
    }


    public static String urlToBase64(String imgUrl) {
        String res = null;
        byte[] img = urlToByte(imgUrl);
        if (img != null) {
            res = base64Encode(img);
        }
        return res;
    }

    /**
     * 生成md5
     *
     * @param file 图片文件
     * @return MD5值
     * @throws FileNotFoundException
     */
    public static String getStringMd5(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
}
