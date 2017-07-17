package vn.danhtran.baseproject.serverAPI.apiconfig;

/**
 * Created by tamhuynh on 1/31/16.
 */
public class APIConfig {

    public static final String terms = "http://www.soev.vn/terms";
    public static final String policies = "http://www.soev.vn/policies";

    public static final String domainAPI = "https://animeloversapp.com:3000/api/";
//    public static final String domainAPI = "http://asquare-dev.ddns.net:3001/v0.1/";

    //Authentication
    public static final String accessTokens = "accessTokens";
    public static final String accessTokensPath = "accessTokens/{accessTokenId}";

    public static final String resetPassword = "user/auth/resetPassword";

    //Post
    public static final String posts = "posts";
    public static final String postsPath = "posts/{postId}";

    //Category
    public static final String categories = "categories";
    public static final String categoriesPath = "categories/{categoryId}";

    //Media
    public static final String mediaIdGet = "media/{mediaId}";
    public static final String mediaDownload = "media/download/{name}";
    public static final String mediaUpload = "media/upload";

    //special link image
    public static final String mediaUrl = domainAPI + "media/download/"; // + image name

    //User
    public static final String users = "users";
    public static final String usersPath = "users/{userId}";

    //Push
    public static final String pushes = "pushes";
    public static final String pushId = "pushes/{pushId}";
}
