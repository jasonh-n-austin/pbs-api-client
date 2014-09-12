package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.paperbackswap.exceptions.InvalidMemberDataException;
import com.paperbackswap.exceptions.InvalidResponseException;
import com.paperbackswap.exceptions.ResponseHasErrorsException;
import com.paperbackswap.modules.MemberDataModule;
import org.json.JSONObject;

public class MemberDataFactory {
    private static Injector mInjector;

    static {
        mInjector = Guice.createInjector(new MemberDataModule());
    }

    public static MemberData getMemberData(JSONObject response) throws InvalidMemberDataException, InvalidResponseException, ResponseHasErrorsException {
        return mInjector.getInstance(MemberDataBuilder.class).construct(response);
    }
}
