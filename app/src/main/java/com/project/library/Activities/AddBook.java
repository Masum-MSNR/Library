
package com.project.library.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.library.ModelClass.Book;
import com.project.library.R;

public class AddBook extends AppCompatActivity {

    EditText bookName,writerName,noOfPage;
    Button addNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        bookName=findViewById(R.id.bookName);
        writerName=findViewById(R.id.writerName);
        noOfPage=findViewById(R.id.noOfPage);
        addNow=findViewById(R.id.addNow);

        addNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookName.getText().toString().isEmpty()){
                    Toast.makeText(AddBook.this,"Enter Book Name",Toast.LENGTH_SHORT).show();
                }else if(writerName.getText().toString().isEmpty()){
                    Toast.makeText(AddBook.this,"Enter Writer Name",Toast.LENGTH_SHORT).show();
                }else if(noOfPage.getText().toString().isEmpty()){
                    Toast.makeText(AddBook.this,"Enter Number of Page",Toast.LENGTH_SHORT).show();
                }else{
                    Book book=new Book(bookName.getText().toString(),noOfPage.getText().toString(),writerName.getText().toString());
                    DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("Books");
                    database.push().setValue(book);
                }
            }
        });
    }
}