package cn.magikarpll.ticket.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class StreamUtils {

    /**
     * 序列化,List
     */
    public static <T> boolean writeObject(List<T> list, File file)
    {
        T[] array = (T[]) list.toArray();
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            out.writeObject(array);
            out.flush();
            return true;
        }
        catch (IOException e)
        {
            log.error("序列化List失败", e);
            return false;
        }
    }

    /**
     * 反序列化,List
     */
    public static <E> List<E> readObjectForList(File file)
    {
        E[] object;
        try(ObjectInputStream out = new ObjectInputStream(new FileInputStream(file)))
        {
            object = (E[]) out.readObject();
            return Arrays.asList(object);
        }
        catch (IOException e)
        {
            log.error("反序列化List失败",e);
        }
        catch (ClassNotFoundException e)
        {
            log.error("反序列化list失败",e);
        }
        return null;
    }

}
