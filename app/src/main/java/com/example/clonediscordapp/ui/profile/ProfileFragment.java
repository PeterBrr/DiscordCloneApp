package com.example.clonediscordapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.clonediscordapp.data.MockData;
import com.example.clonediscordapp.data.model.User;
import com.example.clonediscordapp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load mock user ME
        User me = MockData.ME;

        binding.tvDisplayName.setText(me.getName());
        binding.tvUsername.setText(me.getUsername());
        binding.tvStatusMessage.setText(me.getAboutMe());

        // Load premium avatar and banner using Glide
        Glide.with(this)
                .load(me.getAvatarUrl())
                .circleCrop()
                .into(binding.ivAvatar);

        Glide.with(this)
                .load(MockData.IMG_BANNER)
                .centerCrop()
                .into(binding.ivBanner);

        // Active online status
        if (me.isOnline()) {
            binding.vOnlineStatus.setVisibility(View.VISIBLE);
        } else {
            binding.vOnlineStatus.setVisibility(View.GONE);
        }

        // Setting items click listeners for interactive feel
        binding.btnBuyNitro.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Redirecting to Discord Nitro subscription page...", Toast.LENGTH_SHORT).show()
        );

        binding.btnSettingAccount.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Account Settings opened", Toast.LENGTH_SHORT).show()
        );

        binding.btnSettingProfile.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "User Profile Settings opened", Toast.LENGTH_SHORT).show()
        );

        binding.btnSettingVoice.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Voice & Audio Settings opened", Toast.LENGTH_SHORT).show()
        );

        binding.btnSettingStyling.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Appearance / Styling Settings opened", Toast.LENGTH_SHORT).show()
        );

        binding.btnSettingSupport.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Support Helpdesk opened", Toast.LENGTH_SHORT).show()
        );

        binding.btnLogout.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_LONG).show()
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
