package com.example.noteapp.ui.fragments.note

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
    private var selectedColor: Int = R.color.black_bck
    private var textColor: Int = R.color.orange_text_color
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
        if (noteId != -1) {
            val args = App().getInstance()?.noteDao()?.getNoteById(noteId)
            args?.let { model ->
                binding.etDetailTitle.setText(model.title)
                binding.etDetailDescription.setText(model.title)
            }
        }
    }

    private fun setupListener() {

        binding.radioBlackBtn.setOnClickListener{
            textColor = R.color.text_color_light_gray
            selectedColor = R.color.black_bck
            binding.tvReady.visibility = View.VISIBLE
        }
        binding.radioBeigeBtn.setOnClickListener{
            textColor = R.color.brown_text_color
            selectedColor = R.color.beige_bck
            binding.tvReady.visibility = View.VISIBLE
        }
        binding.radioRedBtn.setOnClickListener{
            textColor = R.color.orange_text_color
            selectedColor = R.color.red_bck
            binding.tvReady.visibility = View.VISIBLE
        }

        binding.tvReady.setOnClickListener {
            val etTitle = binding.etDetailTitle.text.toString()
            val etDescription = binding.etDetailDescription.text.toString()
            val currentDate = binding.tvDate.text.toString()
            val currentTime = binding.tvTime.text.toString()

            if (noteId != -1) {
                val updateNote =
                    NoteModel(etTitle, etDescription, currentDate, currentTime, textColor, selectedColor)
                updateNote.id = noteId
                App().getInstance()?.noteDao()?.updateNote(updateNote)
            } else {
                App().getInstance()?.noteDao()?.insertNote(
                    NoteModel(
                        etTitle,
                        etDescription,
                        currentDate,
                        currentTime,
                        selectedColor,
                        textColor
                    )
                )
                findNavController().navigateUp()
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
            selectedColor = when (checkedId) {
                R.id.radio_black_btn -> R.color.black_bck
                R.id.radio_beige_btn -> R.color.beige_bck
                R.id.radio_red_btn -> R.color.red_bck
                else -> R.color.black_bck
            }
        }
    }
}

