package com.generalcode;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Objects;

public class FirebaseActivity {

    private static final String TAG = "FirebaseActivity";

    private final FirebaseRemoteConfig mFirebaseRemoteConfig;
    LibFirebase libFirebase;
    private final Activity activity;

    public FirebaseActivity(LibFirebase libFirebase, Activity activity) {
        this.libFirebase = libFirebase;
        this.activity = activity;
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Set Remote Config Settings
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600) // Faster fetch for debug builds
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        // Fetch remote config with cache expiration
        fetchRemoteConfig();
    }

    private void fetchRemoteConfig() {
        mFirebaseRemoteConfig.fetch(3600).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Activate fetched configs
                mFirebaseRemoteConfig.activate().addOnCompleteListener(activateTask -> {
                    if (activateTask.isSuccessful()) {
                        Const.INSTANCE.eventCall(activity, "fire_" + activateTask.isSuccessful());
                        Log.e(TAG, "Remote config activated successfully.");
                        libFirebase.LibRemoteConfig(mFirebaseRemoteConfig);
                    } else {
                        Const.INSTANCE.eventCall(activity, "fire_" + activateTask.isSuccessful());
                        Log.e(TAG, "Failed to activate remote config: " + Objects.requireNonNull(activateTask.getException()).getMessage());
                    }
                });
            } else {
                Const.INSTANCE.eventCall(activity, "fire_else");
                // Log fetch failure
                Log.e(TAG, "Failed to fetch remote config: " + (task.getException() != null ? task.getException().getMessage() : "Unknown error"));
            }
        }).addOnFailureListener(e -> Log.e(TAG, "Fetch failed: " + e.getMessage(), e));
        mFirebaseRemoteConfig.addOnConfigUpdateListener(new ConfigUpdateListener() {
            @Override
            public void onUpdate(@NonNull ConfigUpdate configUpdate) {
                mFirebaseRemoteConfig.activate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        Const.INSTANCE.eventCall(activity, "fire_update");
                        libFirebase.LibRemoteConfig(mFirebaseRemoteConfig);
                    }
                });
                Log.e(TAG, "onUpdate: " + configUpdate.getUpdatedKeys());

            }

            @Override
            public void onError(@NonNull FirebaseRemoteConfigException error) {
                Log.e(TAG, "onError: " + error.getMessage());
                Const.INSTANCE.eventCall(activity, "fire_update_error");
            }
        });
    }
}