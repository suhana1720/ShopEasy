package com.example.shopeasy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopeasy.R;
import com.example.shopeasy.model.Content;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Content> contentList;

    public RecyclerViewAdapter(Context context, List<Content> contentList) {
        this.context = context;
        this.contentList = contentList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // here we create a view (connecting xml)
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);

        return new ViewHolder(view, context); // creating vh obj
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int position) {
        Content content = contentList.get(position); // content obj
        viewholder.item.setText(MessageFormat.format("Item:{0}", content.getItem()));
        viewholder.itemBrand.setText(MessageFormat.format("Brand:{0}", content.getItemBrand()));
        viewholder.itemPrice.setText(MessageFormat.format("Price:{0}", content.getItemPrice()));
        viewholder.itemQuantity.setText(MessageFormat.format("Quantity:{0}", content.getItemQuantity()));
        viewholder.dateAdded.setText(MessageFormat.format("Added on:{0}", content.getDateItemAdded()));

    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {// here we adding xml
        public TextView item;
        public TextView itemQuantity;
        public TextView itemBrand;
        public TextView itemPrice;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public int id; // handy when want to pass these items to next activity

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            item = itemView.findViewById(R.id.item); // make sure id is from list row xml
            itemQuantity = itemView.findViewById(R.id.enterquantity);
            itemBrand = itemView.findViewById(R.id.enter_Brand);
            itemPrice = itemView.findViewById(R.id.enter_price);
            dateAdded = itemView.findViewById(R.id.itemdate);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position;
            position=getAdapterPosition();
            Content content=contentList.get(position);
            switch (v.getId()) {
                case R.id.editButton:
                    // edit item
                    break;
                //  case  R.id.deleteButton:
                // delete item
                //  deleteContact(id);
                //   break;
            }
        }

    }
}
