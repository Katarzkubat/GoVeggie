package com.example.katarzkubat.goveggie.viewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class DisplayFavoriteDetailFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private int rowId;

    public DisplayFavoriteDetailFactory(Application mApplication, int rowId) {
        this.mApplication = mApplication;
        this.rowId = rowId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FavoriteDetailViewModel(mApplication, rowId);
    }
}
