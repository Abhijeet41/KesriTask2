package com.abhi41.kesritravelstask2.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.abhi41.kesritravelstask2.local.Subject
import com.google.gson.reflect.TypeToken
import dagger.Provides

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
    // private val jsonParser: GsonParser  //we don't need to this whatever converts use moshi, gson etc
    // private val jsonParser: MoshiParser
) {

    @TypeConverter
    fun fromSubjectJson(json: String): List<Subject> {
        return jsonParser.fromJson<ArrayList<Subject>>(
            json = json,
            type = object : TypeToken<ArrayList<Subject>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toSubjectJson(subjects: List<Subject>): String {
        return jsonParser.toJson(
            subjects,
            type = object : TypeToken<ArrayList<Subject>>() {}.type
        ) ?: "[]"
    }

}