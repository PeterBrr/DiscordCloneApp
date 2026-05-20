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

    private final MutableLiveData<Boolean> _isDeafened = new MutableLiveData<>(false);
    public LiveData<Boolean> isDeafened() { return _isDeafened; }

    private final MutableLiveData<Boolean> _isVideoOn = new MutableLiveData<>(false);
    public LiveData<Boolean> isVideoOn() { return _isVideoOn; }

    private final MutableLiveData<Boolean> _isSharingScreen = new MutableLiveData<>(false);
    public LiveData<Boolean> isSharingScreen() { return _isSharingScreen; }

    public void toggleMute() {
        if (_isMuted.getValue() != null) {
            boolean nextMuteState = !_isMuted.getValue();
            _isMuted.setValue(nextMuteState);
            // If we unmute but are deafened, we must also undeafen!
            if (!nextMuteState && Boolean.TRUE.equals(_isDeafened.getValue())) {
                _isDeafened.setValue(false);
            }
        }
    }

    public void toggleDeafen() {
        if (_isDeafened.getValue() != null) {
            boolean nextDeafenState = !_isDeafened.getValue();
            _isDeafened.setValue(nextDeafenState);
            // If deafened, automatically mute!
            if (nextDeafenState) {
                _isMuted.setValue(true);
            }
        }
    }

    public void toggleVideo() {
        if (_isVideoOn.getValue() != null) {
            _isVideoOn.setValue(!_isVideoOn.getValue());
        }
    }

    public void toggleScreenShare() {
        if (_isSharingScreen.getValue() != null) {
            _isSharingScreen.setValue(!_isSharingScreen.getValue());
        }
    }
}
