package com.example.noteapp.ui.fragments.note

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.interfaces.OnClickItem

import com.example.noteapp.ui.adapter.NoteAdapter
import com.google.android.material.navigation.NavigationView

class NoteFragment : Fragment(), OnClickItem {

    private lateinit var binding: FragmentNoteBinding
    private val noteAdapter = NoteAdapter(this, this)
    private var isGridLayout = false
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        drawerLayout = binding.drawerLayout
        navigationView = binding.navView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
        getData()
    }

    private fun initialize() {
        binding.rvNote.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }

    private fun setupListeners() = with(binding) {
        btnPlusNote.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_detailNoteFragment)
        }

        btnChangeLayout.setOnClickListener {
            isGridLayout = !isGridLayout
            val layoutManager = if (isGridLayout) {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }
            binding.rvNote.layoutManager = layoutManager
        }

        menuButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            clickMenuItem(menuItem)
            true
        }
    }

    private fun clickMenuItem(menuItem: MenuItem) {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()

        when (menuItem.itemId) {
            R.id.noteFragment -> findNavController().navigate(R.id.noteFragment)
            R.id.chatFragment -> findNavController().navigate(R.id.chatFragment)
        }
    }

    private fun getData() {
        App().getInstance()?.noteDao()?.getAll()?.observe(viewLifecycleOwner) {
            noteAdapter.submitList(it)
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Вы точно хотите удалить ?")
            setPositiveButton("Да") { _, _ ->
                App().getInstance()?.noteDao()?.deleteNote(noteModel)
            }
            setNegativeButton("Нет") { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }

    override fun onClick(noteModel: NoteModel) {
        val action = NoteFragmentDirections.actionNoteFragmentToDetailNoteFragment(noteModel.id)
        findNavController().navigate(action)
    }
}
