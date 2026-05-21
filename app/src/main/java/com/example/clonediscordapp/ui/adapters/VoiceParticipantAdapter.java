package com.example.clonediscordapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clonediscordapp.R;
import com.example.clonediscordapp.data.model.Participant;
import com.example.clonediscordapp.databinding.ItemVoiceParticipantBinding;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtc2.Constants;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.video.VideoCanvas;

public class VoiceParticipantAdapter extends RecyclerView.Adapter<VoiceParticipantAdapter.ViewHolder> {

    private List<Participant> participants = new ArrayList<>();
    private RtcEngine rtcEngine;

    public void submitList(List<Participant> list) {
        this.participants = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setRtcEngine(RtcEngine rtcEngine) {
        this.rtcEngine = rtcEngine;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVoiceParticipantBinding binding = ItemVoiceParticipantBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(participants.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            String payload = payloads.get(0).toString();
            Participant participant = participants.get(position);

            if (payload.equals("border_update")) {
                holder.updateSpeakingBorder(participant.isSpeaking);
                return;
            } else if (payload.equals("state_update")) {
                holder.updateState(participant);
                return;
            }
        }
        super.onBindViewHolder(holder, position, payloads);
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

        public void bind(Participant participant) {
            Context context = itemView.getContext();
            binding.tvName.setText(participant.name);

            // Clean old views first to prevent overlapping layers
            binding.videoContainer.removeAllViews();

            // Set up video rendering
            if (rtcEngine != null && !participant.isVideoOff) {
                SurfaceView surfaceView = new SurfaceView(context);
                binding.videoContainer.addView(surfaceView);

                if (participant.name.equals("Màn hình của tôi")) {
                    VideoCanvas canvas = new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0);
                    canvas.sourceType = Constants.VIDEO_SOURCE_SCREEN_PRIMARY;
                    rtcEngine.setupLocalVideo(canvas);
                    rtcEngine.startPreview(Constants.VideoSourceType.VIDEO_SOURCE_SCREEN_PRIMARY);
                } else if (participant.name.contains("Me")) {
                    surfaceView.setZOrderMediaOverlay(true);
                    rtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0));
                } else {
                    surfaceView.setZOrderMediaOverlay(true);
                    int renderMode = (participant.uid >= 1000) ? VideoCanvas.RENDER_MODE_FIT : VideoCanvas.RENDER_MODE_HIDDEN;
                    rtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, renderMode, participant.uid));
                }
            }

            updateState(participant);
            updateSpeakingBorder(participant.isSpeaking);
        }

        public void updateState(Participant participant) {
            binding.videoContainer.setVisibility(participant.isVideoOff ? View.GONE : View.VISIBLE);
            binding.avatarContainer.setVisibility(participant.isVideoOff ? View.VISIBLE : View.GONE);
            binding.flMuteIndicator.setVisibility(participant.isMuted ? View.VISIBLE : View.GONE);
            binding.flVideoIndicator.setVisibility(!participant.isVideoOff ? View.VISIBLE : View.GONE);
            binding.flScreenIndicator.setVisibility(participant.isSharingScreen ? View.VISIBLE : View.GONE);
        }

        public void updateSpeakingBorder(boolean isSpeaking) {
            if (isSpeaking) {
                binding.cardParticipant.setBackgroundResource(R.drawable.bg_voice_participant_speaking);
                binding.vSpeakingDot.setVisibility(View.VISIBLE);
            } else {
                binding.cardParticipant.setBackgroundResource(R.drawable.bg_voice_participant);
                binding.vSpeakingDot.setVisibility(View.GONE);
            }
        }
    }
}
