package com.project.library.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.library.ModelClass.Admin;
import com.project.library.ModelClass.Student;
import com.project.library.R;

public class SignUp extends AppCompatActivity {

    EditText fullName, userName, password, email;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button signUp;
    boolean xx = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //EditText
        fullName = findViewById(R.id.fullName);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        //RadioGroup
        radioGroup = findViewById(R.id.radioGroup);
        //RadioButton
        radioButton = findViewById(R.id.radioButton2);
        //Button
        signUp = findViewById(R.id.signUpButton);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountType = radioButton.getText().toString();
                if (accountType.equals("Student")) {
                    if (fullName.getText().toString().isEmpty()) {
                        Toast.makeText(SignUp.this, "Enter Full Name", Toast.LENGTH_SHORT).show();
                    } else if (userName.getText().toString().isEmpty()) {
                        Toast.makeText(SignUp.this, "Enter User Name", Toast.LENGTH_SHORT).show();
                    } else if (email.getText().toString().isEmpty()) {
                        Toast.makeText(SignUp.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().isEmpty()) {
                        Toast.makeText(SignUp.this, "Enter A Valid Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Query query1 = FirebaseDatabase.getInstance().getReference("Students").orderByChild("userName").equalTo(userName.getText().toString());

                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    if (xx) {
                                        userName.setError("User Name Already in Use");
                                        userName.requestFocus();
                                    }
                                } else {
                                    Student student = new Student(fullName.getText().toString(),
                                            userName.getText().toString(),
                                            password.getText().toString(),
                                            email.getText().toString());
                                    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Students");
                                    database.push().setValue(student);
                                    xx = false;
                                    Toast.makeText(SignUp.this, "Id Created Successfully", Toast.LENGTH_SHORT).show();
                                    SignUp.this.finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                } else {
                    if (fullName.getText().toString().isEmpty()) {
                        Toast.makeText(SignUp.this, "Enter Full Name", Toast.LENGTH_SHORT).show();
                    } else if (userName.getText().toString().isEmpty()) {
                        Toast.makeText(SignUp.this, "Enter User Name", Toast.LENGTH_SHORT).show();
                    } else if (email.getText().toString().isEmpty()) {
                        Toast.makeText(SignUp.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().isEmpty()) {
                        Toast.makeText(SignUp.this, "Enter A Valid Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Query query1 = FirebaseDatabase.getInstance().getReference("Admins").orderByChild("userName").equalTo(userName.getText().toString());

                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    if (xx) {
                                        userName.setError("User Name Already in Use");
                                        userName.requestFocus();
                                    }
                                } else {
                                    Admin admin = new Admin(fullName.getText().toString(),
                                            userName.getText().toString(),
                                            password.getText().toString(),
                                            email.getText().toString());
                                    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Admins");
                                    database.push().setValue(admin);

                                    xx = false;
                                    Toast.makeText(SignUp.this, "Id Created Successfully", Toast.LENGTH_SHORT).show();
                                    SignUp.this.finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });


    }

    public void radioButtonClicked(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }
}