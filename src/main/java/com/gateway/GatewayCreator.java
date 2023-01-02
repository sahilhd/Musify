package com.gateway;

import androidx.appcompat.app.AppCompatActivity;

/**
 * A Gateway class that create the corresponding Gateway class.
 */

public class GatewayCreator{


    /**
     * Create a specific Gateway based on the input FileType filetype and also construct the new gateway using the
     * filePath string and entityType.
     * @param fileType the type of the input file
     * @param currPageActivity a PageActivity object
     * @return the corresponding type of gateway
     */
    public IGateway createIGateway(FileType fileType, AppCompatActivity currPageActivity) {
        if (fileType == FileType.SER){
            return new SerGateway(currPageActivity);
        }
        return null;
    }
}
