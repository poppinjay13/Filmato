package poppinjay13.projects.android.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import poppinjay13.projects.android.R;
import poppinjay13.projects.android.model.configuration.PrefConfig;

public class SettingsFragment extends Fragment {

    private PrefConfig prefConfig = new PrefConfig();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Context context = getContext();
        prefConfig.prefConfig(context);
        String uname = prefConfig.readName();
        TextView name = view.findViewById(R.id.prof_name);
        name.setText(uname);

        String uemail = prefConfig.readEmail();
        TextView email = view.findViewById(R.id.prof_email);
        email.setText(uemail);
        return view;
    }


}
