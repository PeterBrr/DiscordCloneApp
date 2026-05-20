package com.example.clonediscordapp.ui.voice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonediscordapp.data.MockData;
import com.example.clonediscordapp.data.model.User;

import java.util.Arrays;
import java.util.List;

public class VoiceViewModel extends ViewModel {

    private final MutableLiveData<List<User>> _participants = new MutableLiveData<>(
            Arrays.asList(MockData.ME, MockData.VALKYRIE, MockData.NOVA, MockData.DOGGO)
    );
    public LiveData<List<User>> getParticipants() { return _participants; }

    private final MutableLiveData<Boolean> _isMuted = new MutableLiveData<>(false);
    public LiveData<Boolean> isMuted() { return _isMuted; }

    public void toggleMute() {
        if (_isMuted.getValue() != null) {
            _isMuted.setValue(!_isMuted.getValue());
        }
    }
}
