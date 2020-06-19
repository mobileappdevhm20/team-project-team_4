package edu.hm.cs.ma.todoguru.explanation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R

class ExplanationFragmentTwo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.explanation_pages_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_next2).setOnClickListener {
            findNavController().navigate(ExplanationFragmentTwoDirections.actionExplanationFragmentTwoToExplanationFragmentThree())
        }
        view.findViewById<Button>(R.id.button_prev2).setOnClickListener {
            findNavController().navigate(ExplanationFragmentTwoDirections.actionExplanationFragmentTwoToExplanationFragment())
        }
        view.findViewById<Button>(R.id.button_skip2).setOnClickListener {
            findNavController().navigate(ExplanationFragmentTwoDirections.actionExplanationFragmentTwoToTaskListFragment())
        }
    }
}
