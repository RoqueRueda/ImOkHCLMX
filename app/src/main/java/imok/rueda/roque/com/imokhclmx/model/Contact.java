package imok.rueda.roque.com.imokhclmx.model;

/**
 * Contact details of a person in order to know if he or her are ok.
 *
 * @author roquerueda
 * @version 1.0
 * @since 11/10/17
 */
public class Contact {

  private String mSapId;
  private String mName;
  private String mSeatCode;
  private boolean mIsOk;

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
}
