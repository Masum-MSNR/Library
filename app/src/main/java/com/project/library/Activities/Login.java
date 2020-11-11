package com.project.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.library.ModelClass.Admin;
import com.project.library.ModelClass.Student;
import com.project.library.R;

public class Login extends AppCompatActivity {

    EditText userName, password;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button login;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //EditText
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        //RadioGroup
        radioGroup = findViewById(R.id.radioGroup);
        //RadioButton
        radioButton = findViewById(R.id.radioButton2);
        //Button
        login = findViewById(R.id.loginButton);
        //TextView
        signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountType = radioButton.getText().toString();
                if (accountType.equals("Student")) {
                    Query query1 = FirebaseDatabase.getInstance().getReference("Students").orderByChild("userName").equalTo(userName.getText().toString());
                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                Student obj = snap.getValue(Student.class);
                                String password1 = obj.getPassword();
                                if (password1.equals(password.getText().toString())) {
                                    String name = obj.getUserName();
                                    Intent intent = new Intent(Login.this, Home_Student.class);
                                    intent.putExtra("name", name);
                                    startActivity(intent);
                                    Login.this.finish();
                                } else {
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (!dataSnapshot.hasChildren()) {
                                Toast.makeText(Login.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Query query1 = FirebaseDatabase.getInstance().getReference("Admins").orderByChild("userName").equalTo(userName.getText().toString());

                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                Admin obj = snap.getValue(Admin.class);
                                String password1 = obj.getPassword();
                                if (password1.equals(password.getText().toString())) {
                                    String name = obj.getUserName();
                                    Intent intent = new Intent(Login.this, Home_Admin.class);
                                    intent.putExtra("name", name);
                                    startActivity(intent);
                                    Login.this.finish();

                                } else {
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (!dataSnapshot.hasChildren()) {
                                Toast.makeText(Login.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }


    public void radioButtonClicked(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }
}