package com.moworks.eqinfo.data.source.remote;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.moworks.eqinfo.pojo.EqModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public abstract  class NetworkService implements Service {

    private  final String TAG = RemoteDataSource.class.getSimpleName();

    private static final String BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?";

    private static final String FORMAT = "format";
    private static final String FORMAT_PARAM = "geojson";

    private static final String LIMIT = "limit";

    private static final String LIMIT_PARAM = "300";

    private static final String MIN_MAG = "minmagnitude";

    private static final String MIN_MAG_PARAM ="1";

    private static final String END_TIME = "endtime";


    @Override
    public abstract List<EqModel> service() throws IOException;

    @Override
    public URL bailUrl() {
        String d = new SimpleDateFormat("yyyy-MM-dd-hh-mm", Locale.getDefault()).format(new Date());

        Uri build = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(FORMAT, FORMAT_PARAM).appendQueryParameter(LIMIT, LIMIT_PARAM)
                .appendQueryParameter(MIN_MAG, MIN_MAG_PARAM).appendQueryParameter(END_TIME, d).build();

        URL url = null;
        try {
            url = new URL(build.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, "builUrl: " + url, e);
        }

        return url;
    }

    @Override
    public String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                in = httpURLConnection.getInputStream();
                if (in != null) {

                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");
                    boolean hasNext = scanner.hasNext();
                    if (hasNext) return scanner.next();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
            if (in != null) in.close();
        }

        return null;
    }


    @Override
    public List<EqModel> parseJsonResponse(String rawJSON) throws JSONException {


        if (TextUtils.isEmpty(rawJSON) || rawJSON == null) {
            return null;
        }

        JSONObject jsonObject;
        List<EqModel> earthquake_info = new ArrayList<>();
        String mapUrl;
        String place;
        long time;
        int mag;

        try {
            jsonObject = new JSONObject(rawJSON);
            JSONArray features = jsonObject.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                JSONObject properties = features.getJSONObject(i).getJSONObject("properties");
                place = properties.getString("place");
                time = properties.getLong("time");
                mag = properties.getInt("mag");
                mapUrl = properties.getString("url");

                earthquake_info.add(new EqModel(place, time, mag, mapUrl));
            }
            return earthquake_info;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "parseJsonResponse", e);
        }
        return null;
    }

}
