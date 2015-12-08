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
                int layoutId = R.layout.widget_now_small;
                RemoteViews views = new RemoteViews(getPackageName(), layoutId);

                views.setTextViewText(R.id.widget_high_temperature, "Test");

               /// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                //    setRemoteContentDescription(views, description);
                //}

                // Create an Intent to launch MainActivity
                Intent launchIntent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
                views.setOnClickPendingIntent(R.id.widget, pendingIntent);

                // Tell the AppWidgetManager to perform an update on the current app widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }

}
