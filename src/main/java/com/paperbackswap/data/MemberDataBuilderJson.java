package com.paperbackswap.data;

import com.google.inject.Inject;
import com.paperbackswap.exceptions.InvalidMemberDataException;
import com.paperbackswap.exceptions.InvalidResponseException;
import com.paperbackswap.exceptions.ResponseHasErrorsException;
import org.json.JSONObject;

public class MemberDataBuilderJson implements MemberDataBuilder {
    private MemberData memberData;
    private ResponseHandler responseHandler;

    @Inject
    public MemberDataBuilderJson(ResponseHandler responseHandler, MemberData memberData) {
        this.responseHandler = responseHandler;
        this.memberData = memberData;
    }

    @Override
    public MemberData construct(Object response) throws InvalidMemberDataException, InvalidResponseException, ResponseHasErrorsException {
        PbsResponse pbsResponse = responseHandler.construct(response);
        JSONObject responseJson = pbsResponse.getResponse();

        if (responseJson != null) {
            memberData.setCareOf(responseJson.optString("CareOf"));
            memberData.setCity(responseJson.optString("City"));
            memberData.setCredits(responseJson.getInt("Credits"));
            memberData.setName(responseJson.optString("Name"));
            memberData.setStreetAddress(responseJson.optString("Street"));
            memberData.setState(responseJson.optString("State"));
            memberData.setZipCode(responseJson.optString("Zip"));
            memberData.setPbsMoney(responseJson.optString("PBSMoney"));

        } else {
            throw new InvalidMemberDataException();
        }

        return memberData;
    }
}
