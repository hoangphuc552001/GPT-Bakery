package com.test.gpt_bakery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.gpt_bakery.databasehelpers.ProductDatabaseHelper;
import com.test.gpt_bakery.models.Cookie;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class AdminActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_GALLERY = 123;
    private EditText etName, etPrice, etDescription;
    private Button btnSave;
    private ImageView image;
    private static ProductDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initViews();

        db = new ProductDatabaseHelper(this);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AdminActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                if(!etName.getText().toString().equals("") && image.getDrawable() != null)  {
                    builder.setMessage("Are you sure about that?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
                                    Bitmap bitmap = drawable.getBitmap();
                                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                                    byte[] image = byteArray.toByteArray();

                                    if (db.add(new Cookie(-1, etName.getText().toString(), Double.valueOf(etPrice.getText().toString()), etDescription.getText().toString(), image)))    {
                                        Toast.makeText(AdminActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AdminActivity.this, ChefFoodPanel_BottomNavigation.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }else   {
                    builder.setMessage("Please enter Cookie's Name and select an Image from your gallery!")
                            .setPositiveButton("Ok", null)
                            .show();
                }
            }
        });
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);
        image = findViewById(R.id.image);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)    {

            case REQUEST_CODE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)    {
                    openGallery();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    ActivityResultLauncher<Intent> setImageFromGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    try {
                        image.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData())));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private void openGallery()   {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        setImageFromGallery.launch(intent);
    }
}