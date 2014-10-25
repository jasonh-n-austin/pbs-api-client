package com.paperbackswap.data;

import com.paperbackswap.Url.PbsUrlBuilder;
import com.paperbackswap.exceptions.InvalidResponseException;
import org.json.JSONArray;
import org.json.JSONObject;

public class LinkProcessorJsonCache extends JsonBuilderBase implements LinkProcessor {
    @Override
    public PbsUrlBuilder getLinkByRef(Object response, String rel) throws InvalidResponseException {
        JSONObject responseObject = validateObject(response);

        PbsUrlBuilder output = null;
        if (responseObject.has("_links")) {
            JSONArray links = responseObject.getJSONArray("_links");
            for (int i = 0; i < links.length(); i++) {
                JSONObject link = links.getJSONObject(i);
                if (link.optString("rel").equals(rel)) {
                    output = PbsUrlBuilder.fromUrl(link.getString("href"));
                }
            }
        }
        return output;
    }
}
