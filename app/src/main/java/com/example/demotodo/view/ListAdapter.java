package com.example.demotodo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.demotodo.MainActivity;
import com.example.demotodo.R;
import com.example.demotodo.model.ItemVO;

import java.util.List;

public class ListAdapter extends ArrayAdapter<ItemVO> {

    public ListAdapter(Context context, List<ItemVO> list){
        super (context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemVO item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }


        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        Button deleteButton = (Button) convertView.findViewById(R.id.deleteBtn);

        MainActivity activity = (MainActivity) getContext();

        checkBox.setOnClickListener(activity);
        deleteButton.setOnClickListener(activity);

        checkBox.setText(item.title);
        checkBox.setChecked(item.isDone);

        return convertView;

    }


}
