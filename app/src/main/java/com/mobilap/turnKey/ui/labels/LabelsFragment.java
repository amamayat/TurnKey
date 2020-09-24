package com.mobilap.turnKey.ui.labels;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mobilap.turnKey.R;
import com.mobilap.turnKey.models.FolderModel;
import com.mobilap.turnKey.services.FolderService;

import java.util.ArrayList;
import java.util.List;

public class LabelsFragment extends Fragment {
    private EditText label_add;
    private ImageButton btn_add;
    private ListView list_label;
    private ArrayList<String> list;
    private List<LabelViewModel> labels;
    private AppBarConfiguration mAppBarConfiguration;
    private String label;
    private FolderService foldersService;
    private List<FolderModel> listFolders;
    LabelAdapter labelAdapter;



    public LabelsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_labels, container, false);

        list = new ArrayList<String>();
        label_add = (EditText)root.findViewById(R.id.label_add);
        btn_add = (ImageButton) root.findViewById(R.id.btn_add);
        list_label = (ListView)root.findViewById(R.id.label_list);
        btn_add.setVisibility(View.INVISIBLE);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setVisibility(container.INVISIBLE);
        label_add.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_add.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(label_add.getText().toString().length() > 0){
                    btn_add.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        labels = new ArrayList<LabelViewModel>();
        //Get Folders
        foldersService = new FolderService(getContext());
        listFolders = foldersService.getFolders();
        for(int i=0; i< listFolders.size(); i++){
            label = listFolders.get(i).getNom();
            labels.add(new LabelViewModel(label, R.drawable.ic_label_black_24dp, R.drawable.ic_delete_forever_black_24dp));
        }


        View.OnClickListener add = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                label = label_add.getText().toString();
                labels.add(new LabelViewModel(label, R.drawable.ic_label_black_24dp, R.drawable.ic_delete_forever_black_24dp));
                LabelAdapter labelAdapter = new LabelAdapter(getActivity().getApplicationContext(), labels);
                labelAdapter.setListLabels(labels);
                int id_newFolder = ((int) foldersService.insertFolder(label));

                list_label.setAdapter(labelAdapter);
                label_add.setText("");
                list.add(label);
                NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                Menu menu = navigationView.getMenu();
                menu.add(R.id.labels, id_newFolder, Menu.NONE, label).setIcon(R.drawable.ic_label_black_24dp);

            }
        };
        labelAdapter = new LabelAdapter(getActivity().getApplicationContext(), labels);
        labelAdapter.setListLabels(labels);
        list_label.setAdapter(labelAdapter);
        btn_add.setOnClickListener(add);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
}
