package com.slzh.service.sso.util;


import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.ECFieldElement.Fp;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import org.bouncycastle.util.encoders.Hex;

/**
 * SM2加密解密工具
 */
public class SM2Util {

    /**
     * 内部加密用的publicKey
     */
    public static final String INTERNAL_PUBLIC_KEY =
            "0441DFFDB189CCA52174D35CA396C6BB9481F55E8CDDE69494EE42A1D0EE92CA7FD73C4FCF37DCE320E15197C344EAE623BE0979A854DDFE443531DA79587914FD";

    /**
     * 外部解密用的privateKey
     */
    public static final String EXTERNAL_PRIVATE_KEY = "00F40A8483E24AA2E1684061238E11490DAD4B2BE72C0BB0FA55C31EB16B620416";

    /**
     * 外部加密用的publicKey
     */
    public static final String EXTERNAL_PUBLIC_KEY =
            "049A7C8B4E1423C47DE6B190F283FB98B6C37BD2C65C68D1A43D63349D4ABB6A08BD9744483ECB6D554AD5DB086B384E8D0F571472F7E6080D07C67C689DAEF368";

    /**
     * 内部解密用的privateKey
     */
    public static final String INTERNAL_PRIVATE_KEY = "0081CAD32441C629E742FC3A0FC905A497B629F2598B5B42FA75644F88D4F97369";


    //生成随机秘钥对
    public static void generateKeyPair(){
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();

        System.out.println("公钥: " + Util.byteToHex(publicKey.getEncoded(false)));
        System.out.println("私钥: " + Util.byteToHex(privateKey.toByteArray()));
    }

    //数据加密
    public static String encrypt(byte[] publicKey, byte[] data) throws IOException
    {
        if (publicKey == null || publicKey.length == 0)
        {
            return null;
        }

        if (data == null || data.length == 0)
        {
            return null;
        }

        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);

        Cipher cipher = new Cipher();
        SM2 sm2 = SM2.Instance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);

        ECPoint c1 = cipher.Init_enc(sm2, userKey);
        cipher.Encrypt(source);
        byte[] c3 = new byte[32];
        cipher.Dofinal(c3);

//      System.out.println("C1 " + Util.byteToHex(c1.getEncoded()));
//      System.out.println("C2 " + Util.byteToHex(source));
//      System.out.println("C3 " + Util.byteToHex(c3));
        //C1 C2 C3拼装成加密字串
        return Util.byteToHex(c1.getEncoded(false)) + Util.byteToHex(source) + Util.byteToHex(c3);

    }

    //数据解密
    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException
    {
        if (privateKey == null || privateKey.length == 0)
        {
            return null;
        }

        if (encryptedData == null || encryptedData.length == 0)
        {
            return null;
        }
        //加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
        String data = Util.byteToHex(encryptedData);
        /***分解加密字串
         * （C1 = C1标志位2位 + C1实体部分128位 = 130）
         * （C3 = C3实体部分64位  = 64）
         * （C2 = encryptedData.length * 2 - C1长度  - C2长度）
         */
        byte[] c1Bytes = Util.hexToByte(data.substring(0,130));
        int c2Len = encryptedData.length - 97;
        byte[] c2 = Util.hexToByte(data.substring(130,130 + 2 * c2Len));
        byte[] c3 = Util.hexToByte(data.substring(130 + 2 * c2Len,194 + 2 * c2Len));

        SM2 sm2 = SM2.Instance();
        BigInteger userD = new BigInteger(1, privateKey);

        //通过C1实体字节来生成ECPoint
        ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
        Cipher cipher = new Cipher();
        cipher.Init_dec(userD, c1);
        cipher.Decrypt(c2);
        cipher.Dofinal(c3);

        //返回解密结果
        return c2;
    }

    /*public static void main(String[] args) throws Exception
    {
        //生成密钥对
        generateKeyPair();

        String plainText = "ererfeiis[{}];,god";
        byte[] sourceData = plainText.getBytes();

        //下面的秘钥可以使用generateKeyPair()生成的秘钥内容
        // 国密规范正式私钥
        String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
        // 国密规范正式公钥
        String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";

        System.out.println("加密: ");
        String cipherText = encrypt(Util.hexToByte(pubk), sourceData);
        System.out.println(cipherText);
        System.out.println("解密: ");
        plainText = new String(decrypt(Util.hexToByte(prik), Util.hexToByte(cipherText)));
        System.out.println(plainText);
    }*/

//    public static void main(String[] args) throws IOException {
//        String key2Decode = "[\n" +
//                "    {\n" +
//                "        \"numId\":\"3435343434343\",\n" +
//                "        \"licenseCode\": \"bulDFxPZ6v8Gx6ncj5CbyFTMOy+/FwxLkQ7p1oKoj7fk1RXTEnOevCR0i0LThQg3AYgAmSs1Ekd0K5Z7thp86en8AlFXtboNpIn3hHujlPkGZreMsk9jVglLXYP4HWXIKA20R5Kxq/+upA7Pl04+lCUIzlsTqsZMUmv7zuaxmck=\",\n" +
//                "        \"userName\":\"admin\",\n" +
//                "        \"terminalId\":\"13434343\",\n" +
//                "        \"operateType\": \"0\",\n" +
//                "        \"operateTime\":\"2020-09-214:58:12\",\n" +
//                "        \"operateResult\":\"1\",\n" +
//                "        \"errorCode\":\"\",\n" +
//                "        \"operateName\":\"系统管理-新增用户\",\n" +
//                "        \"operateCondition\":\"age=12,name=add\",\n" +
//                "        \"collectType\":\"9\",\n" +
//                "        \"sendId\":\"111111\"\n" +
//                "    },{\n" +
//                "        \"numId\":\"343534565343\",\n" +
//                "        \"licenseCode\": \"bulDFxPZ6v8Gx6ncj5CbyFTMOy+/FwxLkQ7p1oKoj7fk1RXTEnOevCR0i0LThQg3AYgAmSs1Ekd0K5Z7thp86en8AlFXtboNpIn3hHujlPkGZreMsk9jVglLXYP4HWXIKA20R5Kxq/+upA7Pl04+lCUIzlsTqsZMUmv7zuaxmck=\",\n" +
//                "        \"userName\":\"admin\",\n" +
//                "        \"terminalId\":\"13445343\",\n" +
//                "        \"operateType\": \"6\",\n" +
//                "        \"operateTime\":\"2020-09-21 14:55:12\",\n" +
//                "        \"operateResult\":\"0\",\n" +
//                "        \"errorCode\":\"400\",\n" +
//                "        \"operateName\":\"系统管理-删除用户\",\n" +
//                "        \"operateCondition\":\"userId=1\",\n" +
//                "        \"collectType\":\"9\",\n" +
//                "        \"sendId\":\"222222\"\n" +
//                "    }\n" +
//                "]";
//        System.out.println("origPlainText:" + key2Decode);
//        String cipherText = encrypt(Util.hexToByte(EXTERNAL_PUBLIC_KEY), key2Decode.getBytes());
//        System.out.println("encrypt：" + cipherText);
//        String plainText = new String(decrypt(Util.hexToByte(INTERNAL_PRIVATE_KEY), Util.hexToByte(cipherText)));
//        System.out.println("plainText:" + plainText);
//    }
    public static void main(String[] args) throws IOException {
        /*String licenseCode = "bulDFxPZ6v8Gx6ncj5CbyFTMOy+/FwxLkQ7p1oKoj7fk1RXTEnOevCR0i0LThQg3AYgAmSs1Ekd0K5Z7thp86en8AlFXtboNpIn3hHujlPkGZreMsk9jVglLXYP4HWXIKA20R5Kxq/+upA7Pl04+lCUIzlsTqsZMUmv7zuaxmck=";
        String external = encrypt(Util.hexToByte(EXTERNAL_PUBLIC_KEY), licenseCode.getBytes());
        System.out.println(external);
        String internal = new String(decrypt(Util.hexToByte(INTERNAL_PRIVATE_KEY), Util.hexToByte(external)), "utf-8");
        System.out.println(internal);*/
        /*String plainText = "admin";
        String encodeText = encrypt(Util.hexToByte("0425D0443DAB720C5ADF2B8E03081DE41B06EDE8F0CFD9C3D394E290B30BA1630DA99084693CCFC21B2EBE8A76508A2EA6432C473DFF3A77BFBA82C48F3CB926A9"), plainText.getBytes());
        System.out.println("encodeText:" + encodeText);
        plainText = new String(decrypt(Util.hexToByte("00A39269D9853E01BC9787F5C4E77617F5391B74CC7D9E5BFA61A555D24C449E08"), Util.hexToByte(encodeText)), "utf-8");
        System.out.println("plainText:" + plainText);*/
        String encodeText = "0468801474477D9C5E2370F3BC664B4EDB84394225172B773520FBCB81DBE1D14CCDB2E445826C446B1983BDC4E82E4A79760B1D906E37FE9042DD04E96072D65D86DE983133A633040EA0121037F9A651B993947A6D9723116DC4B947DBBC4CA8DBA121ED56";
        String plainText = new String(decrypt(Util.hexToByte("00A39269D9853E01BC9787F5C4E77617F5391B74CC7D9E5BFA61A555D24C449E08"), Util.hexToByte(encodeText)), "utf-8");
        System.out.println("plainText:" + plainText);

    }

}
class Cipher {
    private int ct;
    private ECPoint p2;
    private SM3Digest sm3keybase;
    private SM3Digest sm3c3;
    private byte key[];
    private byte keyOff;

    public Cipher()
    {
        this.ct = 1;
        this.key = new byte[32];
        this.keyOff = 0;
    }

    private void Reset()
    {
        this.sm3keybase = new SM3Digest();
        this.sm3c3 = new SM3Digest();

        byte p[] = Util.byteConvert32Bytes(p2.getX().toBigInteger());
        this.sm3keybase.update(p, 0, p.length);
        this.sm3c3.update(p, 0, p.length);

        p = Util.byteConvert32Bytes(p2.getY().toBigInteger());
        this.sm3keybase.update(p, 0, p.length);
        this.ct = 1;
        NextKey();
    }

    private void NextKey()
    {
        SM3Digest sm3keycur = new SM3Digest(this.sm3keybase);
        sm3keycur.update((byte) (ct >> 24 & 0xff));
        sm3keycur.update((byte) (ct >> 16 & 0xff));
        sm3keycur.update((byte) (ct >> 8 & 0xff));
        sm3keycur.update((byte) (ct & 0xff));
        sm3keycur.doFinal(key, 0);
        this.keyOff = 0;
        this.ct++;
    }

    public ECPoint Init_enc(SM2 sm2, ECPoint userKey)
    {
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger k = ecpriv.getD();
        ECPoint c1 = ecpub.getQ();
        this.p2 = userKey.multiply(k);
        Reset();
        return c1;
    }

    public void Encrypt(byte data[])
    {
        this.sm3c3.update(data, 0, data.length);
        for (int i = 0; i < data.length; i++)
        {
            if (keyOff == key.length)
            {
                NextKey();
            }
            data[i] ^= key[keyOff++];
        }
    }

    public void Init_dec(BigInteger userD, ECPoint c1)
    {
        this.p2 = c1.multiply(userD);
        Reset();
    }

    public void Decrypt(byte data[])
    {
        for (int i = 0; i < data.length; i++)
        {
            if (keyOff == key.length)
            {
                NextKey();
            }
            data[i] ^= key[keyOff++];
        }

        this.sm3c3.update(data, 0, data.length);
    }

    public void Dofinal(byte c3[])
    {
        byte p[] = Util.byteConvert32Bytes(p2.getY().toBigInteger());
        this.sm3c3.update(p, 0, p.length);
        this.sm3c3.doFinal(c3, 0);
        Reset();
    }
}

class SM3 {
    public static final byte[] iv = { 0x73, (byte) 0x80, 0x16, 0x6f, 0x49,
            0x14, (byte) 0xb2, (byte) 0xb9, 0x17, 0x24, 0x42, (byte) 0xd7,
            (byte) 0xda, (byte) 0x8a, 0x06, 0x00, (byte) 0xa9, 0x6f, 0x30,
            (byte) 0xbc, (byte) 0x16, 0x31, 0x38, (byte) 0xaa, (byte) 0xe3,
            (byte) 0x8d, (byte) 0xee, 0x4d, (byte) 0xb0, (byte) 0xfb, 0x0e,
            0x4e };

    public static int[] Tj = new int[64];

    static
    {
        for (int i = 0; i < 16; i++)
        {
            Tj[i] = 0x79cc4519;
        }

        for (int i = 16; i < 64; i++)
        {
            Tj[i] = 0x7a879d8a;
        }
    }

    public static byte[] CF(byte[] V, byte[] B)
    {
        int[] v, b;
        v = convert(V);
        b = convert(B);
        return convert(CF(v, b));
    }

    private static int[] convert(byte[] arr)
    {
        int[] out = new int[arr.length / 4];
        byte[] tmp = new byte[4];
        for (int i = 0; i < arr.length; i += 4)
        {
            System.arraycopy(arr, i, tmp, 0, 4);
            out[i / 4] = bigEndianByteToInt(tmp);
        }
        return out;
    }

    private static byte[] convert(int[] arr)
    {
        byte[] out = new byte[arr.length * 4];
        byte[] tmp = null;
        for (int i = 0; i < arr.length; i++)
        {
            tmp = bigEndianIntToByte(arr[i]);
            System.arraycopy(tmp, 0, out, i * 4, 4);
        }
        return out;
    }

    public static int[] CF(int[] V, int[] B)
    {
        int a, b, c, d, e, f, g, h;
        int ss1, ss2, tt1, tt2;
        a = V[0];
        b = V[1];
        c = V[2];
        d = V[3];
        e = V[4];
        f = V[5];
        g = V[6];
        h = V[7];

        int[][] arr = expand(B);
        int[] w = arr[0];
        int[] w1 = arr[1];

        for (int j = 0; j < 64; j++)
        {
            ss1 = (bitCycleLeft(a, 12) + e + bitCycleLeft(Tj[j], j));
            ss1 = bitCycleLeft(ss1, 7);
            ss2 = ss1 ^ bitCycleLeft(a, 12);
            tt1 = FFj(a, b, c, j) + d + ss2 + w1[j];
            tt2 = GGj(e, f, g, j) + h + ss1 + w[j];
            d = c;
            c = bitCycleLeft(b, 9);
            b = a;
            a = tt1;
            h = g;
            g = bitCycleLeft(f, 19);
            f = e;
            e = P0(tt2);

		            /*System.out.print(j+" ");
		            System.out.print(Integer.toHexString(a)+" ");
		            System.out.print(Integer.toHexString(b)+" ");
		            System.out.print(Integer.toHexString(c)+" ");
		            System.out.print(Integer.toHexString(d)+" ");
		            System.out.print(Integer.toHexString(e)+" ");
		            System.out.print(Integer.toHexString(f)+" ");
		            System.out.print(Integer.toHexString(g)+" ");
		            System.out.print(Integer.toHexString(h)+" ");
		            System.out.println("");*/
        }
//		      System.out.println("");

        int[] out = new int[8];
        out[0] = a ^ V[0];
        out[1] = b ^ V[1];
        out[2] = c ^ V[2];
        out[3] = d ^ V[3];
        out[4] = e ^ V[4];
        out[5] = f ^ V[5];
        out[6] = g ^ V[6];
        out[7] = h ^ V[7];

        return out;
    }

    private static int[][] expand(int[] B)
    {
        int W[] = new int[68];
        int W1[] = new int[64];
        for (int i = 0; i < B.length; i++)
        {
            W[i] = B[i];
        }

        for (int i = 16; i < 68; i++)
        {
            W[i] = P1(W[i - 16] ^ W[i - 9] ^ bitCycleLeft(W[i - 3], 15))
                    ^ bitCycleLeft(W[i - 13], 7) ^ W[i - 6];
        }

        for (int i = 0; i < 64; i++)
        {
            W1[i] = W[i] ^ W[i + 4];
        }

        int arr[][] = new int[][] { W, W1 };
        return arr;
    }

    private static byte[] bigEndianIntToByte(int num)
    {
        return back(Util.intToBytes(num));
    }

    private static int bigEndianByteToInt(byte[] bytes)
    {
        return Util.byteToInt(back(bytes));
    }

    private static int FFj(int X, int Y, int Z, int j)
    {
        if (j >= 0 && j <= 15)
        {
            return FF1j(X, Y, Z);
        }
        else
        {
            return FF2j(X, Y, Z);
        }
    }

    private static int GGj(int X, int Y, int Z, int j)
    {
        if (j >= 0 && j <= 15)
        {
            return GG1j(X, Y, Z);
        }
        else
        {
            return GG2j(X, Y, Z);
        }
    }

    // 逻辑位运算函数
    private static int FF1j(int X, int Y, int Z)
    {
        int tmp = X ^ Y ^ Z;
        return tmp;
    }

    private static int FF2j(int X, int Y, int Z)
    {
        int tmp = ((X & Y) | (X & Z) | (Y & Z));
        return tmp;
    }

    private static int GG1j(int X, int Y, int Z)
    {
        int tmp = X ^ Y ^ Z;
        return tmp;
    }

    private static int GG2j(int X, int Y, int Z)
    {
        int tmp = (X & Y) | (~X & Z);
        return tmp;
    }

    private static int P0(int X)
    {
        int y = rotateLeft(X, 9);
        y = bitCycleLeft(X, 9);
        int z = rotateLeft(X, 17);
        z = bitCycleLeft(X, 17);
        int t = X ^ y ^ z;
        return t;
    }

    private static int P1(int X)
    {
        int t = X ^ bitCycleLeft(X, 15) ^ bitCycleLeft(X, 23);
        return t;
    }

    /**
     * 对最后一个分组字节数据padding
     *
     * @param in
     * @param bLen
     *            分组个数
     * @return
     */
    public static byte[] padding(byte[] in, int bLen)
    {
        int k = 448 - (8 * in.length + 1) % 512;
        if (k < 0)
        {
            k = 960 - (8 * in.length + 1) % 512;
        }
        k += 1;
        byte[] padd = new byte[k / 8];
        padd[0] = (byte) 0x80;
        long n = in.length * 8 + bLen * 512;
        byte[] out = new byte[in.length + k / 8 + 64 / 8];
        int pos = 0;
        System.arraycopy(in, 0, out, 0, in.length);
        pos += in.length;
        System.arraycopy(padd, 0, out, pos, padd.length);
        pos += padd.length;
        byte[] tmp = back(Util.longToBytes(n));
        System.arraycopy(tmp, 0, out, pos, tmp.length);
        return out;
    }

    /**
     * 字节数组逆序
     *
     * @param in
     * @return
     */
    private static byte[] back(byte[] in)
    {
        byte[] out = new byte[in.length];
        for (int i = 0; i < out.length; i++)
        {
            out[i] = in[out.length - i - 1];
        }

        return out;
    }

    public static int rotateLeft(int x, int n)
    {
        return (x << n) | (x >> (32 - n));
    }

    private static int bitCycleLeft(int n, int bitLen)
    {
        bitLen %= 32;
        byte[] tmp = bigEndianIntToByte(n);
        int byteLen = bitLen / 8;
        int len = bitLen % 8;
        if (byteLen > 0)
        {
            tmp = byteCycleLeft(tmp, byteLen);
        }

        if (len > 0)
        {
            tmp = bitSmall8CycleLeft(tmp, len);
        }

        return bigEndianByteToInt(tmp);
    }

    private static byte[] bitSmall8CycleLeft(byte[] in, int len)
    {
        byte[] tmp = new byte[in.length];
        int t1, t2, t3;
        for (int i = 0; i < tmp.length; i++)
        {
            t1 = (byte) ((in[i] & 0x000000ff) << len);
            t2 = (byte) ((in[(i + 1) % tmp.length] & 0x000000ff) >> (8 - len));
            t3 = (byte) (t1 | t2);
            tmp[i] = (byte) t3;
        }

        return tmp;
    }

    private static byte[] byteCycleLeft(byte[] in, int byteLen)
    {
        byte[] tmp = new byte[in.length];
        System.arraycopy(in, byteLen, tmp, 0, in.length - byteLen);
        System.arraycopy(in, 0, tmp, in.length - byteLen, byteLen);
        return tmp;
    }
}

class SM3Digest {
    /** SM3值的长度 */
    private static final int BYTE_LENGTH = 32;

    /** SM3分组长度 */
    private static final int BLOCK_LENGTH = 64;

    /** 缓冲区长度 */
    private static final int BUFFER_LENGTH = BLOCK_LENGTH * 1;

    /** 缓冲区 */
    private byte[] xBuf = new byte[BUFFER_LENGTH];

    /** 缓冲区偏移量 */
    private int xBufOff;

    /** 初始向量 */
    private byte[] V = SM3.iv.clone();

    private int cntBlock = 0;

    public SM3Digest() {
    }

    public SM3Digest(SM3Digest t)
    {
        System.arraycopy(t.xBuf, 0, this.xBuf, 0, t.xBuf.length);
        this.xBufOff = t.xBufOff;
        System.arraycopy(t.V, 0, this.V, 0, t.V.length);
    }

    /**
     * SM3结果输出
     *
     * @param out 保存SM3结构的缓冲区
     * @param outOff 缓冲区偏移量
     * @return
     */
    public int doFinal(byte[] out, int outOff)
    {
        byte[] tmp = doFinal();
        System.arraycopy(tmp, 0, out, 0, tmp.length);
        return BYTE_LENGTH;
    }

    public void reset()
    {
        xBufOff = 0;
        cntBlock = 0;
        V = SM3.iv.clone();
    }

    /**
     * 明文输入
     *
     * @param in
     *            明文输入缓冲区
     * @param inOff
     *            缓冲区偏移量
     * @param len
     *            明文长度
     */
    public void update(byte[] in, int inOff, int len)
    {
        int partLen = BUFFER_LENGTH - xBufOff;
        int inputLen = len;
        int dPos = inOff;
        if (partLen < inputLen)
        {
            System.arraycopy(in, dPos, xBuf, xBufOff, partLen);
            inputLen -= partLen;
            dPos += partLen;
            doUpdate();
            while (inputLen > BUFFER_LENGTH)
            {
                System.arraycopy(in, dPos, xBuf, 0, BUFFER_LENGTH);
                inputLen -= BUFFER_LENGTH;
                dPos += BUFFER_LENGTH;
                doUpdate();
            }
        }

        System.arraycopy(in, dPos, xBuf, xBufOff, inputLen);
        xBufOff += inputLen;
    }

    private void doUpdate()
    {
        byte[] B = new byte[BLOCK_LENGTH];
        for (int i = 0; i < BUFFER_LENGTH; i += BLOCK_LENGTH)
        {
            System.arraycopy(xBuf, i, B, 0, B.length);
            doHash(B);
        }
        xBufOff = 0;
    }

    private void doHash(byte[] B)
    {
        byte[] tmp = SM3.CF(V, B);
        System.arraycopy(tmp, 0, V, 0, V.length);
        cntBlock++;
    }

    private byte[] doFinal()
    {
        byte[] B = new byte[BLOCK_LENGTH];
        byte[] buffer = new byte[xBufOff];
        System.arraycopy(xBuf, 0, buffer, 0, buffer.length);
        byte[] tmp = SM3.padding(buffer, cntBlock);
        for (int i = 0; i < tmp.length; i += BLOCK_LENGTH)
        {
            System.arraycopy(tmp, i, B, 0, B.length);
            doHash(B);
        }
        return V;
    }

    public void update(byte in)
    {
        byte[] buffer = new byte[] { in };
        update(buffer, 0, 1);
    }

    public int getDigestSize()
    {
        return BYTE_LENGTH;
    }

    public static void main(String[] args)
    {
        byte[] md = new byte[32];
        byte[] msg1 = "ererfeiisgod".getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(msg1, 0, msg1.length);
        sm3.doFinal(md, 0);
        String s = new String(Hex.encode(md));
        System.out.println(s.toUpperCase());
    }
}

class SM2 {
    //测试参数
//  public static final String[] ecc_param = {
//      "8542D69E4C044F18E8B92435BF6FF7DE457283915C45517D722EDB8B08F1DFC3",
//      "787968B4FA32C3FD2417842E73BBFEFF2F3C848B6831D7E0EC65228B3937E498",
//      "63E4C6D3B23B0C849CF84241484BFE48F61D59A5B16BA06E6E12D1DA27C5249A",
//      "8542D69E4C044F18E8B92435BF6FF7DD297720630485628D5AE74EE7C32E79B7",
//      "421DEBD61B62EAB6746434EBC3CC315E32220B3BADD50BDC4C4E6C147FEDD43D",
//      "0680512BCBB42C07D47349D2153B70C4E5D7FDFCBFA36EA1A85841B9E46E09A2"
//  };

    //正式参数
    public static String[] ecc_param = {
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",
            "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",
            "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",
            "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"
    };

    public static SM2 Instance()
    {
        return new SM2();
    }

    public final BigInteger ecc_p;
    public final BigInteger ecc_a;
    public final BigInteger ecc_b;
    public final BigInteger ecc_n;
    public final BigInteger ecc_gx;
    public final BigInteger ecc_gy;
    public final ECCurve ecc_curve;
    public final ECPoint ecc_point_g;
    public final ECDomainParameters ecc_bc_spec;
    public final ECKeyPairGenerator ecc_key_pair_generator;
    public final ECFieldElement ecc_gx_fieldelement;
    public final ECFieldElement ecc_gy_fieldelement;

    public SM2()
    {
        this.ecc_p = new BigInteger(ecc_param[0], 16);
        this.ecc_a = new BigInteger(ecc_param[1], 16);
        this.ecc_b = new BigInteger(ecc_param[2], 16);
        this.ecc_n = new BigInteger(ecc_param[3], 16);
        this.ecc_gx = new BigInteger(ecc_param[4], 16);
        this.ecc_gy = new BigInteger(ecc_param[5], 16);

        this.ecc_gx_fieldelement = new Fp(this.ecc_p, this.ecc_gx);
        this.ecc_gy_fieldelement = new Fp(this.ecc_p, this.ecc_gy);

        this.ecc_curve = new ECCurve.Fp(this.ecc_p, this.ecc_a, this.ecc_b);
        this.ecc_point_g = new ECPoint.Fp(this.ecc_curve, this.ecc_gx_fieldelement, this.ecc_gy_fieldelement);

        this.ecc_bc_spec = new ECDomainParameters(this.ecc_curve, this.ecc_point_g, this.ecc_n);

        ECKeyGenerationParameters ecc_ecgenparam;
        ecc_ecgenparam = new ECKeyGenerationParameters(this.ecc_bc_spec, new SecureRandom());

        this.ecc_key_pair_generator = new ECKeyPairGenerator();
        this.ecc_key_pair_generator.init(ecc_ecgenparam);
    }
}