package com.example.eventos.utils;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eventos.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Manager User from FirebaseAuth and
 *
 * @author Walisson Rodrigo
 * @email walissonrodrigo@outlook.com
 * @phonesNumbers +5538997498037 || +5538988278978
 * @facebook walissonrodrigo0
 * @linkedin walissonrodrigo
 */

public class UserAuthFirebase {

    public static FirebaseUser getCurrentUser() {

        FirebaseAuth userAuth = ManagerFirebase.getFirebaseAuth();
        return userAuth.getCurrentUser();
    }

    public static boolean getInstanceId() {
        boolean response = true;
        try {
            FirebaseInstanceId instance = FirebaseInstanceId.getInstance();
            if (instance != null)
                response = true;
            else
                response = false;
        } catch (Exception ex) {
            Log.e("FirebaseInstanceId", ex.getMessage());
            ex.printStackTrace();
            response = false;
        }
        return response;
    }

    public static String getUserID() {
        return getCurrentUser().getUid();
    }

    public static void updateNameUserAuthFirebase(String name) {

        try {

            //User auth on app
            FirebaseUser userAuth = getCurrentUser();

            //Config object to update profile
            UserProfileChangeRequest profile = new UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(name)
                    .build();
            userAuth.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Log.d("Profile", "Update name for profile user on auth firebase Faill");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updatePhotoUserAuthFirebase(Uri url) {

        try {
            //User Auth on app
            FirebaseUser userAuth = getCurrentUser();
            //Config object to update photo on profile
            UserProfileChangeRequest profile = new UserProfileChangeRequest
                    .Builder()
                    .setPhotoUri(url)
                    .build();
            userAuth.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Log.d("Profile", "Update photo on profile Faill");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Usuario getInstanceCurrentUserAuth() {

        FirebaseUser firebaseUser = getCurrentUser();

        Usuario user = new Usuario();
        user.setUserId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setNomeUser(firebaseUser.getDisplayName());
//        if (firebaseUser.getPhotoUrl() == null) {
//            user.setPicture(null);
//        } else {
//            user.setPicture(firebaseUser.getPhotoUrl().toString());
//        }
        return user;
    }

    public static void setLoggoutUser(){
        ManagerFirebase.getFirebaseAuth().signOut();
    }
}
