package com.assignment.radius.radiusassignment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<User> userList;
    View v;

    public RecyclerViewAdapter(List<User> userList){
        this.userList=userList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final User user=userList.get(i);
        myViewHolder.name.setText(user.getName());
        myViewHolder.age.setText("Age : "+user.getAge());
        Picasso.get().load(user.getImage()).into(myViewHolder.image);
//        Picasso.get().load(user.getLarge()).into(myViewHolder.i);
        myViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),user.getName(), Toast.LENGTH_SHORT).show();
                final Dialog di = new Dialog(view.getContext(), android.R.style.Theme_Black_NoTitleBar);
                di.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                di.setContentView(R.layout.picture);
                di.setCancelable(true);
                ImageView img=(ImageView)di.findViewById(R.id.picture);
                Picasso.get().load(user.getLarge()).into(img);
                di.show();
                di.setCanceledOnTouchOutside(true);
                RelativeLayout relativeLayout=(RelativeLayout)di.findViewById(R.id.pictureLayout);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        di.dismiss();
                    }
                });
            }
        });
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext(), android.R.style.Theme_Black_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100,0,0,0)));
                dialog.setContentView(R.layout.popup);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setTitle(user.getName());
                TextView x=(TextView)dialog.findViewById(R.id.textClose);
                TextView u=(TextView)dialog.findViewById(R.id.user);
                TextView g=(TextView)dialog.findViewById(R.id.gen);
                TextView e=(TextView)dialog.findViewById(R.id.email);
                TextView ph=(TextView)dialog.findViewById(R.id.phone);
                CircleImageView p=(CircleImageView)dialog.findViewById(R.id.pic);
                Picasso.get().load(user.getLarge()).into(p);
                u.setText(user.getUser());
                g.setText(user.getGender());
                e.setText(user.getEmail());
                ph.setText(user.getPhone());
                x.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,age;
        public CircleImageView image;
        public ImageView i;

        public MyViewHolder(View view) {
            super(view);
            name=(TextView)view.findViewById(R.id.name);
            age=(TextView)view.findViewById(R.id.age);
            image=(CircleImageView) view.findViewById(R.id.profile);
            i=(ImageView)view.findViewById(R.id.picture);
        }
    }



}
