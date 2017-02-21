package com.tengio.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraManager;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.IOException;

import static android.R.attr.bitmap;
import static android.R.attr.mimeType;

public class MainActivity extends AppCompatActivity {
    public final static int REQUEST_IMAGE_CAPTURE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void takePhoto(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.w("Width", String.valueOf(imageBitmap.getWidth()));
            Log.w("Height", String.valueOf(imageBitmap.getHeight()));
            Log.w("Loc", getFilesDir().getAbsolutePath());

            ImageView mImageView = (ImageView) findViewById(R.id.imageView);

            Drawable bd = new BitmapDrawable(getResources(), imageBitmap);
            mImageView.setImageDrawable(bd);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            TessBaseAPI tessBaseAPI = new TessBaseAPI();

            tessBaseAPI.setImage(imageBitmap);
            tessBaseAPI.init(getFilesDir() + "/tessdata/", "eng");

            Toast.makeText(MainActivity.this, "Toast: " + tessBaseAPI.getUTF8Text(), Toast.LENGTH_LONG).show();
        }
    }
}
