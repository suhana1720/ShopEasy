package com.example.shopeasy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shopeasy.data.DatabaseHandler;
import com.example.shopeasy.model.Content;
import com.example.shopeasy.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Content> contentList;
    private DatabaseHandler dataBaseHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText Item;
    private EditText itemQuantity;
    private EditText itemPrice;
    private EditText itemBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        recyclerView.setHasFixedSize(true);// make sure everything is correc in term of size nd manging layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//lm knows all the needed things (for efficient ways)
        contentList = new ArrayList<>();
        // instantiate
        // get item from db
        dataBaseHandler = new DatabaseHandler(this);
        List<Content> contactList = dataBaseHandler.getAllItems();
        for (Content content : contactList) {
            Log.d("MainActivity", "onCreate:" + content.getItem());
            contentList.add(content);
        }
        recyclerViewAdapter = new RecyclerViewAdapter(this, contentList);
        recyclerView.setAdapter(recyclerViewAdapter);
        // for updating recycle view itself so we use notify to notify itself whenver data is changed
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });
    }

    private void createPopDialog() {
        // refract code instead of copy and paste
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        Item = view.findViewById(R.id.grocery_item);
        itemQuantity = view.findViewById(R.id.enter_quantity);
        itemBrand = view.findViewById(R.id.enter_brandname);
        itemPrice = view.findViewById(R.id.enter_price);
        // savebutton is not related to main activity thts why we use here in view
        saveButton = view.findViewById(R.id.button);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Item.getText().toString().isEmpty()
                        && !itemBrand.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()
                        && !itemPrice.getText().toString().isEmpty()) {
                    saveItem(v);
                } else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }

        });
    }

    private void saveItem(View v) {
        //ToDo: save each item to db
        Content content= new Content();
        String newItem= Item.getText().toString().trim();
        String newBrand= itemBrand.getText().toString().trim();
        int newPrice= Integer.parseInt(itemPrice.getText().toString().trim());
        String newQuantity= itemQuantity.getText().toString().trim(); // date added automatcally
        // set to the content variable and in recycler we get content
        content.setItem(newItem);
        content.setItemQuantity(newQuantity);
        content.setItemBrand(newBrand);
        content.setItemPrice(newPrice);
        dataBaseHandler.addItem(content); // 1 set of data
        // snackbar same as toast just a advance version
        Snackbar.make(v, "Iem Saved", Snackbar.LENGTH_SHORT)
                .show();

        //wait for few sec then remove popup log as well as start new activity called lisiview activity
        // using postdelay mth frm handler class
        // whenever u want to delay something then use a handler obj nd invoke post delay in which pass runable interface
        new Handler().postDelayed(new Runnable() {// passng interface
            @Override
            public void run() {
                alertDialog.dismiss();
                //ToDo: move to next screen- details screen
                startActivity(new Intent(ListActivity.this,ListActivity.class));
                finish();// to kill previous activity


            }
        },1200); // 1 sec


    }
    }

