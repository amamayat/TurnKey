package com.mobilap.turnKey;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mobilap.turnKey.models.FolderModel;
import com.mobilap.turnKey.services.FolderService;
import com.mobilap.turnKey.ui.home.ListFragment;
import com.mobilap.turnKey.ui.labels.LabelsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static boolean connexion = false;
    private List<FolderModel> listFolderModels;
    private String folder;
    private Toolbar toolbar;
    private FolderService foldersService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent =  getIntent();
        connexion = intent.getBooleanExtra("connexion", false);
        if(connexion== false){

            intent = new Intent(this, ConnexionActivity.class);
            startActivityForResult(intent, 0);
        }
        folder =  intent.getStringExtra("folder");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);


        final Menu menu = navigationView.getMenu();



        //Add folders to menu
        foldersService = new FolderService(this);
        listFolderModels = foldersService.getFolders();


        if(listFolderModels.size() > 0){
            for(int i = 0; i< listFolderModels.size(); i++){
                menu.add(R.id.labels, listFolderModels.get(i).getId(), Menu.NONE, listFolderModels.get(i).getNom()).setIcon(R.drawable.ic_label_black_24dp);
            }
        }
        if(folder == null){
            toolbar.setTitle(R.string.menu_home);
            menu.getItem(1).setChecked(true);
        }else{
            toolbar.setTitle(folder);
            setChecked(folder, menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                navigationMenu(menuItem, navigationView, menu);
                drawer.closeDrawers();
                return true;
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(folder != "Liste"){
            setDetachFragment(fragmentManager, fragmentTransaction);
        }
        fragmentTransaction.replace(R.id.nav_host_fragment,new ListFragment(), folder);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
    public void setMenuITemChecked(Menu menu){
        for(int i = 0; i< listFolderModels.size(); i++){

            for(int x = 0 ; x < menu.size(); x++){

                menu.getItem(x).setChecked(false);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setChecked(String name_label, Menu menu){

        for(int i = 0; i< listFolderModels.size(); i++){
            for(int x = 0 ; x < menu.size(); x++){
                if(menu.getItem(x).getTitle().equals(name_label)){
                    menu.getItem(x).setChecked(true);
                }
            }
        }
    }

    private void navigationMenu(MenuItem menuItem, NavigationView navigationView, Menu menu){
        folder = menuItem.getTitle().toString();
        menuItem.getItemId();
        listFolderModels = foldersService.getFolders();
        setMenuITemChecked(menu);
        menuItem.setChecked(true);

        toolbar.setTitle(folder);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        menuItem.setTitle(menuItem.getTitle().toString());
        Boolean isFolder = false;
        Fragment fragment = null;

        if(folder.contentEquals("Liste")){
            fragment = new ListFragment();
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        }else {

            setDetachFragment(fragmentManager, fragmentTransaction);

            for (int i = 0; i < listFolderModels.size(); i++) {
                if (listFolderModels.get(i).getNom().contentEquals(folder)) {
                    isFolder = true;
                    fragment = new ListFragment();

                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment, String.valueOf(listFolderModels.get(i).getNom()));

                }
            }
            if (!isFolder) {


                fragment = new LabelsFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            }
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }
    public void setDetachFragment(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction){
        List<Fragment> fra = fragmentManager.getFragments();

        if (fra.size() > 0) {
            fragmentTransaction.detach(fra.get(0));
        }
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.close_app);
        builder.setPositiveButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finishAffinity();
                System.exit(0);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.action_closeApp:
                finishAffinity();
                System.exit(0);
                return true;
        }
        return false;
    }
}
