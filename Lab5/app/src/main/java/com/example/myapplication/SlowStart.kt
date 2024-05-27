package com.example.myapplication

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView


class SlowTask(private val context: Context, private val tvStatus: TextView) :
    AsyncTask<String?, Long?, Void?>() {
    private var pdWaiting: ProgressDialog? = null
    private var startTime: Long? = null

    override fun onPreExecute() {
        super.onPreExecute()
        // We can use UI thread
        pdWaiting = ProgressDialog(context)
        startTime = System.currentTimeMillis()
        tvStatus.text = "Start time: $startTime"
        pdWaiting!!.setMessage(context.getString(R.string.please_wait))
        pdWaiting!!.show()
    }

    override fun onPostExecute(unused: Void?) {
        super.onPostExecute(unused)

        // We can use UI thread here
        if (pdWaiting!!.isShowing) pdWaiting!!.dismiss()

        //Show done messages
        tvStatus.append(
            """
                
                End time: ${System.currentTimeMillis()}
                """.trimIndent()
        )
        tvStatus.append("\nDone!")
    }

    override fun onProgressUpdate(vararg values: Long?) {
        super.onProgressUpdate(*values)
        tvStatus.append(
            """
                
                Working...${values[0]}
                """.trimIndent()
        )
    }

    override fun doInBackground(vararg params: String?): Void? {
        try {
            for (i in 0L..3L - 1) {
                Thread.sleep(2000)
                publishProgress(i)
            }
        } catch (e: Exception) {
            Log.e("SlowJob", e.message!!)
        }
        return null
    }
}