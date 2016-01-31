package barqsoft.footballscores.service;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.NowWidgetIntentService;
import barqsoft.footballscores.R;

public class NowWidget extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

//
//        String description = "Clear";
//        double maxTemp = 24;
//
//        for (int appWidgetId : appWidgetIds) {
//            int layoutId = R.layout.widget_now_small;
//            RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);
//
//            // Content Descriptions for RemoteViews were only added in ICS MR1
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
//                setRemoteContentDescription(views, description);
//            }
//            //views.setTextViewText(R.id.widget_high_temperature, "600");
//            // Create an Intent to launch MainActivity
//            Intent launchIntent = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
//            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
//
//            // Tell the AppWidgetManager to perform an update on the current app widget
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//        }

        context.startService(new Intent(context, NowWidgetIntentService.class));
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setRemoteContentDescription(RemoteViews views, String description) {
       // views.setContentDescription(R.id.widget_icon, description);
    }
}