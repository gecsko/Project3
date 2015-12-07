package barqsoft.footballscores;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.RemoteViews;

/**
 * Created by Tim on 2015/12/8.
 */
public class NowWidgetIntentService extends IntentService {
        public NowWidgetIntentService() {
            super("NowWidgetIntentService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            // Retrieve all of the Today widget ids: these are the widgets we need to update
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                    NowWidgetProvider.class));

            // Perform this loop procedure for each Today widget
            for (int appWidgetId : appWidgetIds) {
                // Find the correct layout based on the widget's width
                int widgetWidth = getWidgetWidth(appWidgetManager, appWidgetId);

                layoutId = R.layout.widget_now_small;

                RemoteViews views = new RemoteViews(getPackageName(), layoutId);

                // Add the data to the RemoteViews
                views.setImageViewResource(R.id.widget_icon, weatherArtResourceId);
                // Content Descriptions for RemoteViews were only added in ICS MR1
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    setRemoteContentDescription(views, description);
                }

                // Create an Intent to launch MainActivity
                Intent launchIntent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
                views.setOnClickPendingIntent(R.id.widget, pendingIntent);

                // Tell the AppWidgetManager to perform an update on the current app widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }

        private int getWidgetWidth(AppWidgetManager appWidgetManager, int appWidgetId) {
            // Prior to Jelly Bean, widgets were always their default size
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                return getResources().getDimensionPixelSize(R.dimen.widget_today_default_width);
            }
            // For Jelly Bean and higher devices, widgets can be resized - the current size can be
            // retrieved from the newly added App Widget Options
            return getWidgetWidthFromOptions(appWidgetManager, appWidgetId);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        private int getWidgetWidthFromOptions(AppWidgetManager appWidgetManager, int appWidgetId) {
            Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
            if (options.containsKey(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)) {
                int minWidthDp = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
                // The width returned is in dp, but we'll convert it to pixels to match the other widths
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minWidthDp,
                        displayMetrics);
            }
            return  getResources().getDimensionPixelSize(R.dimen.widget_today_default_width);
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
        private void setRemoteContentDescription(RemoteViews views, String description) {
            views.setContentDescription(R.id.widget_icon, description);
        }
    }
}
