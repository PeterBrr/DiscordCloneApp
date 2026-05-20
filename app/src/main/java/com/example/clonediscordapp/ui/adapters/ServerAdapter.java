package com.example.clonediscordapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clonediscordapp.data.model.Server;
import com.example.clonediscordapp.databinding.ItemServerBinding;

import java.util.ArrayList;
import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> {

    private List<Server> servers = new ArrayList<>();
    private final OnServerClickListener listener;
    private String activeServerId = null;

    public interface OnServerClickListener {
        void onServerClick(Server server);
    }

    public ServerAdapter(OnServerClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Server> list) {
        this.servers = list;
        notifyDataSetChanged();
    }

    public void setActiveServerId(String id) {
        this.activeServerId = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemServerBinding binding = ItemServerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(servers.get(position));
    }

    @Override
    public int getItemCount() {
        return servers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemServerBinding binding;

        public ViewHolder(ItemServerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Server server) {
            Glide.with(itemView.getContext())
                    .load(server.getImageUrl())
                    .circleCrop()
                    .into(binding.ivServerIcon);

            if (server.getId().equals(activeServerId)) {
                binding.vActiveIndicator.setVisibility(View.VISIBLE);
            } else {
                binding.vActiveIndicator.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onServerClick(server);
                }
            });
        }
    }
}
