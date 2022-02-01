package com.moworks.eqinfo.report;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.moworks.eqinfo.data.EqRepository;
import com.moworks.eqinfo.data.source.local.EqEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReportViewModel  extends AndroidViewModel {


    private EqRepository repository ;

    private  LiveData<List<EqEntity>> report ;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();


    public ReportViewModel(Application application , @NotNull EqRepository repo) {
        super(application);
        this.repository = repo;
        repo.refreshData();
        report = repository.getReport();
        isLoading.setValue(false);
    }


    public LiveData<List<EqEntity>> getReport() {
        return report;
    }

    public Boolean getIsLoading() {
        return isLoading.getValue();
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        Application application;
        EqRepository repository;

        public Factory(Application application, EqRepository repository) {
            this.application = application;
            this.repository = repository;
        }

        @Override
        public <T extends ViewModel> @NotNull T create(@NotNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ReportViewModel.class)){
                return (T) new ReportViewModel(application , repository);
            }
            throw new IllegalArgumentException("Unknwon viewModel");
        }
    }

}
