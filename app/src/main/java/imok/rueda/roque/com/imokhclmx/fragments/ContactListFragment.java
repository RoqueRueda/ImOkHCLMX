package imok.rueda.roque.com.imokhclmx.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import imok.rueda.roque.com.imokhclmx.R;
import imok.rueda.roque.com.imokhclmx.model.Contact;
import imok.rueda.roque.com.imokhclmx.utils.Constants;

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

  private FloatingActionButton mImOk;
  private RecyclerView mContactRecyclerView;
  private FirebaseRecyclerAdapter<Contact, ContactViewHolder> mContactAdapter;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContacts = new ArrayList<>();

    mDatabase = FirebaseDatabase.getInstance().getReference();
    mContactReference = mDatabase.child(Contact.CONTACTS_REFERENCE);
  }

  @Override
  public void onStart() {
    super.onStart();
    mContactAdapter.startListening();
  }

  @Override
  public void onStop() {
    super.onStop();
    //mContactAdapter.cleanup();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View v = inflater.inflate(R.layout.fragment_contact_list, container, false);
    mContactRecyclerView = v.findViewById(R.id.contacts_recycler_view);

    mImOk = v.findViewById(R.id.btn_im_ok);
    mImOk.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Get the current user key
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userKey = sharedPreferences.getString(Constants.CONTACT_KEY, null);
        if (userKey != null) {
          // Update the user on the cloud database.
          mContactReference.child(userKey).child(Contact.OK_PROPERTY).setValue(true);
        }
      }
    });

    Query query = mContactReference.orderByKey();

    mContactAdapter = new FirebaseRecyclerAdapter<Contact, ContactViewHolder>(
            Contact.class,
            R.layout.list_item_contact,
            ContactViewHolder.class,
            query) {

      @Override
      public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_contact, parent, false);
        return new ContactViewHolder(view);
      }

      @Override
      protected void populateViewHolder(ContactViewHolder viewHolder, Contact model, int position) {
        viewHolder.bindViewsWithData(model);
      }
    };

    mContactRecyclerView.setAdapter(mContactAdapter);
    mContactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    return v;
  }

  private class ContactViewHolder extends RecyclerView.ViewHolder {

    private ImageView mContactImage;
    private TextView mName;
    private TextView mSeatCode;
    private TextView mSapId;
    private ImageView mContactStatus;
    private Contact mContact;

    ContactViewHolder(View itemView) {
      super(itemView);

      mContactImage = itemView.findViewById(R.id.contact_picture);
      mName = itemView.findViewById(R.id.contact_name);
      mSeatCode = itemView.findViewById(R.id.contact_seat_code);
      mSapId = itemView.findViewById(R.id.contact_sap_id);
      mContactStatus = itemView.findViewById(R.id.contact_status);
    }

    void bindViewsWithData(Contact c) {
      mContact = c;
      // Get the first letter
      // Set as contact icon
      String firstLetter = c.getName().substring(0, 1);
      ColorGenerator generator = ColorGenerator.MATERIAL;
      int color = generator.getRandomColor();
      TextDrawable drawable = TextDrawable.builder()
              .buildRound(firstLetter, color);
      mContactImage.setImageDrawable(drawable);

      // Set the name
      mName.setText(c.getName());

      // Set sap id
      mSapId.setText(c.getSapId());

      // Set seat code
      mSeatCode.setText(c.getSeatCode());

      // Set contact status
      if (c.isOk()) {
        mContactStatus.setImageResource(R.drawable.ic_ok_black);
      } else {
        mContactStatus.setImageResource(R.drawable.ic_warning_red);
      }
    }

    @Override
    public String toString() {
      return "ContactViewHolder: { Contact: "+ mContact +"}";
    }
  }

}
