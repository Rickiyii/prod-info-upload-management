package org.flowwork.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

/**
 * @author Ricky Zhang
 * @date 2023/2/18 11:25
 */
public class PasswordHashing {

    // 定义一个加密算法，这里使用SHA-256算法，可以根据实际需要选择其他算法
    private static final String ALGORITHM = "SHA-256";

    // 定义加密使用的salt值
    // 加盐的值
    private static final byte[] SALT = {
            (byte) 0x1B, (byte) 0x4F, (byte) 0x32, (byte) 0x2E,
            (byte) 0x6A, (byte) 0x0F, (byte) 0xA8, (byte) 0xCF
    };

    public static void main(String[] args) {
        String password = "zzy202302";
        byte[] saltedPassword = concatenateByteArrays(password.getBytes());
        byte[] hashedPassword = hashPassword(saltedPassword);
        System.out.println( DatatypeConverter.printHexBinary(hashedPassword));
    }

    // 将两个byte数组进行拼接
    private static byte[] concatenateByteArrays(byte[] a) {
        byte[] result = new byte[a.length + PasswordHashing.SALT.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(PasswordHashing.SALT, 0, result, a.length, PasswordHashing.SALT.length);
        return result;
    }

    // 使用SHA-256算法对密码进行hash
    private static byte[] hashPassword(byte[] password) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(password);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 验证用户输入的密码是否正确
    public static boolean validate(String password, String storedPasswordHash) {
        byte[] saltedPassword = concatenateByteArrays(password.getBytes());
        byte[] hashedPassword = hashPassword(saltedPassword);
        return Arrays.equals(hashedPassword, DatatypeConverter.parseHexBinary(storedPasswordHash));
    }
}
