package com.example.clonediscordapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clonediscordapp.data.model.User;
import com.example.clonediscordapp.databinding.ItemVoiceParticipantBinding;

import java.util.ArrayList;
import java.util.List;

public class VoiceParticipantAdapter extends RecyclerView.Adapter<VoiceParticipantAdapter.ViewHolder> {

    private List<User> participants = new ArrayList<>();

    public void submitList(List<User> list) {
        this.participants = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVoiceParticipantBinding binding = ItemVoiceParticipantBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(participants.get(position));
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemVoiceParticipantBinding binding;

        public ViewHolder(ItemVoiceParticipantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(User user) {
            Glide.with(itemView.getContext())
                    .load(user.getAvatarUrl())
                    .circleCrop()
                    .into(binding.ivAvatar);

            binding.tvName.setText(user.getName());
            
            // In a real app we'd have a VoiceParticipant class with isMuted and isSpeaking,
            // but for this UI clone we can mock it
            binding.flMuteIndicator.setVisibility(user.isOnline() ? View.GONE : View.VISIBLE);
        }
    }
}
