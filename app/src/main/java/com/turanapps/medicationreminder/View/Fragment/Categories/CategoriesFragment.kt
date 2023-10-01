package com.turanapps.medicationreminder.View.Fragment.Categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.turanapps.medicationreminder.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clicked()

    }

    private fun clicked(){
        syrupClicked()
        pillClicked()
    }

    private fun syrupClicked(){
        binding.syrupImageView.setOnClickListener {
            loadFragment(CategoriesFragmentDirections.actionCategoriesFragmentToSyrupFragment())
        }
    }

    private fun pillClicked(){
        binding.pillImageView.setOnClickListener {
            loadFragment(CategoriesFragmentDirections.actionCategoriesFragmentToPillFragment())
        }
    }

    private fun loadFragment(navDirections: NavDirections){
        Navigation.findNavController(requireView()).navigate(navDirections)
    }

}