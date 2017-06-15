package fr.formation.tp12;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.formation.tp12.database.datasource.DataSource;
import fr.formation.tp12.database.modele.User;

public class Principale extends AppCompatActivity {

    List<User> users = new ArrayList<>();
    List<String> usersList = new ArrayList<>();

    RecyclerSimpleViewAdapter adapter;

    DataSource<User> dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);

        // Create or retrieve the database
        try {
            dataSource = new DataSource<>(this, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // open the database
        openDB();

        //Prepare adapter for RecyclerView of users
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_users);

        adapter = new RecyclerSimpleViewAdapter(usersList, android.R.layout.simple_list_item_1);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshListUsers();
    }

    /**
     * On va sur la page de création d'un utilisateur
     *
     * @param view
     */
    public void newUser(View view) {
        Intent intent = new Intent(Principale.this, PageCreateUser.class);
        startActivity(intent);
    }

    private void refreshListUsers() {
        users = dataSource.readAll();

        usersList.clear();

        for(User user : users) {
            usersList.add(user.getNom());
        }

        adapter.notifyDataSetChanged();
    }

    public void openDB() throws SQLiteException {
        dataSource.open();
    }

    public void closeDB() {
        dataSource.close();
    }

    protected long insertRecord(User user) throws Exception {

        // Insert the line in the database
        long rowId = dataSource.insert(user);

        // Test to see if the insertion was ok
        if (rowId == -1) {
            Toast.makeText(this, "Error when creating an User",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User created and stored in database",
                    Toast.LENGTH_LONG).show();
        }
        return rowId;
    }

    /**
     * * Update a record
     *
     * @return the updated row id
     */
    private long updateRecord(User user) throws Exception {

        int rowId = dataSource.update(user);

        // test to see if the insertion was ok
        if (rowId == -1) {
            Toast.makeText(this, "Error when updating an User",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User updated in database", Toast.LENGTH_LONG)
                    .show();
        }
        return rowId;
    }

    private void deleteRecord(User user) {
        long rowId = dataSource.delete(user);
        if (rowId == -1) {
            Toast.makeText(this, "Error when deleting an User",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User deleted in database", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * Query a the database
     */
    private void queryTheDatabase() {
        List<User> users = dataSource.readAll();
        displayResults(users);
    }

    private void displayResults(List<User> users) {

        int count = 0;
        for (User user : users
                ) {
            Toast.makeText(
                    this,
                    "Utilisateur :" + user.getNom() + "("
                            + user.getId() + ")", Toast.LENGTH_LONG).show();
            count++;
        }
        Toast.makeText(this,
                "The number of elements retrieved is " + count,
                Toast.LENGTH_LONG).show();

    }
}
