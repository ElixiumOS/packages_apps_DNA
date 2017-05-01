/*
 * Copyright (C) 2016-2017 AOTP - Android Open Tuning Project
 * Copyright (C) 2017 ElixiumOS
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

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import android.content.res.Configuration;

import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Surface;

import com.android.internal.logging.MetricsProto.MetricsEvent;

// import com.elix.dna.Home;

public class Splash extends Activity {

    private static int SPLASH_DURATION = 1250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent starthomeclass = new Intent(getActivity(), Home.class);
                startActivity(starthomeclass);
                finish();
            }
        },SPLASH_DURATION);
    }

    public static class Home extends PreferenceFragment {
       @Override
       public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          addPreferencesFromResource(R.xml.home);
        }
    }

    public static class AboutApp extends PreferenceFragment {
       @Override
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState); 
           addPreferencesFromResource(R.xml.about);
        }
    }

    public static class MiscFrag extends PreferenceFragment {
       @Override
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState); 
           addPreferencesFromResource(R.xml.misc);
        }
    }
	
    public static class Device extends PreferenceFragment {	
       @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           return inflater.inflate(R.layout.devices, container, false);
        }
    }
}  
