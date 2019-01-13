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


            case Constants.REQUEST_REGISTER_USER:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.REGISTRATION_URL;

                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_EMAIL,
                        (String) parameters.get(Constants.PARAM_EMAIL)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PASSWORD1,
                        (String) parameters.get(Constants.PARAM_PASSWORD1)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PASSWORD2,
                        (String) parameters.get(Constants.PARAM_PASSWORD2)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PHONE,
                        (String) parameters.get(Constants.PARAM_PHONE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_STORE_NAME,
                        (String) parameters.get(Constants.PARAM_STORE_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_INSTAGRAM,
                        (String) parameters.get(Constants.PARAM_INSTAGRAM)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_SELLER,
                        (String) parameters.get(Constants.PARAM_SELLER)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_BUYER,
                        (String) parameters.get(Constants.PARAM_BUYER)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ADDRESS,
                        (String) parameters.get(Constants.PARAM_ADDRESS)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_CITY,
                        (String) parameters.get(Constants.PARAM_CITY)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_COUNTRY,
                        (String) parameters.get(Constants.PARAM_COUNTRY)));

                if (parameters.containsKey(Constants.PARAM_IMG)) {
                    // create hash map to save avatar bitmap
                    Map.Entry<String, Bitmap> hashIcon = new Map.Entry<String, Bitmap>() {

                        @Override
                        public String getKey() {
                            // TODO Auto-generated method stub
                            return Constants.PARAM_IMG;
                        }

                        @Override
                        public Bitmap getValue() {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_IMG);
                        }

                        @Override
                        public Bitmap setValue(Bitmap object) {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_IMG);
                        }
                    };

                    bitmapParams.add(hashIcon);
                }
                break;

            case Constants.REQUEST_CREATE_SELLER:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.SELLER_CREATE_URL;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_STORE_NAME,
                        (String) parameters.get(Constants.PARAM_STORE_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACC_NAME,
                        (String) parameters.get(Constants.PARAM_ACC_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_BANK_NAME,
                        (String) parameters.get(Constants.PARAM_BANK_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACC_NAME,
                        (String) parameters.get(Constants.PARAM_ACC_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACC_NUMBER,
                        (String) parameters.get(Constants.PARAM_ACC_NUMBER)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PHONE,
                        (String) parameters.get(Constants.PARAM_PHONE)));

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);

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
