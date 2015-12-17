package de.zalando.zmon.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.zalando.zmon.R;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public static final int[] navigationItems = {
            R.string.nav_login,
            R.string.nav_zmon_status,
            R.string.nav_dashboard,
            R.string.nav_observe_alerts,
            R.string.nav_observed_teams
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected int holderId;

        protected ImageView imageView;

        protected TextView appLabel;
        protected TextView itemLabel;
        protected TextView usernameLabel;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_HEADER) {
                imageView = (ImageView) itemView.findViewById(R.id.app_image);
                appLabel = (TextView) itemView.findViewById(R.id.app_label);
                usernameLabel = (TextView) itemView.findViewById(R.id.username);
                holderId = 0;
            } else {
                itemLabel = (TextView) itemView.findViewById(R.id.item_label);
                imageView = (ImageView) itemView.findViewById(R.id.item_image);
                holderId = 1;
            }
        }
    }

    private final String username;

    public NavigationAdapter(String username) {
        this.username = username;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header, parent, false);
            return new ViewHolder(view, viewType);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_item, parent, false);
            return new ViewHolder(view, viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.holderId == TYPE_HEADER) {
            holder.usernameLabel.setText(username);
        } else {
            int item = navigationItems[position - 1];
            holder.itemLabel.setText(item);

            switch (item) {
                case R.string.nav_login:
                    holder.imageView.setImageResource(R.drawable.ic_settings_power_white_24dp);
                    break;
                case R.string.nav_zmon_status:
                    holder.imageView.setImageResource(R.drawable.ic_favorite_white_24dp);
                    break;
                case R.string.nav_dashboard:
                    holder.imageView.setImageResource(R.drawable.ic_home_white_24dp);
                    break;
                case R.string.nav_observed_teams:
                    holder.imageView.setImageResource(R.drawable.ic_group_white_24dp);
                    break;
                case R.string.nav_observe_alerts:
                    holder.imageView.setImageResource(R.drawable.ic_notifications_active_white_24dp);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return navigationItems.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }
}
