package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.leaderNews

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ReminderClickHelper {

    @JvmStatic
    fun reminderEvent(context: Context,time: String?, name: String?, address: String?) {
        // event insert
        val cr: ContentResolver = context.contentResolver
        var values = ContentValues()
        val calID: Long = 3
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm a", Locale.getDefault())
        try {
            val mDate = sdf.parse(time)
            val timeInMilliseconds = mDate.time
            values.put(CalendarContract.Events.DTSTART, timeInMilliseconds) // event starts at  from
            values.put(CalendarContract.Events.DTEND, timeInMilliseconds + 20 * 60 * 1000) // ends 20 minutes from now
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        values.put(CalendarContract.Events.TITLE, name)
        values.put(CalendarContract.Events.DESCRIPTION, address)
        values.put(CalendarContract.Events.CALENDAR_ID, calID)
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        val event = cr.insert(CalendarContract.Events.CONTENT_URI, values)
        values = ContentValues()
        if (event != null) {
            values.put("event_id", event.lastPathSegment!!.toLong())
        }
        values.put("method", 1)
        values.put("minutes", 10)
        ///  cr.insert(CalendarContract.Reminders.CONTENT_URI, values);

    }
}