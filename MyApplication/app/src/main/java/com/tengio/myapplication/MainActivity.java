package com.tengio.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.googlecode.tesseract.android.TessBaseAPI;

import static android.R.attr.bitmap;
import static android.R.attr.mimeType;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.tengio.myapplication.MESSAGE";
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
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Intent intent = new Intent(this, FullscreenActivity.class);
            intent.putExtra("com.tengio.app.MESSAGE", imageBitmap.toString());

            startActivity(intent);

            ImageView mImageView = (ImageView) findViewById(R.id.imageView);

            mImageView.setImageBitmap(imageBitmap);
            mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }
}
