package com.example.user.caretaker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;


public class CropMainActivity extends ActionBarActivity {

    @SuppressWarnings("deprecation")
    private String userid = "userid.jpeg";
    // LogCat tag
    public Uri destinationcopy ;
    private ImageView resultView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_activity_main);
        resultView = (ImageView) findViewById(R.id.result_image);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toUploadMain = new Intent(CropMainActivity.this,UploadMainActivity.class);

                startActivity(toUploadMain);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_select) {
            resultView.setImageDrawable(null);
            Crop.pickImage(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), userid));
        destinationcopy = destination;
        Crop.of(source, destination).asSquare().start(this);

    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            resultView.setImageURI(Crop.getOutput(result));
            Intent i = new Intent(CropMainActivity.this, UploadActivity.class);
            i.putExtra("filePath", destinationcopy.getPath());
            i.putExtra("isImage", true);
            startActivity(i);
            //String stringUri = destinationcopy.toString();
            //Toast.makeText(this, stringUri, Toast.LENGTH_SHORT).show();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}