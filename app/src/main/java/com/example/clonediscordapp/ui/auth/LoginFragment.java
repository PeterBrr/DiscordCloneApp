package com.example.clonediscordapp.ui.auth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clonediscordapp.R;
import com.example.clonediscordapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private boolean isPasswordVisible = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Pre-fill credentials for instant testing convenience
        binding.etEmail.setText("guest@discord.com");
        binding.etPassword.setText("discord123");

        // 1. Password Visibility Toggle Eye Icon
        binding.btnPasswordToggle.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.btnPasswordToggle.setColorFilter(getResources().getColor(R.color.discord_blurple));
            } else {
                binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.btnPasswordToggle.setColorFilter(getResources().getColor(R.color.discord_text_muted));
            }
            binding.etPassword.setSelection(binding.etPassword.getText().length());
        });

        // 2. Redirect Navigation to Register Fragment
        binding.btnGotoRegister.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_login_to_register);
        });

        // 3. Forgot Password Toast simulator
        binding.btnForgotPassword.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter your email to request a reset link!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Simulated password reset email sent to \"" + email + "\"! ✉️🔑", Toast.LENGTH_LONG).show();
            }
        });

        // 4. Login Action Trigger with network mock progress bar simulator
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Email and password fields cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Enter loading state
            binding.btnLogin.setEnabled(false);
            binding.btnLogin.setText(""); // Hide text during progress
            binding.pbLoginLoading.setVisibility(View.VISIBLE);

            // Delay 1.5 seconds to simulate API latency
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (getContext() == null) return;

                // Toast success
                Toast.makeText(requireContext(), "Welcome back! Logged in as " + email + " 👾🎮", Toast.LENGTH_SHORT).show();

                // Navigate to Main DMs/Home Fragment
                Navigation.findNavController(view).navigate(R.id.action_login_to_home);
            }, 1500);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
