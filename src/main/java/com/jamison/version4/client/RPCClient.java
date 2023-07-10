package com.jamison.version4.client;


import com.jamison.version4.common.RPCRequest;
import com.jamison.version4.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
