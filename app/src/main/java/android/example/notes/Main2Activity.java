package android.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;



public class Main2Activity extends AppCompatActivity implements TextWatcher {

    int id;
    FloatingActionButton b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        b=findViewById(R.id.floatingActionButton);
        EditText ed = (EditText)findViewById(R.id.editText);
        Intent in = getIntent();

        id=in.getIntExtra("id",-1);

        if (id!=-1)
        {
            String s = MainActivity.notes.get(id);
            ed.setText(s);
        }
        ed.addTextChangedListener(this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(main);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        MainActivity.notes.set(id,String.valueOf(s));
        MainActivity.ar.notifyDataSetChanged();

        SharedPreferences sharedPreferences = this.getSharedPreferences("android.example.notes", Context.MODE_PRIVATE);

        if(MainActivity.set==null)
        {
            MainActivity.set=new HashSet<String>();
        }
        else
        {
            MainActivity.set.clear();
        }
        MainActivity.set.clear();
        MainActivity.set.addAll(MainActivity.notes);
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes",MainActivity.set).apply();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
