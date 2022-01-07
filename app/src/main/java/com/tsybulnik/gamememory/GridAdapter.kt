package com.tsybulnik.gamememory

import android.content.Context
import android.content.res.Resources
import android.os.SystemClock
import android.system.Os.close
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList
import com.tsybulnik.gamememory.R.drawable


class GridAdapter(val context: Context, val mCols: Int, val mRows: Int, images:String) : BaseAdapter() {

    private enum class Status {
        CELL_OPEN, CELL_CLOSE, CELL_DELETE
    }

    private val arrStatus // состояние ячеек
            : ArrayList<Status> = ArrayList()

    private val arrPict // массив картинок
    : MutableList<String?> = mutableListOf<String?>()

    private val PictureCollection = images // Префикс набора картинок

    private var mRes // Ресурсы приложения
            : Resources = context.resources


    override fun getCount(): Int {
        return mCols * mRows
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
    init {
        makePictArray()
        closeAllCells()
        checkOpenCells()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: ImageView // выводиться у нас будет картинка
//        view = if (convertView == null) ImageView(context) else convertView as ImageView
//        // Получаем идентификатор ресурса для картинки,
//        // которая находится в векторе vecPict на позиции position
//        // Получаем идентификатор ресурса для картинки,
//        // которая находится в векторе vecPict на позиции position

        // if it's not recycled, initialize some attributes
        // if it's not recycled, initialize some attributes

//        imageView.setLayoutParams(AbsListView.LayoutParams(85, 85))


//
        val drawableId =
            mRes.getIdentifier(arrPict.get(position), "drawable", context.getPackageName())

//        view.setImageResource(drawableId)
//        return view
        if (convertView == null)
            view = ImageView(context);

        else
            view = convertView as ImageView
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.width = 140
        params.height = 140
        view.setLayoutParams(params)

        when(arrStatus.get(position)){
            Status.CELL_OPEN -> view.setImageResource(drawableId)
            Status.CELL_CLOSE -> view.setImageResource(R.drawable.close)
            Status.CELL_DELETE ->view.setImageResource(R.drawable.none)
        }
        return view;
    }

    private fun makePictArray() {
        // очищаем вектор
        arrPict.clear()
        // добавляем
        for (i in 0 until (mCols * mRows )/ 2) {
            arrPict.add(PictureCollection + Integer.toString(i))
            arrPict.add(PictureCollection + Integer.toString(i))
        }
        // перемешиваем
        Collections.shuffle(arrPict)
    }
     fun closeAllCells() {
        arrStatus.clear()
        for (i in 0 until mCols * mRows) arrStatus.add(Status.CELL_CLOSE)
    }

    fun checkOpenCells() {
        val first:Int = arrStatus.indexOf(Status.CELL_OPEN)
        val second:Int = arrStatus.lastIndexOf(Status.CELL_OPEN)
        if (first == second)
            return
        if (arrPict[first].equals(arrPict[second])) {
            arrStatus.set(first, Status.CELL_DELETE)
            arrStatus.set(second, Status.CELL_DELETE)
        }
        else {
            arrStatus.set(first, Status.CELL_CLOSE)
            arrStatus.set(second, Status.CELL_CLOSE)
        }
        return
    }


    fun openCell(position: Int): Boolean {
        if (arrStatus[position] === Status.CELL_DELETE || arrStatus[position] === Status.CELL_OPEN) return false
        if (arrStatus[position] !== Status.CELL_DELETE) arrStatus[position] = Status.CELL_OPEN
        notifyDataSetChanged()
        return true
    }

    fun checkGameOver(): Boolean {
        if (arrStatus.indexOf(Status.CELL_CLOSE) < 0)
            return true;
        return false;

    }
    fun checkDelete(): Boolean {
        if (arrStatus.indexOf(Status.CELL_DELETE) == 0)
            return true;
        return false;

    }




}