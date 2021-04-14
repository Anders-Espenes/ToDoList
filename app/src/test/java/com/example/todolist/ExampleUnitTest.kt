package com.example.todolist

import com.example.todolist.data.Task
import com.example.todolist.data.TodoList
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun todoListFormatTest() {
        val temp = mutableListOf(
            Task("Clean bedroom", false),
            Task("Go shopping", false),
            Task("Go for a walk", true),
            Task("Test this app", false)
        )
        val todoList = TodoList("test", temp)
        assertEquals("test\n" +
                "Clean bedroom,false\n" +
                "Go shopping,false\n" +
                "Go for a walk,true\n" +
                "Test this app,false\n", todoList.toString())
    }
}