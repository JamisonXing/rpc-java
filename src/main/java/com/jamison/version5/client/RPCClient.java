package com.jamison.version5.client;


import com.jamison.version5.common.RPCRequest;
import com.jamison.version5.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
