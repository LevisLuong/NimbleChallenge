//package com.levis.nimblechallenge.data.network.adapter
//
//import com.google.gson.Gson
//import com.google.gson.JsonElement
//import com.google.gson.TypeAdapter
//import com.google.gson.TypeAdapterFactory
//import com.google.gson.reflect.TypeToken
//import com.google.gson.stream.JsonReader
//import com.google.gson.stream.JsonWriter
//import com.levis.nimblechallenge.data.network.dtos.BaseDataResponse
//import java.lang.reflect.ParameterizedType
//
//class ApiResponseAdapterFactory : TypeAdapterFactory {
//    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
//        val rawType = type.rawType
//        if (rawType != BaseDataResponse::class.java) {
//            return null
//        }
//        val parameterizedType = type.type as ParameterizedType
//        val actualType = parameterizedType.actualTypeArguments[0]
//        val elementAdapter = gson.getAdapter(TypeToken.get(actualType))
//        return ApiResponseTypeAdapter(gson, elementAdapter) as TypeAdapter<T>
//    }
//}
//
//class ApiResponseTypeAdapter<T>(
//    private val gson: TypeAdapter<out Any>,
//    private val elementAdapter: TypeAdapter<T>
//) : TypeAdapter<BaseDataResponse<T>>() {
//    override fun write(out: JsonWriter?, value: BaseDataResponse<T>?) {
//        // Serialize logic here if needed
//    }
//
//    override fun read(`in`: JsonReader?): BaseDataResponse<T> {
//        val jsonElement = gson.fromJson<JsonElement>(`in`, JsonElement::class.java)
//        val jsonObject = jsonElement.asJsonObject
//        val dataElement = jsonObject.get("data")
//
//        return if (dataElement.isJsonArray) {
//            val listType =
//                TypeToken.getParameterized(List::class.java, elementAdapter.javaClass).type
//            val dataList: List<T> = gson.fromJson(dataElement, listType)
//            BaseDataResponse(dataList as T)
//        } else {
//            val data: T = gson.fromJson<T>(dataElement, elementAdapter.javaClass)
//            BaseDataResponse(data)
//        }
//    }
//}