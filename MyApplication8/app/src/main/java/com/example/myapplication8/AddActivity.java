package com.example.myapplication8;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    final private myDB myDatabase = new myDB(this, "app8_db", null, 1);
    final private String TABLENAME = "app8Table";
    private String phone;
    //private List<Map<String, String>> nameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        phone = "";
        final EditText nameInput = (EditText) findViewById(R.id.nameInput);
        final EditText birthDay = (EditText) findViewById(R.id.birthdayInput);
        final EditText gift = (EditText) findViewById(R.id.giftInput);
        final Button addButton = (Button) findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameInput.getText().toString().equals("")) {
                    Toast.makeText(AddActivity.this, "名字不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursorContact = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                    while (cursorContact.moveToNext()) {
                        if (nameInput.getText().toString().equals(cursorContact.getString(cursorContact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME.toString()))))
                            phone = cursorContact.getString(cursorContact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    SQLiteDatabase sqLiteDatabase = myDatabase.getReadableDatabase();
                    Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{"name"}, null, null, null, null, null);
                    boolean hasRepeat = false;
                    while (cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex("name")).equals(nameInput.getText().toString())) {
                            hasRepeat = true;
                            break;
                        }
                    }
                    // 如果存在相同用户名
                    if (hasRepeat) {
                        Toast.makeText(AddActivity.this, "名字重复了！", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", nameInput.getText().toString());
                        bundle.putString("birthday", birthDay.getText().toString());
                        bundle.putString("gift", gift.getText().toString());
                        bundle.putString("phone", phone);
                        intent.putExtras(bundle);
                        setResult(1, intent);
                        finish();
                    }
                    sqLiteDatabase.close();
                    setResult(0, new Intent(AddActivity.this, MainActivity.class));
                }
            }
        });
    }
}
