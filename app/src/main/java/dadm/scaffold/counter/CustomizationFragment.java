package dadm.scaffold.counter;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;

import android.content.SharedPreferences;



public class CustomizationFragment extends BaseFragment implements View.OnClickListener {
    int shipNum = 1;
    View rootView;

    SharedPreferences settings;
    SharedPreferences.Editor editor;


    public CustomizationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settings = getContext().getSharedPreferences("MyPrefsFile", 0);
        editor = settings.edit();

        editor.putInt("SHIPID", 1);
        editor.commit();

        rootView = inflater.inflate(R.layout.fragment_customization, container, false);
        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.startGame).setOnClickListener(this);
        view.findViewById(R.id.leftBtn).setOnClickListener(this);
        view.findViewById(R.id.rightBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.startGame:
                ((ScaffoldActivity)getActivity()).startGame();
                break;
            case R.id.leftBtn:
                changeShip(1, v);
                break;
            case R.id.rightBtn:
                changeShip(-1, v);
                break;
            default:
                break;
        }
    }

    private void changeShip(int i, View view){
        shipNum += i;
        int shipName = 0;
        if(shipNum < 1)
            shipNum = 4;
        else if(shipNum > 4)
            shipNum = 1;
        switch(shipNum){
            case 1:
                shipName = R.drawable.spaceship_1;
                break;
            case 2:
                shipName = R.drawable.spaceship_2;
                break;
            case 3:
                shipName = R.drawable.spaceship_3;
                break;
            case 4:
                shipName = R.drawable.spaceship_4;
                break;
        }
        Drawable image = ContextCompat.getDrawable(getActivity(), shipName);

        ImageView img = rootView.findViewById(R.id.image);
        img.setImageBitmap(((BitmapDrawable) image).getBitmap());

        editor.putInt("SHIPID", shipNum);
        editor.commit();
    }
}
