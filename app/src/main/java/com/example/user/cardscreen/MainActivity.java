package com.example.user.cardscreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private RecyclerView r;
    private DatabaseReference f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f = FirebaseDatabase.getInstance().getReference().child("global");
        f.keepSynced(true);
        r = (RecyclerView) findViewById(R.id.rec);
        r.setHasFixedSize(true);
        r.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<blog> options =
                new FirebaseRecyclerOptions.Builder<blog>()
                        .setQuery(f, blog.class)
                        .build();
        FirebaseRecyclerAdapter<blog,Bloghold> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<blog, Bloghold>
                (options) {
            @Override
            protected void onBindViewHolder(Bloghold holder, int position, blog model) {
                holder.setTitle(model.getTitle());
                holder.setRate(model.getRate());
                holder.setImage(getApplicationContext(),model.getImage());

            }

            @NonNull
            @Override
            public Bloghold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }
        };
        r.setAdapter(firebaseRecyclerAdapter);
    }


    public static class Bloghold extends RecyclerView.ViewHolder
    {   View v;

        public Bloghold(@NonNull View itemView) {
            super(itemView);
            v=itemView;

        }
        public void setTitle(String title )
        {
            TextView t1=(TextView)v.findViewById(R.id.textView);
            t1.setText(title);
        }
        public void setRate(String rate )
        {
            TextView t2=(TextView)v.findViewById(R.id.textView2);
            t2.setText(rate);
        }
        public void setImage(Context c, String image)
        {
            ImageView i=(ImageView)v.findViewById(R.id.imageView);
            Picasso.with(c).load(image).into(i);
        }
    }
}
