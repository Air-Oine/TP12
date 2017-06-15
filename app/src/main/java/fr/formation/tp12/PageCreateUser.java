package fr.formation.tp12;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import fr.formation.tp12.database.modele.User;

public class PageCreateUser extends AppCompatActivity {

    public static final String RESULT = "result";

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

            //On converti l'user en JSON pour le renvoyer à l'activité parente
            String userJson = new Gson().toJson(newUser);

            //On renvoie le user à l'activité parente
            Intent intent = new Intent();
            intent.putExtra(RESULT, userJson);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
