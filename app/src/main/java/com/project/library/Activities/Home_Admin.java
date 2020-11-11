package com.project.library.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.library.Adapters.BooksAdapter1;
import com.project.library.ModelClass.Book;
import com.project.library.R;

import java.util.ArrayList;

public class Home_Admin extends AppCompatActivity {

    TextView addNewBook, name;
    SearchView searchView;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<Book> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        reference = FirebaseDatabase.getInstance().getReference().child("Books");

        //TextView
        addNewBook = findViewById(R.id.addNewBook);
        name = findViewById(R.id.name);
        //SearchView
        searchView = findViewById(R.id.search);
        //RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Intend
        Intent intent = getIntent();
        String Name = intent.getStringExtra("name");

        name.setPaintFlags(name.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        name.setText(Name);
        addNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Admin.this, AddBook.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (reference != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(Book.class));
                        }
                        BooksAdapter1 booksAdapter1 = new BooksAdapter1(list);
                        recyclerView.setAdapter(booksAdapter1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = newText.toLowerCase();
                    ArrayList<Book> mylist = new ArrayList<>();
                    for (Book ddt : list) {
                        String s = ddt.getName().toLowerCase();
                        if (s.contains(newText)) {
                            mylist.add(ddt);
                        }
                    }
                    BooksAdapter1 adapter = new BooksAdapter1(mylist);
                    recyclerView.setAdapter(adapter);
                    return false;
                }
            });
        }
    }
}