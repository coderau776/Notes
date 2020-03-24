package android.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button b;
    Intent inte;
    ListView ls;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter ar;
    static Set<String> set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b=(Button) findViewById(R.id.btn);

//        SharedPreferences shared = this.getSharedPreferences("android.example.notes", Context.MODE_PRIVATE);
//        shared.edit().putString("name","atharva").apply();
//        String s = shared.getString("name","");

        ls = (ListView)findViewById(R.id.list_view);
        SharedPreferences shared = this.getSharedPreferences("android.example.notes", Context.MODE_PRIVATE);
        set=shared.getStringSet("notes",null);

        notes.clear();

        if(set!=null)
        {
            notes.addAll(set);
        }
        else{

            set = new HashSet<String>();
            set.addAll(notes);
            shared.edit().remove("notes").apply();
            shared.edit().putStringSet("notes",set).apply();
        }

        ar = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        ls.setAdapter(ar);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inte = new Intent(getApplicationContext(),Main2Activity.class);
                inte.putExtra("id",position);
                startActivity(inte);
            }
        });

        ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("are you sure").setMessage("do you want to delete this item")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(position);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("android.example.notes",Context.MODE_PRIVATE);
                        if(MainActivity.set==null)
                        {
                            MainActivity.set=new HashSet<String>();
                        }
                        else
                        {
                            MainActivity.set.clear();
                        }
                        set.addAll(notes);
                        sharedPreferences.edit().remove("notes").apply();

                        sharedPreferences.edit().putStringSet("notes",set).apply();
                        ar.notifyDataSetChanged();
                    }
                }).setNegativeButton("No",null).show();
                return true;
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notes.add("");
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("android.example.notes", Context.MODE_PRIVATE);

                if(MainActivity.set==null)
                {
                    MainActivity.set=new HashSet<String>();
                }
                else
                {
                    MainActivity.set.clear();
                }
                set.addAll(notes);
                sharedPreferences.edit().remove("notes").apply();

                sharedPreferences.edit().putStringSet("notes",set).apply();
                ar.notifyDataSetChanged();
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("id",notes.size()-1);
                startActivity(intent);
            }
        });
    }

}
