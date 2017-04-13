package vn.danhtran.baseproject.serverAPI;

import vn.danhtran.baseproject.serverAPI.apiservice.AuthenticateService;

/**
 * Created by danhtran on 14/03/2017.
 */
public class APIManager {
    private static volatile APIManager apiManager;

    public static APIManager instance() {
        if (apiManager == null) {
            synchronized (APIManager.class) {
                if (apiManager == null) {
                    apiManager = new APIManager();
                }
            }
        }

        return apiManager;
    }

    public AuthenticateService authen() {
        return AuthenticateService.instance();
    }
}
