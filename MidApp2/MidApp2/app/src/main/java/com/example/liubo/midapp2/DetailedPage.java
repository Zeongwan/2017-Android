package com.example.liubo.midapp2;

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
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DetailedPage extends AppCompatActivity {
    private TextView name;
    private ImageView pic;
    private TextView bdDate;
    private Spinner nation;
    private TextView home;
    private TextView intro;
    private TextView sex;
    private EditText editText;
    private Integer elementPos;
    private int picId;
    private AlertDialog.Builder dialog;
    final private int STARTLIST = 1;
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
        name = (TextView) findViewById(R.id.name);
        pic = (ImageView) findViewById(R.id.pic);
        bdDate = (TextView) findViewById(R.id.bdDate);
        nation = (Spinner) findViewById(R.id.nation);
        home = (TextView) findViewById(R.id.home);
        intro = (TextView) findViewById(R.id.intro);
        sex = (TextView) findViewById(R.id.sex);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_page);
        findId();
        imagePath = "";
        Bundle bundle = this.getIntent().getExtras();
        elementPos = bundle.getInt("pos");
        name.setText(bundle.getString("name"));
        picId = bundle.getInt("pic");
        if (picId == -1) {
            imagePath = bundle.getString("picPath");
            imageFile = new File(imagePath);
            Bitmap bitmap = ThumbnailUtils.extractThumbnail(getBitmapFromFile(imageFile), 200, 200);
            bitmapdown = bitmap;
            pic.setImageBitmap(bitmapdown);
        } else {
            pic.setImageResource(picId);
        }
        bdDate.setText(bundle.getString("bdDate"));
        String nationString = bundle.getString("nation");
        if (nationString.equals("蜀")) {
            nation.setSelection(2, true);
        } else if (nationString.equals("吴")) {
            nation.setSelection(0, true);
        } else if (nationString.equals("魏")) {
            nation.setSelection(1, true);
        } else if (nationString.equals("东汉")) {
            nation.setSelection(3, true);
        } else {
            nation.setSelection(4, true);
        }
        home.setText(bundle.getString("home"));
        intro.setText(bundle.getString("intro"));
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewData(name);
            }
        });
        bdDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewData(bdDate);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewData(home);
            }
        });
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewData(intro);
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder picDialog = new AlertDialog.Builder(DetailedPage.this);
                final String[] items = new String[] {"拍摄", "从相机选择"};
                picDialog.setTitle("更换头像").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        changePic(i);
                    }
                });
                picDialog.show();
            }
        });
        setNewResult();
    }

    private void changePic(int choice) {
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

    private void setNewResult() {
        // 设置Activity结果
        Intent resultIntent = new Intent();
        Bundle resultBundle = new Bundle();
        resultBundle.putInt("pos", elementPos);
        resultBundle.putString("name", name.getText().toString());
        resultBundle.putString("home", home.getText().toString());
        resultBundle.putString("sex", sex.getText().toString());
        resultBundle.putString("nation", nation.getSelectedItem().toString());
        resultBundle.putString("picPath", imagePath);
        resultBundle.putString("intro", intro.getText().toString());
        if (imagePath != "") {
            resultBundle.putInt("picId", -1);
        } else {
            resultBundle.putInt("picId", picId);
        }
        resultIntent.putExtras(resultBundle);
        setResult(STARTLIST, resultIntent);
    }

    private void setNewData(final TextView textView) {
        editText = new EditText(this);
        dialog = new AlertDialog.Builder(this);
        dialog.setView(editText);
        dialog.setPositiveButton("确定修改", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(editText.getText());
                setNewResult();
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
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
        Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                    pic.setImageBitmap(decodeSampledBitmapFromFilePath(imagePath, 200, 200));
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
                pic.setImageBitmap(bitmapdown);
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
