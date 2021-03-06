package com.example.lenovo_pc.aktu_lab;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.TAG;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {

    private static final String TAG = "MyAccountFragment";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Button mSignOut;
    TextView cntctus, prsnlinfo, mybkngs;
    ImageView profilepic;
    Context context;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        //Access gallery
        profilepic = view.findViewById(R.id.imageview1);
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                } else {
                    startGallery();
                }
            }
        });






            context = getActivity();



            mybkngs = view.findViewById(R.id.mybookings);
            mybkngs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
//                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                Toast.makeText(v2.getContext(), "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MyBookingRecyclerActivity.class);
                    startActivity(intent);
                }
            });

            prsnlinfo = view.findViewById(R.id.personalinfo);
            prsnlinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    openDialog("Your Registered email ID is", currentFirebaseUser.getEmail());

                }
            });
            cntctus = view.findViewById(R.id.contactus);
            cntctus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    openDialog("Kindly contact us on -", "aktulabs@aktu.ac.in");

                }
            });


            mSignOut = (Button) view.findViewById(R.id.signout);
            setupFirebaseListener();

            mSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: attempting to sign out the user");
                    FirebaseAuth.getInstance().signOut();
                }
            });
            return view;
        }

    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();

                }
                profilepic.setImageBitmap(bitmapImage);
            }
        }
        //Uri returnUri;
        //returnUri = data.getData();

}






    private void startGallery () {
        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

        public void openDialog (String string, String string2){

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

            // Set Custom Title

            TextView title = new TextView(context);
            // Title Properties
            title.setText(string);
            title.setPadding(10, 10, 10, 10);   // Set Position
            title.setGravity(Gravity.CENTER);
            title.setTextColor(Color.BLACK);
            title.setTextSize(20);
            alertDialog.setCustomTitle(title);
            // Set Message
            TextView msg = new TextView(context);
            // Message Properties
            msg.setText(string2);
            msg.setGravity(Gravity.CENTER_HORIZONTAL);
            msg.setTextColor(Color.BLACK);
            alertDialog.setView(msg);
            // Set Button
            // you can more buttons
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//                // Perform Action on Button
//
//            }
//
//        });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Perform Action on Button
                }

            });
            new Dialog(getActivity());
            alertDialog.show();
            // Set Properties for OK Button

            final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
            LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
            neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
            okBT.setPadding(50, 10, 10, 10);   // Set Position
            okBT.setTextColor(Color.BLUE);
            okBT.setLayoutParams(neutralBtnLP);

            final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

            LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
            negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
            cancelBT.setTextColor(Color.RED);
            cancelBT.setLayoutParams(negBtnLP);

        }

        private void setupFirebaseListener () {
            Log.d(TAG, "setupFirebaseListener: setting up the auth state listener");
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                    } else {
                        Log.d(TAG, "onAuthStateChanged: signed_out");
                        Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            };
        }

        @Override
        public void onStart () {
            super.onStart();
            FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
        }

        @Override
        public void onStop () {
            super.onStop();
            if (mAuthStateListener != null) {
                FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
            }
        }

    }

