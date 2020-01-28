package com.simarro.pmm_proyecto_arnau.ui.novedades;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lista de juegos vacia.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}