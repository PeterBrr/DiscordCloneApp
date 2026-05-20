package com.example.clonediscordapp.ui.server;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clonediscordapp.databinding.FragmentChannelSettingsBinding;

public class ChannelSettingsFragment extends Fragment {

    private FragmentChannelSettingsBinding binding;
    private String selectedSlowmode = "Off";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChannelSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Back Arrow - returns to channel chat
        binding.btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        // 2. Save Button - commits changes and returns
        binding.btnSave.setOnClickListener(v -> {
            String channelName = binding.etChannelName.getText().toString().trim();
            String channelTopic = binding.etChannelTopic.getText().toString().trim();

            if (channelName.isEmpty()) {
                Toast.makeText(requireContext(), "Channel name cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(requireContext(), "Channel settings saved successfully:\n" 
                    + "Name: \"" + channelName + "\"\n"
                    + "Slowmode: " + selectedSlowmode + " 💾✨", Toast.LENGTH_LONG).show();

            Navigation.findNavController(v).navigateUp();
        });

        // 3. Switch toggles responsive toast alerts
        binding.switchPrivate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(requireContext(), "Channel locked. Only administrators and white-listed roles can view. 🔒", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Channel visibility restored to Public. 🔓", Toast.LENGTH_SHORT).show();
            }
        });

        binding.switchNsfw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(requireContext(), "Age-restricted NSFW mode enabled. Users must verify 18+ age! 🔞", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "NSFW age-restrictions disabled.", Toast.LENGTH_SHORT).show();
            }
        });

        // 4. Slowmode Chips interactive click simulation
        binding.cgSlowmode.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == com.example.clonediscordapp.R.id.chip_slowmode_off) {
                selectedSlowmode = "Off";
            } else if (checkedId == com.example.clonediscordapp.R.id.chip_slowmode_5s) {
                selectedSlowmode = "5 Seconds";
            } else if (checkedId == com.example.clonediscordapp.R.id.chip_slowmode_10s) {
                selectedSlowmode = "10 Seconds";
            } else if (checkedId == com.example.clonediscordapp.R.id.chip_slowmode_30s) {
                selectedSlowmode = "30 Seconds";
            } else if (checkedId == com.example.clonediscordapp.R.id.chip_slowmode_1m) {
                selectedSlowmode = "1 Minute";
            } else {
                selectedSlowmode = "Off";
            }
            Toast.makeText(requireContext(), "Slowmode cooldown updated: " + selectedSlowmode + " ⏳", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
