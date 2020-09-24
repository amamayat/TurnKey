package com.mobilap.turnKey;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobilap.turnKey.form.FormActivity;
import com.mobilap.turnKey.models.PasswordModel;
import com.mobilap.turnKey.services.PasswordService;

import java.text.SimpleDateFormat;

public class PasswordActivity extends AppCompatActivity {
    private EditText psw_user;
    private EditText psw_password;
    private EditText psw_url;
    private EditText psw_infos;
    private EditText psw_dateInsert;
    private TextView psw_dateUpdate;
    private PasswordModel password;
    private TextView psw_title;
    private ImageView psw_image;
    private FloatingActionButton btn_send_url;
    private TextView title_psw_url;
    private TextView title_psw_infos;
    private TextView title_psw_dateInsert;
    private PasswordService passwordService;
    private ImageView imageVisibiliteOn;
    private ImageView imageCopy;
    private ImageView imageCopy_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Intent intent = getIntent();
        String passwortToString = intent.getStringExtra("password");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        password = new PasswordModel(".").setPasswordToSring(passwortToString);
        psw_user = findViewById(R.id.psw_user);
        psw_password = findViewById(R.id.psw_password);
        psw_url = findViewById(R.id.psw_url);
        psw_infos = findViewById(R.id.psw_infos);
        psw_dateInsert = findViewById(R.id.psw_insert);
        psw_dateUpdate = findViewById(R.id.psw_dateUpdate);
        psw_title = findViewById(R.id.psw_title);
        psw_image = findViewById(R.id.psw_image);
        btn_send_url = findViewById(R.id.btn_send_url_psw);
        title_psw_url = findViewById(R.id.title_psw_url);
        title_psw_infos= findViewById(R.id.title_psw_infos);
        title_psw_dateInsert = findViewById(R.id.title_psw_dateInsert);
        imageVisibiliteOn = findViewById(R.id.imageVisibiliteOn);
        imageCopy = findViewById(R.id.imageCopy);
        imageCopy_user = findViewById(R.id.imageCopy_user);
        passwordService = new PasswordService(this);
        Glide.with(getApplicationContext())
                .load(password.getImage())
                .into(psw_image);

        psw_title.setText(password.getTitle());
        psw_user.setText(password.getUser());
        psw_user.setEnabled(false);
        String pswOff ="";
        final Boolean[] visibiliteOn = {false};
        for(int i=0; i< password.getPassword().length(); i++){
            pswOff += "*";
        }
        psw_password.setText(pswOff);
        imageVisibiliteOn.setClickable(true);
        final String finalPswOff = pswOff;
        imageVisibiliteOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!visibiliteOn[0]){
                    imageVisibiliteOn.setImageResource(R.drawable.ic_visibility_off);
                    psw_password.setText(password.getPassword());
                    visibiliteOn[0] = true;
                }else{
                    imageVisibiliteOn.setImageResource(R.drawable.ic_visibility);
                    psw_password.setText(finalPswOff);
                    visibiliteOn[0] = false;
                }

            }
        });

        imageCopy.setClickable(true);
        imageCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cManager = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cData = ClipData.newPlainText("text", password.getPassword());
                cManager.setPrimaryClip(cData);
                Toast.makeText(getApplicationContext(), R.string.copy_text, Toast.LENGTH_SHORT).show();
            }
        });
        imageCopy_user.setClickable(true);
        imageCopy_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cManager = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cData = ClipData.newPlainText("text", password.getUser());
                cManager.setPrimaryClip(cData);
                Toast.makeText(getApplicationContext(), R.string.copy_text, Toast.LENGTH_SHORT).show();
            }
        });


        psw_password.setEnabled(true);
        psw_password.setKeyListener(null);
        psw_password.setHorizontallyScrolling(true);
        if(password.getUrl().length() >0){
            psw_url.setText(password.getUrl());
            psw_url.setEnabled(false);
            btn_send_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "";
                    if(!password.getUrl().startsWith("https://") && !password.getUrl().startsWith("http://")){
                        if(!password.getUrl().startsWith("www.")){
                            url = "http://www."+password.getUrl();
                        }else{
                            url = "http://"+password.getUrl();
                        }

                    }else{
                        url = password.getUrl();
                    }
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
            });

        }else{
            psw_url.setVisibility(View.GONE);
            btn_send_url.setVisibility(View.GONE);
            title_psw_url.setVisibility(View.GONE);
        }

        if(password.getInformations().length()>0){
            psw_infos.setText(password.getInformations());
            psw_infos.setEnabled(false);

        }else{
            psw_infos.setVisibility(View.GONE);
            title_psw_infos.setVisibility(View.GONE);
        }

        String dateinsert = new SimpleDateFormat("dd/MM/yyyy").format(password.getDateCreation());
        String dateUpdate =  new SimpleDateFormat("dd/MM/yyyy").format(password.getDateModification());
        if(!dateinsert.equals(dateUpdate)){
            psw_dateInsert.setText(new SimpleDateFormat("dd/MM/yyyy").format(password.getDateCreation()));
            psw_dateInsert.setEnabled(false);

        }else{
            psw_dateInsert.setVisibility(View.GONE);
            title_psw_dateInsert.setVisibility(View.GONE);
        }
        psw_dateUpdate.setText(new SimpleDateFormat("dd/MM/yyyy").format(password.getDateModification()));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.password_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menuItem_psw_edit:
                finish();
                intent = new Intent(PasswordActivity.this, FormActivity.class);
                intent.putExtra("password", password.getToString());

                startActivityForResult(intent, 0);
                return true;
            case R.id.menuItem_psw_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage(R.string.delete_psw);
                builder.setPositiveButton(R.string.ok_deleteLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        passwordService.DeletePassword(password.getId());
                        Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                        intent.putExtra("connexion", true);
                        intent.putExtra("folder", password.getFolder());
                        startActivityForResult(intent, 0);
                    }
                });

                builder.setNegativeButton(R.string.no_deleteLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                return true;
        }
        finish();
        return false;
    }

}
