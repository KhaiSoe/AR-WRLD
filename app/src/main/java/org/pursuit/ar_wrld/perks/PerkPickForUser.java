package org.pursuit.ar_wrld.perks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import org.pursuit.ar_wrld.GameInformation;
import org.pursuit.ar_wrld.R;
import org.pursuit.ar_wrld.login.UserHomeScreenActivity;

import java.util.ArrayList;
import java.util.List;

public class PerkPickForUser extends FragmentActivity implements ViewPagerListener{
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_pick_for_user);

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(ViewPagerForPerk.newInstance(getResources().getString(R.string.more_ammo_perk), R.drawable.ammo_perk, GameInformation.MORE_AMMO_PERK, getString(R.string.more_ammo_perk_name)));
        fragmentList.add(ViewPagerForPerk.newInstance(getResources().getString(R.string.more_time_perk), R.drawable.more_time_perk_image, GameInformation.MORE_TIME_PERK, getString(R.string.more_time_perk_name)));
        fragmentList.add(ViewPagerForPerk.newInstance(getResources().getString(R.string.slow_time_perk), R.drawable.slow_time_perk, GameInformation.SLOW_TIME_PERK, getString(R.string.slow_time_perk_name)));
        fragmentList.add(ViewPagerForPerk.newInstance(getResources().getString(R.string.more_damage_park), R.drawable.more_damage_perk_image, GameInformation.MORE_DAMAGE_PERK, getString(R.string.more_damage_perk_name)));

        viewPager = findViewById(R.id.mainActivity_viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmentList));
    }

    @Override
    public void goToUserHome() {
        startActivity(new Intent(this, UserHomeScreenActivity.class));
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            viewPager.setCurrentItem(0, true);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
