package com.example.demotodo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.example.demotodo.model.ICallBackInterface;
import com.example.demotodo.model.ItemService;
import com.example.demotodo.model.ItemVO;
import com.example.demotodo.view.ListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ICallBackInterface, View.OnClickListener {

    private ItemService itemService;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        itemService = new ItemService(this);
        itemService.get();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showDialog() {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Pridėti naują įrašą")
                .setMessage("Ką norite dar nuveikti?")
                .setView(taskEditText)
                .setPositiveButton("Pridėti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        ItemVO itemVO = new ItemVO();
                        itemVO.title=task;
                        itemVO.isDone=false;
                        itemService.post(itemVO);

                    }
                })
                .setNegativeButton("Atšaukti", null)
                .create();
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(List<ItemVO> list) {
        adapter = new ListAdapter(this, list);
        ListView listView = (ListView) this.findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        ListView list = (ListView) v.getParent().getParent();

        int position = list.getPositionForView(v);

        ItemVO itemVO = adapter.getItem(position);

        switch (v.getId()) {
            case R.id.deleteBtn:
                itemService.delete(itemVO);
                break;
            case R.id.checkbox:
                itemVO.setDone(!itemVO.getDone());
                itemService.put(itemVO);
                break;
        }
    }
}
