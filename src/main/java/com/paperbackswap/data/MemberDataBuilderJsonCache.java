package com.paperbackswap.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paperbackswap.exceptions.InvalidMemberDataException;
import com.paperbackswap.exceptions.InvalidResponseException;
import com.paperbackswap.exceptions.ResponseHasErrorsException;
import org.json.JSONObject;

import java.util.Date;

public class MemberDataBuilderJsonCache extends JsonBuilderBase implements MemberDataBuilder {
    @Override
    public MemberData construct(Object source, int statusCode) throws InvalidMemberDataException, InvalidResponseException, ResponseHasErrorsException {
        JSONObject response = validateObject(source);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new IsoDateSerializer()).create();
        MemberData data = gson.fromJson(response.toString(), MemberDataImpl.class);
        return data;
    }
}
