package com.example.shopeasy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopeasy.data.DatabaseHandler;
import com.example.shopeasy.model.Content;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity<view> extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText item;
    private EditText itemQuantity;
    private EditText itemPrice;
    private EditText itemBrand;
    private DatabaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // means activity build
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataBaseHandler= new DatabaseHandler(this);
        byPassActivity(); // bypass the 1st activity after knowing that we alreday hve items in our db and there we can create a new item
        // check if item was saved
        List<Content> contentList = dataBaseHandler.getAllItems();
        for (Content content: contentList) {
            Log.d("Main", "onCreate: " + content.getItemBrand());
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
            }
        });
    }

    private void byPassActivity(){
        if(dataBaseHandler.getItemsCount()>0){
            Intent launch=new Intent(MainActivity.this, com.example.shopeasy.ListActivity.class);
            startActivity(launch);
            finish(); // use whenever want to get rid from stack of activity( remove main activity and present on listactivity)
        }
    }

    private void saveItem(View view) {
        //ToDo: save each item to db
        Content content= new Content();
        String newItem= item.getText().toString().trim();
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
        Snackbar.make(view, "Item Saved", Snackbar.LENGTH_SHORT)
        .show();

        //wait for few sec then remove popup log as well as start new activity called lisiview activity
        // using postdelay mth frm handler class
        // whenever u want to delay something then use a handler obj nd invoke post delay in which pass runable interface
        new Handler().postDelayed(new Runnable() {// passng interface
            @Override
            public void run() {
                dialog.dismiss();
                //ToDo: move to next screen- details screen
                Intent launchscr=new Intent(MainActivity.this, com.example.shopeasy.ListActivity.class);
                startActivity(launchscr);


            }
        },1200); // 1 sec


    }

    private void createPopupDialog() {
        //instantiate
        builder=new AlertDialog.Builder(this);
        // for getting popup xml it neede to be inflated
        View view;
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // in this view obj we have popup xml
        view=inflater.inflate(R.layout.popup,null);
      //  setContentView(view);
        item =view.findViewById(R.id.grocery_item);
        itemQuantity=view.findViewById(R.id.enter_quantity);
        itemPrice =view.findViewById(R.id.enter_price);
        itemBrand =view.findViewById(R.id.enter_brandname);
        // savebutton is not related to main activity thts why we use here in view

        saveButton = view.findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(item.getText().toString())
                        && !TextUtils.isEmpty(itemPrice.getText().toString())
                        && !TextUtils.isEmpty(itemQuantity.getText().toString())
                        && !TextUtils.isEmpty(itemBrand.getText().toString())) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                       .show();
                }

        }
        });


    // now to show in dialog we set this xml
        builder.setView(view);
        dialog=builder.create(); // crating our dilog obj
        dialog.show(); // imp step!!


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}


