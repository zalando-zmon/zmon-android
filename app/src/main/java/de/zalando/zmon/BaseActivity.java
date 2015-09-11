package de.zalando.zmon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import de.zalando.zmon.navigation.NavigationClickListener;
import de.zalando.zmon.navigation.NavigationItemAdapter;

public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected ListView navigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        navigationDrawer = (ListView) findViewById(R.id.navigation_drawer);
        navigationDrawer.setAdapter(new NavigationItemAdapter(this));
        navigationDrawer.setOnItemClickListener(new NavigationClickListener(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            toolbar.setTitle(R.string.label_dashboard);
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_36dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(Gravity.START);
                }
            });
        }
    }

    protected abstract int getLayoutId();
}
