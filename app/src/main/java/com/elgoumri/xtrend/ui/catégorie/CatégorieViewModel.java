package com.elgoumri.xtrend.ui.catégorie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatégorieViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CatégorieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}