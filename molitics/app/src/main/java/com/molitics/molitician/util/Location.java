package com.molitics.molitician.util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Location {

    public static String stateListJson = "{\"state\":[\n" +
            "\n" +
            "    {\n" +
            "        \"key\":\"Andaman and Nicobar\",\n" +
            "        \"value\":30\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Andhra Pradesh\",\n" +
            "        \"value\":1\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Arunachal Pradesh\",\n" +
            "        \"value\":2\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Assam\",\n" +
            "        \"value\":3\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Bihar\",\n" +
            "        \"value\":4\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Chandigarh\",\n" +
            "        \"value\":36\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Chhattisgarh\",\n" +
            "        \"value\":5\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Dadra and Nagar Haveli\",\n" +
            "        \"value\":31\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Daman and Diu\",\n" +
            "        \"value\":32\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Delhi\",\n" +
            "        \"value\":29\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Goa\",\n" +
            "        \"value\":6\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Gujarat\",\n" +
            "        \"value\":7\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Haryana\",\n" +
            "        \"value\":8\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Himachal Pradesh\",\n" +
            "        \"value\":9\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Jammu and Kashmir\",\n" +
            "        \"value\":10\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Jharkhand\",\n" +
            "        \"value\":11\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Karnataka\",\n" +
            "        \"value\":12\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Kerala\",\n" +
            "        \"value\":13\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Lakshadweep\",\n" +
            "        \"value\":33\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Madhya Pradesh\",\n" +
            "        \"value\":14\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Maharashtra\",\n" +
            "        \"value\":15\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Manipur\",\n" +
            "        \"value\":16\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Meghalaya\",\n" +
            "        \"value\":17\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Mizoram\",\n" +
            "        \"value\":18\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Nagaland\",\n" +
            "        \"value\":19\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Odisha\",\n" +
            "        \"value\":20\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Puducherry\",\n" +
            "        \"value\":34\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Punjab\",\n" +
            "        \"value\":21\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Rajasthan\",\n" +
            "        \"value\":22\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Sikkim\",\n" +
            "        \"value\":23\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Tamil Nadu\",\n" +
            "        \"value\":24\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Telangana\",\n" +
            "        \"value\":35\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Tripura\",\n" +
            "        \"value\":25\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Uttar Pradesh\",\n" +
            "        \"value\":26\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"Uttarakhand\",\n" +
            "        \"value\":27\n" +
            "    },\n" +
            "    {\n" +
            "        \"key\":\"West Bengal\",\n" +
            "        \"value\":28\n" +
            "    }\n" +
            "\n" +
            "]}";

    public static int getStateId(String stateName) throws JSONException {
        JSONObject object = new JSONObject(stateListJson);
        JSONArray sObj = object.getJSONArray("state");
        int value = -1;
        for(int i=0; i<sObj.length();i++){
            JSONObject state = sObj.getJSONObject(i);
            if(state.getString("key").equals(stateName)) {
                value = state.getInt("value");
                return value;
            }
        }
        return value;
    }
}
