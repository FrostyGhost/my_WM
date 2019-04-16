package com.fg.frostyghost.my_wm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.WatermarkDetector;
import com.watermark.androidwm.bean.WatermarkText;
import com.watermark.androidwm.listener.BuildFinishListener;
import com.watermark.androidwm.listener.DetectFinishListener;
import com.watermark.androidwm.task.DetectionReturnValue;

import java.io.File;
import java.io.FileOutputStream;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity  {

    Button button;
    TextView txt_1, txt_2;
    float x, y, m_lastTouchY;
    boolean tileMod = true;
    double frontSize;
    ConstraintLayout constraintLayout;

    private int edT = 1;
    private ImageButton writeText, confirmET, addText, frontB, frontI, frontSize_btn, invisible_text_btn, detectWM_btn, save_text_btn;
    private EditText editText;
    private LinearLayout containerScrollText, container_n0, container_text;

  private FrameLayout mInsideBottomSheet;
    private ImageView mResultImg;
//    private BaseMedia mMedia;

    private int REQUEST_GET_SINGLE_FILE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        constraintLayout = findViewById(R.id.ConstraintLayout);
        txt_1 = findViewById(R.id.formatText);
        txt_2 = findViewById(R.id.textView4);

        SeekBar setFrontSize = findViewById(R.id.seekBar);

        writeText = findViewById(R.id.imageButton8);
        addText = findViewById(R.id.imageButton9);
        invisible_text_btn = findViewById(R.id.invisible_text_btn);
        detectWM_btn = findViewById(R.id.detectWM);
        save_text_btn = findViewById(R.id.save_text_btn);

        frontB = findViewById(R.id.front_B_btn);
        frontI = findViewById(R.id.front_I_btn);
        frontSize_btn = findViewById(R.id.front_Size_btn);
        confirmET = findViewById(R.id.confirmET);
        editText = findViewById(R.id.editText2);
        containerScrollText= findViewById(R.id.container_scroll_text);
        container_text = findViewById(R.id.container_text);

        //delete
        container_n0=findViewById(R.id.container0);
        container_n0.removeView(editText);
        container_n0.removeView(confirmET);
        container_text.removeView(txt_1);
        container_n0.removeView(setFrontSize);

        //txt_1 = new TextView(this);
        button = findViewById(R.id.button7);

        mResultImg = findViewById(R.id.imageView3);




        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addTextWM();
            }
        });
        invisible_text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInVisibleTextWM();
            }
        });
        detectWM_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetectText();
            }
        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = event.getX();
                y = event.getY();
                txt_2.setText("x " + x + " y " + y);

//                ConstraintSet set = new ConstraintSet();
//                set.clone(constraintLayout);
//
//                TransitionManager.beginDelayedTransition(constraintLayout);
//                set.applyTo(constraintLayout);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        m_lastTouchY = event.getY();
                        txt_2.setText("x " + x + " y " + y);
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        if (event.getY() > m_lastTouchY){
                            if (event.getY() > 110){
                                ConstraintSet set = new ConstraintSet();
                                set.clone(constraintLayout);
                                changeConstraints(set,1);
                                TransitionManager.beginDelayedTransition(constraintLayout);
                                set.applyTo(constraintLayout);
                            }
// if (event.getY() > 100) {
//                                ConstraintSet set = new ConstraintSet();
//                                set.clone(constraintLayout);
//                                changeConstraints(set,0);
//                                TransitionManager.beginDelayedTransition(constraintLayout);
//                                set.applyTo(constraintLayout);
//                            }

                        }else {
                            if (event.getY() < -200){
                                ConstraintSet set = new ConstraintSet();
                                set.clone(constraintLayout);
                                changeConstraints(set,2);
                                TransitionManager.beginDelayedTransition(constraintLayout);
                                set.applyTo(constraintLayout);
                            }if (event.getY() < - 400) {
                                ConstraintSet set = new ConstraintSet();
                                set.clone(constraintLayout);
                                changeConstraints(set,3);
                                TransitionManager.beginDelayedTransition(constraintLayout);
                                set.applyTo(constraintLayout);
                            }

                        }
                        txt_2.setText("x " + x + " y " + y);
                        break;

                    case MotionEvent.ACTION_UP: // отпускание
                    case MotionEvent.ACTION_CANCEL:
                        break;

                }
                txt_1.setText("x " + x + " y " + y);
                return true;
            }
        });

        confirmET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_1.setText(editText.getText().toString());
                container_n0.removeView(editText);
                container_n0.removeView(confirmET);
            }
        });

        writeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edT==0) {
                    container_n0.removeView(editText);
                    container_n0.removeView(confirmET);
                    container_text.removeView(txt_1);
                    edT = 1;
                }else {
                    container_n0.addView(editText);
                    container_text.addView(txt_1);
                    container_n0.addView(confirmET);
                    edT = 0;
                }
            }
        });

        //add photo
        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_GET_SINGLE_FILE);
            }
        });

        View.OnClickListener setFront = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edT == 0) {
                    switch (v.getId()) {
                        case R.id.front_B_btn:
                            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                                txt_1.setTypeface(boldTypeface);
                            break;
                        case R.id.front_I_btn:
                            Typeface italicTypeface = Typeface.defaultFromStyle(Typeface.ITALIC);
                                txt_1.setTypeface(italicTypeface);
                            break;
                        case R.id.front_Size_btn:
                            container_n0.addView(setFrontSize);
                            break;
                    }
                }
            }
        };
        frontB.setOnClickListener(setFront);
        frontI.setOnClickListener(setFront);
        frontSize_btn.setOnClickListener(setFront);

        setFrontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                   frontSize = setFrontSize.getProgress();
                   txt_2.append(" +fs " + frontSize);
                   if (frontSize<5){
                       frontSize=5;
                   }
                   container_n0.removeView(setFrontSize);
            }
        });

        save_text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAsBitmap(mResultImg, "image.png");
            }
        });



        /// Dozvil

        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    // Get the path from the Uri
                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                    }
                    // Set the image in ImageView
                    mResultImg.setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    private void changeConstraints(ConstraintSet set, int pos) {
        switch (pos){
            case 0:
                break;
            case 1:set.clear(R.id.button7, ConstraintSet.LEFT);
                set.clear(R.id.button7, ConstraintSet.TOP);
                set.connect(R.id.button7, ConstraintSet.TOP, R.id.guideline, ConstraintSet.TOP);
                set.connect(R.id.button7, ConstraintSet.LEFT, R.id.guideline, ConstraintSet.LEFT);
                break;
            case 2:set.clear(R.id.button7, ConstraintSet.LEFT);
                set.clear(R.id.button7, ConstraintSet.TOP);
                set.connect(R.id.button7, ConstraintSet.TOP, R.id.guideline2, ConstraintSet.TOP);
                set.connect(R.id.button7, ConstraintSet.LEFT, R.id.guideline2, ConstraintSet.LEFT);
                break;
            case 3:set.clear(R.id.button7, ConstraintSet.LEFT);
                set.clear(R.id.button7, ConstraintSet.TOP);
                set.connect(R.id.button7, ConstraintSet.TOP, R.id.guideline3, ConstraintSet.TOP);
                set.connect(R.id.button7, ConstraintSet.LEFT, R.id.guideline3, ConstraintSet.LEFT);
                break;
        }
    }

    private void addTextWM(){
        WatermarkText watermarkText = new WatermarkText(txt_1.getText().toString()) //editText
                .setPositionX(0.5)
                .setPositionY(0.5)
                .setTextAlpha(255)
                .setTextSize(frontSize)
                .setTextColor(Color.WHITE)
                .setTextFont(R.font.champagne)
                .setTextShadow(0.1f, 5, 5, Color.BLUE);

        WatermarkBuilder.create(MainActivity.this, mResultImg)
                .setTileMode(tileMod)
                .loadWatermarkText(watermarkText)
                .getWatermark()
                .setToImageView(mResultImg);
    }
    private void addInVisibleTextWM(){
       // progressBar.setVisibility(View.VISIBLE);
        WatermarkText watermarkText = new WatermarkText(editText.getText().toString());
        WatermarkBuilder
                .create(MainActivity.this, mResultImg)
                .loadWatermarkText(watermarkText)
                .setInvisibleWMListener(true, new BuildFinishListener<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap object) {
                        //progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,
                                "Successfully create invisible watermark!", Toast.LENGTH_SHORT).show();
                        if (object != null) {
                            mResultImg.setImageBitmap(object);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                       // progressBar.setVisibility(View.GONE);
                        Timber.e(message);
                    }
                });
    }

    private void DetectText(){
        //progressBar.setVisibility(View.VISIBLE);
        WatermarkDetector.create(mResultImg, true)
                .detect(new DetectFinishListener() {
                    @Override
                    public void onSuccess(DetectionReturnValue returnValue) {
                        //progressBar.setVisibility(View.GONE);
                        if (returnValue != null) {
                            Toast.makeText(MainActivity.this, "Successfully detected invisible text: "
                                    + returnValue.getWatermarkString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                       //progressBar.setVisibility(View.GONE);
                        Timber.e(message);
                    }
                });
    }

    private void saveAsBitmap(View view, String filename) {
//        view.setDrawingCacheEnabled(true);
//        Bitmap bitmap = view.getDrawingCache();
//        File root = Environment.getExternalStorageDirectory();
//        File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
//        try {
//            cachePath.createNewFile();
//            FileOutputStream ostream = new FileOutputStream(cachePath);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
//            ostream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Something Went Wrong, Please Try Again!2", Toast.LENGTH_SHORT).show();
//        }

        try {
            view.setDrawingCacheEnabled(true);
            Bitmap b = view.getDrawingCache();
            MediaStore.Images.Media.insertImage(MainActivity.this.getContentResolver(), b, txt_1.getText().toString(), "WM_name_");
            Toast.makeText(this, "Ура, все вийшло, вітаю!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Something Went Wrong, Please Try Again!", Toast.LENGTH_SHORT).show();
        }

    }
}
