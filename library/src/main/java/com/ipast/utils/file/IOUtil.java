package com.ipast.utils.file;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class IOUtil {
    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            close(closeable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(Closeable[] paramArrayOfCloseable)
            throws IOException {
        if (paramArrayOfCloseable != null) {
            int i = paramArrayOfCloseable.length;
            for (int j = 0; j < i; j++) {
                Closeable localCloseable = paramArrayOfCloseable[j];
                if (localCloseable == null)
                    continue;
                localCloseable.close();
            }
        }
    }

    public static void closeQuietly(Closeable[] paramArrayOfCloseable) {
        try {
            close(paramArrayOfCloseable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(InputStream paramInputStream)
            throws IOException {
        return getString(paramInputStream, null);
    }

    public static String getString(InputStream paramInputStream, String paramString)
            throws IOException {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrayOfByte = new byte[10240];
        while (true) {
            int i = paramInputStream.read(arrayOfByte);
            if (i == -1)
                break;
            localByteArrayOutputStream.write(arrayOfByte, 0, i);
        }
        if (paramString == null)
            return localByteArrayOutputStream.toString();
        return localByteArrayOutputStream.toString(paramString);
    }

}
