package com.paperbackswap.data;

import com.paperbackswap.Url.PbsUrlBuilder;
import com.paperbackswap.exceptions.InvalidResponseException;

public interface LinkProcessor {
    public PbsUrlBuilder getLinkByRef(Object response, String rel) throws InvalidResponseException;
}
