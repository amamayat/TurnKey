package com.mobilap.turnKey.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobilap.turnKey.R;
import com.mobilap.turnKey.form.FormActivity;
import com.mobilap.turnKey.models.FolderModel;
import com.mobilap.turnKey.models.PasswordModel;
import com.mobilap.turnKey.services.FolderService;
import com.mobilap.turnKey.services.PasswordService;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private Button test;
    private List<PasswordModel> listePasswords = new ArrayList<>();
    private ListView listView_passwords;
    private String folder_id;
    private List<FolderModel> listFolderModels;
    private FolderService foldersService;
    private PasswordService passwordService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        listView_passwords = (ListView) root.findViewById(R.id.listView_passwords);

        ItemPasswordAdapter itemPasswordAdapter = null;
        if(getTag()!= null){
            folder_id = getTag();
        }else{
            folder_id = "Liste";
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity().getApplication(), FormActivity.class);
                intent.putExtra("menuItem", folder_id);
                startActivity(intent);
            }
        });
        //ListPasswords
        passwordService = new PasswordService(getContext());
        listePasswords = passwordService.getListPasswords();

        List<PasswordModel> listForAdapter = new ArrayList<>();
        for(int i=0; i< listePasswords.size(); i++){
            String f = listePasswords.get(i).getFolder();
            if(listePasswords.get(i).getFolder().equals(folder_id)){
                //int image = Integer.valueOf(listePasswords.get(i).getImage());

                listForAdapter.add(listePasswords.get(i));
            }
        }

        if(listForAdapter.size() > 0){

            itemPasswordAdapter = new ItemPasswordAdapter(getActivity().getApplicationContext(), listForAdapter);
            listView_passwords.setAdapter(itemPasswordAdapter);
        }

        return root;
    }

}
