package wiki.scene.lib_network.api2;


import wiki.scene.lib_network.bean.ApiResponse;

public interface NetCallback<T extends ApiResponse> {

    void onSuccess(T response);

    void onFail(String msg);
}
