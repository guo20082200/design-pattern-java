package com.zishi.spi;

import org.apache.dubbo.common.extension.ExtensionDirector;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ModuleModel;

public class ExtensionLoaderTest {

    public static void main(String[] args) {
        ModuleModel defaultModule = ApplicationModel.defaultModel().getDefaultModule();
        ExtensionLoader<DownloadStrategy> extensionLoader =
                defaultModule.getExtensionLoader(DownloadStrategy.class);
        /*ExtensionLoader<DownloadStrategy> extensionLoader =
                ExtensionLoader.getExtensionLoader(DownloadStrategy.class);*/
        //这里getExtension方法进行加载具体扩展点
        DownloadStrategy downloadStrategy = extensionLoader.getExtension("sftp");
        downloadStrategy.download();
    }
}
