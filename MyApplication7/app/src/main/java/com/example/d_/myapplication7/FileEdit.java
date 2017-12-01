package com.example.d_.myapplication7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileEdit extends AppCompatActivity {
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);
        final Button save = (Button) findViewById(R.id.save);
        final Button load = (Button) findViewById(R.id.load);
        final Button clear = (Button) findViewById(R.id.clear);
        final Button delete = (Button) findViewById(R.id.delete);

        final EditText fileNameText = (EditText) findViewById(R.id.fileName);
        final EditText fileContentText = (EditText) findViewById(R.id.fileContent);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = fileNameText.getText().toString();
                try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)) {
                    fileOutputStream.write(fileContentText.getText().toString().getBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Toast.makeText(FileEdit.this, "Save file " + fileNameText.getText().toString() + " succedd", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = fileNameText.getText().toString();
                try (FileInputStream fileInputStream = openFileInput(fileName)) {
                    byte[] content = new byte[fileInputStream.available()];
                    fileInputStream.read(content);
                    fileContentText.setText(new String(content));
                    Toast.makeText(FileEdit.this, "Load file " + fileNameText.getText().toString() + " succedd", Toast.LENGTH_SHORT).show();
                    fileInputStream.close();
                } catch (Exception e) {
                    Toast.makeText(FileEdit.this, "File " + fileNameText.getText().toString() + " not exist!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileNameText.setText("");
                fileContentText.setText("");
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (deleteFile(fileNameText.getText().toString())) {
                        Toast.makeText(FileEdit.this, "File " + fileNameText.getText().toString() + " delete!" +
                                "!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
