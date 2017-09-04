package com.jordan.project.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 昕 on 2017/4/19.
 */

public class BluetoothDataUtils {
    /**
     * 从文件中读取二进制数据内容
     */
    public static String readFileToBinaryData(String filePath){
        return null;
    }
    /**
     * 二进制数据转换成为byte数组
     */
    public static byte[] binaryDataToBytes(String binaryData){
        return null;
    }
    /**
     * 从SDCard文件中读取byte数组
     */
    public static byte[] readSDCardFileToBinaryBytes(String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        byte[] binaryBytes = toByteArray(in);
        in.close();
        return binaryBytes;
    }
    /**
     * 从资源文件中读取byte数组
     */
    public static byte[] readAssetFileToBinaryBytes(Context context, String fileName) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream in= assetManager.open(fileName); //表示获取assets/image目录下的所有文件
        byte[] binaryBytes = toByteArray(in);
        in.close();
        return binaryBytes;
    }
    private static byte[] toByteArray(InputStream in) throws IOException {
 
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024 * 4];
    int n = 0;
    while ((n = in.read(buffer)) != -1) {
        out.write(buffer, 0, n);
    }
    return out.toByteArray();
    }
    /**
     * 读取byte数组字节对应内容
     */
    public static byte[] bytesDataToContent(byte[] bytesData){
        return null;
    }
    /**
     * byte数组字节转换十六进制
     * 十六进制转换成Float形式
     * 疑问：转换为int不行吗？
     */
    public static float bytesDataToFloat(byte[] bytesData){
        char[] chars = HexadecimalUtils.encodeHex(bytesData,false);
        int floats=HexadecimalUtils.toDigit(chars[0],0);
        return 0;
    }
}
