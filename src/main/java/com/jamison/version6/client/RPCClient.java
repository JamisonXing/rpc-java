package com.jamison.version6.client;


import com.jamison.version6.common.RPCRequest;
import com.jamison.version6.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
