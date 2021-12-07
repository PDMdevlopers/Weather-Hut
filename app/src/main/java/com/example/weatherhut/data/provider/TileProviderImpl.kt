package com.example.weatherhut.data.provider

import com.google.android.gms.maps.model.UrlTileProvider
import java.net.URL


val OWM_TILE_URL = "http://tile.openweathermap.org/map/{layer}/{z}/{x}/{y}.png?appid={b03dfc04eacec68a2f8b4bce3f0889d0}"

class TileProviderImpl(private val titleType: String?) : UrlTileProvider(256,256), TileProvider {

    override fun getTileUrl(x: Int, y: Int, zoom: Int): URL? {
        val fUrl =
            String.format(OWM_TILE_URL, titleType ?: "clouds", zoom, x, y)
        var url: URL? = null;
        try {
            url = URL(fUrl);
        }
        catch(mfe: Exception ) {
            mfe.printStackTrace();
        }
        return url;
    }

}