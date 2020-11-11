package com.project.library.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.library.ModelClass.Book;
import com.project.library.ModelClass.BorrowBook;
import com.project.library.R;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    String userName;
    ArrayList<Book> list;

    public BooksAdapter(String userName, ArrayList<Book> list) {
        this.userName = userName;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bookName.setText(list.get(position).getName());
        holder.writerName.setText(list.get(position).getWriterName());
        holder.borrowNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("StudentsBooksList");
                BorrowBook item=new BorrowBook(list.get(position).getName(),list.get(position).getWriterName());
                databaseReference.child(userName).setValue(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bookName, writerName, borrowNow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookName);
            writerName = itemView.findViewById(R.id.writerName);
            borrowNow = itemView.findViewById(R.id.borrowNow);
        }
    }
}
