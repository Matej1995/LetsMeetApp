package cz.sandera.letsmeet.data.dataStore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import cz.sandera.letsmeet.data.dataStore.WeatherPreferences
import java.io.InputStream
import java.io.OutputStream

object WeatherPreferencesSerializer : Serializer<WeatherPreferences> {

    override val defaultValue: WeatherPreferences = WeatherPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): WeatherPreferences {
        try {
            return WeatherPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: WeatherPreferences, output: OutputStream) = t.writeTo(output)

}