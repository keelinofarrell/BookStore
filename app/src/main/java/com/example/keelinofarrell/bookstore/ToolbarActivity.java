package com.example.keelinofarrell.bookstore;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public abstract class ToolbarActivity extends LoggingActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(getToolbarLayout());

        toolbar = (Toolbar) this.findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.loading);
        this.setSupportActionBar(toolbar);
    }

    protected int getToolbarLayout() {
        return R.layout.toolbar_content;
    }

    protected void enableBackButton() {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Back Button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}