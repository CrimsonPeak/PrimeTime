package redhill.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class FavouriteMovies extends AppCompatActivity {

    private ListView listView_favouriteMovies;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_movies);

        prefs = this.getSharedPreferences("Favourite", Context.MODE_PRIVATE);

        //Toast.makeText(FavouriteMovies.this, R.string.DisabledMovies_toast,
        //        Toast.LENGTH_SHORT).show();

        listView_favouriteMovies = (ListView) findViewById(R.id.listView_favMovies);

        final DBHelperFav db2 = new DBHelperFav(this);
        ArrayList<String> names2 = new ArrayList<String>();

        for (int i=0; i<db2.getAllMovies2().size(); i++)
            names2.add(db2.getAllMovies2().get(i).getName());

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names2);
        listView_favouriteMovies.setAdapter(adapter);

        listView_favouriteMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String favChosen = (String) parent.getItemAtPosition(position);
                editor = prefs.edit();
                editor.putString("fav", favChosen);
                editor.apply();

                finish();
                startActivity(new Intent(FavouriteMovies.this, Main3Activity.class));
            }
        });

        listView_favouriteMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

                String data = (String)arg0.getItemAtPosition(arg2);
                String[] newdata = new String[]{data};
                db2.deleteMovie2(newdata);
                adapter.remove(data);

                return true;
            }
        });

    }

}
