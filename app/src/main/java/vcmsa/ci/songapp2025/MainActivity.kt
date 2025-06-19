package vcmsa.ci.songapp2025





// MainActivity.kt
package com.example.songapp2025

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Parallel arrays for storing song details
    private val songs = ArrayList<String>()
    private val artists = ArrayList<String>()
    private val ratings = ArrayList<Int>()
    private val comments = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button to add song to playlist
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener { showAddSongDialog() }

        // Button to navigate to details screen
        val btnDetails = findViewById<Button>(R.id.btnDetails)
        btnDetails.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            // Pass arrays to next activity
            intent.putStringArrayListExtra("songs", songs)
            intent.putStringArrayListExtra("artists", artists)
            intent.putIntegerArrayListExtra("ratings", ratings)
            intent.putStringArrayListExtra("comments", comments)
            startActivity(intent)
        }

        // Button to exit app
        val btnExit = findViewById<Button>(R.id.btnExit)
        btnExit.setOnClickListener { finishAffinity() }
    }

    // Function to show dialog for adding a song
    private fun showAddSongDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_song, null)
        val songInput = dialogView.findViewById<EditText>(R.id.etSong)
        val artistInput = dialogView.findViewById<EditText>(R.id.etArtist)
        val ratingInput = dialogView.findViewById<EditText>(R.id.etRating)
        val commentInput = dialogView.findViewById<EditText>(R.id.etComment)

        AlertDialog.Builder(this)
            .setTitle("Add Song to Playlist")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val song = songInput.text.toString().trim()
                val artist = artistInput.text.toString().trim()
                val ratingStr = ratingInput.text.toString().trim()
                val comment = commentInput.text.toString().trim()

                // Error handling
                if (song.isEmpty() || artist.isEmpty() || ratingStr.isEmpty()) {
                    Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val rating = try {
                    ratingStr.toInt()
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Rating must be a number.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                if (rating !in 1..5) {
                    Toast.makeText(this, "Rating must be between 1 and 5.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                // Add to arrays
                songs.add(song)
                artists.add(artist)
                ratings.add(rating)
                comments.add(comment)
                Toast.makeText(this, "Song added!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}




