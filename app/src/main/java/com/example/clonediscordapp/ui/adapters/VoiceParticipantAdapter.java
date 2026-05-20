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
    private boolean isMeMuted = false;
    private boolean isMeVideoOn = false;
    private boolean isMeScreenOn = false;

    public void submitList(List<User> list) {
        this.participants = list;
        notifyDataSetChanged();
    }

    public void setMeMuted(boolean isMuted) {
        this.isMeMuted = isMuted;
        notifyDataSetChanged();
    }

    public void setMeVideoOn(boolean isVideoOn) {
        this.isMeVideoOn = isVideoOn;
        notifyDataSetChanged();
    }

    public void setMeScreenOn(boolean isScreenOn) {
        this.isMeScreenOn = isScreenOn;
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
            
            boolean isSpeaking = false;
            boolean isMuted = false;
            boolean isVideoActive = false;
            boolean isScreenSharing = false;

            if (user.getId().equals("u0")) { // "Me" user profile
                isMuted = isMeMuted;
                isVideoActive = isMeVideoOn;
                isScreenSharing = isMeScreenOn;
                isSpeaking = !isMeMuted; // Speaking if not muted
            } else if (user.getId().equals("u1")) { // Valkyrie
                isSpeaking = true; // Active speaker speaking ring
                isMuted = false;
                isVideoActive = false;
                isScreenSharing = false;
            } else if (user.getId().equals("u2")) { // Nova
                isSpeaking = false;
                isMuted = true;
                isVideoActive = true; // Video active badge
                isScreenSharing = false;
            } else { // Doggo
                isSpeaking = false;
                isMuted = false;
                isVideoActive = false;
                isScreenSharing = true; // Screen share active badge
            }

            // Speak border background outline and name tag dot change
            if (isSpeaking) {
                binding.cardParticipant.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_voice_participant_speaking);
                binding.vSpeakingDot.setVisibility(View.VISIBLE);
            } else {
                binding.cardParticipant.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_voice_participant);
                binding.vSpeakingDot.setVisibility(View.GONE);
            }

            // Bind status badges visibility
            binding.flMuteIndicator.setVisibility(isMuted ? View.VISIBLE : View.GONE);
            binding.flVideoIndicator.setVisibility(isVideoActive ? View.VISIBLE : View.GONE);
            binding.flScreenIndicator.setVisibility(isScreenSharing ? View.VISIBLE : View.GONE);
        }
    }
}
