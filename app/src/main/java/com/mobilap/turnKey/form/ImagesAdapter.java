package com.mobilap.turnKey.form;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mobilap.turnKey.R;
import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ImagesAdapter extends ArrayAdapter<LogoModel> {

    private LogoModel cursor;
    private EditText title;
    private FloatingActionButton button;
    private ImageView newImageLogo;
    private Dialog dialog;
    public String imagesRessources;
    public ImagesAdapter(@NonNull Context context, List<LogoModel> logos) {
        super(context,0, logos);
    }


    public String GetImage(){
        return this.imagesRessources;
    }

    public View getView(int position, View convertView, final ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
        }

        LogoViewHolder viewlogo = (LogoViewHolder) convertView.getTag();
        final LogoViewHolder finalViewlogo = viewlogo;

        if(viewlogo == null){
            viewlogo = new LogoViewHolder();
            viewlogo.logoText = (TextView)convertView.findViewById(R.id.logo_text);
            viewlogo.logoImage = (ImageView) convertView.findViewById(R.id.logo_image);
            convertView.setTag(viewlogo);
        }
        final LogoModel logo = getItem(position);
        viewlogo.logoText.setText(logo.getText());

        Glide.with(getContext())
                .load(logo.getUri().toString())
                .into(viewlogo.logoImage);



        convertView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "RestrictedApi"})
            @Override
            public void onClick(View view) {
                cursor = logo;
                title.setText(logo.getText());

                button.setVisibility(View.GONE);

                Glide.with(getContext())
                        .load(logo.getUri().toString())
                        .into(newImageLogo);

                newImageLogo.setVisibility(View.VISIBLE);
                imagesRessources = logo.getUri().toString();
                dialog.dismiss();
            }
        });

        return convertView;
    }

    private class LogoViewHolder{
        public TextView logoText;
        public ImageView logoImage;
    }

    public void setText(EditText edit){
        title = edit;
    }
    public LogoModel getCursor(){
        return cursor;
    }
    public void setImage(FloatingActionButton button){
        this.button = button;
    }

    public void setNewImageLogo(ImageView newImageLogo){
        this.newImageLogo = newImageLogo;
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }
}


