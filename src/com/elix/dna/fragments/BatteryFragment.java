/*
 *  Copyright (C) 2015 The OmniROM Project
 *  Copyright (C) 2017 ElixiumOS
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.elix.dna.fragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceCategory;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;
import com.android.internal.util.omni.DeviceUtils;
import com.android.settings.Utils;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import java.util.List;
import java.util.ArrayList;

import com.elix.dna.preferences.ColorPickerPreference;

public class BatteryFragment extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {
    private static final String TAG = "StatusbarBatterySettings";

    private static final String STATUSBAR_BATTERY_STYLE = "statusbar_battery_style";
    private static final String STATUSBAR_BATTERY_PERCENT = "statusbar_battery_percent";
    private static final String STATUSBAR_CHARGING_COLOR = "statusbar_battery_charging_color";
    private static final String STATUSBAR_BATTERY_PERCENT_INSIDE = "statusbar_battery_percent_inside";
    private static final String STATUSBAR_BATTERY_SHOW_BOLT = "statusbar_battery_charging_image";
    private static final String STATUSBAR_BATTERY_ENABLE = "statusbar_battery_enable";
    private static final String STATUSBAR_CATEGORY_CHARGING = "statusbar_category_charging";

    private ListPreference mBatteryStyle;
    private ListPreference mBatteryPercent;
    private ColorPickerPreference mChargingColor;
    private CheckBoxPreference mPercentInside;
    private CheckBoxPreference mShowBolt;
    private int mShowPercent;
    private int mBatteryStyleValue;
    private ListPreference mBatteryEnable;
    private int mShowBattery = 1;
    private PreferenceCategory mChargingCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.battery);

        PreferenceScreen prefScreen = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

        mBatteryStyle = (ListPreference) findPreference(STATUSBAR_BATTERY_STYLE);
        mBatteryStyleValue = Settings.System.getInt(resolver,
                Settings.System.STATUSBAR_BATTERY_STYLE, 0);

        mBatteryStyle.setValue(Integer.toString(mBatteryStyleValue));
        mBatteryStyle.setSummary(mBatteryStyle.getEntry());
        mBatteryStyle.setOnPreferenceChangeListener(this);

        mBatteryPercent = (ListPreference) findPreference(STATUSBAR_BATTERY_PERCENT);
        mShowPercent = Settings.System.getInt(resolver,
                Settings.System.STATUSBAR_BATTERY_PERCENT, 0);

        mBatteryPercent.setValue(Integer.toString(mShowPercent));
        mBatteryPercent.setSummary(mBatteryPercent.getEntry());
        mBatteryPercent.setOnPreferenceChangeListener(this);

        mChargingColor = (ColorPickerPreference) prefScreen.findPreference(STATUSBAR_CHARGING_COLOR);
        // TODO 0xFFFFFFFF is not really the default - but the config value R.color.batterymeter_charge_color is not exposed
        int chargingColor = Settings.System.getInt(resolver, Settings.System.STATUSBAR_BATTERY_CHARGING_COLOR, 0xFFFFFFFF);
        mChargingColor.setColor(chargingColor);
        String hexColor = String.format("#%08X", chargingColor);
        mChargingColor.setSummary(hexColor);
        mChargingColor.setOnPreferenceChangeListener(this);

        mPercentInside = (CheckBoxPreference) findPreference(STATUSBAR_BATTERY_PERCENT_INSIDE);

        mShowBolt = (CheckBoxPreference) findPreference(STATUSBAR_BATTERY_SHOW_BOLT);

        mBatteryEnable = (ListPreference) findPreference(STATUSBAR_BATTERY_ENABLE);
        mShowBattery = Settings.System.getInt(resolver,
                Settings.System.STATUSBAR_BATTERY_ENABLE, 1);

        mBatteryEnable.setValue(Integer.toString(mShowBattery));
        mBatteryEnable.setSummary(mBatteryEnable.getEntry());
        mBatteryEnable.setOnPreferenceChangeListener(this);

        mChargingCategory = (PreferenceCategory) findPreference(STATUSBAR_CATEGORY_CHARGING);
        updateEnablement();
    }
     
    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.DNA;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        // If we didn't handle it, let preferences handle it.
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mBatteryStyle) {
            mBatteryStyleValue = Integer.valueOf((String) newValue);
            int index = mBatteryStyle.findIndexOfValue((String) newValue);
            mBatteryStyle.setSummary(
                    mBatteryStyle.getEntries()[index]);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_STYLE, mBatteryStyleValue);
            updateEnablement();
        } else if (preference == mBatteryPercent) {
            mShowPercent = Integer.valueOf((String) newValue);
            int index = mBatteryPercent.findIndexOfValue((String) newValue);
            mBatteryPercent.setSummary(
                    mBatteryPercent.getEntries()[index]);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_PERCENT, mShowPercent);
            updateEnablement();
        } else if (preference == mChargingColor) {
            String hexColor = String.format("#%08X", mChargingColor.getColor());
            mChargingColor.setSummary(hexColor);
            Settings.System.putInt(resolver,
                    Settings.System.STATUSBAR_BATTERY_CHARGING_COLOR, mChargingColor.getColor());
        } else if (preference == mBatteryEnable) {
            mShowBattery = Integer.valueOf((String) newValue);
            int index = mBatteryEnable.findIndexOfValue((String) newValue);
            mBatteryEnable.setSummary(
                    mBatteryEnable.getEntries()[index]);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_ENABLE, mShowBattery);
            updateEnablement();
        }
        return true;
    }

    private void updateEnablement() {
        mPercentInside.setEnabled(mShowBattery != 0 && mBatteryStyleValue < 3 && mShowPercent != 0);
        mShowBolt.setEnabled(mBatteryStyleValue < 3);
        mBatteryStyle.setEnabled(mShowBattery != 0);
        mBatteryPercent.setEnabled(mShowBattery != 0 && mBatteryStyleValue != 3);
        mChargingCategory.setEnabled(mShowBattery != 0);
    }
}
