package app.hkm.pinehrm.apis;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.hkm.pinehrm.constants.Constants;
import app.hkm.pinehrm.constants.UrlConstants;
import app.hkm.pinehrm.utils.GlobalUtils;
import app.hkm.pinehrm.utils.SharedPreferencesUtils;


/**
 * Request and get data from API
 *
 * @author PhanTri
 */
public class RequestData {
    private JsonParser mJsonParser = null;
    private String REQUEST_DATA_URL = null;
    private int mRestType = 0;
    private Context mContex = null;

    public RequestData(Context context) {
        mJsonParser = new JsonParser();
        mContex = context;
    }

    /**
     * TODO <br>
     * Function to get data
     *
     * @return json in string
     * @author Phan Tri
     * @date Oct 15, 2014
     */
    @SuppressWarnings("unchecked")
    public String getData(int typeOfRequest, final HashMap<String, Object> parameters) {
        ArrayList<Object> listParams = new ArrayList<Object>();
        ArrayList<NameValuePair> nameValueParams = new ArrayList<NameValuePair>();
        ArrayList<Map.Entry<String, Bitmap>> bitmapParams = new ArrayList<Map.Entry<String, Bitmap>>();
        JSONObject returnJson = null;

        switch (typeOfRequest) {


            case Constants.REQUEST_LOGIN:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.LOGIN_URL;

                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_USERNAME,
                        (String) parameters.get(Constants.PARAM_EMAIL)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PASSWORD,
                        (String) parameters.get(Constants.PARAM_PASSWORD)));

                break;

            case Constants.REQUEST_CHECK_TOKEN:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.VALIDATE_TOKEN_URL;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACCESS_TOKEN,
                        (String) parameters.get(Constants.PARAM_ACCESS_TOKEN)));
                break;

            default:
                break;
        }

        listParams.add(nameValueParams);
        listParams.add(bitmapParams);
        // Building Parameters
        Log.e("Request URL:", REQUEST_DATA_URL);
        returnJson = mJsonParser.getJSONFromUrl(REQUEST_DATA_URL, mRestType, listParams);

        return (returnJson != null) ? returnValues(returnJson) : null;
    }

    /**
     * TODO <br>
     * Function to return values from server after check <br>
     *
     * @param returnObj
     * @return
     * @author Phan Tri
     * @date Oct 15, 2014
     */
    public String returnValues(JSONObject returnObj) {
        return returnObj.toString();
    }
}
