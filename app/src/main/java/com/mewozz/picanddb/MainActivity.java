package com.mewozz.picanddb;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int PICK_IMAGE = 7;
    private EditText etName;
    private Button btnAdd;
    private ImageButton imgBtnAddImg;

    private EditText etSearchByName;
    private Button btnSearch;

    private People people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        people=new People();


        etName = (EditText) findViewById(R.id.etName);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        imgBtnAddImg = (ImageButton) findViewById(R.id.img_btn_pic);

        etSearchByName = (EditText) findViewById(R.id.etSearchName);
        btnSearch = (Button) findViewById(R.id.btnSearch);


        imgBtnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPick = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                goPick.setType("image/*");
                startActivityForResult(goPick, PICK_IMAGE);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Provide a name please", Toast.LENGTH_SHORT).show();
                    return;
                }
                people.setName(etName.getText().toString());
                PeopleDao.savePeople(people);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSearchByName.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Provide a name please", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent goResult=new Intent(MainActivity.this,SearchResultActivity.class);
                goResult.putExtra("name",etSearchByName.getText().toString());
                startActivity(goResult);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Log.e(TAG, "onActivityResult: pick image error" );
                return;
            }
            Uri imgUri = data.getData();
            ContentResolver resolver=getContentResolver();
            try {
                InputStream is=resolver.openInputStream(imgUri);
                people.setBmp(BitmapFactory.decodeStream(is));
                imgBtnAddImg.setImageBitmap(people.getBmp());

            } catch (FileNotFoundException e) {
                Log.e(TAG, "onActivityResult: ",e );
            }
        }
    }
}
