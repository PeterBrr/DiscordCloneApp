package com.example.clonediscordapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clonediscordapp.data.model.DirectMessage;
import com.example.clonediscordapp.databinding.ItemDirectMessageBinding;

import java.util.ArrayList;
import java.util.List;

public class DirectMessageAdapter extends RecyclerView.Adapter<DirectMessageAdapter.ViewHolder> {

    private List<DirectMessage> messages = new ArrayList<>();
    private final OnMessageClickListener listener;

    public interface OnMessageClickListener {
        void onMessageClick(DirectMessage message);
        void onAvatarClick(DirectMessage message);
    }

    public DirectMessageAdapter(OnMessageClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<DirectMessage> list) {
        this.messages = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDirectMessageBinding binding = ItemDirectMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDirectMessageBinding binding;

        public ViewHolder(ItemDirectMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DirectMessage dm) {
            Glide.with(itemView.getContext())
                    .load(dm.getUser().getAvatarUrl())
                    .circleCrop()
                    .into(binding.ivAvatar);

            binding.tvName.setText(dm.getUser().getName());
            binding.tvMessage.setText(dm.getLastMessage());
            binding.tvTimestamp.setText(dm.getTimestamp());

            if (dm.getUser().isOnline()) {
                binding.vOnlineStatus.setVisibility(View.VISIBLE);
            } else {
                binding.vOnlineStatus.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onMessageClick(dm);
            });
            
            binding.ivAvatar.setOnClickListener(v -> {
                if (listener != null) listener.onAvatarClick(dm);
            });
        }
    }
}
