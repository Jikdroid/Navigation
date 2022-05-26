package org.inu.navigation

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder

class DeepLinkAppWidgetProvider:AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray?) {
        val remoteViews = RemoteViews(context.packageName, R.layout.deep_link_appwidget)

        val args = Bundle()
        args.putString("myarg", "From Widget")

        // construct and set a PendingIntent using DeepLinkBuilder
        val pendingIntent  = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.deeplink_dest)
            .setArguments(args)
            .createPendingIntent()
        remoteViews.setOnClickPendingIntent(R.id.deep_link_button,pendingIntent)

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }
}