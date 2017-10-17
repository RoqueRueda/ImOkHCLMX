package imok.rueda.roque.com.imokhclmx.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import imok.rueda.roque.com.imokhclmx.R;
import imok.rueda.roque.com.imokhclmx.model.Contact;

/**
 * List of contact that will be de main point of the app.
 *
 * @author roquerueda
 * @version 1.0
 * @since 11/10/17
 */
public class ContactListFragment extends Fragment {

  private DatabaseReference mDatabase;
  private DatabaseReference mContactReference;
  private List<Contact> mContacts;

  private RecyclerView mContactRecyclerView;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContacts = new ArrayList<>();

    mDatabase = FirebaseDatabase.getInstance().getReference();
    mContactReference = mDatabase.child(Contact.CONTACTS_REFERENCE);

    mContactReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot contactSnapShoot : dataSnapshot.getChildren()) {
          Contact contact = contactSnapShoot.getValue(Contact.class);
          mContacts.add(contact);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        View fragmentContainer = getActivity().findViewById(R.id.fragment_container);
        Snackbar.make(fragmentContainer, getText(R.string.err_msg_contact_problem),
                Snackbar.LENGTH_SHORT);
      }
    });
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_contact_list, container, false);
    mContactRecyclerView = v.findViewById(R.id.contacts_recycler_view);
    return v;
  }

  private class ContactHolder extends RecyclerView.ViewHolder {




    public ContactHolder(View itemView) {
      super(itemView);
    }
  }
}
