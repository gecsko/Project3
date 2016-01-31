package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NowWidgetProvider extends AppWidgetProvider {
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

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //Uri matchForCurrentUri = DatabaseContract.scores_table.buildScoreWithDate();
        //Cursor data = getContentResolver().query(matchForCurrentUri, SCORES_COLUMNS, null, null, DatabaseContract.scores_table.DATE_COL + " ASC");

        Date dateNow = new Date(System.currentTimeMillis());
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String[] fragmentdate = new String[1];
        fragmentdate[0] = mformat.format(dateNow);
        Uri matchUri = DatabaseContract.scores_table.buildScoreWithDate();
        Cursor data = context.getContentResolver().query(matchUri, SCORES_COLUMNS, null, fragmentdate, null);

        if (data == null) {
            return;
        }

        if (!data.moveToFirst()) {
            data.close();
            return;
        }

        String home = data.getString(COL_HOME);
        String away = data.getString(COL_AWAY);
        String homeScore = data.getString(COL_HOME_GOALS);
        String awayScore = data.getString(COL_AWAY_GOALS);

        data.close();

        // Perform this loop procedure for each Today widget
        for (int appWidgetId : appWidgetIds) {
            // Find the correct layout based on the widget's width
            int layoutId = R.layout.widget_now_small;
            RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);

            views.setTextViewText(R.id.widget_home, home);
            views.setTextViewText(R.id.widget_home_score, homeScore);
            views.setTextViewText(R.id.widget_away, away);
            views.setTextViewText(R.id.widget_away_score, awayScore);

            /// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            //    setRemoteContentDescription(views, description);
            //}

            // Create an Intent to launch MainActivity
            Intent launchIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        Log.d("NowWidgetProvider", intent.getAction());
        //if (FootballSyncAdapter.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            // update the data
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, NowWidgetProvider.class));

            onUpdate(context, appWidgetManager, appWidgetIds);
        //}
    }
}
