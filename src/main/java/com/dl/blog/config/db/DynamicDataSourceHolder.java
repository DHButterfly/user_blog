package com.dl.blog.config.db;

/**
 * 实现数据源A和B的切换，数据源标识保存在线程变量中，避免多线程操作数据源时互相干扰。
 */
public class DynamicDataSourceHolder {
    private static ThreadLocal<String> holderDataSource = new ThreadLocal<>();

    public static void setDataSource(String dataSource) {
        holderDataSource.set(dataSource);
    }

    public static String getDataSource() {
        return holderDataSource.get();
    }

    public static void clear() {
        holderDataSource.remove();
    }

}

