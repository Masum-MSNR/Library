package com.project.library.Activities;

import android.content.Intent;
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
import com.project.library.Adapters.BooksAdapter;
import com.project.library.ModelClass.Book;
import com.project.library.R;

import java.util.ArrayList;
import java.util.List;

public class Home_Admin extends AppCompatActivity {

    TextView addNewBook;
    SearchView searchView;
    RecyclerView recyclerView;
    TextView name;
    DatabaseReference reference;
    ArrayList<Book> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        reference=FirebaseDatabase.getInstance().getReference().child("Books");
        addNewBook = findViewById(R.id.addNewBook);
        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        name=findViewById(R.id.name);
        Intent intent=getIntent();
        String Name=intent.getStringExtra("name");

        name.setText(Name);
        addNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Home_Admin.this,AddBook.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(reference!=null){
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        list=new ArrayList<>();
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            list.add(ds.getValue(Book.class));
                        }
                        BooksAdapter booksAdapter=new BooksAdapter(list);
                        recyclerView.setAdapter(booksAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    newText=newText.toLowerCase();
                    ArrayList<Book> mylist=new ArrayList<>();
                    for(Book ddt:list){
                        String s=ddt.getName().toLowerCase();
                        if(s.contains(newText)){
                            mylist.add(ddt);
                        }
                    }
                    BooksAdapter adapter=new BooksAdapter(mylist);
                    recyclerView.setAdapter(adapter);
                    return false;
                }
            });
        }

    }
}