package com.example.clonediscordapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.clonediscordapp.data.MockData;
import com.example.clonediscordapp.databinding.FragmentProfileSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UserProfileBottomSheet extends BottomSheetDialogFragment {

    private FragmentProfileSheetBinding binding;
    private ProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.tvDisplayName.setText(user.getName());
            binding.tvUsername.setText(user.getUsername());
            binding.tvAboutMe.setText(user.getAboutMe());

            Glide.with(this)
                    .load(user.getAvatarUrl())
                    .circleCrop()
                    .into(binding.ivAvatar);
                    
            Glide.with(this)
                    .load(MockData.IMG_BANNER)
                    .centerCrop()
                    .into(binding.ivBanner);

            if (user.isOnline()) {
                binding.vOnlineStatus.setVisibility(View.VISIBLE);
            } else {
                binding.vOnlineStatus.setVisibility(View.GONE);
            }
        });

        binding.btnSendMessage.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
