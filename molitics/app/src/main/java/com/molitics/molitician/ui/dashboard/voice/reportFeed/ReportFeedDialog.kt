package com.molitics.molitician.ui.dashboard.voice.reportFeed

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.molitics.molitician.R
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil
import com.molitics.molitician.util.Util
import kotlinx.android.synthetic.main.dialog_report_feed.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportFeedDialog : BottomSheetDialogFragment() {

    private var reportId: Int = 0
    private var userId: Int = 0
    private var feedId: Int = 0
    private lateinit var from: String

    private val reportAdapter: ReportAdapter by lazy {
        ReportAdapter {
            showConfirmDialog(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_report_feed, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDialogArgument()
        setAdapter()
        getReportList()
    }

    private fun getDialogArgument() {
        userId = PrefUtil.getInt(Constant.PreferenceKey.USER_ID)
        feedId = arguments?.getInt(Constant.IntentKey.ISSUE_ID) ?: 0
        reportId = arguments?.getInt(Constant.IntentKey.REPORT_ID) ?: 0
        from = arguments?.getString(Constant.IntentKey.FROM) ?: ""

        reportTextView.text = "Report $from"
    }

    private fun setAdapter() {
        reportRecyclerView.apply {
            adapter = reportAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun showConfirmDialog(reasonId: Int) {
        val dialogMessage = getString(R.string.are_you_sure_report)

        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("$dialogMessage $from?")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes)) { dialog: DialogInterface, id1: Int ->
                    if (arguments?.getString(Constant.IntentKey.FROM) == getString(R.string.feed))
                        reportFeed(reasonId)
                    else reportUser(reasonId)
                    dialog.cancel()
                }
                .setNegativeButton(getString(R.string.no)) { dialog: DialogInterface, id12: Int ->
                    dialog.cancel()
                }
        val alert = builder.create()
        alert.setTitle(getString(R.string.report))
        alert.show()
    }

    private fun getReportList() {
        GlobalScope.launch(coroutineHandler) {
            withContext(Dispatchers.Main) {
                val result = RetrofitRestClient.instance.reportList(from.toLowerCase())
                reportAdapter.setData(result.data.reasonsData)
            }
        }
    }

    private fun reportFeed(reasonId: Int) {
        GlobalScope.launch(coroutineHandler) {
            withContext(Dispatchers.Main) {
                val request = ReportRequestModel(reported_by_id = userId, reason_id = reasonId, feed_id = feedId)
                val result = RetrofitRestClient.instance.reportFeed(request)
                Util.toastLong(requireContext(), getString(R.string.successfully_reported))
                dismiss()
            }
        }
    }

    private fun reportUser(reasonId: Int) {
        GlobalScope.launch(coroutineHandler) {
            withContext(Dispatchers.Main) {
                val request = ReportRequestModel(reported_by_id = userId, reason_id = reasonId, reported_for_id = reportId)
                val result = RetrofitRestClient.instance.reportUser(request)
                Util.toastLong(requireContext(), getString(R.string.successfully_reported))
                dismiss()
            }
        }
    }

    private val coroutineHandler = CoroutineExceptionHandler { _, error ->
        Util.showLog("error", error.message)
    }
}