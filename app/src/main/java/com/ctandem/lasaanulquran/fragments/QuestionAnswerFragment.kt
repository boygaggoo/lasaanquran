package com.ctandem.lasaanulquran.fragments

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctandem.lasaanulquran.R
import com.ctandem.lasaanulquran.adapters.QuestionAnswerAdapter
import com.ctandem.lasaanulquran.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_question_answer.view.*

class QuestionAnswerFragment : BaseFragment() {

    companion object {
        private var qainstance: QuestionAnswerFragment? = null
        fun getInstance(): QuestionAnswerFragment {
            if (qainstance == null) {
                qainstance = QuestionAnswerFragment()
            }
            return qainstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question_answer, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {

        view.question_rv.layoutManager = LinearLayoutManager(activity)
        view.question_rv.itemAnimator = DefaultItemAnimator()

        val list = mSessionController.qaList
//        for (i in 0 until 5) {
//            val model = ContentList()
//            model.arabic = "انت ساكن في انهي شقة؟"
//            model.urdu = "آپ کونسی اپارٹمنٹ رہتے ہیں؟"
//            list.add(model)
//        }
        if (list != null) {
            view.question_rv.adapter = QuestionAnswerAdapter(activity, list)
        }
    }
}
