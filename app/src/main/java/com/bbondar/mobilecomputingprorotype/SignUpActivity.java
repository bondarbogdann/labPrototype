package com.bbondar.mobilecomputingprorotype;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected Button signUpButton;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        passwordEditText = (EditText)findViewById(R.id.passwordField);
        emailEditText = (EditText)findViewById(R.id.emailField);
        signUpButton = (Button)findViewById(R.id.signupButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                password = password.trim();
                email = email.trim();

                final String usrEmail = email;
                final String usrPass = password;

                if (usrPass.isEmpty() || usrEmail.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    firebaseAuth.createUserWithEmailAndPassword(usrEmail, usrPass)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("AUTH", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                        builder.setMessage(task.getException().getMessage())
                                                .setTitle(R.string.signup_error_title)
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                        builder.setMessage(R.string.signup_success)
                                                .setPositiveButton(R.string.login_button_label, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        firebaseAuth.signInWithEmailAndPassword(usrEmail, usrPass)
                                                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                        Log.d("AUTH", "signInWithEmail:onComplete:" + task.isSuccessful());

                                                                        if (!task.isSuccessful()) {
                                                                            Log.w("AUTH", "signInWithEmail", task.getException());
                                                                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                                                    Toast.LENGTH_SHORT).show();
                                                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                            startActivity(intent);
                                                                        } else {
                                                                            FirebaseUser user = task.getResult().getUser();
                                                                            if (user != null) {
                                                                                // Authenticated successfully with payload authData
                                                                                Map<String, Object> map = new HashMap<String, Object>();
                                                                                map.put("email", usrEmail);
                                                                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                                                firebaseDatabase.getReference().child("users").child(user.getUid()).updateChildren(map);

                                                                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                startActivity(intent);
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
