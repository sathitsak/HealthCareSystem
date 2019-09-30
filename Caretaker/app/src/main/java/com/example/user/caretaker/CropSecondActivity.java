package com.example.user.caretaker;

/**
 * Created by S on 6/4/2559.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

import com.soundcloud.android.crop.Crop;

import java.io.File;

public class CropSecondActivity extends ActionBarActivity {
    @SuppressWarnings("deprecation")
    private String userid = "userid.jpeg";
    // LogCat tag
    String id_c;
    String id_p;

    private Button button;
    private Uri fileUri;
    private Uri fileUriCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_came);

        Intent i=getIntent();
        id_c=i.getStringExtra("caretaker_id");
        id_p=i.getStringExtra("patient_id");
        Intent extras = getIntent();
        Uri myUri = Uri.parse(extras.getStringExtra("imageUri"));
        fileUri = Uri.fromFile(new File(getCacheDir(), userid));
        fileUriCopy = fileUri;
        Crop.of(myUri, fileUri).asSquare().start(CropSecondActivity.this);



    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Intent i = new Intent(CropSecondActivity.this, UploadActivity.class);
            i.putExtra("filePath", fileUriCopy.getPath());
            i.putExtra("patient_id",id_p);
            i.putExtra("caretaker_id",id_c);

            i.putExtra("isImage", true);
            startActivity(i);
        }
    }


}
