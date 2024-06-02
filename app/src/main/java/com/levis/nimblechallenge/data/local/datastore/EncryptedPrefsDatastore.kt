package com.levis.nimblechallenge.data.local.datastore

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val APP_KEY_PREFS = "app_key_prefs"

interface LocalDataSource {
    fun isLogin(): Boolean
    fun saveAccessToken(token: String)
    fun getAccessToken(): String
    fun removeAccessToken()
    fun saveRefreshToken(refreshToken: String)
    fun getRefreshToken(): String
    fun removeRefreshToken()
    fun saveTokenType(tokenType: String)
    fun getTokenType(): String
    fun removeTokenType()
    fun clearAll()
}

class EncryptedSharedPreferences @Inject constructor(
    @ApplicationContext applicationContext: Context
) : LocalDataSource {
    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_TOKEN_TYPE = "token_type"
        const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    private var sharedPreferences: SharedPreferences

    init {
        val masterKey = MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences = EncryptedSharedPreferences.create(
            applicationContext,
            APP_KEY_PREFS,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun isLogin(): Boolean = sharedPreferences
        .getString(KEY_TOKEN, null)
        .isNullOrBlank()
        .not()

    override fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    override fun getAccessToken(): String = sharedPreferences
        .getString(KEY_TOKEN, null)
        .orEmpty()

    override fun removeAccessToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply()
    }

    override fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, refreshToken).apply()
    }

    override fun getRefreshToken(): String = sharedPreferences
        .getString(KEY_REFRESH_TOKEN, null)
        .orEmpty()

    override fun removeRefreshToken() {
        sharedPreferences.edit().remove(KEY_REFRESH_TOKEN).apply()
    }

    override fun saveTokenType(tokenType: String) {
        sharedPreferences.edit().putString(KEY_TOKEN_TYPE, tokenType).apply()
    }

    override fun getTokenType(): String = sharedPreferences
        .getString(KEY_TOKEN_TYPE, null)
        .orEmpty()

    override fun removeTokenType() {
        sharedPreferences.edit().remove(KEY_TOKEN_TYPE).apply()
    }

    override fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}