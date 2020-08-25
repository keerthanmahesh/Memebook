package com.example.memegenerator;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memegenerator.APIResponse.Data;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Data> dataList;

    public RecyclerViewAdapter(List<Data> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data data = dataList.get(position);
        holder.textView.setText(dataList.get(position).getName());

        new LoadImage(holder.imageView).execute(dataList.get(position).getImageURL().toString());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MemesEditActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, data.getImageURL().toString());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public Button button;
        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.textView = (TextView) view.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);
            this.button = (Button) view.findViewById(R.id.explore_button);
        }
    }
}
