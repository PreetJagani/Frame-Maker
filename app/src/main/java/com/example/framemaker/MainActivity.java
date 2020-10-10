package com.example.framemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    ImageView mainImage, photo;
    int PICK, backgroundColour, textColour, PICK1, flag = 0;
    int backPress;
    Uri imageUri;

    CheckBox whatsappCheckbox, facebookCheckbox, instagramCheckbox;

    Spinner spinner;


    Bitmap bitmap;
    LinearLayout l1, l2;

    TextView textViewText, textViewTextWhatsapp, textViewTextFacebook, textViewTextInstagram;
    EditText messageEditText, whatsappEdittext, facebookEdittext, instagramEdittext;

    Button backgroundBtn, textColourBtn, ok, shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainImage = findViewById(R.id.mainImage);
        textViewText = findViewById(R.id.textboxText);
        messageEditText = findViewById(R.id.messageEditText);

        whatsappEdittext = findViewById(R.id.messageEditTextWhatsapp);
        textViewTextWhatsapp = findViewById(R.id.textboxTextWhatsapp);
        whatsappCheckbox = findViewById(R.id.whatsappCheckbox);


        facebookEdittext = findViewById(R.id.messageEditTextFacebook);
        textViewTextFacebook = findViewById(R.id.textboxTextFacebook);
        facebookCheckbox = findViewById(R.id.facebookCheckbox);


        instagramEdittext = findViewById(R.id.messageEditTextInstagram);
        textViewTextInstagram = findViewById(R.id.textboxTextInstagram);
        instagramCheckbox = findViewById(R.id.instagramCheckbox);

        photo = findViewById(R.id.photo);

        spinner = findViewById(R.id.spinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        backPress = 0;


        whatsappCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    whatsappEdittext.setEnabled(true);
                    textViewTextWhatsapp.setVisibility(View.VISIBLE);
                    textViewTextWhatsapp.setTextColor(textColour);
                    textViewTextWhatsapp.setBackgroundColor(backgroundColour);
                } else {
                    whatsappEdittext.setEnabled(false);
                    textViewTextWhatsapp.setVisibility(View.GONE);
                }

            }
        });

        whatsappEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textViewTextWhatsapp.setText(whatsappEdittext.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        facebookCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    facebookEdittext.setEnabled(true);
                    textViewTextFacebook.setVisibility(View.VISIBLE);
                    textViewTextFacebook.setBackgroundColor(backgroundColour);
                    textViewTextFacebook.setTextColor(textColour);

                } else {
                    facebookEdittext.setEnabled(false);
                    textViewTextFacebook.setVisibility(View.GONE);
                }

            }
        });

        facebookEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textViewTextFacebook.setText(facebookEdittext.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        instagramCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    instagramEdittext.setEnabled(true);
                    textViewTextInstagram.setVisibility(View.VISIBLE);
                    textViewTextInstagram.setBackgroundColor(backgroundColour);
                    textViewTextInstagram.setTextColor(textColour);

                } else {
                    instagramEdittext.setEnabled(false);
                    textViewTextInstagram.setVisibility(View.GONE);
                }

            }
        });

        instagramEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textViewTextInstagram.setText(instagramEdittext.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ok = findViewById(R.id.ok);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);

        backgroundBtn = findViewById(R.id.backgroundColourBtn);
        textColourBtn = findViewById(R.id.textColourBtn);
        shareBtn = findViewById(R.id.ShareBtn);

        backgroundColour = ContextCompat.getColor(MainActivity.this, R.color.Black);
        textColour = ContextCompat.getColor(MainActivity.this, R.color.White);


        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                textViewText.setText(messageEditText.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {

                    bitmap = Bitmap.createBitmap(l1.getWidth(), l1.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    l1.draw(canvas);

                    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file1 = new File(file, "Frame Maker");

                    file1.mkdir();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");

                    String fileName = file1.getAbsolutePath() + "/" + "Img" + simpleDateFormat.format(new Date()) + ".jpg";

                    File newFile = new File(fileName);

                    try {
                        newFile.createNewFile();
                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    try {

                        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        Toast.makeText(MainActivity.this, "Image saved", Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                            } else {

                                                bitmap = Bitmap.createBitmap(l1.getWidth(), l1.getHeight(), Bitmap.Config.ARGB_8888);
                                                Canvas canvas = new Canvas(bitmap);
                                                l1.draw(canvas);


                                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                                shareIntent.setType("image/jpeg");

                                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                                                File file1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/Frame Maker");
                                                file1.mkdir();

                                                File file = new File(file1, "/sendImage.jpg");


                                                try {
                                                    file.createNewFile();
                                                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                                                    fileOutputStream.write(byteArrayOutputStream.toByteArray());

                                                } catch (IOException e) {
                                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                                }

                                                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory() + "/DCIM/Frame Maker/sendImage.jpg"));
                                                startActivity(Intent.createChooser(shareIntent, "Share Image"));

                                            }
                                        }
                                    }
        );

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Typeface typeface;

                switch (i) {
                    case 0:
                        typeface = ResourcesCompat.getFont(MainActivity.this, R.font.pt_serif);
                        textViewText.setTypeface(typeface);
                        break;


                    case 1:
                        typeface = ResourcesCompat.getFont(MainActivity.this, R.font.abeezee);
                        textViewText.setTypeface(typeface);
                        break;

                    case 2:
                        typeface = ResourcesCompat.getFont(MainActivity.this, R.font.alex_brush);
                        textViewText.setTypeface(typeface);
                        break;


                    case 3:
                        typeface = ResourcesCompat.getFont(MainActivity.this, R.font.headland_one);
                        textViewText.setTypeface(typeface);
                        break;


                    case 4:
                        typeface = ResourcesCompat.getFont(MainActivity.this, R.font.new_rocker);
                        textViewText.setTypeface(typeface);
                        break;


                    case 5:
                        typeface = ResourcesCompat.getFont(MainActivity.this, R.font.nova_square);
                        textViewText.setTypeface(typeface);
                        break;


                    case 6:
                        typeface = ResourcesCompat.getFont(MainActivity.this, R.font.simonetta);
                        textViewText.setTypeface(typeface);
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK && resultCode == RESULT_OK && data != null && data.getData() != null && flag == 0) {

            imageUri = data.getData();


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1);
            mainImage.setLayoutParams(layoutParams);


            // mainImage.setImageURI(data.getData());

            try {

                Picasso.with(this).load(imageUri).into(mainImage);


                Picasso.with(this).load(imageUri).resize(mainImage.getWidth(), 0).onlyScaleDown().into(mainImage);


            } catch (Exception e) {
                Toast.makeText(this, "Can't load image in this resolution", Toast.LENGTH_SHORT).show();

            }

        }

        if (requestCode == PICK1 && resultCode == RESULT_OK && data != null && data.getData() != null && flag == 1) {

            imageUri = data.getData();

            Picasso.with(this).load(imageUri).into(photo);

        }


    }

    public void chooseImage(View view) {

        flag = 0;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK);
    }

    public void chooseBackgroud(View view) {


        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, backgroundColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                backgroundColour = color;
                textViewText.setBackgroundColor(backgroundColour);
                backgroundBtn.setBackgroundColor(backgroundColour);
                l2.setBackgroundColor(backgroundColour);

                if (whatsappCheckbox.isChecked()) {
                    textViewTextWhatsapp.setBackgroundColor(backgroundColour);
                }

                if (facebookCheckbox.isChecked()) {
                    textViewTextFacebook.setBackgroundColor(backgroundColour);
                }

                if (instagramCheckbox.isChecked()) {
                    textViewTextInstagram.setBackgroundColor(backgroundColour);
                }

            }
        });

        ambilWarnaDialog.show();
    }

    public void chooseTextColour(View view) {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, textColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                textColour = color;
                textViewText.setTextColor(textColour);
                textColourBtn.setBackgroundColor(textColour);


                if (whatsappCheckbox.isChecked()) {
                    textViewTextWhatsapp.setTextColor(textColour);
                }

                if (facebookCheckbox.isChecked()) {
                    textViewTextFacebook.setTextColor(textColour);
                }

                if (instagramCheckbox.isChecked()) {
                    textViewTextInstagram.setTextColor(textColour);
                }
            }
        });

        ambilWarnaDialog.show();

    }

    public void onBackPressed() {


        if (backPress == 1) {
            finishAffinity();
        } else {

            Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();
            backPress = 1;

            CountDownTimer t1 = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {

                    backPress = 0;
                }
            };
            t1.start();
        }

    }

    public void centerSelect(View view) {
        textViewText.setTextAlignment(view.TEXT_ALIGNMENT_CENTER);
    }

    public void leftSelect(View view) {
        textViewText.setTextAlignment(view.TEXT_ALIGNMENT_TEXT_START);
    }

    public void rightSelect(View view) {
        textViewText.setTextAlignment(view.TEXT_ALIGNMENT_VIEW_END);
    }

    public void selectTextBox(View view) {
        messageEditText.requestFocus();

    }

    public void choosePhoto(View view) {
        flag = 1;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK1);
    }

}




