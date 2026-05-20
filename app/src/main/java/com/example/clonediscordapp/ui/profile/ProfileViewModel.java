package com.example.clonediscordapp.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonediscordapp.data.MockData;
import com.example.clonediscordapp.data.model.User;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<User> _user = new MutableLiveData<>(MockData.VALKYRIE);
    public LiveData<User> getUser() { return _user; }

    public void loadUser(String userId) {
        // In a real app we'd load by ID, for clone we just show Valkyrie
        _user.setValue(MockData.VALKYRIE);
    }
}
