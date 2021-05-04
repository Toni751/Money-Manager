package dk.tonigr.moneymanager.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;

import dk.tonigr.moneymanager.models.UserLiveData;

public class UserRepository {
    private static UserRepository instance;
    private final Application application;
    private UserLiveData currentUser;

    private UserRepository(Application application) {
        this.application = application;
        currentUser = new UserLiveData();
    }

    public static UserRepository getInstance(Application application) {
        if(instance == null) {
            instance = new UserRepository(application);
        }

        return instance;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    public void signOut() {
        AuthUI.getInstance().signOut(application.getApplicationContext());
    }
}
