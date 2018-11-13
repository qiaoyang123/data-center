package com.ggj.datacenter.entity.page;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author <a href="mailto:zhongchao@gegejia.com">珞玉</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Data
public class PageResult<T> {

    private int total = 0;

    private List<T> list;

    public static <T> PageResult<T> build(int count, List<T> data) {
        PageResult<T> result = new PageResult<>();
        result.setTotal(count);
        result.setList(data);
        return result;
    }

    public static <T> PageResult<T> build() {
        return build(0, Collections.emptyList());
    }

    public boolean isEmpty() {
        return total == 0;
    }

    public <V> PageResult<V> convert(Callable<List<V>> callable) {

        if(this.total == 0)
        {
            return PageResult.build();
        }

        PageResult<V> pageResult = new PageResult<>();
        pageResult.setTotal(this.total);
        try {
            pageResult.setList(callable.call());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return pageResult;
    }
}
