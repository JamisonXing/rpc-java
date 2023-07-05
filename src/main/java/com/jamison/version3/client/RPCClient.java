package com.jamison.version3.client;



import com.jamison.version3.common.RPCRequest;
import com.jamison.version3.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
