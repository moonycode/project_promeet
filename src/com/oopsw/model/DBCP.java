package com.oopsw.model;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBCP {
    private static DBCP dbcp;
    
    private DBCP(){		
    }

    private static SqlSessionFactory sessionFactory;

    public static SqlSessionFactory getSqlSessionFactory(){
        if(dbcp == null) 
        	dbcp = new DBCP();

        if(sessionFactory == null){
            String resource = "config/mybatis-Config.xml";
            try{
                InputStream inputStream = Resources.getResourceAsStream(resource);
                sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }catch(IOException e){
                e.printStackTrace();
            }
        }		

        return sessionFactory;
    }

}
