package com.example.myapplication

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class NotifyFragment : DialogFragment() {
    private var add: addNameKotlin? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        add = context as addNameKotlin
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val input = EditText(context)
        return builder
                .setTitle("Enter notification message")
                .setIcon(R.drawable.ic_dialog_alert)
                .setView(input)
                .setPositiveButton("OK?") { dialog, which ->
                    val prefs = requireActivity().getSharedPreferences("main", 0)
                    val editor = prefs.edit()
                    editor.putString("name", input.text.toString())
                    editor.apply()
                    Toast.makeText(context, input.text.toString(), Toast.LENGTH_LONG).show()
                    add!!.add(input.text.toString())
                    //                        removable.remove(phone);
                }
                .setNegativeButton("Отмена", null)
                .create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}