package com.bellatrix.aditi.documentorganizer.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Aditi on 28-03-2019.
 */

public class CommonFunctions {
    public static byte[] uriToBitmap(Context c, Uri uri, String TAG) {
        if (uri != null) {
            try {
                Bitmap photo = (Bitmap) MediaStore.Images.Media.getBitmap(c.getContentResolver(), uri);
                return upload(photo);

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(c, "Image picking failed", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(c, "Image picker gave us a null image.", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private static byte[] upload(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }
}
