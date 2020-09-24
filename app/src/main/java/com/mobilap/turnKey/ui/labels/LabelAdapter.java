package com.mobilap.turnKey.ui.labels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mobilap.turnKey.models.PasswordModel;
import com.mobilap.turnKey.services.FolderService;
import com.mobilap.turnKey.services.PasswordService;
import com.mobilap.turnKey.R;
import java.util.Date;
import java.util.List;

public class LabelAdapter extends ArrayAdapter<LabelViewModel> {
    private List<LabelViewModel> listLabels;
    private FolderService foldersService;
    private PasswordService passwordService;

    public LabelAdapter(Context context, List<LabelViewModel> labels) {
        super(context, 0, labels);
    }

    public void setListLabels(List<LabelViewModel> listLabels){
        this.listLabels = listLabels;
    }
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item,parent, false);
        }

        LabelViewHolder viewHolder = (LabelViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new LabelViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.content);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageLabel);
            viewHolder.delete = (ImageButton) convertView.findViewById(R.id.delete);
            convertView.setTag(viewHolder);
        }


        foldersService = new FolderService(getContext());
        passwordService = new PasswordService(getContext());
        final LabelViewModel label = getItem(position);

        viewHolder.text.setText(label.getText());
        viewHolder.image.setImageResource(label.getImage());
        viewHolder.delete.setImageResource(label.getDelete());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), label.getText(),
                        Toast.LENGTH_LONG).show();
            }
        });
        View.OnClickListener onClickListenerDelete = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                String mess = getContext().getResources().getString(R.string.mess_deleteLabel);
                builder.setMessage(  mess + " " + label.getText());
                builder.setPositiveButton(R.string.ok_deleteLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listLabels.remove(label);
                        foldersService.DeleteFolder(label.getText());
                        removeFolderList(label.getText());
                        notifyDataSetChanged();
                        NavigationView navigationView =  parent.getRootView().findViewById(R.id.nav_view);
                        Menu menu = navigationView.getMenu();
                        int item = -1;
                        for(int m = 0 ; m<menu.size(); m++){
                            if(menu.getItem(m).getTitle().equals(label.getText())){
                                menu.removeItem(m);
                                item = menu.getItem(m).getItemId();

                            }
                        }
                        menu.removeItem(item);
                    }
                });
                builder.setNegativeButton(R.string.no_deleteLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }


        };

        viewHolder.delete.setOnClickListener(onClickListenerDelete);


        return convertView;
    }

    private class LabelViewHolder {
        public TextView text;
        public ImageView image;
        public ImageButton delete;
    }

    public void removeFolderList(String label){
        List<PasswordModel> list = passwordService.getListPasswords();
        for(int i = 0 ; i< list.size(); i++){
            if(list.get(i).getFolder().equals(label)){
                list.get(i).setDateModification(new Date());
                list.get(i).setFolder("Liste");
                long result = passwordService.UpdatePassword(list.get(i).getId(), list.get(i));
            }
        }
    }

}
