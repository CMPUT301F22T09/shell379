package com.cmput301f22t09.shell379.data.util;

import com.google.common.primitives.Bytes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SerializeUtil {
    //serialize/deserialize
    //https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
    //and
    //https://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string
    public static String serialize(Object obj) {
        byte[] bytes = {};
        String serialized = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            bytes = baos.toByteArray();
            serialized = new String(bytes, StandardCharsets.UTF_8);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serialized;
    }

    public static Object deserialize(String serialized) {
        byte[] bytes = serialized.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Object obj = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
            bais.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
