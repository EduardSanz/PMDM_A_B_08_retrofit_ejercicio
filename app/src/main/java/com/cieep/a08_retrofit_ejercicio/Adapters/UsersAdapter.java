package com.cieep.a08_retrofit_ejercicio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cieep.a08_retrofit_ejercicio.R;
import com.cieep.a08_retrofit_ejercicio.modelos.DataItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserVH> {

    private List<DataItem> objects;
    private int resource;
    private Context context;

    public UsersAdapter(List<DataItem> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(context).inflate(resource, null);
        userView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new UserVH(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        DataItem data = objects.get(position);
        holder.lblEmail.setText(data.getEmail());
        holder.lblNombre.setText(data.getFirstName());
        holder.lblApellidos.setText(data.getLastName());

        Picasso.get()
                .load(data.getAvatar())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class UserVH extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView lblNombre;
        TextView lblApellidos;
        TextView lblEmail;

        public UserVH(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhotoUserCardView);
            lblNombre = itemView.findViewById(R.id.lblNombreUserCardView);
            lblApellidos = itemView.findViewById(R.id.lblApellidosUserCardView);
            lblEmail = itemView.findViewById(R.id.lblEmailUserCardView);
        }
    }
}
