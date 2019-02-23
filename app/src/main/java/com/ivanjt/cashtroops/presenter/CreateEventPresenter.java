
package com.ivanjt.cashtroops.presenter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivanjt.cashtroops.model.Event;
import com.ivanjt.cashtroops.view.CreateEventView;

public class CreateEventPresenter {
    private CreateEventView view;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public CreateEventPresenter(CreateEventView view) {
        this.view = view;
    }

    public void createEvent(Event event) {
        view.showProgressBar();
        final DatabaseReference mEventRef = mDatabase.child(Event.PATH_NAME);
        mEventRef.child(event.getId()).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                view.hideProgressBar();
            }
        });
    }
}