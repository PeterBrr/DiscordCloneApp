package com.example.clonediscordapp.ui.auth;

import android.app.DatePickerDialog;
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
import com.example.clonediscordapp.databinding.FragmentRegisterBinding;

import java.util.Calendar;
import java.util.Locale;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private boolean isPasswordVisible = false;
    private String selectedDob = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Password Visibility Toggle
        binding.btnRegPasswordToggle.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                binding.etRegPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.btnRegPasswordToggle.setColorFilter(getResources().getColor(R.color.discord_blurple));
            } else {
                binding.etRegPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.btnRegPasswordToggle.setColorFilter(getResources().getColor(R.color.discord_text_muted));
            }
            binding.etRegPassword.setSelection(binding.etRegPassword.getText().length());
        });

        // 2. Redirect to Login Fragment
        binding.btnGotoLogin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_register_to_login);
        });

        // 3. Interactive Native Date of Birth Picker Dialog
        binding.btnDobSelect.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR) - 18; // Default to 18 years ago
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog pickerDialog = new DatePickerDialog(requireContext(),
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        Calendar dobCal = Calendar.getInstance();
                        dobCal.set(selectedYear, selectedMonth, selectedDay);
                        
                        // Format: "Month dd, yyyy"
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM dd, yyyy", Locale.US);
                        selectedDob = sdf.format(dobCal.getTime());
                        
                        binding.tvDobValue.setText(selectedDob);
                        binding.tvDobValue.setTextColor(getResources().getColor(R.color.discord_text_primary));
                        Toast.makeText(requireContext(), "Age verified successfully! 🎂", Toast.LENGTH_SHORT).show();
                    }, year, month, day);

            pickerDialog.show();
        });

        // 4. Continue/Register Button with validations and loader latency simulator
        binding.btnRegister.setOnClickListener(v -> {
            String email = binding.etRegEmail.getText().toString().trim();
            String username = binding.etRegUsername.getText().toString().trim();
            String password = binding.etRegPassword.getText().toString().trim();
            boolean agreed = binding.cbTerms.isChecked();

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Email cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (username.isEmpty()) {
                Toast.makeText(requireContext(), "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(requireContext(), "Password cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedDob.isEmpty()) {
                Toast.makeText(requireContext(), "Please select your date of birth!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!agreed) {
                Toast.makeText(requireContext(), "You must agree to the Terms of Service & Privacy Policy!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Enable loading state
            binding.btnRegister.setEnabled(false);
            binding.btnRegister.setText(""); // Hide text
            binding.pbRegLoading.setVisibility(View.VISIBLE);

            // Simulate server network registration processing delay
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (getContext() == null) return;

                // Success Feedback
                Toast.makeText(requireContext(), "Account created successfully! Welcome, " + username + "! 🎉🎮", Toast.LENGTH_LONG).show();

                // Navigate directly to Home Fragment (DMs list)
                Navigation.findNavController(view).navigate(R.id.action_register_to_home);
            }, 1500);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
