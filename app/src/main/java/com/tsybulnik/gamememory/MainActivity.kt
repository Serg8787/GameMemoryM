package com.tsybulnik.gamememory


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager






class MainActivity : AppCompatActivity() {
    lateinit var mGrid: GridView
    lateinit var mAdapter: GridAdapter
    lateinit var mStepScreen: TextView
    lateinit var mTimeScreen: Chronometer
    var stepCount = 0
    var mCols:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)







        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val images = preference.getString("PictureCollection","animal")
        val intSquares = preference.getString("Наборы полей","6*6")
        mCols = intSquares?.first()!!.digitToInt()
        val mRows = intSquares.substring(intSquares.length - 1).toInt()

        mTimeScreen = findViewById(R.id.chronometr);
        mStepScreen = findViewById(R.id.stepview);

        mGrid = findViewById(R.id.gridView)
        mGrid.setNumColumns(mCols)

        mGrid.setEnabled(true)

        mAdapter = GridAdapter(this, mCols,mRows,images!!)
        mGrid.adapter = mAdapter


        mGrid.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mTimeScreen.start()
                mAdapter.checkOpenCells();

                if (mAdapter.openCell(position)) {
                    stepCount++;
                    mStepScreen.setText(stepCount.toString())
                }
                if (mAdapter.checkGameOver()) {
                    mTimeScreen.stop()
                    ShowGameOver()
                    mAdapter.closeAllCells()
                }
            }
        }

    }
    private fun ShowGameOver() {
        val time = mTimeScreen.text.toString()
        val builder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.dialog_fragment,null)
        val  buttonYes = view.findViewById<Button>(R.id.btDialogYes)
        val  buttonNo = view.findViewById<Button>(R.id.btDialogNO)
        val  score = view.findViewById<TextView>(R.id.tvDialogScore)
        score.text = "Игра закончена за ${stepCount} ходов" + "\n"+ "Время игры: $time "
        val sharedPref = getSharedPreferences("highScore",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
                        putInt("step",stepCount)
                        putString("time", mTimeScreen.text.toString())
                        apply()
                    }

        builder.setView(view)
        buttonYes.setOnClickListener {
            recreate()
            builder.dismiss()
        }
        buttonNo.setOnClickListener {
            finish()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()

    }
    }