package cn.edu.upc.util;

/**
 * 保证多线程get的数据源   不冲突
 * @author JingHongLi
 *
 */
public class HandleDataSource {
    public static final ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 绑定当前线程数据源
     * 
     * @param key
     */
    public static void putDataSource(String datasource) {
        holder.set(datasource);
    }

    /**
     * 获取当前线程的数据源
     * 
     * @return
     */
    public static String getDataSource() {
        return holder.get();
    }
}