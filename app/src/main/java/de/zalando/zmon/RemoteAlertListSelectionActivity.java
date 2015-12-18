package de.zalando.zmon;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.zalando.zmon.fragment.AlertHeadersListFragment;
import de.zalando.zmon.persistence.AlertHeader;
import de.zalando.zmon.task.GetAlertHeadersTask;

public class RemoteAlertListSelectionActivity extends BaseActivity implements AlertHeadersListFragment.Callback {

    private AlertHeadersListFragment alertHeadersListFragment;

    private List<AlertHeader> alertHeaders;
    private String currentSearchFilter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alert_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertHeadersListFragment = new AlertHeadersListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.alert_list_fragment, alertHeadersListFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ZmonApplication app = (ZmonApplication) getApplication();
        new GetAlertHeadersTask(app) {
            @Override
            protected void onPostExecute(List<AlertHeader> alertHeaders) {
                Log.i("[zmon]", "Received " + alertHeaders.size() + " alerts");
                setAlertHeaders(alertHeaders);

                if (Strings.isNullOrEmpty(RemoteAlertListSelectionActivity.this.currentSearchFilter)) {
                    displayAlertHeaders(alertHeaders);
                } else {
                    filterAlertHeaders(RemoteAlertListSelectionActivity.this.currentSearchFilter);
                }
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_remote_alert_list, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.search_alert).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                setCurrentSearchFilter(query);
                filterAlertHeaders(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                setCurrentSearchFilter(query);
                filterAlertHeaders(query);

                return true;
            }
        });

        return true;
    }

    @Override
    public void onAlertSelected(AlertHeader alertHeader) {
        startActivity(new AlertDetailActivity.AlertDetailActivityIntent(this, alertHeader.getAlertId()));
    }

    private void setAlertHeaders(List<AlertHeader> alertHeaders) {
        this.alertHeaders = alertHeaders;
    }

    private void setCurrentSearchFilter(String currentSearchFilter) {
        this.currentSearchFilter = currentSearchFilter;
    }

    private void displayAlertHeaders(List<AlertHeader> alertHeaders) {
        alertHeadersListFragment.setAlertHeaders(alertHeaders);
    }

    private void filterAlertHeaders(String query) {
        if (Strings.isNullOrEmpty(query)) {
            displayAlertHeaders(this.alertHeaders);
        } else {
            displayAlertHeaders(findAlertHeaders(query));
        }
    }

    @SuppressWarnings("unchecked")
    private List<AlertHeader> findAlertHeaders(final String query) {
        final String filterQuery = query.toLowerCase();

        if (this.alertHeaders != null) {
            Collection<AlertHeader> filteredHeaders = Collections2.filter(this.alertHeaders, new Predicate<AlertHeader>() {
                @Override
                public boolean apply(AlertHeader input) {
                    return input.getName().toLowerCase().contains(filterQuery) ||
                            input.getTeam().toLowerCase().contains(filterQuery) ||
                            input.getResponsibleTeam().toLowerCase().contains(filterQuery);
                }
            });

            return new ArrayList<>(filteredHeaders);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
