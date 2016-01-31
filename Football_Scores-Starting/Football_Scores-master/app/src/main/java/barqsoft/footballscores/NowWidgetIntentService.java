package barqsoft.footballscores;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class NowWidgetIntentService extends IntentService {
    private static final String[] SCORES_COLUMNS = {
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.LEAGUE_COL,
            DatabaseContract.scores_table.MATCH_DAY,
            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.TIME_COL
    };

    public static final int COL_HOME = 0;
    public static final int COL_AWAY = 1;
    public static final int COL_HOME_GOALS = 2;
    public static final int COL_AWAY_GOALS = 3;
    public static final int COL_DATE = 4;
    public static final int COL_LEAGUE = 5;
    public static final int COL_MATCHDAY = 6;
    public static final int COL_ID = 7;
    public static final int COL_MATCHTIME = 8;
    public double detail_match_id = 9;

     public NowWidgetIntentService() {
            super("NowWidgetIntentService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            Log.d("NowWidgetIntentService", "onHandleIntent()");

            // Retrieve all of the Today widget ids: these are the widgets we need to update
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NowWidgetProvider.class));

            Log.d("NowWidgetIntentService", "appWidgetIds: " + appWidgetIds.length);

            //Uri matchForCurrentUri = DatabaseContract.scores_table.buildScoreWithDate();
            //Cursor data = getContentResolver().query(matchForCurrentUri, SCORES_COLUMNS, null, null, DatabaseContract.scores_table.DATE_COL + " ASC");

            Date dateNow = new Date(System.currentTimeMillis());
            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String[] fragmentdate = new String[1];
            fragmentdate[0] = mformat.format(dateNow);
            Uri matchUri = DatabaseContract.scores_table.buildScoreWithDate();
            Cursor data = getContentResolver().query(matchUri, SCORES_COLUMNS, null, fragmentdate, null);


            if (data == null) {
                return;
            }

            Log.d("NowWidgetIntentService", "onHandleIntent()2");

            if (!data.moveToFirst()) {
                data.close();
                return;
            }
            Log.d("NowWidgetIntentService", "onHandleIntent()3");

            String home = data.getString(COL_HOME);
            String away = data.getString(COL_AWAY);
            int homeScore = data.getInt(COL_HOME_GOALS);
            int awayScore = data.getInt(COL_AWAY_GOALS);

            data.close();

            // Perform this loop procedure for each Today widget
          //  for (int appWidgetId : appWidgetIds) {
          //      Log.d("NowWidgetIntentService", "onHandleIntent()3.5");
                // Find the correct layout based on the widget's width
           //     int layoutId = R.layout.widget_now_small;
           //     RemoteViews views = new RemoteViews(getPackageName(), layoutId);

             //   views.setTextViewText(R.id.widget_high_temperature, home);
            //    Log.d("NowWidgetIntentService", "44444");

                /// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                //    setRemoteContentDescription(views, description);
                //}

                // Create an Intent to launch MainActivity
          //      Intent launchIntent = new Intent(this, MainActivity.class);
           //     PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
           //     views.setOnClickPendingIntent(R.id.widget, pendingIntent);

                // Tell the AppWidgetManager to perform an update on the current app widget
         //       appWidgetManager.updateAppWidget(appWidgetId, views);
           // }
        }
}
