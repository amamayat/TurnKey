package com.mobilap.turnKey.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.mobilap.turnKey.PasswordActivity;
import com.mobilap.turnKey.models.PasswordModel;
import com.mobilap.turnKey.R;
import java.text.SimpleDateFormat;
import java.util.List;

public class ItemPasswordAdapter extends ArrayAdapter<PasswordModel> {

    public ItemPasswordAdapter(@NonNull Context context, List<PasswordModel> listPasswords) {
        super(context, 0, listPasswords);

    }
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_password,parent, false);
        }



        ItemPasswordAdapter.PasswordViewHolder viewHolder = (ItemPasswordAdapter.PasswordViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ItemPasswordAdapter.PasswordViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.title_password);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img_password);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date_creation);
            convertView.setTag(viewHolder);
        }


        //final PasswordViewModel password = getItem(position);
        final PasswordModel password = getItem(position);

        viewHolder.title.setText(password.getTitle());

        Glide.with(getContext())
                    .load(password.getImage())
                    .into(viewHolder.image);




        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
        viewHolder.date.setText(format.format(password.getDateCreation()));

        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext().getApplicationContext(), PasswordActivity.class);
                intent.putExtra("password", password.getToString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finalConvertView.getContext().startActivity(intent);


            }
        });

        return convertView;
    }

    private class PasswordViewHolder {
        public TextView title;
        public ImageView image;
        public TextView date;
    }
}
