/*
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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.CardView;
import com.elix.dna.fragments.GestureFragment;
import com.elix.dna.fragments.StatusbarFragment;
import com.elix.dna.fragments.MiscFragment;
import com.elix.dna.R;

public class Home extends Activity {

    private CardView mTestcard1;
    private CardView mStatusbarcard;
    private CardView mMisccard;

    private String FGRAGMENT_1 = "fragment_1";
    private String STATUSBAR_CARD= "statusbar_card";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Test card that open a test fragment
        mTestcard1 = findViewById(R.id.gesture_card);
        mTestcard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.container_layout);
                GestureFragment fg1 = new GestureFragment();
                FragmentManager fmanager = getFragmentManager();
                FragmentTransaction ftransaction = fmanager.beginTransaction();
                fmanager.beginTransaction();
                ftransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ftransaction.replace(R.id.fgcontainer, fg1, FGRAGMENT_1);
                ftransaction.addToBackStack(fg1.getClass().getName());
                ftransaction.commit();
            }
        });

        // Statusbar CardView
        mStatusbarcard = findViewById(R.id.statusbar_card);
        mStatusbarcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.container_layout);
                StatusbarFragment fg1 = new StatusbarFragment();
                FragmentManager fmanager = getFragmentManager();
                FragmentTransaction ftransaction = fmanager.beginTransaction();
                fmanager.beginTransaction();
                ftransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ftransaction.replace(R.id.fgcontainer, fg1, FGRAGMENT_1);
                ftransaction.addToBackStack(fg1.getClass().getName());
                ftransaction.commit();
            }
        });

    // Misc CardView
    mMisccard = findViewById(R.id.misc_card);
    mMisccard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.container_layout);
            MiscFragment fg1 = new MiscFragment();
            FragmentManager fmanager = getFragmentManager();
            FragmentTransaction ftransaction = fmanager.beginTransaction();
            fmanager.beginTransaction();
            ftransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ftransaction.replace(R.id.fgcontainer, fg1, FGRAGMENT_1);
            ftransaction.addToBackStack(fg1.getClass().getName());
            ftransaction.commit();
        }
    });
}

    @Override
    public void onBackPressed() {
        FragmentManager fmanager = getFragmentManager();
        FragmentTransaction ftransaction = fmanager.beginTransaction();

        if (fmanager.getBackStackEntryCount() > 0) {
            Intent homeIntent = new Intent(Home.this, Home.class);
            startActivity(homeIntent);
        } else {
            super.onBackPressed();
        }
    }
}
