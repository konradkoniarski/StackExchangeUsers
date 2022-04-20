package co.uk.stackexchangeusers.service

import android.content.Context
import co.uk.stackexchangeusers.R

class SettingsService(private val context: Context) {

    fun getUserApiUrl():String{
        return context.getString(R.string.users_api_url)
    }
}