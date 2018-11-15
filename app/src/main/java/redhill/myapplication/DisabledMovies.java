package redhill.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by LAL on 26.01.2017.
 */

public class DisabledMovies extends AppCompatActivity {

    private ListView listView_disabledMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disabled_movies);

        Toast.makeText(DisabledMovies.this, R.string.DisabledMovies_toast,
                Toast.LENGTH_SHORT).show();

        listView_disabledMovies = (ListView) findViewById(R.id.listView_disMovies);

        final DBHelper db = new DBHelper(this);
        ArrayList<String> names = new ArrayList<String>();

        for (int i=0; i<db.getAllMovies().size(); i++)
            names.add(db.getAllMovies().get(i).getName());

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        listView_disabledMovies.setAdapter(adapter);

        listView_disabledMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

                String data = (String)arg0.getItemAtPosition(arg2);
                String[] newdata = new String[]{data};
                db.deleteMovie(newdata);
                adapter.remove(data);

                return true;
            }
        });

    }

}
