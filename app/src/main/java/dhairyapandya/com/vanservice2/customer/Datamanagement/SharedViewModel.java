package dhairyapandya.com.vanservice2.customer.Datamanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<RegisterationData> registrationData = new MutableLiveData<>(new RegisterationData());

    // Getter for LiveData
    public LiveData<RegisterationData> getRegistrationData() {
        return registrationData;
    }

    // Method to update the data
    public void updateRegistrationData(RegisterationData data) {
        registrationData.setValue(data);
    }
}
