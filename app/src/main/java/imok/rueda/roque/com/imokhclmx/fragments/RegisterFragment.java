package imok.rueda.roque.com.imokhclmx.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import imok.rueda.roque.com.imokhclmx.R;

/**
 * TODO: Class description.
 *
 * @author roquerueda
 * @version 1.0
 * @since 11/10/17
 */
public class RegisterFragment extends Fragment {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_register, container, false);
    return v;
  }
}
