package com.example.clonediscordapp.ui.voice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.clonediscordapp.databinding.FragmentVoiceCallBinding;
import com.example.clonediscordapp.ui.adapters.VoiceParticipantAdapter;

public class VoiceCallFragment extends Fragment {

    private FragmentVoiceCallBinding binding;
    private VoiceViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVoiceCallBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private boolean isSpeakerOn = true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(VoiceViewModel.class);

        // Grid Adapter configured for 2 items per row
        VoiceParticipantAdapter adapter = new VoiceParticipantAdapter();
        binding.rvVoiceParticipants.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvVoiceParticipants.setAdapter(adapter);

        // Observe Data
        viewModel.getParticipants().observe(getViewLifecycleOwner(), adapter::submitList);
        
        // Dynamic Microphone Mute state observer
        viewModel.isMuted().observe(getViewLifecycleOwner(), isMuted -> {
            adapter.setMeMuted(isMuted);
            if (isMuted) {
                binding.ivMuteIcon.setImageResource(com.example.clonediscordapp.R.drawable.ic_voice_mic_off);
                binding.ivMuteIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.RED));
                binding.btnMute.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_elevated); // White pill shape
                binding.btnMute.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
            } else {
                binding.ivMuteIcon.setImageResource(com.example.clonediscordapp.R.drawable.ic_voice_mic);
                binding.ivMuteIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
                binding.btnMute.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_dark);
                binding.btnMute.setBackgroundTintList(null); // reset standard gray container background
            }
        });

        // Dynamic Deafen state observer
        viewModel.isDeafened().observe(getViewLifecycleOwner(), isDeafened -> {
            if (isDeafened) {
                binding.ivDeafenIcon.setImageResource(com.example.clonediscordapp.R.drawable.ic_voice_deafen_off);
                binding.ivDeafenIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.RED));
                binding.btnDeafen.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_elevated);
                binding.btnDeafen.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
            } else {
                binding.ivDeafenIcon.setImageResource(com.example.clonediscordapp.R.drawable.ic_voice_deafen);
                binding.ivDeafenIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
                binding.btnDeafen.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_dark);
                binding.btnDeafen.setBackgroundTintList(null);
            }
        });

        // Dynamic Camera active state observer
        viewModel.isVideoOn().observe(getViewLifecycleOwner(), isVideoOn -> {
            adapter.setMeVideoOn(isVideoOn);
            if (isVideoOn) {
                binding.ivVideoIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
                binding.btnVideo.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_dark);
                binding.btnVideo.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(com.example.clonediscordapp.R.color.discord_blurple, null)));
                Toast.makeText(requireContext(), "Camera active. Broadcasting video...", Toast.LENGTH_SHORT).show();
            } else {
                binding.ivVideoIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
                binding.btnVideo.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_dark);
                binding.btnVideo.setBackgroundTintList(null);
            }
        });

        // Dynamic Screen Share state observer
        viewModel.isSharingScreen().observe(getViewLifecycleOwner(), isSharing -> {
            adapter.setMeScreenOn(isSharing);
            if (isSharing) {
                binding.ivScreenShareIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
                binding.btnScreenShare.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_dark);
                binding.btnScreenShare.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(com.example.clonediscordapp.R.color.discord_green, null)));
                Toast.makeText(requireContext(), "Screen sharing active. Streaming desktop...", Toast.LENGTH_SHORT).show();
            } else {
                binding.ivScreenShareIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
                binding.btnScreenShare.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_dark);
                binding.btnScreenShare.setBackgroundTintList(null);
            }
        });

        // Interactive Click listeners
        binding.btnMute.setOnClickListener(v -> viewModel.toggleMute());
        binding.btnDeafen.setOnClickListener(v -> viewModel.toggleDeafen());
        binding.btnVideo.setOnClickListener(v -> viewModel.toggleVideo());
        binding.btnScreenShare.setOnClickListener(v -> viewModel.toggleScreenShare());

        // Header Accessories Click Handlers
        binding.btnSpeakerToggle.setOnClickListener(v -> {
            isSpeakerOn = !isSpeakerOn;
            if (isSpeakerOn) {
                binding.btnSpeakerToggle.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
                Toast.makeText(requireContext(), "Switched audio output to speakerphone", Toast.LENGTH_SHORT).show();
            } else {
                binding.btnSpeakerToggle.setImageTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(com.example.clonediscordapp.R.color.discord_text_muted, null)));
                Toast.makeText(requireContext(), "Switched audio output to earpiece/headset", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnChatShortcut.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Opening voice call text channel chat room...", Toast.LENGTH_SHORT).show()
        );

        // Collapse / End voice session navigations
        binding.btnMinimize.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.btnEndCall.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Voice call disconnected", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigateUp();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
