package rashakacom.rashaka.utils.helpers.pagination;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by User on 14.09.2017.
 */

public interface PagingListener<T> {
    Observable<List<T>> onNextPage(int offset);
}