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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(VoiceViewModel.class);

        // Grid Adapter
        VoiceParticipantAdapter adapter = new VoiceParticipantAdapter();
        binding.rvVoiceParticipants.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvVoiceParticipants.setAdapter(adapter);

        // Observe Data
        viewModel.getParticipants().observe(getViewLifecycleOwner(), adapter::submitList);
        
        viewModel.isMuted().observe(getViewLifecycleOwner(), isMuted -> {
            if (isMuted) {
                binding.ivMuteIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.DKGRAY));
                binding.btnMute.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_elevated); // Mocking white bg
            } else {
                binding.ivMuteIcon.setImageTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
                binding.btnMute.setBackgroundResource(com.example.clonediscordapp.R.drawable.bg_circle_dark);
            }
        });

        // Click Listeners
        binding.btnMute.setOnClickListener(v -> viewModel.toggleMute());
        
        binding.btnMinimize.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
        
        binding.btnEndCall.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
