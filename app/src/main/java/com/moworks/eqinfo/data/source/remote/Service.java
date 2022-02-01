package com.moworks.eqinfo.data.source.remote;


import com.moworks.eqinfo.pojo.EqModel;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface Service {

    List<EqModel> service () throws IOException;

    URL bailUrl();

    String makeHttpRequest( URL url ) throws IOException;

    List<EqModel> parseJsonResponse(String rawJSON) throws JSONException;

}
