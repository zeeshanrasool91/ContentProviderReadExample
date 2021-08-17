package com.example.readfromcontentprovider;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readfromcontentprovider.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        onClickShowDetails(binding.btnRetrieve);
        Objects.requireNonNull(binding.txtName.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.txtName.setError(null);
            }
        });
    }

    public void onClickAddDetails(View view) {
        String name = Objects.requireNonNull(binding.txtName.getEditText()).getText().toString();
        if(TextUtils.isEmpty(name)){
            binding.txtName.setError("Cannot be Empty!");
            return;
        }
        final String PROVIDER_NAME = "com.example.readfromcontentprovider.Birthday";
        final String URL = "content://" + PROVIDER_NAME + "/users";
        final Uri CONTENT_URI = Uri.parse(URL);
        ContentValues values = new ContentValues();
        values.put("name", "ZEESHAN RASOOL");
        getContentResolver().insert(CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
        onClickShowDetails(binding.btnRetrieve);
    }


    public void onClickShowDetails(View view) {
        // Retrieve employee records
        TextView resultView = findViewById(R.id.res);
        resultView.setMovementMethod(new ScrollingMovementMethod());
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.readfromcontentprovider.Birthday/users"), null, null, null, null);
        if(cursor == null){
            resultView.setText("No Records Found");
            return;
        }
        if (cursor.moveToFirst()) {
            StringBuilder strBuild = new StringBuilder();
            while (!cursor.isAfterLast()) {
                int idIndex = cursor.getColumnIndex("id");
                String id = cursor.getString(idIndex);
                int nameIndex = cursor.getColumnIndex("name");
                String name = cursor.getString(nameIndex);
                strBuild.append("\n" + id + "-" + name);
                cursor.moveToNext();
            }
            resultView.setText(strBuild);
        } else {
            resultView.setText("No Records Found");
        }
    }
}
