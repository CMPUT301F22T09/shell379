package com.cmput301f22t09.shell379.data.util;

import com.google.common.primitives.Bytes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {
    //serialize/deserialize
    //https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
    public static List<Byte> serialize(Object obj) {
        byte[] bytes = {};
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            bytes = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Bytes.asList(bytes);
    }

    public static Object deserialize(List<Byte> bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(Bytes.toArray(bytes));
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
