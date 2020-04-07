package com.example.android.gymondoautomationtest


import android.content.Context
import android.net.wifi.WifiManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    //Login using valid credentials
    fun logInWithValidCredentials() {

        onView(withId(R.id.editText)).perform(typeText("automation@gymondo.de"))
        onView(withId(R.id.editText2)).perform(typeText("automation"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.btnClear)).check(matches(isDisplayed()))

    }
    @Test
    // Perform login using no credentials
    fun logInWithNoCredentials() {
        onView(withId(R.id.button)).perform(click())
        onView(withText("Username and/or password incorrect"))
            .inRoot(withDecorView(not(activityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))

    }

    @Test
    // Perform login using e-mail only
    fun logInWithEmailOnly() {
        onView(withId(R.id.editText)).perform(typeText("automation@gymondo.de"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        onView(withText("Username and/or password incorrect"))
            .inRoot(withDecorView(not(activityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    @Test
    // Perform login using password only
    fun logInWithPasswordOnly() {
        onView(withId(R.id.editText2)).perform(typeText("automation"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        onView(withText("Username and/or password incorrect"))
            .inRoot(withDecorView(not(activityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    @Test
    // Perform login using valid email (existing user) and invalid password
    fun logInWithValidEmailInvalidPW() {
        onView(withId(R.id.editText)).perform(typeText("automation@gymondo.de"))
        onView(withId(R.id.editText2)).perform(typeText("fakepassword"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        onView(withText("Username and/or password incorrect"))
            .inRoot(withDecorView(not(activityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    @Test
    // Perform login using invalid email format and valid password
    fun logInWithInvalidEmailValidPW() {
        onView(withId(R.id.editText)).perform(typeText("automation@"))
        onView(withId(R.id.editText2)).perform(typeText("automation"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        onView(withText("Username and/or password incorrect"))
            .inRoot(withDecorView(not(activityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))

    }

    @Test
    // Check that e-mail and password fields have expected hint
    fun checkEmailPassFields() {
        onView(withId(R.id.editText)).check(matches(withHint("Enter e-mail here")))
        onView(withId(R.id.editText2)).check(matches(withHint("Enter password here")))
    }


    /*@Test
    // Perform login while having no internet connection
    fun noInternetLogIn() {
        val context = activityRule.activity.applicationContext
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = false
    }*/
}