package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Turn {
        NOUGHT,
        CROSS
    }

    private var crossesScore = 0
    private var noughtsScore = 0

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var boardList = mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

        // Initialize scores
        binding.xScoreTV.text = "X: $crossesScore"
        binding.oScoreTV.text = "O: $noughtsScore"
    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View) {
        if (view !is Button)
            return
        addToBoard(view)

        if (checkForVictory(NOUGHT)) {
            result("O Wins!")
        } else if (checkForVictory(CROSS)) {
            result("X Wins!")
        } else if (fullBoard()) {
            result("Draw")
        }
    }

    private fun checkForVictory(s: String): Boolean {
        // Horizontal Victory
        if (match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s))
            return true
        if (match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s))
            return true
        if (match(binding.c1, s) && match(binding.c2, s) && match(binding.c3, s))
            return true

        // Vertical Victory
        if (match(binding.a1, s) && match(binding.b1, s) && match(binding.c1, s))
            return true
        if (match(binding.a2, s) && match(binding.b2, s) && match(binding.c2, s))
            return true
        if (match(binding.a3, s) && match(binding.b3, s) && match(binding.c3, s))
            return true

        // Diagonal Victory
        if (match(binding.a1, s) && match(binding.b2, s) && match(binding.c3, s))
            return true
        if (match(binding.a3, s) && match(binding.b2, s) && match(binding.c1, s))
            return true

        return false
    }

    private fun match(button: Button, symbol: String): Boolean = button.text == symbol

    private fun result(title: String) {
        // Increment scores based on the winner
        if (title == "O Wins!") {
            noughtsScore++
        } else if (title == "X Wins!") {
            crossesScore++
        }

        // Prepare the message to show in the dialog
        val message = "\nX wins: $crossesScore\n\nO wins: $noughtsScore\n"

        // Show the result dialog
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Play Again") { _, _ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()

        // Update the scores on the UI
        binding.xScoreTV.text = "X: $crossesScore"
        binding.oScoreTV.text = "O: $noughtsScore"
    }

    private fun resetBoard() {
        for (button in boardList) {
            button.text = ""
        }

        firstTurn = if (firstTurn == Turn.NOUGHT) Turn.CROSS else Turn.NOUGHT
        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean {
        for (button in boardList) {
            if (button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if (button.text != "")
            return

        if (currentTurn == Turn.NOUGHT) {
            button.text = NOUGHT
            button.setTextColor(resources.getColor(R.color.red, null)) // Set O to red
            currentTurn = Turn.CROSS
        } else if (currentTurn == Turn.CROSS) {
            button.text = CROSS
            button.setTextColor(resources.getColor(R.color.blue, null)) // Set X to blue
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        val turnText = if (currentTurn == Turn.CROSS) "Turn $CROSS" else "Turn $NOUGHT"
        binding.turnTV.text = turnText
    }

    companion object {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }

}
