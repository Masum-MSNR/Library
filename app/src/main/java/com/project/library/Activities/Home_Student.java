package com.project.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.library.Adapters.BooksAdapter;
import com.project.library.ModelClass.Book;
import com.project.library.R;

import java.util.ArrayList;

public class Home_Student extends AppCompatActivity {

    RecyclerView recyclerView;
    BooksAdapter adapter;
    DatabaseReference reference;
    TextView name;
    SearchView searchView;
    ArrayList<Book> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        recyclerView = findViewById(R.id.recyclerView);
        searchView=findViewById(R.id.search);
        name=findViewById(R.id.name);
        Intent intent=getIntent();
        String Name=intent.getStringExtra("name");
        name.setText(Name);
        reference=FirebaseDatabase.getInstance().getReference().child("Books");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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