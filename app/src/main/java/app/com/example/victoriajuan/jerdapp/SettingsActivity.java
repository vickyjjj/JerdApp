package app.com.example.victoriajuan.jerdapp;

/**
 * Created by victoriajuan on 10/25/16.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private Preference myPref;
    private Preference passwordPref;
    private Preference emailPref;
    private Preference deletePref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        //bindPreferenceSummaryToValue(findPreference("pref_incognito_mode_key"));

        myPref = findPreference("pref_set_acc_key");
        myPref.setSummary("Currently signed in: " + SaveSharedPreference.getEmail(getActivity()));
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Sign out?");
                    alertDialog.setMessage("Are you sure you want to sign out of current account?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();
                                    SaveSharedPreference.setDefaultAcc(getActivity(), "NONE");
                                    dialog.dismiss();
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });

        passwordPref = findPreference("pref_change_pass_key");
        passwordPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity()).create();
                    alertDialog1.setTitle("No Account");
                    alertDialog1.setMessage("You are currently not signed into an account. Sign in to change password.");
                    alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog1.show();
                }
                return true;
            }
        });

        emailPref = findPreference("pref_change_email_key");
        emailPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getActivity(), ChangeEmailActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("No Account");
                    alertDialog.setMessage("You are currently not signed into an account. Sign in to change email.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                return true;
            }
        });

        deletePref = findPreference("pref_delete_acc_key");
        deletePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Delete account?");
                alertDialog.setMessage("To delete, enter your account credentials.");

                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText input = new EditText(getActivity());
                final EditText input2 = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                layout.addView(input);
                layout.addView(input2);
                alertDialog.setView(layout);

                alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(input.getText().toString(), input2.getText().toString());

                                user.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                            }
                                        });

                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Account deleted.",
                                                    Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getActivity(), "Error: account not deleted.",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                FirebaseAuth.getInstance().signOut();
                                SaveSharedPreference.setDefaultAcc(getActivity(), "NONE");
                                myPref.setSummary("Currently signed in: " + SaveSharedPreference.getEmail(getActivity()));

                            }
                        });
                alertDialog.show();
                return true;
            }
        });
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getBoolean(preference.getKey(), true));
    }

    @Override
    public void onResume() {
        super.onResume();
        myPref.setSummary("Currently signed in: " + SaveSharedPreference.getEmail(getActivity()));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

        SaveSharedPreference.setIncognitoMode(getActivity(), value.toString());

        return true;
    }

}
