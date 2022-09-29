package com.zhixing.common.dubbo.serialization;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.dubbo.common.serialize.support.SerializationOptimizer;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName SerializationOptimizerImpl
 * Description 序列化
 * @Author 2456910384
 * @Date 2022/9/14 15:40
 * @Version 1.0
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {

    private static final List<Class<?>> classes = new ArrayList<>();

    static {
        ClassPathResource resource = new ClassPathResource("kryo.serial");
        if (resource.exists() && resource.isFile() && resource.isReadable()) {
            try (
                    FileReader fileReader = new FileReader(resource.getFile());
                    BufferedReader reader = new BufferedReader(fileReader);
            ) {
                String className = reader.readLine();
                while (null != className && ObjectUtils.isNotEmpty(className.trim())) {
                    classes.add(
                            Class.forName(className.trim())
                    );
                    className = reader.readLine();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Collection<Class<?>> getSerializableClasses() {
        return classes;
    }
}
