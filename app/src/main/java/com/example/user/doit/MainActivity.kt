package com.example.user.doit

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.wdullaer.swipeactionadapter.SwipeActionAdapter
import com.wdullaer.swipeactionadapter.SwipeDirection
import kotlinx.android.synthetic.main.activity_main.*

var cal = Calendar


class MainActivity : Activity(), SwipeActionAdapter.SwipeActionListener{
    private lateinit var mAdapter: SwipeActionAdapter
    var dbHelper: DBHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cal.initDB(dbHelper)
        cal.loadEvents()

        val myAdpt = EventAdapter(this, cal.cases)
        Log.d("count", cal.cases.size.toString())
        mAdapter = SwipeActionAdapter(myAdpt)

        mAdapter.setSwipeActionListener(this)
                .setDimBackgrounds(true)
                .setListView(mainList)

        mainList.adapter = mAdapter
        mAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.left)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.left)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.right)
                .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.right)
    }

    override fun onSwipe(positionList: IntArray, directionList: Array<out SwipeDirection>) {
        for (i in 0 until positionList.size) {
            val direction = directionList[i]
            val position = positionList[i]

            when (direction) {
                SwipeDirection.DIRECTION_FAR_LEFT -> {
                    cal.removeEvent(position)
                }
                SwipeDirection.DIRECTION_NORMAL_LEFT -> {}
                SwipeDirection.DIRECTION_FAR_RIGHT -> {}
                SwipeDirection.DIRECTION_NORMAL_RIGHT -> {}
                else -> { }
            }

            mAdapter.notifyDataSetChanged()
        }
    }

    override fun shouldDismiss(position: Int, direction: SwipeDirection?): Boolean {
        return direction == SwipeDirection.DIRECTION_FAR_LEFT
    }

    override fun hasActions(position: Int, direction: SwipeDirection): Boolean {
        if(direction.isLeft) return true
        if(direction.isRight) return true
        return false
    }


    fun onclick(v: View){
        cal.createEvent(this, mAdapter)
    }

}
