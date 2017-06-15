package fr.formation.tp12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import fr.formation.tp12.database.modele.User;

public class PageCreateUser extends Principale {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_create_user);
    }

    public void createUser(View view) {
        EditText nameUser = (EditText) findViewById(R.id.nomUser);

        String nameUserString = nameUser.getText().toString();
        if(!nameUserString.isEmpty()) {
            User newUser = new User();
            newUser.setNom(nameUserString);

            try {
                insertRecord(newUser);

                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
