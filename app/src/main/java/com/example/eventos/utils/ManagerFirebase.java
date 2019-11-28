package com.example.eventos.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * Helper Class for give static instances from firebase.
 * @author Walisson Rodrigo
 * @email walissonrodrigo@outlook.com
 * @phonesNumbers +5538997498037 || +5538988278978
 * @facebook walissonrodrigo0
 * @linkedin walissonrodrigo
 */

public class ManagerFirebase {

    private static FirebaseFirestore fireStore;
    private static FirebaseAuth authRef;
    //private static StorageReference storage;

    //return instance from database (DataBase NoSQL Realtime from Google Firebase)
    public static FirebaseFirestore getFireStore(){
        if( fireStore == null ){
            fireStore = FirebaseFirestore.getInstance();
        }
        return fireStore;
    }

    /**
     * Give current instance from FirebaseAuth
     * @return FirebaseAuth
     */
    public static FirebaseAuth getFirebaseAuth(){
        if( authRef == null ){
            authRef = FirebaseAuth.getInstance();
        }
        //return instance from FirebaseAuth
        return authRef;
    }

    /**
     * Give current instance from FirebaseStorage
     * @return FirebaseStorage
     */
//    public static StorageReference getFirebaseStorage(){
//        if( storage == null ){
//            storage = FirebaseStorage.getInstance().getReference();
//        }
//        //return instance from FirebaseStorage
//        return storage;
//    }

}
