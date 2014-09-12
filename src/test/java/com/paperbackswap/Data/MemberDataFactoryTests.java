package com.paperbackswap.Data;

import com.paperbackswap.Test.TestDataLoader;
import com.paperbackswap.data.MemberData;
import com.paperbackswap.data.MemberDataFactory;
import com.paperbackswap.exceptions.InvalidMemberDataException;
import com.paperbackswap.exceptions.InvalidResponseException;
import com.paperbackswap.exceptions.ResponseHasErrorsException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MemberDataFactoryTests {

    @Test
    public void creates_member_data() throws IOException, InvalidMemberDataException, InvalidResponseException, ResponseHasErrorsException {
        final String testMemberData = "test_member_data.json";
        JSONObject testMember = TestDataLoader.loadTestFileToJson(testMemberData);
        assertNotNull(testMember);

        MemberData memberData = MemberDataFactory.getMemberData(testMember);
        assertEquals("Krendi Harmon", memberData.getName());
        assertEquals("", memberData.getCareOf());
        assertEquals("BRIARCLIFF", memberData.getCity());
        assertEquals("TX", memberData.getState());
        assertEquals("22208 SHOTTS DR", memberData.getStreetAddress());
        assertEquals("78669-2335", memberData.getZipCode());
        assertTrue(memberData.getCredits() == 1);
        assertEquals("9.01", memberData.getPbsMoney());
    }
}
