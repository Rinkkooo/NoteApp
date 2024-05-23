package com.example.noteapp.ui.fragments.note

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailNoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private var currentColor: Int = Color.BLACK
    private var noteId: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        update()
        setupListener()
        getCurrentDateAndTime()
        setupRadioGroupListener()
    }

    private fun update() {
        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
        if (noteId != -1){
            var args = App().getInstance()?.noteDao()?.getNoteById(noteId)
            args?.let { model ->
                binding.etDetailTitle.setText(model.title)
                binding.etDetailDescription.setText(model.title)
            }
        }
    }

    private fun setupListener() {
        binding.tvReady.setOnClickListener {
            val etTitle = binding.etDetailTitle.text.toString()
            val etDescription = binding.etDetailDescription.text.toString()
            val currentDate = binding.tvDate.text.toString()
            val currentTime = binding.tvTime.text.toString()
            val newNote = NoteModel(etTitle, etDescription, currentDate, currentTime, currentColor)
            App().getInstance()?.noteDao()?.insertNote(newNote)

            val bundle = Bundle().apply {
                putInt("selectedColor", currentColor)
            }
            findNavController().navigate(R.id.action_detailNoteFragment_to_noteFragment, bundle)

            if (noteId != -1){
                val updateNote = NoteModel(etTitle, etDescription, currentDate, currentTime, currentColor)
                updateNote.id = noteId
                App().getInstance()?.noteDao()?.updateNote(updateNote)
            }else{
                App().getInstance()?.noteDao()?.insertNote(NoteModel(etTitle, etDescription, currentDate, currentTime, currentColor))
            }
        }
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getCurrentDateAndTime() {
        val currentDate = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date())
        binding.tvDate.text = currentDate

        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        binding.tvTime.text = currentTime
    }

    private fun setupRadioGroupListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            currentColor = when (checkedId) {
                R.id.radio_black_btn -> Color.parseColor("#191818")
                R.id.radio_beije_btn -> Color.parseColor("#EBE4C9")
                R.id.radio_red_btn -> Color.parseColor("#571818")
                else -> Color.BLACK
            }
            binding.root.setBackgroundColor(currentColor)
        }
    }
}

