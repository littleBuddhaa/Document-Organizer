package com.bellatrix.aditi.documentorganizer.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Aditi on 28-03-2019.
 */

public class CommonFunctions {

    private static final String UNDERSCORE = "_";
    private static final String SPACE = " ";

    public static byte[] uriToBytes(Context c, Uri uri, String TAG, int quality) {
        Bitmap bitmap = uriToBitmap(c, uri, TAG);
        return bitmapToBytes(bitmap, quality);
    }

    public static String getTextFromUri(Context c, Uri uri, String TAG) {
        Bitmap bitmap = uriToBitmap(c, uri, TAG);
        return getTextFromBitmap(c, bitmap);
    }

    public static Bitmap uriToBitmap(Context c, Uri uri, String TAG) {
        if (uri != null) {
            try {
                Bitmap photo = (Bitmap) MediaStore.Images.Media.getBitmap(c.getContentResolver(), uri);
                return photo;

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

    public static byte[] bitmapToBytes(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }

    public static String getTextFromBitmap(Context c, Bitmap bitmap) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(c).build();
        Frame imageFrame = new Frame.Builder()
                .setBitmap(bitmap)
                .build();
        StringBuilder imageText = new StringBuilder();
        SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);
        TextBlock[] myTextBlocks = new TextBlock[textBlocks.size()];
        for (int i = 0; i < textBlocks.size(); i++)
        {
            myTextBlocks[i] = textBlocks.get(textBlocks.keyAt(i));
        }
        Arrays.sort(myTextBlocks, TextBlockComparator);
        for (TextBlock textBlock : myTextBlocks)
        {
            if (imageText.toString().equals(""))
            {
                imageText.append(textBlock.getValue());
            } else {
                imageText.append("\n");
                imageText.append(textBlock.getValue());
            }
        }
        return imageText.toString();
    }

    private static Comparator<TextBlock> TextBlockComparator
            = new Comparator<TextBlock>() {
        public int compare(TextBlock fruit1, TextBlock fruit2) {
            return fruit1.getBoundingBox().bottom - fruit2.getBoundingBox().bottom;
        }
    };

    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public static String toReadableString(String str) {
        return str.replace(UNDERSCORE, SPACE);
    }


}
