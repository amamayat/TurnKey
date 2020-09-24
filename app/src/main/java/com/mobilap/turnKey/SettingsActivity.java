package com.mobilap.turnKey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobilap.turnKey.models.FolderModel;
import com.mobilap.turnKey.models.PasswordModel;
import com.mobilap.turnKey.services.CreateXLSFile;
import com.mobilap.turnKey.services.FolderService;
import com.mobilap.turnKey.services.PasswordService;


//import org.apache.poi.hssf.model.Sheet;
//import org.apache.poi.hssf.model.Workbook;
//import org.apache.poi.hssf.record.ColumnInfoRecord;
//import org.apache.poi.hssf.record.RowRecord;

import org.apache.poi.hssf.model.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout rgpd;
    private LinearLayout csv;
    private LinearLayout delete_all;
    private PasswordService passwordService;
    private FolderService folderService;
    private List<PasswordModel> listePasswords = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        passwordService = new PasswordService(this);
        folderService = new FolderService(this);
        listePasswords = passwordService.getListPasswords();

        rgpd = findViewById(R.id.rgpd);
        csv = findViewById(R.id.csv);
        delete_all = findViewById(R.id.delete_all);

        rgpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_rgpd);
                dialog.show();
            }
        });

        if(passwordService.getListPasswords().size() == 0 && folderService.getFolders().size() == 0){
            delete_all.setVisibility(View.GONE);
        }
        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setMessage(R.string.dialog_reset);
                builder.setPositiveButton(R.string.reset_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteAllPasswords();
                        finish();
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        intent.putExtra("connexion", true);
                        startActivityForResult(intent, 0);
                    }
                });

                builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });




        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isReadStoragePermissionGranted();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return false;
    }

    public void DeleteAllPasswords(){
        List<PasswordModel> psws = passwordService.getListPasswords();
        List<FolderModel> folders = folderService.getFolders();

        for(int i = 0 ; i < psws.size(); i++){
            passwordService.DeletePassword(psws.get(i).getId());
        }

        for(int i=0; i < folders.size(); i++){
            folderService.DeleteFolder(folders.get(i).getNom());
        }
    }


    public  void isReadStoragePermissionGranted() {

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            DowloadFile();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                DowloadFile();
            }else{
                Toast.makeText(getBaseContext(), "L'application doit avoir accès au fichiers sur votre appareil pour la sauvegarde.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void DowloadFile(){

        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Users");
//        Row row = sheet.createRow(0);
//        row.createCell(0).setCellValue("tTest");


//        Workbook workbook = new Workbook();
//        Sheet sheet = new Sheet();
//
//        RowRecord row = sheet.createRow(0);
       // ColumnInfoRecord col = row.
        CreateXLSFile fileXls = new CreateXLSFile();
        String filename = "TurnKeyFile.xls";
        //String fileContents = fileXls.createContentFile(listePasswords);
        File folder = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "AppKeyDownload");
        if(!folder.exists()){
            folder.mkdirs();
        }

        File mfile = new File(folder, filename);
        try {

            FileOutputStream fos =  getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
            //workbook.write(fos);
            //fos.write(fileContents.getBytes());
            if(fos != null){
                fos.close();
            }

            if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) &&
                    !Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){

                mfile.createNewFile();
                fos = new FileOutputStream(mfile);
                //fos.write(fileContents.getBytes());
                //workbook.write(fos);
                if(fos != null){
                    fos.close();
                }
            }
            Toast.makeText(getBaseContext(), "Le fichier a été sauvegarder dans vos téléchargements.", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
