package dadm.scaffold.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;

import android.content.SharedPreferences;
import android.widget.TextView;

public class GameOverFragment  extends BaseFragment implements View.OnClickListener {

    SharedPreferences settings;

    public GameOverFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settings = getContext().getSharedPreferences("MyPrefsFile", 0);
        View rootView = inflater.inflate(R.layout.fragment_game_over, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.go_to_menu_btn).setOnClickListener(this);
        TextView pointsText = view.findViewById(R.id.points_text);
        String p = Integer.toString(settings.getInt("POINTS", 0));
        pointsText.setText(p);
    }

    @Override
    public void onClick(View v) {
        ((ScaffoldActivity)getActivity()).goToMenu();
    }
}
