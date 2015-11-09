package cn.andaction.newesttech;

import cn.andaction.newesttech.bean.BoardContent;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * User: Geek_Soledad(wuhaiyang@andthink.cn)
 * Date: 2015-11-09
 * Time: 17:44
 * Description:
 */
public interface ApiService {

    @GET("/store/board")
    Observable<BoardContent> getBoardContents(@Query("region") String region,@Query("page") int page,@Query("pageSize") int pageSize);

}