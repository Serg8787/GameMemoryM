package com.tsybulnik.gamememory

import android.content.res.Configuration
import android.content.res.Configuration.*
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import kotlinx.coroutines.*


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

        val job = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + job)

        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val images = preference.getString("PictureCollection","animal")
        val intSquares = preference.getString("Наборы полей","6*6")
        mCols = intSquares?.first()!!.digitToInt()
        val mRows = intSquares.substring(intSquares.length - 1).toInt()

        mTimeScreen = findViewById(R.id.chronometr);
        mStepScreen = findViewById(R.id.stepview);
        mStepScreen.text = stepCount.toString()

        mTimeScreen.start()

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
                mAdapter.checkOpenCells();

                if (mAdapter.openCell(position)) {
                    stepCount++;
                    mStepScreen.setText(stepCount.toString())
                }
                uiScope.launch(Dispatchers.Main){
                    delay(2000)

                    //perform second operation
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
        val alertbox: AlertDialog.Builder = AlertDialog.Builder(this)

        // Заголовок и текст
        alertbox.setTitle("Поздравляем!")
        val time = mTimeScreen.text.toString()
        val TextToast =
            "Игра закончена nХодов: " + stepCount.toString().toString() + "nВремя: " + time
        alertbox.setMessage(TextToast)

        // Добавляем кнопку
        alertbox.setNeutralButton(
            "Ok",
            DialogInterface.OnClickListener { arg0, arg1 -> // закрываем текущюю Activity
                finish()
            })
        // показываем окно
        alertbox.show()
    }
    }