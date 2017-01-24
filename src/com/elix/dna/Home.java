/*
 * Copyright (C) 2017 ElixiumOS
 * Copyright (C) 2016 AOTP - Android Open Tuning Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.elix.dna;

import com.elix.dna.Device;
import com.elix.dna.fragments.StatusbarFragment;
//import com.elix.dna.fragments.ButtonFragment;
//import com.elix.dna.fragments.PowerMenuSettings;

import com.android.internal.logging.MetricsProto.MetricsEvent;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Surface;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import com.elix.dna.R;

import java.util.ArrayList;
import java.util.List;

public class Home extends PreferenceActivity {

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return Device.class.getName().equals(fragmentName)
		  || StatusbarFragment.class.getName().equals(fragmentName)
//		  || ButtonFragment.class.getName().equals(fragmentName)
//		  || PowerMenuSettings.class.getName().equals(fragmentName)
		  || AboutApp.class.getName().equals(fragmentName);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
    loadHeadersFromResource(R.xml.headers, target);
    }
	
	public static class AboutApp extends PreferenceFragment {

            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState); 
                addPreferencesFromResource(R.xml.about);
        }
    }

}
