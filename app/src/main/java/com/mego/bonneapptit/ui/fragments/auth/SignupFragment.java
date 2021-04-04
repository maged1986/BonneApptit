package com.mego.bonneapptit.ui.fragments.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mego.bonneapptit.R;
import com.mego.bonneapptit.databinding.FragmentSignupBinding;
import com.mego.bonneapptit.ui.MainActivity;
import com.mego.bonneapptit.utils.SessionManager;
import com.mego.bonneapptit.viewmodels.AuthViewModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

@AndroidEntryPoint
public class SignupFragment extends Fragment {
    private AuthViewModel mViewModel;
    private FragmentSignupBinding binding;




    @Inject
    public SignupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_signup, container, false);
        mViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        /*binding.fragSingupBtnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.fragSingupEtUser.getText() == null) {
                    binding.fragSingupEtUser.setError("this field is required...");
                } else if (binding.fragSingupEtEmail.getText() == null) {
                    binding.fragSingupEtEmail.setError("this field is required...");
                } else if (binding.fragSingupEtPassword.getText() == null) {
                    binding.fragSingupEtPassword.setError("this field is required...");
                } else {
                viewModel.singup(binding.fragSingupEtEmail.getText().toString().trim()
                        , binding.fragSingupEtPassword.getText().toString().trim()
                        , getActivity());
                viewModel.updateUserData(binding.fragSingupEtUser.getText().toString().trim(),
                        binding.fragSingupEtEmail.getText().toString().trim()
                        , binding.fragSingupEtPassword.getText().toString().trim());
                startActivity(new Intent(getActivity(), MainActivity.class));
            }}
        });
        binding.fragSingupTxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });*/
        return binding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.registerBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doStringsMatch(binding.registerEtPassword, binding.registerEtConfirmPassword)) {
                    if ((checkField(binding.registerEtEmail) == true
                            && checkField(binding.registerEtCountry) == true
                            && checkField(binding.registerEtCity) == true
                            && checkField(binding.registerEtName) == true
                            && checkField(binding.registerEtPassword) == true
                            && checkField(binding.registerEtConfirmPassword) == true)
                    ) {
                         mViewModel.singup(binding.registerEtName.getText().toString()
                                , binding.registerEtCountry.getText().toString()
                                , binding.registerEtCity.getText().toString()
                                , binding.registerEtEmail.getText().toString()
                                , binding.registerEtPassword.getText().toString(),
                                getContext());

                    } else {
                        Toast.makeText(getContext(), "this is required field", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getContext(), "paswords doesnmatch", Toast.LENGTH_SHORT).show();
                }
                resetFields();
            }
        });

        binding.registerTvLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });

    }


    private boolean doStringsMatch(EditText editText1, EditText editText2) {
        return editText1.getText().toString().equals(editText2.getText().toString());
    }

    private void resetFields() {
        binding.registerEtCountry.setText(null);
        binding.registerEtCity.setText(null);
        binding.registerEtEmail.setText(null);
        binding.registerEtName.setText(null);
        binding.registerEtPassword.setText(null);
        binding.registerEtConfirmPassword.setText(null);
    }

    private Boolean checkField(EditText editText) {
        if (editText.getText().toString().length() > 2) {
            return true;
        } else {
            editText.setError("this is reqired field");
            Toast.makeText(getContext(),"please fill all required fields",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}