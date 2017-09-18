package com.thedevelopercat.whatsappstatussave.Callbacks;

import java.io.File;

/**
 * Created by Ariq on 15-09-2017.
 */

public interface WhatsAppFolderTaskCallback
{
    void whatAppFolderExist(boolean exists, File statusFolder);
}
