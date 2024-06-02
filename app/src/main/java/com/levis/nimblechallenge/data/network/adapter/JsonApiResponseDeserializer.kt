package com.levis.nimblechallenge.data.network.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.levis.nimblechallenge.data.network.dtos.BaseDataResponse
import com.levis.nimblechallenge.data.network.dtos.Meta
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class JsonApiResponseDeserializer<T> :
    JsonDeserializer<BaseDataResponse<T>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BaseDataResponse<T> {
        val jsonObject = json?.asJsonObject
        val dataElement = jsonObject?.get("data")
        val metaElement = jsonObject?.get("meta")

        val dataType = (typeOfT as? ParameterizedType)?.actualTypeArguments?.get(0)
        val meta = context?.deserialize<Meta>(metaElement, Meta::class.java)

        return if (dataElement?.isJsonArray == true) {
            val objectType = (dataType as ParameterizedType).actualTypeArguments.first()
            val dataList: ArrayList<Any> = arrayListOf()
            dataElement.asJsonArray.forEach {
                val dataObject = it?.asJsonObject
                val id = dataObject?.get("id")?.asString ?: ""
                val attributes = dataObject?.getAsJsonObject("attributes")
                val dataJson = attributes?.deepCopy() ?: JsonObject()
                dataJson.addProperty("id", id)
                val data: Any = context?.deserialize<T>(dataJson, objectType)!!
                dataList.add(data)
            }
            BaseDataResponse(dataList as T, meta)
        } else {
            val dataObject = dataElement?.asJsonObject
            val id = dataObject?.get("id")?.asString ?: ""
            val attributes = dataObject?.getAsJsonObject("attributes")
            val dataJson = attributes?.deepCopy() ?: JsonObject()
            dataJson.addProperty("id", id)
            val data: T = context?.deserialize(dataJson, dataType)!!
            BaseDataResponse(data, meta)
        }
    }
}
