package com.example.midapplication;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class addActivity extends AppCompatActivity {
    private ImageView addPic;
    private EditText addName;
    private EditText addHome;
    private EditText addIntro;
    private EditText addBddate;
    private RadioButton addMale;
    private RadioButton addFemale;
    private RadioButton addSexElse;
    private RadioButton addShu;
    private RadioButton addWu;
    private RadioButton addWei;
    private RadioButton addDonghan;
    private RadioButton addNationElse;
    private RadioGroup sexGroup;
    private RadioGroup nationGroup;
    private Button makeAdd;
    private Button cancelAdd;
    private int NOADD = 2;
    private int MAKEADD = 3;
    String sex = "男", nation = "蜀";
    private AlertDialog.Builder dialog;
    final static int CAMERA =1;
    final static int ICON =2;
    final static int CAMERAPRESS =3;
    final static int ICONPRESS=4;
    final static int cameraChoice = 0;
    final static int iconChoice = 1;
    Uri imageUri; //图片路径
    File imageFile; //图片文件
    String imagePath;
    Bitmap bitmapdown;
    private void findId() {
        addPic = findViewById(R.id.addPic);
        addName = findViewById(R.id.nameInput);
        addHome = findViewById(R.id.homeInput);
        addIntro = findViewById(R.id.introInput);
        addBddate = findViewById(R.id.bdDateInput);
        addMale = findViewById(R.id.male);
        addFemale = findViewById(R.id.female);
        addSexElse = findViewById(R.id.sexElse);
        addShu = findViewById(R.id.shu);
        addWu = findViewById(R.id.wu);
        addWei = findViewById(R.id.wei);
        addDonghan = findViewById(R.id.donghan);
        addNationElse = findViewById(R.id.elseNation);
        sexGroup = findViewById(R.id.radioSex);
        nationGroup = findViewById(R.id.radioNations);
        makeAdd = findViewById(R.id.makeAdd);
        cancelAdd = findViewById(R.id.cancelAdd);

        //性别选择
        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (addMale.isChecked())
                    sex = "男";
                else if (addFemale.isChecked())
                    sex = "女";
                else
                    sex = "其他";
                setNewResult();
            }
        });
        //国家选择
        nationGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (addShu.isChecked())
                    nation = "蜀";
                else if (addWu.isChecked())
                    nation = "吴";
                else if (addWei.isChecked())
                    nation = "魏";
                else if (addDonghan.isChecked())
                    nation = "东汉";
                else
                    nation = "其他";
                setNewResult();
            }
        });
        //确认按钮
        makeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addName.getText().toString().equals("")) {
                    Toast.makeText(addActivity.this, "名字为空！", Toast.LENGTH_SHORT).show();
                } else if (addBddate.getText().toString().equals("")) {
                    Toast.makeText(addActivity.this, "生卒为空！", Toast.LENGTH_SHORT).show();
                } else if (addHome.getText().toString().equals("")) {
                    Toast.makeText(addActivity.this, "籍贯为空！", Toast.LENGTH_SHORT).show();
                } else if (addBddate.getText().toString().equals("")) {
                    Toast.makeText(addActivity.this, "简介为空！", Toast.LENGTH_SHORT).show();
                } else {
                    setNewResult();
                    addActivity.this.finish();
                }
            }
        });
        //取消按钮
        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(NOADD, new Intent());
                addActivity.this.finish();
            }
        });
        //选择添加的头像框
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder picDialog = new AlertDialog.Builder(addActivity.this);
                final String[] items = new String[] {"拍摄", "从相机选择"};
                picDialog.setTitle("更换头像").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectPic(i);
                    }
                });
                picDialog.show();
                setNewResult();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setResult(NOADD, new Intent());
        findId();
    }
    private void setNewResult() {
        Intent resultIntent = new Intent();
        Bundle resultBundle = new Bundle();
        resultBundle.putString("name", addName.getText().toString());
        resultBundle.putString("bdDate", addBddate.getText().toString());
        resultBundle.putString("home", addHome.getText().toString());
        resultBundle.putString("sex", sex);
        resultBundle.putString("nation", nation);
        resultBundle.putString("intro", addIntro.getText().toString());
        resultBundle.putString("picPath", imagePath);
        resultIntent.putExtras(resultBundle);
        setResult(MAKEADD, resultIntent);
    }

    // 修改图片部分
    private void selectPic(int choice) {
        switch (choice) {
            case cameraChoice:
                if( Build.VERSION.SDK_INT>=23){
                    Toast.makeText(this,"当前的版本号"+Build.VERSION.SDK_INT,Toast.LENGTH_LONG).show();
                    //android 6.0权限问题
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED||
                            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},CAMERAPRESS);
                        Toast.makeText(this,"执行了权限请求",Toast.LENGTH_LONG).show();
                    }else {
                        startCamera();
                    }
                }else {
                    startCamera();
                }
                break;
            case iconChoice:
                if( Build.VERSION.SDK_INT>=23){
                    Toast.makeText(this,"当前的版本号"+Build.VERSION.SDK_INT,Toast.LENGTH_LONG).show();
                    //android 6.0权限问题
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED||
                            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED   ){
                        Toast.makeText(this,"执行了权限请求",Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},CAMERAPRESS);
                    }else {
                        startIcon();
                    }
                }else {
                    startIcon();
                }
                break;
        }
    }
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public void  startCamera(){
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        imageFile = new File(path, "heard.png");
        try {
            if (imageFile.exists()) {
                imageFile.delete();
            }
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将File对象转换为Uri并启动照相程序
        imageUri = Uri.fromFile(imageFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); //照相
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
        startActivityForResult(intent, CAMERA); //启动照相
    }

    public void startIcon(){
        Intent intent1 = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent1, ICON);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFilePath(String imagePath,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath,options);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult","requestCode"+requestCode+"resultCode"+resultCode);
        switch (requestCode){
            case CAMERA:
                Bitmap bitmap1 = null;
                try {
                    bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    imagePath = getPath(this, imageUri);
                    bitmapdown = bitmap1;
                    addPic.setImageBitmap(decodeSampledBitmapFromFilePath(imagePath, 200, 200));
                } catch (FileNotFoundException e) {
                    imageFile = null;
                    e.printStackTrace();
                }
                Log.d("chenzhu","imagePath"+imagePath);

                break;
            case ICON:
                DisplayMetrics metric = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metric);
                String dst = getPath(this, data.getData());
                imageFile = new File(dst);
                imagePath = dst;
                Bitmap bitmap = ThumbnailUtils.extractThumbnail(getBitmapFromFile(imageFile), 200, 200);
                bitmapdown = bitmap;
                addPic.setImageBitmap(bitmapdown);
                Log.d("chenzhu","imagePath"+imagePath);
                break;
        }
        setNewResult();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERAPRESS:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    //获取到了权限
                    startCamera();
                }else {
                    Toast.makeText(this,"对不起你没有同意该权限",Toast.LENGTH_LONG).show();
                }
                break;

            case ICONPRESS:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]== PackageManager.PERMISSION_GRANTED){
                    //获取到了权限
                    startIcon();
                }else {
                    Toast.makeText(this,"对不起你没有同意该权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public Bitmap getBitmapFromFile(File dst) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            //opts.inJustDecodeBounds = false;
            opts.inSampleSize = 2;

            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
    // 申请权限
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
