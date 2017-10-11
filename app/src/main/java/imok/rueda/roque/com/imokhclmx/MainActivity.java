package imok.rueda.roque.com.imokhclmx;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import imok.rueda.roque.com.imokhclmx.fragments.ContactListFragment;
import imok.rueda.roque.com.imokhclmx.fragments.RegisterFragment;
import imok.rueda.roque.com.imokhclmx.model.Contact;
import imok.rueda.roque.com.imokhclmx.utils.Constants;

public class MainActivity extends AppCompatActivity {

  private DatabaseReference mDatabase;
  private DatabaseReference mContactReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single_fragment);





    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragment_container);
    if (fragment == null) {
      // If no fragment we must create one.

      // If this is the first run then we must register the user.
      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)) {
        fragment = new RegisterFragment();
      } else {
        fragment = new ContactListFragment();
      }

      fm.beginTransaction()
              .add(R.id.fragment_container, fragment)
              .commit();
    }

    /*mDatabase = FirebaseDatabase.getInstance().getReference();
    mContactReference = mDatabase.child(Contact.CONTACTS_REFERENCE);

    mContactReference.setValue("Hello World");*/
  }
}
