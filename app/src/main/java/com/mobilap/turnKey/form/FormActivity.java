package com.mobilap.turnKey.form;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.mobilap.turnKey.MainActivity;
import com.mobilap.turnKey.R;
import com.mobilap.turnKey.models.FolderModel;
import com.mobilap.turnKey.models.PasswordModel;
import com.mobilap.turnKey.services.FolderService;
import com.mobilap.turnKey.services.PasswordService;
import com.mobilap.turnKey.services.RandomString;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormActivity extends AppCompatActivity {
    private EditText title;
    private EditText user;
    private EditText password;
    private EditText url;
    private MultiAutoCompleteTextView infos;
    private FloatingActionButton image;
    private FloatingActionButton alea;
    private Button save;
    private GridView gridView;
    private ImageView newImageLogo;
    private Dialog dialog;
    private PasswordService passwordService;
    private String menuItem;
    private String imageRessources;
    private PasswordModel passwordModel;
    private TextView form_title;
    private Spinner spinner;
    private FolderService folderService;
    private TextView title_spinner;
    private FirebaseStorage storage;
    private ArrayList<LogoModel> logos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Intent intent = getIntent();
        String passwortToString = intent.getStringExtra("password");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        if (passwortToString != null) {
            passwordModel = new PasswordModel(".").setPasswordToSring(passwortToString);
        }


        menuItem = intent.getStringExtra("menuItem");

        title = (EditText) findViewById(R.id.form_title);
        user = (EditText) findViewById(R.id.form_user) ;
        password = (EditText) findViewById(R.id.form_password);
        infos = (MultiAutoCompleteTextView) findViewById(R.id.form_info);
        image = findViewById(R.id.btn_searchImg);
        alea = findViewById(R.id.btn_alea);
        url = findViewById(R.id.form_url);
        save = (Button)findViewById(R.id.btn_save);

        newImageLogo = (ImageView)findViewById(R.id.new_imageLogo) ;
        newImageLogo.setVisibility(View.GONE);
        form_title = findViewById(R.id.form_titleEdit);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(view);
            }
        });
        alea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setText(getAleaPassword());
            }
        });
        folderService = new FolderService(this);
        spinner = findViewById(R.id.spinner_folders);
        spinner.setVisibility(View.GONE);
        title_spinner = findViewById(R.id.title_spinner);
        title_spinner.setVisibility(View.GONE);


        if(passwordModel != null){
            form_title.setText(R.string.form_titeEdit);
            title.setText(passwordModel.getTitle());
            user.setText(passwordModel.getUser());
            password.setText(passwordModel.getPassword());
            infos.setText(passwordModel.getInformations());

            Glide.with(getApplicationContext())
                    .load(passwordModel.getImage())
                    .into(newImageLogo);

            image.setVisibility(View.GONE);

            newImageLogo.setVisibility(View.VISIBLE);
            url.setText(passwordModel.getUrl());

            List<FolderModel> listFolders = new ArrayList<>();
            int idSelection = 0;
            listFolders = folderService.getFolders();
            if(listFolders.size() > 0 ) {
                final List<CharSequence> spinnerList = new ArrayList<CharSequence>();
                spinnerList.add("Liste");
                for (int i = 0; i < listFolders.size(); i++) {
                    spinnerList.add(listFolders.get(i).getNom());
                }

                for(int i=0; i< spinnerList.size(); i++){
                    if(spinnerList.get(i).toString().equals(passwordModel.getFolder())){
                        idSelection = i;
                    }
                }



                ArrayAdapter adapter = new ArrayAdapter<CharSequence>(this,
                        R.layout.support_simple_spinner_dropdown_item, spinnerList);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        passwordModel.setFolder(spinnerList.get(i).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                spinner.setVisibility(View.VISIBLE);
                title_spinner.setVisibility(View.VISIBLE);
                spinner.setAdapter(adapter);
                spinner.setSelection(idSelection);
            }
        }

        infos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_SCROLL:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(infos, InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            }
        });
        passwordService = new PasswordService(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().length() == 0){
                    Drawable d = getApplicationContext().getDrawable(R.drawable.ic_error_outline_red);
                    d.setBounds(0,0,d.getIntrinsicWidth(), d.getIntrinsicHeight());
                    title.requestFocus();

                    title.setError(getResources().getString(R.string.error),d);
                }else{

                    if(passwordModel == null){
                        SavePassword();
                    }else{
                        UpdatePassword();;
                    }
                }
            }
        });
        storage = FirebaseStorage.getInstance();
        logos = getLogos();

    }

    public void SavePassword(){
        String folder = menuItem;
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
        String urlLien = "";
        if(imageRessources == null){
            imageRessources = "https://firebasestorage.googleapis.com/v0/b/turnkey-6975f.appspot.com/o/networking.png?alt=media&token=0028f17d-f1fa-4952-bda5-d8a9e6fb7c6e";
        }

        long insertResult = passwordService.insertPassword(title.getText().toString(), user.getText().toString(), password.getText().toString(),url.getText().toString(),
                infos.getText().toString(), String.valueOf(imageRessources), folder, format.format(d),format.format(d));

        if(insertResult == -1){
            Toast.makeText(getApplicationContext(), R.string.error_insert, Toast.LENGTH_SHORT).show();
        }else{
            finish();
            Intent intent = new Intent(FormActivity.this, MainActivity.class);
            intent.putExtra("connexion", true);
            intent.putExtra("folder", folder);
            startActivityForResult(intent, 0);

        }

    }

    public void UpdatePassword(){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
        if(imageRessources == null){
            imageRessources = passwordModel.getImage();
        }

        passwordModel.setTitle(title.getText().toString());
        passwordModel.setUser(user.getText().toString());
        passwordModel.setPassword(password.getText().toString());
        passwordModel.setUrl(url.getText().toString());
        passwordModel.setInformations(infos.getText().toString());
        passwordModel.setImage(String.valueOf(imageRessources));
        passwordModel.setDateModification(d);

        long update = passwordService.UpdatePassword(passwordModel.getId(), passwordModel);
        if(update == -1){
            Toast.makeText(getApplicationContext(), R.string.error_insert, Toast.LENGTH_SHORT).show();
        }else{
            finish();
            Intent intent = new Intent(FormActivity.this, MainActivity.class);
            intent.putExtra("connexion", true);
            intent.putExtra("folder", passwordModel.getFolder());
            startActivityForResult(intent, 0);
        }
    }
    public String getAleaPassword(){

        RandomString random = new RandomString();
        return random.getAlphaNumericString();
    }

    public void open (View view){
        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_images);
        gridView = (GridView) dialog.findViewById(R.id.gridView);
        final ImagesAdapter imagesAdapter = new ImagesAdapter(getApplicationContext(), logos);

        imagesAdapter.setText(title);
        imagesAdapter.setImage(image);
        imagesAdapter.setNewImageLogo(newImageLogo);
        imagesAdapter.setDialog(dialog);
        gridView.setAdapter(imagesAdapter);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                imageRessources = imagesAdapter.GetImage();
            }
        });



        if(imageRessources != null){
            image.setVisibility(View.GONE);
        }

    }

//
//    @Override
//    public void onBackPressed(){
//        finish();
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        return true;
    }


    public ArrayList<LogoModel> getLogos(){
        final ArrayList<LogoModel> logos = new ArrayList<>();
        StorageReference listRef = storage.getReference().child("logos");
        listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {

                for (StorageReference item : listResult.getItems()) {
                    String name = "";
                try{
                    name = item.getName().substring(0, item.getName().indexOf(".png"));

                }catch(Exception e){

                }

                    final String finalName = name;
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            logos.add(new LogoModel(finalName,uri));

                        }
                    });

                }
            }
        });

        return logos;
    }



}
