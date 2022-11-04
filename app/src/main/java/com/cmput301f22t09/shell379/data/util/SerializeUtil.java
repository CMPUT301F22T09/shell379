package com.cmput301f22t09.shell379.data.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;


/**
 * Utility class that helps with serializing environment data to store in the database.
 */
public class SerializeUtil {

    //serialize/deserialize
    //https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
    //and
    //https://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string
    /**
     * Serializes an object to bytes for db pushing
     * @param obj the object to serialize
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String serialize(Object obj) {
        byte[] bytes = {};
        String serialized = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            bytes = baos.toByteArray();
            serialized = Base64.getEncoder().encodeToString(bytes);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serialized;
    }

    /**
     * Deserializes bytes from the Data base into a java object.
     * @param serialized is the byte version of the object
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Object deserialize(String serialized) {
        byte[] bytes = Base64.getDecoder().decode(serialized);
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

    /**
     * Serialization method specifically for images.
     * @param img image to serialize
     */
    //bitmap serialization/deserialization issue learned from
    //https://stackoverflow.com/questions/6002800/android-serializable-problem
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String serializeImg(Bitmap img) {
        Log.d("SER_UTIL", "SERIALIZING IMG");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Deserializes bytes from the Data base into an image.
     * @param serialized is the byte version of the image
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Bitmap deserializeImg(String serialized) {
        Log.d("SER_UTIL", "DESERIALIZING IMG");
        byte[] bytes = Base64.getDecoder().decode(serialized);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
