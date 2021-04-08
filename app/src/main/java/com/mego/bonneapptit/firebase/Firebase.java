package com.mego.bonneapptit.firebase;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mego.bonneapptit.db.RecipeDao;
import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.models.User;
import com.mego.bonneapptit.ui.MainActivity;
import com.mego.bonneapptit.utils.SessionManager;

import javax.inject.Inject;

public class Firebase  {
    // this class to maneage data from and to faire store
    private static final String TAG = "FireBaseManager";
    private User mUser;
    private RecipeDao recipeDao;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    String userId=mAuth.getCurrentUser().getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Inject
    public Firebase() {
    }

// to cereat new usesr and save user data
    public void createNewUser(String name, String country, String city,
                                 String email, String password, Context context
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //send email verificaiton
                            sendVerificationEmail(context);
                            updateUserData(name, country, city, email);
                            //add user details to firebase database
                            SessionManager manager = new SessionManager(context);
                            manager.setIsLoggedIn(true);
                            Toast.makeText(context, "Successful ",
                                    Toast.LENGTH_SHORT).show();
                          MainActivity.show(context);

                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(context, "Someone with that email ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUserData(String name, String country, String city, String email) {
        //add data to the "users" node
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mUser = new User( name,email);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //insert into users node
        reference.child("users").push()
                .setValue(mUser);
    }


    public void sendVerificationEmail(Context mContext) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(mContext, "couldn't send a verification email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
// to make user login
    public void logIn(String email, String password, Context context) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SessionManager manager = new SessionManager(context);
                            manager.setIsLoggedIn(true);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(context, "Authentication successe.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
// to make user logout

    public void signOut(Context context) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        SessionManager manager = new SessionManager(context);
        manager.setIsLoggedIn(false);
    }
// to upload recipe
    public void uploadRecipe(Recipe recipe, Context context) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
       String userId=mAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(userId).add(recipe).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(context, "Data Update", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
    public void checkSavedRecipes(){
        if (recipeDao.getRecipes() == null){
            db.collection(userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(document != null){
                           Recipe recipe=document.toObject(Recipe.class);
                            recipeDao.insertRecipe(recipe);
                            Log.d(TAG, document.getId() + " => " + document.getData());}
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        }
    }

    }
