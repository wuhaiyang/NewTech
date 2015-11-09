package cn.andaction.newesttech;

import cn.andaction.newesttech.bean.BoardContent;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * User: Geek_Soledad(wuhaiyang@andthink.cn)
 * Date: 2015-11-09
 * Time: 16:27
 * Description: http://112.74.192.247/api/store/board?region=22&page=1&pageSize=10
 */
public class ApiManager  {

    static final String BASE_URL = "http://112.74.192.247/api";


    public interface ApiManagerService{

        /**
         *  如果方法的返回值不是void  同步请求 ；如果在请求参数中传入一个callBack 那么就是异步请求
         * @param region
         * @param page
         * @param pageSize
         * @param callback
         */
        @GET("/store/board")
        void getBoardContents(@Query("region") String region,@Query("page") int page,@Query("pageSize") int pageSize, Callback<BoardContent> callback);
    }
    private static final RestAdapter mRestAdapter = new RestAdapter.Builder().setEndpoint(BASE_URL).build();
    public static ApiManagerService getBoardClient(){
        return mRestAdapter.create(ApiManagerService.class);
    }




}