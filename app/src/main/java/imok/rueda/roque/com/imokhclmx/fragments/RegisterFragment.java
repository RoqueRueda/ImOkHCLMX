package imok.rueda.roque.com.imokhclmx.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import imok.rueda.roque.com.imokhclmx.ContactListActivity;
import imok.rueda.roque.com.imokhclmx.R;
import imok.rueda.roque.com.imokhclmx.model.Contact;
import imok.rueda.roque.com.imokhclmx.utils.Constants;

/**
 * TODO: Class description.
 *
 * @author roquerueda
 * @version 1.0
 * @since 11/10/17
 */
public class RegisterFragment extends Fragment {

  private TextInputLayout mInputSapId;
  private EditText mSapId;
  private TextInputLayout mInputName;
  private EditText mName;
  private TextInputLayout mInputSeatCode;
  private EditText mSeatCode;
  private Button   mRegister;
  private Button   mCancel;

  private DatabaseReference mDatabase;
  private DatabaseReference mContactReference;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mDatabase = FirebaseDatabase.getInstance().getReference();
    mContactReference = mDatabase.child(Contact.CONTACTS_REFERENCE);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_register, container, false);

    // bind widgets
    bindWidgets(v);

    return v;
  }

  /**
   * Gets the reference for form widgets.
   * @param rootView View that contains the widgets
   */
  private void bindWidgets(View rootView) {
    mInputSapId = rootView.findViewById(R.id.input_layout_sap_id);
    mSapId = rootView.findViewById(R.id.input_sap_id);
    mInputName = rootView.findViewById(R.id.input_layout_name);
    mName = rootView.findViewById(R.id.input_name);
    mInputSeatCode = rootView.findViewById(R.id.input_layout_seat_code);
    mSeatCode = rootView.findViewById(R.id.input_seat_code);
    mRegister = rootView.findViewById(R.id.btn_register);
    mCancel = rootView.findViewById(R.id.btn_cancel);

    mRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // We must save the record to Firebase
        String sapId = mSapId.getText().toString();
        String name = mName.getText().toString();
        String seatCode = mSeatCode.getText().toString();

        if (validateUserInput(sapId, name, seatCode)) {
          createFirebaseUser(sapId, name, seatCode);

          // Indicate that the user is already register
          storeAlreadyRegisterFlag();

          // Navigate to the users list
          navigateToListFragment();
        }
      }
    });

    createCancelDialog();
  }

  private void navigateToListFragment() {
    Intent intent = new Intent(getActivity(), ContactListActivity.class);
    startActivity(intent);
  }

  private void storeAlreadyRegisterFlag() {
    SharedPreferences sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(getActivity());
    SharedPreferences.Editor editor = sharedPreferences.edit();
    if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)) {
      editor.putBoolean(Constants.FIRST_RUN, false).apply();
    }
  }

  private boolean validateUserInput(String sapId, String name, String seatCode) {
    return validateSapId(sapId) &&
            validateContactName(name) &&
            validateSeatCode(seatCode);
  }

  private boolean validateSeatCode(String seatCode) {
    if (seatCode == null || seatCode.isEmpty()) {
      mInputSeatCode.setError(getString(R.string.err_msg_seat_code_empty));
      mSeatCode.requestFocus();
      return false;
    } else {
      mInputSeatCode.setErrorEnabled(false);
      return true;
    }
  }

  private boolean validateContactName(String name) {
    if (name == null || name.isEmpty() || name.trim().length() == 0) {
      mInputName.setError(getString(R.string.err_msg_name_empty));
      mName.requestFocus();
      return false;
    } else {
      mInputName.setErrorEnabled(false);
      return true;
    }
  }

  private boolean validateSapId(String sapId) {
    if (sapId == null || sapId.isEmpty()) {
      mInputSapId.setError(getString(R.string.err_msg_sapId_empty));
      mSapId.requestFocus();
      return false;
    } else {
      mInputSapId.setErrorEnabled(false);
      return true;
    }
  }

  private void createFirebaseUser(String sapId, String name, String seatCode) {
    // Create a contact key
    String contactKey = mContactReference.push().getKey();

    // Create a contact instance.
    Contact newContact = new Contact();
    newContact.setSapId(sapId);
    newContact.setName(name);
    newContact.setSeatCode(seatCode);
    newContact.setContactKey(contactKey);

    // Set the contact in the reference.
    mContactReference.child(contactKey).setValue(newContact);
  }

  /**
   * Creates a dialog when the user press the cancel button
   * to inform that they must register in order to user the
   * app
   */
  private void createCancelDialog() {
    // Add a listener to display a Alert Dialog.
    mCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Create a dialog when the user press cancel.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_cancel_register)
                .setTitle(R.string.cancel)
                .setPositiveButton(R.string.dialog_ok, null);
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
          @Override
          public void onShow(DialogInterface dialogInterface) {
            // If the user press ok we will dismiss the dialog.
            Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                dialog.dismiss();
              }
            });
          }
        });
      }
    });
  }
}
