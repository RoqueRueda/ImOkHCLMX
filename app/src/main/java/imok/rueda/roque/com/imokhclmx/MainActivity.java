package imok.rueda.roque.com.imokhclmx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import imok.rueda.roque.com.imokhclmx.model.Contact;

public class MainActivity extends AppCompatActivity {

  private DatabaseReference mDatabase;
  private DatabaseReference mContactReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mDatabase = FirebaseDatabase.getInstance().getReference();
    mContactReference = mDatabase.child(Contact.CONTACTS_REFERENCE);

    mContactReference.setValue("Hello World");
  }
}
