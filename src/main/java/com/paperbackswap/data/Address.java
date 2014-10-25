package com.paperbackswap.data;

public interface Address {
//    <?xml version='1.0' encoding='UTF-8' standalone='yes' ?> <Response>
//    <APIVersion>1.0</APIVersion> <RequestType>AlternateAddresses</RequestType>
//      <ResponseID>MkVSeGZpTmp5VU09</ResponseID>
//     <Address ID="b1RoRWhRWFNWSU09">
//    <Name>Tom Sawyer</Name> <CareOf></CareOf>
//    <Street>456 SYCAMORE RD.</Street> <City>ST PETERSBURG</City> <State>MO</State> <Zip>63376-1234</Zip>
//    </Address>
//    </Response>
    public String getName();
    public String getStreet();
    public String getCity();
    public String getState();
    public String getZip();

}
