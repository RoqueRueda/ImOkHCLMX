package imok.rueda.roque.com.imokhclmx.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact details of a person in order to know if he or her are ok.
 *
 * @author roquerueda
 * @version 1.0
 * @since 11/10/17
 */
public class Contact {

  public static final String CONTACTS_REFERENCE = "contacts";

  private String mSapId;
  private String mName;
  private String mSeatCode;
  private boolean mIsOk;
  private String mContactKey;

  public Contact() {
    // Required by firebase
  }

  public String getSapId() {
    return mSapId;
  }

  public void setSapId(String sapId) {
    mSapId = sapId;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public String getSeatCode() {
    return mSeatCode;
  }

  public void setSeatCode(String seatCode) {
    mSeatCode = seatCode;
  }

  public boolean isOk() {
    return mIsOk;
  }

  public void setOk(boolean ok) {
    mIsOk = ok;
  }

  public static List<Contact> getSampleContacts() {
    List<Contact> contactList = new ArrayList<>(5);
    // create dummy contact list
    Contact roque = new Contact();
    roque.setName("Roque Rueda");
    roque.setSapId("51619344");
    roque.setSeatCode("3E06");
    roque.setOk(true);

    contactList.add(roque);

    Contact silvino = new Contact();
    silvino.setName("Silvino LastName");
    silvino.setSapId("01010101");
    silvino.setSeatCode("10E01");
    silvino.setOk(false);
    contactList.add(silvino);

    return contactList;
  }

  public String getContactKey() {
    return mContactKey;
  }

  public void setContactKey(String contactKey) {
    mContactKey = contactKey;
  }
}
