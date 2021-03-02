package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import java.util.*

class NameListFragment : Fragment() {
    var gridView: GridView? = null
    var names = ArrayList<NameModel>()
    var tempName = arrayOf("Alex", "Jango", "Js", "Java","hello","world")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        gridView = view.findViewById<View>(R.id.gridView) as GridView
        for (item in tempName) {
            names.add(NameModel(item, "Test$item", ""))
        }
        gridView!!.adapter = object : ArrayAdapter<NameModel?>(requireContext(), android.R.layout.simple_selectable_list_item, names as List<NameModel?>) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val tv = view as TextView
                val lp = RelativeLayout.LayoutParams(
                        GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT
                )
                tv.layoutParams = lp
                val params = tv.layoutParams as RelativeLayout.LayoutParams
                tv.layoutParams = params
                tv.height = 150
                tv.gravity = Gravity.CENTER
                tv.text = names[position].getName()
                tv.setBackgroundColor(Color.parseColor("#FF82DE81"))
                return tv
            }
        }
        gridView!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            requireActivity().supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frameLayout, BlankFragment2.newInstance(names[position].getName(), names[position].getNameDate(), names[position].getMeaning()), null)
                    .commit()
        }
        return view
    }
}